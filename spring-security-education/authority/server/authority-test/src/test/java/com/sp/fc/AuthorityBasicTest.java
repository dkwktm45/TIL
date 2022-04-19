package com.sp.fc;

import com.sp.fc.web.AuthorityTestApplication;
import com.sp.fc.web.controller.HomeController;
import com.sp.fc.web.test.WebIntegrationTest;
import org.apache.coyote.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.format;

@SpringBootTest(classes = {AuthorityTestApplication.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorityBasicTest extends WebIntegrationTest {

    TestRestTemplate client;


    @DisplayName("1. greeting 메세지 불러오기")
    @Test
    void test1(){
        client = new TestRestTemplate("user1","1111");
        ResponseEntity<String> respo = client.getForEntity(uri("/greeting/jinyoung"),String.class);

        System.out.println(respo.getBody());

    }
}
