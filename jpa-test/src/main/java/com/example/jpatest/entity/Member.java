package com.example.jpatest.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity @Getter @Setter
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;
	private String name;

	@OneToMany(mappedBy = "member",fetch = FetchType.EAGER)
	@ToString.Exclude
	private Set<Board> boards = Collections.emptySet();
}
