package com.example.demo.service;

import com.example.demo.model.Board;
import com.example.demo.model.BoardParticipants;
import com.example.demo.model.redis.ChatMessage;
import com.example.demo.model.User;
import com.example.demo.repo.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class BoardParticipantService {
	private final Logger logger = LoggerFactory.getLogger(BoardParticipantService.class);


	private final BoardParticipantsRepository participantsRepository;
	private final BoardService boardService;
	private final BoardRepository boardRepository;
	private final UserRepository userRepository;
	private final ChatService chatService;
	public Map<String, Object> setChat(Long boardId, String chatRoomId, Long userId,String name) throws Exception {
		Board board = boardService.getOne(boardId);
		if(board.getBoardParticipantsList().stream().anyMatch(info -> info.getUserId().equals(userId)) && board.getBoardParticipantsList() != null){
			throw new Exception("이미 존재하는 회원입니다.");
		}else {
			BoardParticipants boardParticipants = BoardParticipants.builder()
					.board(board)
					.userName(name)
					.userId(userId)
					.roomId(chatRoomId)
					.build();
			participantsRepository.save(boardParticipants);
			Map<String, Object> result = new HashMap<>();
			result.put("boardId",boardParticipants.getBoard().getBoardId());
			result.put("participants", boardParticipants);
			return result;
		}
	}
	public void updateUserList(BoardParticipants boardParticipants){
		participantsRepository.save(boardParticipants);
	}

	public List<BoardParticipants> myChatRoom(Long userId){
		logger.info("myChatRoom start");
		// 나중에 생략가능 member!
		User user = userRepository.findByUserId(userId);
		List<BoardParticipants> boardList = participantsRepository.findByBoard(userId);

		logger.info("myChatRoom complete");
		return boardList;
	}

	public List<Board> otherChatRoom(Long userId){
		logger.info("otherChatRoom start");
		List<BoardParticipants> participantsList = participantsRepository.findByUserId(userId).get();
		List<Board> boardList = participantsList.stream().map(info -> info.getBoard()).collect(Collectors.toList());
		logger.info("otherChatRoom complete");
		return boardList;
	}

	public List<BoardParticipants> deleteRoom(Long boardId, Long participantId) {
		logger.info("deleteRoom start");
		Board board = boardRepository.findById(boardId).get();

		List<BoardParticipants> boardParticipantsList = board.getBoardParticipantsList()
				.stream().filter(info-> info.getParticipantId() !=participantId).collect(Collectors.toList());

		boardParticipantsList.stream().forEach(info -> info.setBoard(null));

		for(BoardParticipants participants : boardParticipantsList){
			chatService.sendChatMessage(
					ChatMessage.builder()
							.message(board.getUser().getUserName() + "님이 다른회원과 매칭이 되었습니다. 다음기회에 도전해주세요!")
							.roomId(participants.getRoomId())
							.build()
			);
		}
		logger.info("deleteRoom end");
		return boardParticipantsList;
	}

	public BoardParticipants findByRoomId(String roomId) {
		Optional<BoardParticipants> participantsOptional = participantsRepository.findByRoomId(roomId);
		if(participantsOptional.isPresent()){
			return participantsOptional.get();
		}else{
			return null;
		}
	}
}
