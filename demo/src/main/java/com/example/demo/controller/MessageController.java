package com.example.demo.controller;

import com.example.demo.model.redis.ChatMessage;
import com.example.demo.service.ChatMessageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
public class MessageController {
	private final ChatMessageService chatMessageService;

	@PostMapping("/messages-all")
	public List<ChatMessage> messages(@RequestParam("roomId") String roomId) {
		return chatMessageService.findAllMessage(roomId);
	}
}