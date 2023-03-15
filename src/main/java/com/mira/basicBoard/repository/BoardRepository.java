package com.mira.basicBoard.repository;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mira.basicBoard.mapper.BoardMapper;
import com.mira.basicBoard.vo.Attachment;
import com.mira.basicBoard.vo.Board;
import com.mira.basicBoard.vo.PageInfo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
	
	private final BoardMapper boardMapper;
	
	// 게시글 조회
	public ArrayList<Board> boardList(PageInfo pi, String category, String keyword) {
		
		//현재 페이지의 시작 인덱스 계산
		//(현재페이지 -1) * 페이지당 보여질 게시글의 개수
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		//한페이지당 보여질 게시글의 개수
		int limit = pi.getBoardLimit();
		
		//마이바티스에서 제공하는 페이징 처리 클래스, offset부터 limit까지의 데이터만 가져오도록 함
		RowBounds rowBounds = new RowBounds(offset, limit);
		return boardMapper.boardList(rowBounds, category, keyword);
	}

	// 총 게시글 수 조회
	public int countBoardList(String category, String keyword) {
		return boardMapper.countBoardList(category, keyword);
	}

	// 게시글 작성
	public int writeBoard(Board board) {
		return boardMapper.writeBoard(board);
	}

	// 게시글 상세조회
	public Board detailBoard(int boardNo) {
		return boardMapper.detailBoard(boardNo);
	}

	// 조회수 증가
	public void increaseViewCount(int boardNo) {
		boardMapper.increaseViewCount(boardNo);
	}
	
	// 게시글 삭제
	public int deleteBoard(int boardNo) {
		return boardMapper.deleteBoard(boardNo);
	}

	// 첨부파일 삭제
	public int deleteAttachment(int boardNo) {
		return boardMapper.deleteAttachment(boardNo);
	}
	
	// 게시글 수정
	public int updateBoard(Board board) {
		return boardMapper.updateBoard(board);
	}

	// 첨부파일 조회(게시글번호 이용)
	public Attachment selectAttachment(int boardNo) {
		return boardMapper.selectAttachment(boardNo);
	}

	// 첨부파일 가져오기(파일번호 이용)
	public Attachment getAttachment(int fileNo) {
		return boardMapper.getAttachment(fileNo);
	}







}
