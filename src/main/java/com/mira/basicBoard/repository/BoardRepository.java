package com.mira.basicBoard.repository;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mira.basicBoard.mapper.BoardMapper;
import com.mira.basicBoard.vo.Board;
import com.mira.basicBoard.vo.PageInfo;

@Repository
public class BoardRepository {
	
	@Autowired
	private BoardMapper boardMapper;
	
	
	public ArrayList<Board> boardList(PageInfo pi) {

		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		return boardMapper.boardList(rowBounds);
		
		
	}

	public int boardListCount() {
		return boardMapper.boardListCount();
	}

	public int boardWrite(Board board) {
		return boardMapper.boardWrite(board);
	}

	public Board boardDetail(int boardNo) {
		return boardMapper.boardDetail(boardNo);
	}

	public int boardDelete(int boardNo) {
		return boardMapper.boardDelete(boardNo);
	}

	public int boardUpdate(Board board) {
		return boardMapper.boardUpdate(board);
	}



}
