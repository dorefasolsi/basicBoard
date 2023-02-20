package com.mira.basicBoard.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mira.basicBoard.service.BoardService;
import com.mira.basicBoard.vo.Board;
import com.mira.basicBoard.vo.Page;
import com.mira.basicBoard.vo.PageInfo;
import com.mira.basicBoard.vo.User;

@RestController
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/board/list")
	public ModelAndView loginSuccessReturn(PageInfo pi, ModelAndView mv) {
	     
		
		ArrayList<Board> boardList = boardService.boardList(pi);
		mv.addObject("boardList", boardList);

		//페이지 이동 인터페이스 데이터
//		int total = boardService.boardListCount(pi);s
		
//		Page pageMaker = new Page(pi, total);
		

//		mv.addObject("pageMaker", pageMaker);
		
		mv.setViewName("/board/listPage");
		return mv;
	}
	
	
	
	@GetMapping("/board/write")
	public ModelAndView boardWritePage(@AuthenticationPrincipal User user, ModelAndView mv) {
		
		
		mv.setViewName("/board/writePage");
		return mv;
	}
	
	@PostMapping("/board/write")
	public ModelAndView boardWrite(@AuthenticationPrincipal User user, ModelAndView mv, Board board) {
		board.setUserId(user.getUserId());
		int result = boardService.boardWrite(board);
		mv.setViewName("redirect:/board/list");
		return mv;
	}
	
	@GetMapping("/board/{boardNo}")
	public ModelAndView boardDetail(@PathVariable("boardNo") int boardNo, ModelAndView mv) {
		
		Board detailBoard = boardService.boardDetail(boardNo);
		
		mv.addObject("detailBoard", detailBoard);
		
		mv.setViewName("/board/detailPage");
		return mv;
	}
	
	
	@DeleteMapping("/board/{boardNo}")
	public ModelAndView boardDelete(@PathVariable("boardNo") int boardNo, ModelAndView mv) {
		
		int result = boardService.boardDelete(boardNo);
		System.out.println(result);
		mv.setViewName("redirect:/board/list");
		return mv;
	}
	
	
	@GetMapping("/board/update/{boardNo}")
	public ModelAndView boardUpdatePage(@PathVariable("boardNo") int boardNo, ModelAndView mv) {
	
		Board detailBoard = boardService.boardDetail(boardNo);
		mv.addObject("detailBoard", detailBoard);
		mv.setViewName("/board/updatePage");
		return mv;
	}
	
	@PutMapping("/board/update")	
	public ModelAndView boardUpdate(ModelAndView mv, Board board) {
		
		
		int result = boardService.boardUpdate(board);
		mv.setViewName("redirect:/board/list");
		
		return mv;
	}
	
	
	
	
	
	
}
