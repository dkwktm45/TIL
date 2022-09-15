package com.example.demo.controller;

import com.example.demo.model.redis.Alarm;
import com.example.demo.service.FriendService;
import com.example.demo.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {
	private final Logger logger = LoggerFactory.getLogger(FriendController.class);
	private final FriendService friendService;
	private final NotificationService notificationService;

	@PutMapping("/agree")
	public void friendAgree(@RequestParam("name") String name
			,@RequestParam("roomId") String roomId){
		logger.info("plus start");
		friendService.friendYn(name,roomId);
	}

	@PutMapping("/plus")
	public void friendFlus(@RequestBody Alarm alarm){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		friendService.friendFlus(name,alarm.getId());
//		notificationService.deleteAlarm(roomId,userName);
		logger.info("알림 데이터 : {}",alarm);
	}
}
