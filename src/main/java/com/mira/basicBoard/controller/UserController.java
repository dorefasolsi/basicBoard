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
	
	
//	@PostMapping("/login/process")
//	public ModelAndView loginSuccessReturn(ModelAndView mv, Authentication authentication) {
//		System.out.println("dd");
//		System.out.println((User)authentication.getPrincipal());
//		User user = (User)authentication.getPrincipal();
//		
//		System.out.println(user);
//		mv.addObject("user", user);
//		
//		System.out.println(user);
//		mv.setViewName("/board/loginSuccess");
//		return mv;
//	}
	
	
	@GetMapping("/login/fail")
	public ModelAndView loginFailReturn(ModelAndView mv, User user) {
		
		
		mv.setViewName("/user/loginFail");
		return mv;
	}
	
//	@GetMapping("/login/success")
//	public ModelAndView loginSuccessReturn(ModelAndView mv, User user) {
//		mv.setViewName("/board/loginSuccess");
//		return mv;
//	}
	
	
	@GetMapping("/enroll/page")
	public ModelAndView enrollPageReturn(ModelAndView mv) {
		mv.setViewName("/user/enrollPage");
		
		return mv;
	}

	@PostMapping("/enroll/enroll")
	public ModelAndView enrollProcess(ModelAndView mv, User user) {
		
		userService.enrollProcess(user);
		mv.setViewName("/user/enrollPage");
		
		return mv;
	}
	
	
}
