package com.sp.fc.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.fc.web.student.Student;
import net.minidev.json.JSONObject;
import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MultiChainProxyTest {

    @LocalServerPort
    int port;

    RestTemplate restTemplate = new RestTemplate();

    @DisplayName("1. ํ์์กฐ์ฌ")
    @Test
    void test_1() throws JsonProcessingException {
        //restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        String url = format("http://localhost:%d/api/teacher/students",port);

        HttpHeaders httpHeaders = new HttpHeaders();
        // choie์ ๋ํ ์ธ์ฆ์ ๋ฐ๋ ์ฝ๋
        httpHeaders.add(HttpHeaders.AUTHORIZATION,"Basic"+ Base64.getEncoder().encodeToString(
                "choi:1".getBytes()
        ));

        // ๋ณํ์ด ์ ์๋? ๊ฒฝ์ฐ๊ฐ ์๊ธฐ ๋๋ฌธ์ Base64ํ์์ ๊ฐ์?๋ก String์ผ๋ก ๋ณํ ์ฝ๋
        HttpEntity<String> entity = new HttpEntity<>("",httpHeaders);


        ResponseEntity<String> resp = restTemplate.exchange(
                url
                ,HttpMethod.GET
                ,entity
                ,String.class
        );
        List<Student> list = new ObjectMapper().readValue(resp.getBody(),
                new TypeReference<List<Student>>() {
                });
        System.out.println(list);
        assertEquals(3, list.size());


    }

}
