package com.example.demo.repo;

import com.example.demo.model.redis.Alarm;
import com.example.demo.model.redis.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NotifyRepository {
	// Redis
	private static final String USER_NOTIFIED = "NOTIFIED";
	@Resource(name = "redisTemplate")
	private HashOperations<String, String, Notification> opsHashNotification;
	private final RedisTemplate<String, Object> redisTemplate;

	@PostConstruct
	private void init() {
		opsHashNotification = redisTemplate.opsForHash();
	}

	public void saveNoti(Notification notification) {
		opsHashNotification.put(USER_NOTIFIED, notification.getReceiveId(), notification);
	}

	public Optional<Notification> findByReceive(@RequestBody String receiveId) {
		return Optional.ofNullable(opsHashNotification.get(USER_NOTIFIED,receiveId));
	}

	public void deleteAlarm(String roomId, String userName) {
		Notification notification = opsHashNotification.get(USER_NOTIFIED,userName);
		Set<Alarm> alarms =notification.getAlarmList().stream().filter(info -> !info.getId().equals(roomId)).collect(Collectors.toSet());
		notification.setAlarmList(alarms);
		opsHashNotification.put(USER_NOTIFIED, notification.getReceiveId(), notification);
	}
}