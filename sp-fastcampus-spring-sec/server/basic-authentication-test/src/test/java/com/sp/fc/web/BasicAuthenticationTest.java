package com.sp.fc.web;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicAuthenticationTest {

    @LocalServerPort
    int port;

    RestTemplate client = new RestTemplate();

    private String greetingUrl(){
        return "http://localhost:" + port+"/greeting";
    }
    @DisplayName("1. 인증 실패")
    @Test
    void test_1(){
        // 1. permitAll를 통해 처음은 접근이 가능하다.
        //String response = client.getForObject(greetingUrl(),String.class);
        //System.out.println(response);

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,()->{
            client.getForObject(greetingUrl(),String.class);
        });

        assertEquals(401, exception.getRawStatusCode());
    }

    @DisplayName("2. 인증 성공")
    @Test
    void test_2(){

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION,"Basic " + Base64.getEncoder().encodeToString(
                "user1:1111".getBytes()
        ));
        HttpEntity entity = new HttpEntity(null , headers);
        ResponseEntity<String> resp = client.exchange(greetingUrl(), HttpMethod.GET,entity,String.class);

        assertEquals("hello world" , resp.getBody());
    }

    @DisplayName("3. 인증 성공2")
    @Test
    void test_3(){
        // 기본적인 Base64 지원해주는 TestRestTemplete
        TestRestTemplate testClient = new TestRestTemplate("user1","1111");
        String resp = testClient.getForObject(greetingUrl(),String.class);
        assertEquals("hello world",resp);
    }

    @DisplayName("4. Post 인증")
    @Test
    void test_4(){
        // 기본적인 Base64 지원해주는 TestRestTemplete
        TestRestTemplate testClient = new TestRestTemplate("user1","1111");
        String resp = testClient.postForObject(greetingUrl(),"leejin",String.class);
        assertEquals("hello leejin",resp);
    }
}
