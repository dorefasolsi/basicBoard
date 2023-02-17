package com.mira.basicBoard.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mira.basicBoard.vo.User;

@RestController
public class BoardController {
	
	
	@GetMapping("/board/list")
	public ModelAndView loginSuccessReturn(@AuthenticationPrincipal User user, ModelAndView mv, User loginUser, Principal principal, Authentication authentication) {
	    System.out.println("principal.getName : " + principal.getName());
	    
	    UserDetails userDetails = (UserDetails) authentication.getPrincipal(); 
	    System.out.println("authentication : " + userDetails.getUsername());
		
	    System.out.println("@Authen.. : " + user.getUserId());
	    
		mv.setViewName("/board/boardListView");
		return mv;
	}
	
	
	
}
