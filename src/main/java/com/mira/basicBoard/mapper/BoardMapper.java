package com.mira.basicBoard.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.mira.basicBoard.vo.Board;
import com.mira.basicBoard.vo.PageInfo;

@Mapper
public interface BoardMapper {

	public ArrayList<Board> boardList(PageInfo pageInfo); // 게시글 리스트

	public int boardListCount(PageInfo pi); //게시글 수

	public int boardWrite(Board board);


	public Board boardDetail(int boardNo);

	public int boardDelete(int boardNo);

	public int boardUpdate(Board board);

	public int boardListCount();


}
