package com.mira.basicBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mira.basicBoard.service.UserService;
import com.mira.basicBoard.vo.User;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	
	@GetMapping("/")
	public ModelAndView loginPageReturn(ModelAndView mv) {
		mv.setViewName("/user/loginPage");
		return mv;
	}
	
	
	
	@GetMapping("/login/fail")
	public ModelAndView loginFailReturn(ModelAndView mv, User user) {
		
		
		mv.setViewName("/user/loginFail");
		return mv;
	}
		
	
	@GetMapping("/enroll/page")
	public ModelAndView enrollPageReturn(ModelAndView mv) {
		mv.setViewName("/user/enrollPage");
		
		return mv;
	}

	@PostMapping("/enroll/enroll")
	public ModelAndView enrollProcess(ModelAndView mv, User user) {
		
		userService.enrollProcess(user);
		mv.setViewName("/user/loginPage");
		
		return mv;
	}
	
	
}
