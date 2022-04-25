package com.sp.fc.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.bind.DatatypeConverter;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class JWTSimpleTest {

    private void prinToken(String token){
        String[] tokens = token.split("\\.");
        System.out.println("header: " + new String(Base64.getDecoder().decode(tokens[0])));
        System.out.println("header: " + new String(Base64.getDecoder().decode(tokens[1])));
    }

    @DisplayName("1. jjwt 를 이용한 토큰 테스트")
    @Test
    void test_1(){
        String okta_token = Jwts.builder().addClaims(
                Map.of("name" , "LeeJin","price",3000)
        ).signWith(SignatureAlgorithm.HS256,"LeeJin")
                .compact();
        System.out.println(okta_token);
        prinToken(okta_token);

        Jws<Claims> body = Jwts.parser().setSigningKey("LeeJin").parseClaimsJws(okta_token);
        System.out.println(body);

    }


    @DisplayName("2. java-jwt 를 이용한 토큰 테스트")
    @Test
    void test_2() {

        byte[] SEC_KEY = DatatypeConverter.parseBase64Binary("LeeJin");

        String oath0_token = JWT.create().withClaim("name","LeeJin").withClaim("price",3000)
                .sign(Algorithm.HMAC256(SEC_KEY));

        System.out.println(oath0_token);
        prinToken(oath0_token);

        // java-jwt
        DecodedJWT verifier = JWT.require(Algorithm.HMAC256(SEC_KEY)).build().verify(oath0_token);
        System.out.println(verifier.getClaims());

        // jjwt
        Jws<Claims> body = Jwts.parser().setSigningKey(SEC_KEY).parseClaimsJws(oath0_token);
        System.out.println(body);
    }

    @DisplayName("3. 만료 시간")
    @Test
    void test_3() throws InterruptedException {

        // 3초 동안 유효한 토큰
        String token = JWT.create().withSubject("a1234")
                .withExpiresAt(new Date(System.currentTimeMillis() + 3000)) // 3초 동안 유효한 토큰
                .withNotBefore(new Date(System.currentTimeMillis() + 1000)) // 1초는 지나야 사용 가능
                .sign(Algorithm.HMAC256("JinLee"));

        //Thread.sleep(2000);
        try {
            DecodedJWT verifier = JWT.require(Algorithm.HMAC256("JinLee")).build().verify(token);
            System.out.println(verifier.getClaims());
        }catch (Exception e ){
            System.out.println("유효하지 않은 토큰입니다.");
            DecodedJWT decode = JWT.decode(token);
            System.out.println(decode.getClaims());
        }
    }
}
