package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.model.redis.ChatMessage;
import com.example.demo.model.redis.ChatRoom;
import com.example.demo.repo.ChatRoomRepository;
import com.example.demo.service.BoardParticipantService;
import com.example.demo.service.ChatService;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {
	private final Logger logger = LoggerFactory.getLogger(ChatRoomController.class);
	private final ChatRoomRepository chatRoomRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final BoardParticipantService boardParticipantService;
	private final UserService userService;
	private final ChatService chatService;

	@PostMapping(value = "/my-rooms")
	public ResponseEntity<List<BoardParticipants>> myRoom(@RequestBody Long userId) {
		logger.info("my-room 접근");
		return ResponseEntity.ok(boardParticipantService.myChatRoom(userId));
	}

	@PostMapping(value = "/other-rooms")
	public ResponseEntity<List<Board>> otherRoom(@RequestBody Long userId) {
		logger.info("other-room 접근");
		return ResponseEntity.ok(boardParticipantService.otherChatRoom(userId));
	}

	@PostMapping("/room")
	public Map<String, Object> createRoom(@RequestParam(value = "boardId",required=false) Long boardId
			, @RequestParam("userName") String userName, @RequestParam("userId") Long userId) throws Exception {
		logger.info("room 접근");
		ChatRoom chatRoom = chatRoomRepository.createChatRoom();
		return boardParticipantService.setChat(boardId,chatRoom.getRoomId() ,userId ,userName);
	}
	@PostMapping("/friend-create")
	public void friendCreate(@RequestBody String email){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		chatService.createFriendChat(name,email);
	}

	@MessageMapping("/chat/message")
	public void message(ChatMessage message, @Header("token") String token) {
		String nickname = jwtTokenProvider.getUserNameFromJwt(token);
		message.setSender(nickname);
		message.setCreatedAt(LocalDateTime.now());
		chatService.sendChatMessage(message);
	}
}