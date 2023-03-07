package com.mira.basicBoard.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mira.basicBoard.mapper.BoardMapper;
import com.mira.basicBoard.service.BoardService;
import com.mira.basicBoard.template.Pagination;
import com.mira.basicBoard.vo.Attachment;
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
	private final BoardMapper boardMapper;
	
	

	
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
	    
	    log.info("최대페이지는 : " + pi.getMaxPage());
	    
	    mv.addObject("boards", boards).addObject("pi", pi);
	    mv.setViewName("/board/listPage");
	    
	    return mv;
	}
	

	@GetMapping("/board/write")
	public ModelAndView writePageView(ModelAndView mv) {
		
		mv.setViewName("/board/writePage");
		return mv;
	}
	
	@Transactional
	@PostMapping("/board/write")
	public ModelAndView writeBoard(@AuthenticationPrincipal User user, 
									@RequestParam(value="file") MultipartFile file,
									Board board, RedirectAttributes redirectAttributes) throws IOException {
	    
		board.setUserId(user.getUserId());
		int result = 0;

		if(!file.isEmpty()) {
			board.setAttachment("Y");
			result = boardService.writeBoard(board);

			int boardNo = boardMapper.selectLastInsertId();
			board.setBoardNo(boardNo);
			Attachment attachment = saveFile(file, board);
			
			int attachmentResult = boardService.insertAttachment(attachment);
			
		} else {
			result = boardService.writeBoard(board);
			
		}
		
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
	
	//게시글 조회
	@GetMapping("/board/{boardNo}")
	public ModelAndView detailBoard(@PathVariable("boardNo") int boardNo, ModelAndView mv) {
		
		Board detailBoard = boardService.detailBoard(boardNo);
		Attachment attachment = new Attachment();
		
		if(detailBoard.getAttachment().equals("Y")) {			
			attachment = boardService.selectAttachment(boardNo);
		}
		boardService.increaseViewCount(boardNo);
		
		mv.addObject("detailBoard", detailBoard).addObject("attachment", attachment);

		
		mv.setViewName("/board/detailPage");
		return mv;
	}
	
	
	@GetMapping("/attachment/download/{fileNo}")
	public void downloadAttachment(@PathVariable("fileNo") int fileNo, HttpServletResponse response){
	    
	        log.info("파일넘버는" + fileNo);
	        Attachment attachment = boardService.getAttachment(fileNo);
	    
	        log.info("다운로드할 파일명" + attachment.getStoredName());
	        log.info("다운로드할 파일의 경로" + attachment.getPath());
	      
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
	
	
	
	public Attachment saveFile(MultipartFile file, Board board) throws IOException {
		
		Attachment attachment = new Attachment();
		
		String originName = file.getOriginalFilename(); //원본파일명 + 확장자 
		String extension = StringUtils.getFilenameExtension(originName); //확장자만 출력
//		String storedName = UUID.randomUUID().toString() + "." + extension;
		String storedName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "." + extension;
		//UUID이용 OR 날짜변환 OR 직접생성
		
		//String storedName = UUID.randomUUID().toString() + "." + extension;
		//UUID -> 4c2e9cf5-187d-46a0-879c-4f6879ddb1be.jpg  -> 파일명 중복X
		
		//String storedName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "." + extension;
		//yyyyMMddHHmmssSSS -> 년월일시분초밀리초
		//날짜 -> 20230306143307494.jpg						-> 파일명 중복O, 파일명이 의미있는 값을 가짐

		
		attachment.setOriginName(originName);
		attachment.setStoredName(storedName);
		attachment.setStatus("Y");
		attachment.setBoardNo(board.getBoardNo());
		attachment.setUserId(board.getUserId());

		//파일이 저장될 경로
		//C드라이브부터 경로출력
		
//		절대경로
//		Path uploadPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "static", "uploadFiles").toAbsolutePath().normalize();
		
//		상대경로
		Path uploadPath = Paths.get("src/main/resources/static/uploadFiles").toAbsolutePath().normalize();
		
		//파일명 결정
		//uploadPath + storedName
		Path targetLocation = uploadPath.resolve(attachment.getStoredName());
		
		attachment.setPath(uploadPath.toString());
		
		attachment.setStoredName(attachment.getPath()+'\\'+attachment.getStoredName());
		log.info(attachment.getStoredName());
		
//		1. 파일복사(파일을 다른 서버에 전송해야 할 경우 더 적합)
//		try (InputStream inputStream = file.getInputStream()) {
//		    Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
//		}
		
//		2. MultipartFile 객체 메서드 이용(일반적)
		file.transferTo(targetLocation.toFile());
		log.info("원본파일명 " + attachment.getOriginName());
		log.info("변경파일명 " + attachment.getStoredName());
		log.info("저장경로 " + attachment.getPath());	
		

		return attachment;
	}
	
	
	
	
	
}
