package com.example.demo.service;

import com.example.demo.model.redis.ChatMessage;
import com.example.demo.repo.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatMessageService {
	private final ChatMessageRepository chatMessageRepository;

	public List<ChatMessage> findAllMessage(String roomId) {
		List<ChatMessage> chatMessageList = chatMessageRepository.findAllMessage(roomId);
		Collections.sort(chatMessageList,Collections.reverseOrder());
		return chatMessageList;
	}
}
