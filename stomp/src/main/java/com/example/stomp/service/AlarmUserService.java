package com.example.stomp.service;

import com.example.stomp.repository.AlarmUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlarmUserService {

	private final ApplicationEventPublisher eventPublisher;
	private final AlarmUserRepo repo;
	public void CreateUser(Long id, String name, String password) {
		com.example.stomp.dto.AlarmUser alarmUser = new com.example.stomp.dto.AlarmUser(id, name, password);
		if(repo.findById(id).isEmpty()){
			repo.save(alarmUser);
		}
	}
	public com.example.stomp.dto.AlarmUser UpdateUser(Long id) {
		com.example.stomp.dto.AlarmUser alarmUser = repo.getById(id);
		alarmUser.setContent("바꾸기 성공");
		repo.save(alarmUser);
		return alarmUser;
	}

	public List<com.example.stomp.dto.AlarmUser> findALl(Long id) {
		return repo.findAll().stream().filter(user -> !user.getId().equals(id))
				.collect(Collectors.toList());
	}

}

