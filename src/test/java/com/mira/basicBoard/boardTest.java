package com.mira.basicBoard;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mira.basicBoard.controller.UserController;
import com.mira.basicBoard.mapper.BoardMapper;
import com.mira.basicBoard.mapper.UserMapper;
import com.mira.basicBoard.vo.Board;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class boardTest {
	
	@Autowired
	private UserController userController;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private BoardMapper boardMapper;
	
	

	@Test
	public void testSearch() {

		
	}
	

}
