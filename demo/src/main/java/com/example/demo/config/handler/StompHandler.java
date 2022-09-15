package com.example.demo.config.handler;

import com.example.demo.model.redis.ChatMessage;
import com.example.demo.model.redis.ChatRoom;
import com.example.demo.repo.ChatRoomRepository;
import com.example.demo.service.ChatService;
import com.example.demo.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

	private final JwtTokenProvider jwtTokenProvider;
	private final ChatService chatService;
	private final ChatRoomRepository chatRoomRepository;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		if (StompCommand.CONNECT == accessor.getCommand()) { // websocket 연결요청
			String jwtToken = accessor.getFirstNativeHeader("token");
			log.info("CONNECT {}", jwtToken);
			jwtTokenProvider.validateToken(jwtToken);

		} else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {

			// 채팅룸 구독요청
			String roomId = chatService.getRoomId(Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId"));
			String name = Optional.ofNullable((Principal) message.getHeaders().get("simpUser")).map(Principal::getName).orElse("UnknownUser");
			ChatRoom chatRoom = chatRoomRepository.findRoomById(roomId);
			if(!chatRoom.getUserList().contains(name) && chatRoom.getUserList().size() < 3){

				// 클라이언트 입장 메시지를 채팅방에 발송한다.(redis publish)
				log.info("userCount 및 userList 진입");
				chatRoomRepository.plusUserCount(roomId);
				chatRoom.getUserList().add(name);
				chatRoomRepository.updateChatRoom(chatRoom);
				chatService.sendChatMessage(ChatMessage.builder().type(ChatMessage.MessageType.ENTER).createdAt(LocalDateTime.now()).roomId(roomId).sender(name).build());
				log.info("SUBSCRIBED {}, {}", name, roomId);
			}else if(chatRoom.getUserList().contains(name) && chatRoomRepository.getUserCount(roomId) <= 3L){
				chatRoomRepository.plusUserCount(roomId);
			}
		}else if (StompCommand.DISCONNECT == accessor.getCommand()) { // Websocket 연결 종료
			// 연결이 종료된 클라이언트 sesssionId로 채팅방 id를 얻는다.
			Optional<String> roomId = Optional.ofNullable(accessor.getFirstNativeHeader("roomId"));
			if(!roomId.isEmpty()){
				String name = Optional.ofNullable((Principal) message.getHeaders().get("simpUser")).map(Principal::getName).orElse("UnknownUser");
				// 채팅방의 인원수를 -1한다.
				chatRoomRepository.minusUserCount(roomId.get());
				// 클라이언트 퇴장 메시지를 채팅방에 발송한다.(redis publish)
				// 퇴장한 클라이언트의 roomId 맵핑 정보를 삭제한다.
				log.info("DISCONNECTED {}, {}", name, roomId);
			}
		}
		return message;
	}
}