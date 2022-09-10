package com.example.stomp.controller;

import com.example.stomp.dto.Alarm;
import com.example.stomp.service.AlarmUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;



@RequiredArgsConstructor
@Controller
public class NotificationController{

	private final SimpMessageSendingOperations messagingTemplate;

	// stomp 테스트 화면
	@GetMapping("/alarm/stomp")
	public String stompAlarm() {
		return "/stomp";
	}
	// 구독
	@MessageMapping("/{userId}")
	public void message(@DestinationVariable("userId") Long userId) {
		messagingTemplate.convertAndSend("/sub/" + userId, "alarm socket connection completed.");
	}
}