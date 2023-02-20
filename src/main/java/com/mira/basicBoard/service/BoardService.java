package com.mira.basicBoard.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mira.basicBoard.repository.BoardRepository;
import com.mira.basicBoard.vo.Board;
import com.mira.basicBoard.vo.PageInfo;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	
	public ArrayList<Board> boardList(PageInfo pageInfo) {
		return boardRepository.boardList(pageInfo);
	}

	public int boardListCount(PageInfo pi) {
		return boardRepository.boardListCount(pi);
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
