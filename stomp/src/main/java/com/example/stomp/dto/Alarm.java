package com.example.stomp.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Alarm {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long countId;
	private Long sender;
	private int count;

	@ManyToOne(fetch = FetchType.LAZY)
 	private AlarmUser alarmUser;

}
