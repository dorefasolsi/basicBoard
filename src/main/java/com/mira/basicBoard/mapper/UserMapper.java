package com.mira.basicBoard.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.mira.basicBoard.vo.User;

@Mapper
public interface UserMapper {
	
	
	public User getUserAccount(String userId);

	public void enrollProcess(User user);

	public int enrollValidate(String userId);
	
}
