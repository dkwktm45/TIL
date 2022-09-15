package com.example.demo.controller;

import com.example.demo.model.Board;
import com.example.demo.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BoardController {
	private final Logger logger = LoggerFactory.getLogger(BoardController.class);

	private final BoardService boardService;

	@PostMapping(value = "/board-create")
	public Board createBoard(@RequestBody Board board){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		logger.info("board-create");
		return boardService.createBoard(board);
	}

	@PostMapping(value = "/board-all")
	public ResponseEntity<List<Board>> findAll(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		logger.info("board-all");
		return ResponseEntity.ok(boardService.findAllBoard(name));
	}

	@PostMapping(value = "/board-id")
	public ResponseEntity<Map<String,Object>> findById(@RequestBody Long participantId){
		logger.info("board-Id");
		Map<String,Object> result = new HashMap<>();
		Board board = boardService.getBoardId(participantId);
		result.put("board" , board);
		result.put("userName",board.getUser().getUserName());
		return ResponseEntity.ok(result);
	}
}
