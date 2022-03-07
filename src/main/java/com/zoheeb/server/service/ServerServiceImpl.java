package com.zoheeb.server.service;

import com.zoheeb.server.model.Server;
import com.zoheeb.server.repository.ServerDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import static com.zoheeb.server.enumeration.Status.SERVER_DOWN;
import static com.zoheeb.server.enumeration.Status.SERVER_UP;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService  {
    private final ServerDAO serverDAO;

    @Override
    public Server create(Server server) {
        log.info("Saving new server :{}",server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverDAO.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging new server :{}",ipAddress);
        Server server = serverDAO.findByIpAddress(ipAddress);
        InetAddress address =InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? SERVER_UP : SERVER_DOWN);
        serverDAO.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverDAO.findAll(PageRequest.of(0,limit)).toList();
    }

    @Override
    public Server get(long id) {
        log.info("Fetching server by id:{}",id);
        return serverDAO.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Update new server :{}",server.getName());
        return serverDAO.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("deleting server by ID :{}",id);
        serverDAO.deleteById(id);
        return Boolean.TRUE;
    }

    private String setServerImageUrl() {
        String[] imageNames = {"server1.png","server2.png","server3.png","server4.png"};

        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/"+ imageNames[new Random().nextInt(4)]).toUriString();
    }
}
