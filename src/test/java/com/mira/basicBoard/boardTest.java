package com.mira.basicBoard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mira.basicBoard.mapper.BoardMapper;
import com.mira.basicBoard.vo.PageInfo;


@SpringBootTest
public class boardTest {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	public void testPage() {
		PageInfo pi = new PageInfo();
		
	}
}
