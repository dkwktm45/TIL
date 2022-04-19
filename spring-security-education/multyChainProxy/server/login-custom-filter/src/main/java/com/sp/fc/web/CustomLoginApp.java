package com.sp.fc.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.sp.fc.teacher","com.sp.fc.student"})
public class CustomLoginApp {


    public static void main(String[] args) {
        SpringApplication.run(CustomLoginApp.class, args);
    }

}
