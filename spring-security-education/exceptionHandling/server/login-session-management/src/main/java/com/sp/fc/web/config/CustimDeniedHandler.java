package com.sp.fc.web.config;

import com.sp.fc.web.controller.YouCannotAccessUserPage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.parameters.P;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustimDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request
            , HttpServletResponse response
            , AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (accessDeniedException instanceof YouCannotAccessUserPage){
            // 유저 접근권한 에러
            request.getRequestDispatcher("/access-denied").forward(request,response);
        }else{
            // 관리자 접근권한 에러
            request.getRequestDispatcher("/access-denied2").forward(request,response);

        }
    }
}
