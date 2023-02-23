package com.mira.basicBoard.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mira.basicBoard.mapper.UserMapper;

@Repository
public class UserRepository {

	@Autowired
	private UserMapper userMapper;
	
	public int enrollValidate(String userId) {
		return userMapper.enrollValidate(userId);
		
	}

}
