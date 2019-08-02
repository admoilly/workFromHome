package com.ubs.gcmm.devops.mavenapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.io.IOException;
import java.net.ServerSocket;

@SpringBootApplication
@EnableDiscoveryClient
public class MavenApiApplication {
    public static void main(String[] args) {
        /*System.setProperty("server.port",String.valueOf(findFreePort()));*/
        SpringApplication.run(MavenApiApplication.class, args);
    }
    private static int findFreePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            int port = socket.getLocalPort();
            try {
                socket.close();
            } catch (IOException ignored) {
            }
            return port;
        } catch (IOException ignored) {
        }
        throw new IllegalStateException("Could not find a free TCP/IP port to start");
    }
}
