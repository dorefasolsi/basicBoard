package com.mira.basicBoard.repository;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mira.basicBoard.mapper.BoardMapper;
import com.mira.basicBoard.vo.Board;

@Repository
public class BoardRepository {
	
	@Autowired
	private BoardMapper boardMapper;
	
	public ArrayList<Board> boardList() {
		return boardMapper.boardList();
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
