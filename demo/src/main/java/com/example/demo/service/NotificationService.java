package com.example.demo.service;

import com.example.demo.model.redis.Alarm;
import com.example.demo.model.redis.Notification;
import com.example.demo.repo.NotifyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
@Slf4j
public class NotificationService {
	private final Map<String, SseEmitter> connectedUser = new ConcurrentHashMap<>();
	private final NotifyRepository notifyRepository;
	public void setUserEmitter(String userName, SseEmitter sseEmitter) {
		log.info("알림 유저 들러옴" + userName);
		if(userName == null){
			throw new RuntimeException("유저 아이디가 null 입니다.");
		}
		connectedUser.put(userName, sseEmitter);
	}

	public void sendMessage(String receiveId, Map<String, Object> result) throws IOException {
		Notification notification = (Notification) result.get("notification");
		Alarm noti = (Alarm) result.get("noti");

		if(connectedUser.containsKey(receiveId)) {
			connectedUser.get(receiveId).send(noti, MediaType.APPLICATION_JSON);
			notifyRepository.saveNoti(notification);
		}else{
			notifyRepository.saveNoti(notification);
		}
	}
	public Set<Alarm> findAllNoti(String name){
		Optional<Notification> notification = notifyRepository.findByReceive(name);
		if(notification.isEmpty()){
			return null;
		}else{
			return notification.get().getAlarmList();
		}
	}

	public void chatType(String receiveId, String sender , String roomId , Alarm.AlarmType type) throws IOException {
		Set<Alarm> alarms = null;
		Optional<Notification> notificationOptional = notifyRepository.findByReceive(receiveId);
		Notification notification =null;
		Alarm noti = Alarm.builder().alarmType(type).senderId(sender).senderName(sender).id(roomId).build();

		if(notificationOptional.isEmpty()){
			alarms = new HashSet<>();
			alarms.add(noti);
			notification = Notification.builder().receiveId(receiveId).alarmList(alarms).build();
		}else{
			alarms = notificationOptional.get().getAlarmList();
			alarms.add(noti);
			notificationOptional.get().setAlarmList(alarms);
			notification = notificationOptional.get();
		}
		Map<String, Object> result = new HashMap<>();
		result.put("notification",notification);
		result.put("noti",noti);

		sendMessage(receiveId, result);
	}

	public void agreeTyp(String sender, String receiveId, Alarm.AlarmType type){
		Set<Alarm> alarms = null;
		Notification notification =null;

		Optional<Notification> notificationOptional = notifyRepository.findByReceive(receiveId);

		Alarm noti = Alarm.builder().alarmType(type).id(UUID.randomUUID().toString())
				.senderId(sender).senderName(sender).build();

		if(notificationOptional.isEmpty()){
			alarms = new HashSet<>();
			alarms.add(noti);
			notification = Notification.builder().receiveId(receiveId).alarmList(alarms).build();
		}else{
			alarms = notificationOptional.get().getAlarmList();
			alarms.add(noti);
			notificationOptional.get().setAlarmList(alarms);
			notification = notificationOptional.get();
		}
		Map<String, Object> result = new HashMap<>();
		result.put("notification",notification);
		result.put("noti",noti);

		try{
			sendMessage(receiveId, result);
		}catch (Exception e){
			throw new RuntimeException();
		}
	};
	public void deleteAlarm(String roomId,String userName){
		notifyRepository.deleteAlarm(roomId,userName);
	}

	public void removeEmitter(String userId) {
		connectedUser.remove(userId);
	}
}
