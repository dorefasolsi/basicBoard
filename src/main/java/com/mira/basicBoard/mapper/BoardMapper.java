package com.mira.basicBoard.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.mira.basicBoard.vo.Board;
import com.mira.basicBoard.vo.Criteria;

@Mapper
public interface BoardMapper {

	public ArrayList<Board> boardList(RowBounds rowbounds); // 게시글 리스트

	public int boardListCount(); //게시글 수

	public int boardWrite(Board board);


	public Board boardDetail(int boardNo);

	public int boardDelete(int boardNo);

	public int boardUpdate(Board board);

	public void increaseViewCount(int boardNo);



}
