package com.example.demo.service;

import com.example.demo.model.Board;
import com.example.demo.repo.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository boardRepository;
	public Board createBoard(Board board){
		boardRepository.save(board);
		return board;
	}

	public List<Board> findAllBoard(String name){
		return boardRepository.findByBoardNameNot(name).orElseThrow();
	}

	public Board getOne(Long boardId){
		return boardRepository.findById(boardId).orElseThrow(NullPointerException::new);
	}

	public Board getBoardId(Long participantId){
		Board board = boardRepository.findByParticipantId(participantId).orElseThrow(NullPointerException::new);
		return board;
	}

}
