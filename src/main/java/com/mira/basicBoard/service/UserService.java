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

//		코드의 문제점 
//		요구사항이 변경되어서 인자가 추가될경우 생기면 -> 코드변경해야하고, 변경 상황에 맞춰 정확하게 걸리는 조건식을 작성해야함
//		return이 if문 밖에 빠져있어서 실행되지 못하고 던져지는 경우 발생할 가능성		
		//기존코드
//		if(user == null) {			
//			throw new UsernameNotFoundException("사용자 " + userId + "를 찾을 수 없습니다.");			
//		}
//		return user;
		
		//수정1
		if(user != null) {
			log.info(userId + "접속");
			return user;
		} else {
			throw new UsernameNotFoundException("사용자 " + userId + "를 찾을 수 없습니다.");									
		}
		
		//수정2 The operator ! is undefined for the argument type(s) User 오류 발생
		//! => Boolean에서만 적용, User타입이라 에러 발생
//		if(!user) {
//			throw new UsernameNotFoundException("사용자 " + userId + "를 찾을 수 없습니다.");						
//		}
		
		
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
