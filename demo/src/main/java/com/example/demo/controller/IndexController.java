package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class IndexController {
	private final Logger logger = LoggerFactory.getLogger(IndexController.class);
	private final JwtTokenProvider jwtTokenProvider;
	private final UserService userService;

	@PostMapping("/loginInsert")
	public User vue() {
		logger.info("loginInsert");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		return userService.saveUser(name);
	}
	@GetMapping("/user")
	public User getUserInfo() {
		logger.info("user token 발급");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		return User.builder().token(jwtTokenProvider.generateToken(name)).build();
	}
}
