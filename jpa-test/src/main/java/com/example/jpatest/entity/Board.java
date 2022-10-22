package com.example.jpatest.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity @Data
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardId;
	private String title;
	private String content;

	@ManyToOne(fetch = FetchType.EAGER)
	@ToString.Exclude
	private Member member;
}
