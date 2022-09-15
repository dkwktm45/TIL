package com.example.demo.service;

import com.example.demo.model.Friend;
import com.example.demo.model.User;
import com.example.demo.repo.ChatRoomRepository;
import com.example.demo.repo.FriendRepository;
import com.example.demo.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.demo.model.redis.Alarm.AlarmType.AGREE;
import static com.example.demo.model.redis.Alarm.AlarmType.MESSAGE;

@Service
@RequiredArgsConstructor
public class FriendService {
	private final Logger logger = LoggerFactory.getLogger(FriendService.class);

	private final FriendRepository friendRepository;
	private final ChatRoomRepository roomRepository;
	private final UserRepository userRepository;
	private final NotificationService notificationService;

	public void friendFlus(String senderName, String receveName) {
		logger.info("friendFlus start");

		User recevUser = userRepository.findByUserName(receveName);

		User sendUser = userRepository.findByUserName(senderName);
		friendRepository.save(Friend.builder().user(recevUser)
				.priendUuid(UUID.randomUUID().toString())
				.priendEmail(sendUser.getUserName()).build());
		friendRepository.save(Friend.builder().user(sendUser)
				.priendUuid(UUID.randomUUID().toString())
				.priendEmail(recevUser.getUserName()).build());

		notificationService.agreeTyp(sendUser.getUserName(),recevUser.getUserName(),MESSAGE);
		logger.info("friendFlus end");
	}

	public void friendYn(String name, String roomId) {
		logger.info("friendYn start");

		User user = userRepository.findByUserName(name);
		String friendName = roomRepository.findRoomById(roomId).getUserList().stream().filter(info -> !info.equals(name)).collect(Collectors.toList()).get(0);
		notificationService.agreeTyp(user.getUserName(),friendName,AGREE);
		logger.info("friendYn end");

	}
}
