package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	private Long userId;
	private String userName;
	private String userPwd;
	private String token;

	@OneToMany(fetch = FetchType.LAZY )
	@JoinColumn(name = "user_id",updatable = false,insertable = false)
	private List<Board> boardList;

	@OneToMany(fetch = FetchType.LAZY )
	@JoinColumn(name = "user_id",updatable = false,insertable = false)
	private List<Friend> friends;

	public static Long setId(String name) {
		Long userId = null;
		if(name.equals("happydaddy")) {
			userId = 1L;
		}else if(name.equals("angrydaddy")){
			userId = 2L;
		}else{
			userId = 3L;
		}
		return userId;
	}
}
