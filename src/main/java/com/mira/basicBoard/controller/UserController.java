package com.mira.basicBoard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mira.basicBoard.vo.User;

@RestController
public class UserController {

	
	@GetMapping("/")
	public ModelAndView loginPageReturn(ModelAndView mv) {
		mv.setViewName("/user/loginPage");
		return mv;
	}
	
	@PostMapping("/login")
	public ModelAndView login(ModelAndView mv, User user) {
		
		mv.setViewName("/board/loginSuccess");
		
		return mv;
	}
	
	@GetMapping("/login/fail")
	public ModelAndView loginFailReturn(ModelAndView mv) {
		mv.setViewName("/user/loginPage");
		return mv;
	}
}
