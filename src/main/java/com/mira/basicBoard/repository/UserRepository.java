package com.mira.basicBoard.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mira.basicBoard.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {

	private final UserMapper userMapper;
	
	public int enrollValidate(String userId) {
		return userMapper.enrollValidate(userId);
		
	}

}
