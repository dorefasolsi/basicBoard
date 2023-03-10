package com.mira.basicBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mira.basicBoard.service.UserService;
import com.mira.basicBoard.vo.ResponseDto;
import com.mira.basicBoard.vo.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;

	
	// 로그인페이지로 이동
	@GetMapping("/login/page")
	public ModelAndView loginPage(@ModelAttribute("msg") String msg) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("msg", msg);
		mv.setViewName("/user/loginPage");
		return mv;
	}
	
	// 로그인 실패 페이지로 이동
	@GetMapping("/login/fail")
	public ModelAndView loginFailView(ModelAndView mv, User user) {
		mv.setViewName("/user/loginFail");
		return mv;
	}

	
	//회원가입 페이지로 이동
	@GetMapping("/enroll/page")
	public ModelAndView enrollPageView(ModelAndView mv) {
		mv.setViewName("/user/enrollPage");
		return mv;
	}

	
	//회원가입
	@PostMapping("/enroll/enroll")
	public ModelAndView enrollProcess(ModelAndView mv, User user, RedirectAttributes redirectAttributes) {
		int result = userService.enrollProcess(user);
		String msg = "";
		
		if(result != 0) {
			msg = "당신은 이제 나의 회원~❤❤";
		} else {
			msg = "회원가입에 실패하였습니다. 관리자에게 문의하세요";
		}
		
		mv.addObject("msg", msg);
		mv.setViewName("/user/loginPage");
		
		return mv;
	}
	
	
	// 아이디 중복 검사
	@PostMapping("/enroll/validate")
    public ResponseDto<Integer> validateUserId(@RequestParam("userId") String userId) {
		int result = userService.enrollValidate(userId);
			return new ResponseDto<Integer>(HttpStatus.OK, result);
    }
	
	
}
