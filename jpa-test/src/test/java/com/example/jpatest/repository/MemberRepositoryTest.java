package com.example.jpatest.repository;

import com.example.jpatest.entity.Board;
import com.example.jpatest.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	BoardRepository boardRepository;


	@DisplayName("user 정보 가져오기 - Eagger, 내장 메소드")
	@Test
	public void test1(){
		Member result = memberRepository.findById(2L).get();
	}
	@DisplayName("user 정보 가져오기 - Eagger, 커스텀 메소드")
	@Test
	public void test2(){
		Member result = memberRepository.findByMemberId(2L);
	}

	@DisplayName("user 정보 가져오기 - Eagger, fetch join")
	@Test
	public void test3(){
		Member result = memberRepository.findByCustomId(2L);
	}
	@DisplayName("user 정보 가져오기 - Eagger, entitygraph")
	@Test
	public void test4(){
		Member result = memberRepository.findByMemberId(2L);
	}
}