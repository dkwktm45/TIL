package com.example.chat.controller;

import com.example.chat.repository.ChatRoomRepository;
import com.example.chat.service.JwtTokenProvider;
import com.example.chat.vo.ChatRoom;
import com.example.chat.vo.LoginInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

	private final ChatRoomRepository chatRoomRepository;

	private final JwtTokenProvider jwtTokenProvider;

	@GetMapping("/room")
	public String rooms() {
		return "room";
	}

	@GetMapping("/rooms")
	@ResponseBody
	public List<ChatRoom> room() {
		return chatRoomRepository.findAllRoom();
	}

	@PostMapping("/room")
	@ResponseBody
	public ChatRoom createRoom(@RequestParam String name) {
		return chatRoomRepository.createChatRoom(name);
	}

	@GetMapping("/room/enter/{roomId}")
	public String roomDetail(Model model, @PathVariable String roomId) {
		model.addAttribute("roomId", roomId);
		return "roomdetail";
	}

	@GetMapping("/room/{roomId}")
	@ResponseBody
	public ChatRoom roomInfo(@PathVariable String roomId) {
		return chatRoomRepository.findRoomById(roomId);
	}

	@GetMapping("/user")
	@ResponseBody
	public LoginInfo getUserInfo() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		return LoginInfo.builder().name(name).token(jwtTokenProvider.generateToken(name)).build();
	}
}