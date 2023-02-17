package com.mira.basicBoard.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mira.basicBoard.repository.BoardRepository;
import com.mira.basicBoard.vo.Board;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	
	public ArrayList<Board> boardList() {
		return boardRepository.boardList();
	}


	public int boardWrite(Board board) {
		return boardRepository.boardWrite(board);
	}


	public Board boardDetail(int boardNo) {
		return boardRepository.boardDetail(boardNo);
	}


	public int boardDelete(int boardNo) {
		return boardRepository.boardDelete(boardNo);
	}


	public int boardUpdate(Board board) {
		return boardRepository.boardUpdate(board);
	}

}
