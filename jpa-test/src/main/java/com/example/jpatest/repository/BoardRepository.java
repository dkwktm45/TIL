package com.example.jpatest.repository;

import com.example.jpatest.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
	Board findByBoardId(Long id);
}
