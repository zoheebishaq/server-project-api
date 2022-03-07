package com.zoheeb.server;

import com.zoheeb.server.enumeration.Status;
import com.zoheeb.server.model.Response;
import com.zoheeb.server.model.Server;
import com.zoheeb.server.service.ServerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1/server")
@RequiredArgsConstructor
public class ServerController {
    private final ServerServiceImpl serverService;
    @GetMapping("/list")
    public ResponseEntity<Response> getServers(){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("server",serverService.list(30)))
                        .message("Servers retrieved")
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/ping/{ipAdress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAdress ) throws IOException {
        Server server = serverService.ping(ipAdress);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("server",server))
                        .message(server.getStatus() == Status.SERVER_UP ? "Ping sucess": "Ping failed")
                        .statusCode(OK.value())
                        .build()
        );
    }
}
