package com.example.demo.controller;

import com.example.demo.model.BoardParticipants;
import com.example.demo.repo.ChatRoomRepository;
import com.example.demo.service.BoardParticipantService;
import com.example.demo.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/participants")
@RequiredArgsConstructor
public class BoardParticipantController {
	private final BoardParticipantService participantService;
	private final NotificationService notificationService;

	@DeleteMapping("/delete")
	public void deleteRoom(@RequestBody Map<String,Long> info){
		List<BoardParticipants> boardParticipants = participantService.deleteRoom(info.get("boardId"),info.get("participantId"));
	}

	@PostMapping("/one")
	public BoardParticipants getOne(@RequestParam("roomId")String roomId,
	                                @RequestParam("userName")String userName){
		notificationService.deleteAlarm(roomId,userName);
		return participantService.findByRoomId(roomId);
	}
}
