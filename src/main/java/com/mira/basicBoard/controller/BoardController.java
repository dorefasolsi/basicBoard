package com.mira.basicBoard.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import com.mira.basicBoard.mapper.BoardMapper;
import com.mira.basicBoard.service.BoardService;
import com.mira.basicBoard.template.Pagination;
import com.mira.basicBoard.vo.Attachment;
import com.mira.basicBoard.vo.Board;
import com.mira.basicBoard.vo.PageInfo;
import com.mira.basicBoard.vo.ResponseDto;
import com.mira.basicBoard.vo.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	private final BoardMapper boardMapper;
	
	

	// 게시글 리스트 조회
	@GetMapping({"/", "/board/list"})
	public ModelAndView mainPage(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
								String category, String keyword) {
	    
		ModelAndView mv = new ModelAndView();
		
		//총 게시글 수 조회(검색조건 있는 경우 해당하는 게시글 수만 조회)
		int listCount = boardService.countBoardList(category, keyword); 
	    
		//페이징처리 총게시글수, 현재페이지, 한패이지내최대갯수, 페이징바에서보여줄페이지수
	    PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 10);
	    
	    //BOARD테이블에 있고 조건이 일치하는 모든 컬럼 조회
	    ArrayList<Board> boards = boardService.boardList(pi, category, keyword);
	     
	    //검색 시 검색창에서 카테고리와 키워드가 유지될 수 있도록 넘겨줌
	    if(category!=null && keyword!=null && !"".equals(category)) {
	    	mv.addObject("category", category).addObject("keyword", keyword);
	    }
	    
	    //게시글 정보와 페이징처리 넘겨줌
	    mv.addObject("boards", boards).addObject("pi", pi);
	    
	    //이동할 페이지
	    mv.setViewName("/board/listPage");
	    
	    return mv;
	}
	
	
	// 게시글 작성페이지로 이동
	@GetMapping("/board/write")
	public ModelAndView writePageView(ModelAndView mv) {
		mv.setViewName("/board/writePage");
		return mv;
	}
	
	
	// 게시글 작성
	@Transactional
	@PostMapping("/board/write")	//스프링시큐리티 - 현재 인증된 사용자의 User(Principal) 객체 가져옴
	public ModelAndView writeBoard(@AuthenticationPrincipal User user, 
									@RequestParam(value="file") MultipartFile file,
									Board board, RedirectAttributes redirectAttributes) throws IOException {
	    
		//board의 작성자에 현재 로그인되어있는 사용자의 아이디 넣어줌
		board.setUserId(user.getUserId());
		int result = 0;
		String msg = "";
	    

		//넘어온 파일이 있을 경우 board의 첨부파일 상태를 "Y"로 변경함
		if(!file.isEmpty()) {
			board.setAttachment("Y");
			
			//board객체 DB에 등록
			result = boardService.writeBoard(board);

			//가장 최근에 등록된 boardNo 가져옴
			int boardNo = boardMapper.selectLastInsertId();
			
			//board의 boardNo에 가장 최근에 등록된 boardNo 삽입
			board.setBoardNo(boardNo);
			
			//saveFile메소드 - 첨부파일명 변경 및 파일 저장하는 메소드
			Attachment attachment = saveFile(file, board);
			
			//변환한 첨부파일 정보를 db에 등록
			int attachmentResult = boardService.insertAttachment(attachment);
			
		} else {
			
		//넘어온 첨부파일이 없는 경우 게시글 등록만
			result = boardService.writeBoard(board);
		}
		
	    if(result != 0) {
	        msg = "게시글이 등록되었습니다.";

	    } else {
//	        log.info("결과는 " + Integer.toString(result) + " , 실패!");
	        msg = "error";
	    }
	    
	    redirectAttributes.addFlashAttribute("msg", msg);
	    return new ModelAndView("redirect:/board/list");
	}
	
	
	// 게시글 조회
	@GetMapping("/board/{boardNo}")
	public ModelAndView detailBoard(@PathVariable("boardNo") int boardNo, ModelAndView mv) {
		
		//boardNo를 이용하여 게시글 조회
		Board board = boardService.detailBoard(boardNo);

		
		//게시글 정보가 있을 때
		if(board != null) {
			
			Attachment attachment = new Attachment();
			
			//게시글의 첨부파일이 있을 때
			if("Y".equals(board.getAttachment())) {
				//첨부파일 조회
				attachment = boardService.selectAttachment(boardNo);
			}
			
			//게시글과 첨부파일 넘기기
			mv.addObject("attachment", attachment).addObject("board", board);
			boardService.increaseViewCount(boardNo);	
			mv.setViewName("/board/detailPage");
			return mv;


		
		} else {
		// 게시글 정보가 없을 때 에러 페이지로 이동
			mv.setViewName("/error/notFoundPage");
			return mv; 
		}
	}
	
	
		
	// 게시글 삭제
	@PutMapping("/board/{boardNo}/delete")
	public ModelAndView deleteBoard(@PathVariable("boardNo") int boardNo, RedirectAttributes redirectAttributes) {
		
		int result = boardService.deleteBoard(boardNo);
		String msg = "게시글이 삭제되었습니다.";
		redirectAttributes.addFlashAttribute("msg", msg);
		return new ModelAndView("redirect:/board/list");
	}
	
	
	// 게시글 수정페이지로 이동
	@GetMapping("/board/update/{boardNo}")
	public ModelAndView updateBoardView(@PathVariable("boardNo") int boardNo, ModelAndView mv) {
	
		//수정할 게시글 내용 조회
		Board board = boardService.detailBoard(boardNo);
		
		
		//해당 게시글에 첨부파일이 있을 경우 조회하여 넘기기
		if(board.getAttachment() != null && "Y".equals(board.getAttachment())) {
			Attachment attachment = boardService.selectAttachment(boardNo);
			mv.addObject("attachment", attachment);
		}
		
		//게시글 내용이 화면단에 보여질 수 있도록 보냄
		mv.addObject("board", board);
		mv.setViewName("/board/updatePage");
		return mv;
	}
	
	
	// 게시글 수정
	@PutMapping("/board/update")	
	public ModelAndView updateBoard(@RequestParam(value="file", required=false) MultipartFile file,
									ModelAndView mv, 
									Board board, 
									RedirectAttributes redirectAttributes) throws IOException {
		
		//해당 게시글에 기존 첨부파일이 있었는지 조회
		String attachYN = boardMapper.detailBoard(board.getBoardNo()).getAttachment();
				
		//전달받은 첨부파일이 있는 경우
		if(file != null && !file.isEmpty()) {
			log.info("새로 입력된 파일이 있습니다");
			
			//게시글에 첨부파일이 있음을 알림
			board.setAttachment("Y");
			
			//saveFile() -> 파일명 변경, 파일 저장 관련 메소드
            Attachment attachment = saveFile(file, board);
            
            //첨부파일 정보 DB 등록
            int result = boardService.insertAttachment(attachment);
       
        //기존 첨부파일이 있는 경우, 전달받은 첨부파일 관련 처리X 
		} else if("Y".equals(attachYN)){		
			log.info("기존 파일이 있습니다.");
			
			//게시글 첨부파일이 있음을 알림
			board.setAttachment("Y");
		} else {
			//전달받은 첨부파일 없음
			log.info("입력된 파일이 없습니다");
			board.setAttachment("N");				
		}
		
		
		int result = boardService.updateBoard(board);
		String msg = "게시글 수정이 완료되었습니다.";
		
		redirectAttributes.addFlashAttribute("msg", msg);
		return new ModelAndView("redirect:/board/list");
	}
	
	
	
	//첨부파일 삭제
	@PostMapping("/attachment/delete")
	public ResponseDto<Integer> deleteAttachment(Attachment attachment) {
		//ajax 이용하여 넘어왔음
		
		
//		attachment 상태 N으로 변경
		int result = boardMapper.deleteAttachmentFileNo(attachment);
		
		// 결과값 돌려주기 위함
		if(result != 0) {
			return new ResponseDto<Integer>(HttpStatus.OK, result); // 200, 성공적인 요청
		} else {
			return new ResponseDto<Integer>(HttpStatus.INTERNAL_SERVER_ERROR, result); //500 서버에서 예기치 않은 오류 
		}
	}
	
	
	// 첨부파일 셋팅
	public Attachment saveFile(MultipartFile file, Board board) throws IOException {
		
		Attachment attachment = new Attachment();
		
		String originName = file.getOriginalFilename(); //원본파일명 + 확장자 
		String extension = StringUtils.getFilenameExtension(originName); //확장자만 출력
//		String storedName = UUID.randomUUID().toString() + "." + extension;
		String storedName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "." + extension;
		//UUID이용 OR 날짜변환 OR 직접생성
		
		// 방법1.
		//String storedName = UUID.randomUUID().toString() + "." + extension;
		//UUID -> 4c2e9cf5-187d-46a0-879c-4f6879ddb1be.jpg  -> 파일명 중복X
		
		// 방법2.
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
//		log.info(attachment.getStoredName());
		
//		1. 파일복사(파일을 다른 서버에 전송해야 할 경우 더 적합)
//		try (InputStream inputStream = file.getInputStream()) {
//		    Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
//		}
		
//		2. MultipartFile 객체 메서드 이용(일반적)
		file.transferTo(targetLocation.toFile());
//		log.info("원본파일명 " + attachment.getOriginName());
//		log.info("변경파일명 " + attachment.getStoredName());
//		log.info("저장경로 " + attachment.getPath());	
		

		return attachment;
	}
	
	// 첨부파일 다운로드
	@GetMapping("/attachment/download/{fileNo}")
	public ResponseEntity<Resource> downloadAttachment(@PathVariable("fileNo") int fileNo) throws Exception {
	    Attachment attachment = boardService.getAttachment(fileNo);
	    
	    Path file = Paths.get(attachment.getStoredName());
//	    log.info("변경파일명 : " + attachment.getStoredName());
	    //  C:\mira\\basicBoard\\basicBoard\\src\\main\\resources\\static\\uploadFiles\\20230307162438689.txt형태로 들어가있음
	    
//	    log.info("file에는 : " + file.toString());
	    // 	위와 동일하게 출력
	    
	    
	    // Resource ->  org.springframework.core.io.Resource 객체
	    // 			->	파일 데이터 담고 있음
	    Resource resource = new UrlResource(file.toUri());
//	    log.info("리소스의 uri"+resource.getURI());
	    //  file:///C:/mira/basicBoard/basicBoard/src/main/resources/static/uploadFiles/20230307162438689.txt
	    
	    
	    
	    HttpHeaders headers = new HttpHeaders();
	    //  (수정이전)headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getOriginName() + "\"");
	    //	헤더에 다운로드 받을 파일명 설정
	    //	CONTENT-DISPOSITON => HTTP응답에서 보내는 컨텐츠 표시명, 표시방법을 명시하는 헤더
	    //						> 다운로드할 파일의 originName포함해 사용자에게 보여질 파일명 지정하기 위해 사용
	    //	attachment 값은 다운로드할 파일, filename은 다운로드할 파일명 지정
	    //	" -> 공백이 있을 경우 인식 못하는 경우 있어서 사용, \는 "를 이스케이프하기 위함(X 시 큰따옴표가 문자열끝으로 인식)
	    //	HttpHeaders에 "attachment; filename=다운로드할 파일명"형식으로 컨텐츠 처리할 것임을 지시
	    //	toString 출력 시 : [Content-Disposition:"attachment; filename="Script.txt""]

	    String headerValue = String.format("attachment; filename*=UTF-8''%s", UriUtils.encode(attachment.getOriginName(), "UTF-8"));
	    //	(수정버전)한글파일 다운로드x 깨지는 오류 ->  인코딩 설정
	    headers.add(HttpHeaders.CONTENT_DISPOSITION, headerValue);
	    
	    
//	    log.info("ResponseEntity의 contentType : " + MediaType.APPLICATION_OCTET_STREAM);
	    
	    		// 스프링프레임워크에서 제공하는 HTTP응답 포함하는 클래스
	    		// HTTP응답코드, 헤더, 본문데이터 포함
	    return ResponseEntity
					    		.ok()	//상태코드 200 -> 정상
					            .headers(headers) 
					            // 응답에 해더 추가 x 시 이진데이터 바이너리코드 출력
					            // 헤더의 역할 : CONTENT-DISPOSITON헤더 추가해 브라우저가 다운로드할 파일명 알 수 있도록 하고, 이진데이터 다운로드 가능
					            .body(resource); 
	    						//파일 데이터 포함
	    						//이렇게 보내면 클라이언트에서 이진 데이터를 받아서 파일저장 / 바로출력 가능
	}

		
}
