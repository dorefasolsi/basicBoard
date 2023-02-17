package com.mira.basicBoard.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.mira.basicBoard.vo.Board;

@Mapper
public interface BoardMapper {

	public ArrayList<Board> boardList();

	public int boardWrite(Board board);

	public Board boardDetail(int boardNo);

	public int boardDelete(int boardNo);

	public int boardUpdate(Board board);

}
