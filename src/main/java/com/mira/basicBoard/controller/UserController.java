package com.mira.basicBoard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mira.basicBoard.vo.User;

@RestController
public class UserController {

	
//	@GetMapping("/")
//	public ModelAndView loginPageReturn(ModelAndView mv) {
//		System.out.println("gg");
//		return mv;
//	}
	
	@GetMapping("/login")
	public ModelAndView login(ModelAndView mv, User user) {
		mv.setViewName("/user/loginPage");
//		mv.setViewName("/board/loginSuccess");
		
		return mv;
	}
	
	@PostMapping("/login/process")
	public ModelAndView loginProcess(ModelAndView mv, User user) {
		System.out.println(user);
		
		return mv;
	}
	
	@GetMapping("/login/fail")
	public ModelAndView loginFailReturn(ModelAndView mv) {
		mv.setViewName("/user/loginPage");
		return mv;
	}
	
	@GetMapping("/login/success")
	public ModelAndView loginSuccessReturn(ModelAndView mv) {
		mv.setViewName("/board/loginSuccess");
		return mv;
	}
}
