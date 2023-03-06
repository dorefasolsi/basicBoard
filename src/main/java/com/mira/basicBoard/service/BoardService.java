package com.mira.basicBoard.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mira.basicBoard.mapper.BoardMapper;
import com.mira.basicBoard.repository.BoardRepository;
import com.mira.basicBoard.vo.Attachment;
import com.mira.basicBoard.vo.Board;
import com.mira.basicBoard.vo.PageInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final BoardMapper boardMapper;
	
	//전체 게시글 조회
	public ArrayList<Board> boardList(PageInfo pi, String category, String keyword) {
		return boardRepository.boardList(pi, category, keyword);
	}

	//총 게시글수 조회
	public int countBoardList(String category, String keyword) {
		return boardRepository.countBoardList(category, keyword);
	}

	//게시글 작성
	public int writeBoard(Board board) {
		return boardRepository.writeBoard(board);
	}

	//첨부파일 삽입
	@Transactional
	public int insertAttachment(Attachment attachment) {
		
		return boardMapper.insertAttachment(attachment);
	}
	
	//게시글 상세조회
	public Board detailBoard(int boardNo) {
		return boardRepository.detailBoard(boardNo);
	}
	
	public Attachment selectAttachment(int boardNo) {
		return boardRepository.selectAttachment(boardNo);
	}

	//조회수 증가
	public void increaseViewCount(int boardNo) {
		boardRepository.increaseViewCount(boardNo);
	}

	
	//게시글 삭제
	public int deleteBoard(int boardNo) {
		return boardRepository.deleteBoard(boardNo);
	}


	//게시글 수정
	public int updateBoard(Board board) {
		return boardRepository.updateBoard(board);
	}

	

	







}
