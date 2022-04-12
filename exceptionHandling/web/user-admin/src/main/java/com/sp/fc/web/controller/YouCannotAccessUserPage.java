package com.sp.fc.web.controller;

import org.springframework.security.access.AccessDeniedException;

public class YouCannotAccessUserPage extends AccessDeniedException {

    public YouCannotAccessUserPage() {
        super("해당 페이지 접근거부");
    }
}
