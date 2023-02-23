package com.mira.basicBoard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mira.basicBoard.mapper.UserMapper;
import com.mira.basicBoard.repository.UserRepository;
import com.mira.basicBoard.vo.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserRepository userRepository;
	
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
		user.setRole("ROLE_USER");
		
		userMapper.enrollProcess(user);
	}


	public int enrollValidate(String userId) {
		return userRepository.enrollValidate(userId);
	}
	
	
}
