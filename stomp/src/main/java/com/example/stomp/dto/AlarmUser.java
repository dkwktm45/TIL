package com.example.stomp.dto;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class AlarmUser {

	@Id
	private Long id;
	private String name;
	private String password;
	private String content;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id",updatable = false,insertable = false)
	private List<Alarm> alarms = new ArrayList<>();

	public AlarmUser(Long id , String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public AlarmUser() {

	}
}
