package com.sp.fc.web.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLogin {// loginForm 에대한 로그인 객체

    private String username;
    private String password;
    private String site;
    private boolean rememberme;

}
