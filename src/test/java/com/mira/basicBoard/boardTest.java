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
	public void testSearch() {
		String userId = "p13786873";
		
		int result = userMapper.enrollValidate(userId);
		System.out.println(result);
		
	}
	

}
