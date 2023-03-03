package com.mira.basicBoard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mira.basicBoard.controller.UserController;
import com.mira.basicBoard.mapper.BoardMapper;
import com.mira.basicBoard.mapper.UserMapper;
import com.mira.basicBoard.vo.Board;

@SpringBootTest
public class boardTest {
	
	@Autowired
	private UserController userController;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private BoardMapper boardMapper;
	
	
	@Test
	public void insertDummy() {
		
		Board board = new Board();
		
		//21개 삽입
		for(int i = 1; i < 80; i++) {
			board.setBoardTitle("더미" + i);
			board.setBoardContent("더미내용" + i);
			board.setUserId("aaaaa");
			int result = boardMapper.writeBoard(board);
		}
	}

	

}
