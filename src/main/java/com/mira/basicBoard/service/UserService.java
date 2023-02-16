package com.mira.basicBoard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mira.basicBoard.mapper.UserMapper;
import com.mira.basicBoard.vo.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		
		User user = userMapper.getUserAccount(userId);
		
		if(user == null) {
			throw new UsernameNotFoundException("User " + userId + " not found");
		}
		
		return user;
	}
	
	
	@Transactional
	public void enrollProcess(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setUserPwd(passwordEncoder.encode(user.getUserPwd()));
		System.out.println(user.getUserPwd());
		user.setRole("USER");
		
		userMapper.enrollProcess(user);
	}
	
	
	
	
	
	
	
	
}
