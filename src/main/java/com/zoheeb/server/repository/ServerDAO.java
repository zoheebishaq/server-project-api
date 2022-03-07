package com.zoheeb.server.repository;

import com.zoheeb.server.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerDAO extends JpaRepository<Server,Long> {

    Server findByIpAddress(String ipAddress);

}
