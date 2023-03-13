package com.mira.basicBoard.service;

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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{
	

	private final UserMapper userMapper;
	
	private final UserRepository userRepository;
	
	//로그인 시 사용자 정보 가져오기 위한 코드
	//인증 처리 구현체인 UserDetailService 사용자 인증에 필요한 정보 제공
	//입력받은 userId 기반, db에서 사용자 정보 받아와 user객체로 반환 -> 반환된 객체는 비밀번호 검증 등 userDetails 인터페이스 구현
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		
		User user = userMapper.getUserAccount(userId);
		
		if(user != null) {
			log.info(userId + "접속");
		} else {
			throw new UsernameNotFoundException("사용자 " + userId + "를 찾을 수 없습니다.");			
		}
		
		
		return user;
	}
	
	//사용자 정보 등록
	@Transactional // 트랜잭션으로 묶임 -> ACID!!!!! 하나의 작업단위로 실행된다는 뜻
	public int enrollProcess(User user) {
		//사용자 정보 암호화
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setUserPwd(passwordEncoder.encode(user.getUserPwd()));
		user.setRole("ROLE_USER"); //추가할것! 어떻게 들어왔을 때 어떤 권한을 줄 것인지?
		//db등록
		return userMapper.enrollProcess(user);
	}

	//동일아이디 존재유무 조회
	public int enrollValidate(String userId) {
		return userRepository.enrollValidate(userId);
	}
	
	
}
