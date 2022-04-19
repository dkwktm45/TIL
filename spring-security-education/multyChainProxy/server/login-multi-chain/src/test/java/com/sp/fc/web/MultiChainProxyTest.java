package com.sp.fc.web;


import com.fasterxml.jackson.core.JsonProcessingException;

import com.sp.fc.student.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MultiChainProxyTest {

    @LocalServerPort
    int port;

    TestRestTemplate testClient = new TestRestTemplate("choi","1");

    @DisplayName("1. 학생 리스트를 조회한다.")
    @Test
    void test_1() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        //choi:1로 로그인해서 학생 리스트를 내려받는다.
        ResponseEntity<String> response = testClient.exchange("http://localhost:" + port + "/api/teacher/students",
                HttpMethod.GET,null, new ParameterizedTypeReference<String>() {
                });

        System.out.println(response.getBody());

    }

}
