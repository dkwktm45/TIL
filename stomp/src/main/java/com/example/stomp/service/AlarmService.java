package com.example.stomp.service;

import com.example.stomp.dto.Alarm;
import com.example.stomp.repository.AlarmRepo;
import com.example.stomp.repository.AlarmUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmService {
	private final ApplicationEventPublisher eventPublisher;
	private final AlarmRepo alarmRepo;
	private final AlarmUserRepo userRepo;

	public void updateCount(Long id) {
		com.example.stomp.dto.AlarmUser alarmUser =userRepo.findById(id).orElseThrow();
		if(alarmUser.getAlarms().size() == 0){
			Alarm alarm = Alarm.builder().count(1).countId(1L).alarmUser(alarmUser).build();
			alarmRepo.save(alarm);
			eventPublisher.publishEvent(alarm);
		}else{
			Alarm alarm = alarmUser.getAlarms().get(0);
			alarm.setCount(alarm.getCount()+1);

			alarmRepo.save(alarm);
			eventPublisher.publishEvent(alarm);
		}
	}
}
