package com.sp.fc.web.test;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.access.SecurityConfig;

import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.format;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebIntegrationTest {

    @LocalServerPort
    int port;

    public URI uri(String path){
        try {
            return new URI(format("http://localhost:+"+ port+ path));
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

}
