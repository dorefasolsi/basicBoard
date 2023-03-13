package com.mira.basicBoard.controller;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class FileUploadExceptionAdvice {
	
//	추후삭제 참고링크
//	https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MaxUploadSizeExceededException.html
//	https://www.baeldung.com/spring-maxuploadsizeexceeded
	
	
//	// application.properties에서 지정한 파일 크기보다 더 큰 크기의 파일을 입력했을 때 
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException maxEx) {		
	    String message = "잘못된 요청입니다. 5MB가 초과하는 파일을 업로드 할 수 없습니다.";
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message); //400
	}
	
	
	//저장소에서 삭제된 파일을 다운로드하고자 할 때
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<String> handleFileNotFountException(FileNotFoundException fileEx){
		String message = "다은로드 할 수 없는 파일이빈다. 관리자에게 문의하세요.";
		// 단순한 문자열 메시지 반환
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message); //404
	}
}
