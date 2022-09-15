package com.example.demo.service;

import com.example.demo.model.redis.ChatMessage;
import com.example.demo.model.redis.ChatRoom;
import com.example.demo.repo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.model.redis.Alarm.AlarmType.DUO;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
	private final ChannelTopic channelTopic;

	private final RedisTemplate redisTemplate;

	private final ChatRoomRepository chatRoomRepository;
	private final NotificationService notificationService;
	private final ChatMessageRepository chatMessageRepository;

	/**
	 * destination정보에서 roomId 추출
	 */
	public String getRoomId(String destination) {
		int lastIndex = destination.lastIndexOf('/');
		if (lastIndex != -1)
			return destination.substring(lastIndex + 1);
		else
			return "";
	}
	/**
	 * 채팅방에 메시지 발송
	 */
	public void sendChatMessage(ChatMessage chatMessage) {
		if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
			chatMessage.setMessage("님이 방에 입장했습니다.");
			chatMessage.setSender(chatMessage.getSender());
		} else if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {
			chatMessage.setMessage("님이 방에서 나갔습니다.");
			chatMessage.setSender(chatMessage.getSender());
		}else if (ChatMessage.MessageType.DUO.equals(chatMessage.getType())){
			chatMessage.setSender(chatMessage.getSender());
		}
		Long userCount =chatRoomRepository.getUserCount(chatMessage.getRoomId());
		chatMessage.setCreatedAt(LocalDateTime.now());
		Set<String> RoomUsers = chatRoomRepository.findRoomById(chatMessage.getRoomId()).getUserList();
		try {
			if (userCount.equals(1L) && RoomUsers.size() == 2){
				notificationService.chatType(RoomUsers.stream()
								.filter(info -> !info.equals(chatMessage.getSender()))
								.collect(Collectors.toList()).get(0)
						,chatMessage.getSender(),chatMessage.getRoomId(),DUO);
			}
		}catch (Exception e){
			log.error("Exception {}", e);
		}
		chatMessageRepository.createChatMessage(chatMessage);
		redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
	}

	public void createFriendChat(String name,String email) {

		ChatRoom chatRoom = ChatRoom.create();
		Set<String> userList = new HashSet<>();
		userList.add(email);
		userList.add(name);
		chatRoom.setUserList(userList);
		chatRoomRepository.save(chatRoom);

	}
}
