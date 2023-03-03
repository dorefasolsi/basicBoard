package com.mira.basicBoard.repository;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mira.basicBoard.mapper.BoardMapper;
import com.mira.basicBoard.vo.Board;
import com.mira.basicBoard.vo.PageInfo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
	
	private final BoardMapper boardMapper;
	
	
	public ArrayList<Board> boardList(PageInfo pi, String category, String keyword) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		return boardMapper.boardList(rowBounds, category, keyword);
	}

	public int countBoardList(String category, String keyword) {
		return boardMapper.countBoardList(category, keyword);
	}

	public int writeBoard(Board board) {
		return boardMapper.writeBoard(board);
	}

	public Board detailBoard(int boardNo) {
		return boardMapper.detailBoard(boardNo);
	}

	public void increaseViewCount(int boardNo) {
		boardMapper.increaseViewCount(boardNo);
	}
	
	public int deleteBoard(int boardNo) {
		return boardMapper.deleteBoard(boardNo);
	}

	public int updateBoard(Board board) {
		return boardMapper.updateBoard(board);
	}

	public ArrayList<Board> searchTest(String category, String keyword) {
		
		return boardMapper.searchTest(category, keyword);
	}





}
