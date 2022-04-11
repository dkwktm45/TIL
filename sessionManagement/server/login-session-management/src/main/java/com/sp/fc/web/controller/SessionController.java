package com.sp.fc.web.controller;

import com.sp.fc.user.domain.SpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@Controller
public class SessionController {
    ConcurrentSessionFilter concurrentSessionFilter;
    ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy;
    SessionAuthenticationStrategy strategy;
    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
    @GetMapping("/sessions")
    public String sessions(Model model){
        model.addAttribute("sessionList"
                ,sessionRegistry.getAllPrincipals().stream().map(
                        p -> UserSession.builder()
                                .username(((SpUser)p).getUsername())
                                .sessionInfoList(sessionRegistry.getAllSessions(p,false).stream().map(s->
                                        SessionInfo.builder()
                                                .sessionId(s.getSessionId())
                                                .time(s.getLastRequest())
                                                .build()).collect(Collectors.toList()))
                                .build()).collect(Collectors.toList()));
        return "/sessionList";
    }
    @PostMapping("/session/expire")
    public String expiredSession(@RequestParam String sessionId){
        SessionInformation sessionInformation = sessionRegistry.getSessionInformation(sessionId);
        if (!sessionInformation.isExpired()){
            sessionInformation.expireNow();
        }
        return "redirect:/sessions";
    }
    @GetMapping("/session-expired")
    public String sessionExpired(){
        return "/sessionExpired";
    }

}
