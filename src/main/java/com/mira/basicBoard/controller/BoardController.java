package com.mira.basicBoard.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mira.basicBoard.service.BoardService;
import com.mira.basicBoard.template.Pagination;
import com.mira.basicBoard.vo.Board;
import com.mira.basicBoard.vo.PageInfo;
import com.mira.basicBoard.vo.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	

	@GetMapping({"/", "/board/list"})
	public ModelAndView mainPage(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
												@ModelAttribute("msg") String msg) {
	    
		ModelAndView mv = new ModelAndView();
		int listCount = boardService.boardListCount(); 
	    
	    PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 10);
	    
	    ArrayList<Board> boardList = boardService.boardList(pi);
	    
	    mv.addObject("boardList", boardList).addObject("pi", pi);
	    mv.addObject("msg", msg);
	    
	    
	    ArrayList hoho = new ArrayList();
	    hoho.add("a");
	    hoho.add("b");
	    hoho.add("c");
	    
	    mv.addObject("hoho", hoho);
	    
	    mv.setViewName("/board/listPage");
	    
	    return mv;
	}
	

	@GetMapping("/board/write")
	public ModelAndView boardWritePage(ModelAndView mv) {
		
		mv.setViewName("/board/writePage");
		return mv;
	}
	
	@PostMapping("/board/write")
	public ModelAndView boardWrite(@AuthenticationPrincipal User user, Board board, RedirectAttributes redirectAttributes) {
	    
		board.setUserId(user.getUserId());
	    int result = boardService.boardWrite(board);
	    String msg = "";
	    
	    if(result != 0) {
	        msg = "게시글 작성에 성공하였습니다.";

	    } else {
	        log.info("결과는 " + Integer.toString(result) + " , 실패!");
	        msg = "게시글 작성에 실패하였습니다.";
	    }
	    
	    redirectAttributes.addFlashAttribute("msg", msg);
	    return new ModelAndView("redirect:/board/list");
	    
	}
	
	@GetMapping("/board/{boardNo}")
	public ModelAndView boardDetail(@PathVariable("boardNo") int boardNo, ModelAndView mv) {
		
		Board detailBoard = boardService.boardDetail(boardNo);
		
		boardService.increaseViewCount(boardNo);
		
		mv.addObject("detailBoard", detailBoard);
		mv.setViewName("/board/detailPage");
		return mv;
	}
	
	
	@DeleteMapping("/board/{boardNo}")
	public ModelAndView boardDelete(@PathVariable("boardNo") int boardNo, ModelAndView mv) {
		
		int result = boardService.boardDelete(boardNo);
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
