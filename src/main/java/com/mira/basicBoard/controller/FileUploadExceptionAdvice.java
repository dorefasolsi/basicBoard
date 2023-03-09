package com.mira.basicBoard.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class FileUploadExceptionAdvice {
	
	// application.properties에서 지정한 파일 크기보다 더 큰 크기의 파일을 입력했을 때 
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ModelAndView handleMaxSizeException(MaxUploadSizeExceededException maxEx) {
		ModelAndView mv = new ModelAndView();
		
		long maxFileSize = maxEx.getMaxUploadSize();
		
		mv.addObject("msg", "업로드가 가능한 최대 파일 용량은 + " + maxFileSize + "입니다.");
		
		log.info("업로드가 가능한 최대 파일 용량은 " + maxFileSize + "입니다.");
		
		log.info("288" + 288 / 4);
		mv.setViewName("error/maxFileSize");
		return  mv;
		
	}
	
	
}
