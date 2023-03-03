package com.mira.basicBoard.controller;

import java.util.ArrayList;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	
	@GetMapping("/board/search")
	public ModelAndView searchTest(String category, String keyword, ModelAndView mv) {		
		ArrayList<Board> boards = boardService.searchTest(category, keyword);
		
		mv.addObject("boards", boards);
		mv.setViewName("/board/searchPage");
		
	    return mv;
	}
	
	@GetMapping({"/", "/board/list"})
	public ModelAndView mainPage(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
								String category, String keyword) {
	    
		ModelAndView mv = new ModelAndView();
		
		int listCount = boardService.countBoardList(category, keyword); 
	    
	    PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 10);
	    
	    ArrayList<Board> boards = boardService.boardList(pi, category, keyword);
	     
	    if(category!=null && !category.equals("") && keyword!=null) {
	    	mv.addObject("category", category).addObject("keyword", keyword);
	    }

	    System.out.println(pi.getMaxPage());
	    
	    mv.addObject("boards", boards).addObject("pi", pi);
	    mv.setViewName("/board/listPage");
	    
	    return mv;
	}
	

	@GetMapping("/board/write")
	public ModelAndView writePageView(ModelAndView mv) {
		
		mv.setViewName("/board/writePage");
		return mv;
	}
	
	@PostMapping("/board/write")
	public ModelAndView writeBoard(@AuthenticationPrincipal User user, Board board, RedirectAttributes redirectAttributes) {
	    
		board.setUserId(user.getUserId());
	    int result = boardService.writeBoard(board);
	    String msg = "";
	    
	    if(result != 0) {
	        msg = "게시글이 등록되었습니다.";

	    } else {
	        log.info("결과는 " + Integer.toString(result) + " , 실패!");
	        msg = "error";
	    }
	    
	    redirectAttributes.addFlashAttribute("msg", msg);
	    return new ModelAndView("redirect:/board/list");
	    
	}
	
	@GetMapping("/board/{boardNo}")
	public ModelAndView detailBoard(@PathVariable("boardNo") int boardNo, ModelAndView mv) {
		
		Board detailBoard = boardService.detailBoard(boardNo);
		
		boardService.increaseViewCount(boardNo);
		
		mv.addObject("detailBoard", detailBoard);
		mv.setViewName("/board/detailPage");
		return mv;
	}
	
	
	@PutMapping("/board/{boardNo}/delete")
	public ModelAndView deleteBoard(@PathVariable("boardNo") int boardNo, RedirectAttributes redirectAttributes) {
		
		int result = boardService.deleteBoard(boardNo);
		String msg = "게시글이 삭제되었습니다.";
		redirectAttributes.addFlashAttribute("msg", msg);
		return new ModelAndView("redirect:/board/list");
	}
	
	
	@GetMapping("/board/update/{boardNo}")
	public ModelAndView updateBoardView(@PathVariable("boardNo") int boardNo, ModelAndView mv) {
	
		Board detailBoard = boardService.detailBoard(boardNo);
		mv.addObject("detailBoard", detailBoard);
		mv.setViewName("/board/updatePage");
		return mv;
	}
	
	@PutMapping("/board/update")	
	public ModelAndView updateBoard(ModelAndView mv, Board board, RedirectAttributes redirectAttributes) {
		
		
		int result = boardService.updateBoard(board);
		String msg = "게시글 수정이 완료되었습니다.";
		
		redirectAttributes.addFlashAttribute("msg", msg);
		return new ModelAndView("redirect:/board/list");
	}
	
	
	
	
	
	
}
