package com.mira.basicBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootApplication
public class BasicBoardApplication {	

	public static void main(String[] args) {
		log.info("로그테스트입니다");
		log.debug("디버그에서 출력이 될까");
		SpringApplication.run(BasicBoardApplication.class, args);
	}

}
