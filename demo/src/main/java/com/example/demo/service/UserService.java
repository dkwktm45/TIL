package com.example.demo.service;

import com.example.demo.model.Board;
import com.example.demo.model.redis.ChatRoom;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;

	public User findByName(String name) {
		return userRepository.findByUserName(name);
	}

	public User saveUser(String name){
		Long userId = User.setId(name);
		Optional<User> memoryUser = userRepository.findById(userId);
		if(memoryUser.isEmpty()){
			User user = User.builder().userName(name).token(jwtTokenProvider.generateToken(name)).userId(userId).build();
			userRepository.save(user);
			return user;
		}else{
			return memoryUser.get();
		}
	}

}
