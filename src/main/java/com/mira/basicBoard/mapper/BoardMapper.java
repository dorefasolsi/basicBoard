package com.mira.basicBoard.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.mira.basicBoard.vo.Board;
import com.mira.basicBoard.vo.Criteria;

@Mapper
public interface BoardMapper {

	public ArrayList<Board> boardList(RowBounds rowbounds); // 게시글 리스트

	public int countBoardList(); //게시글 수

	public int writeBoard(Board board);

	public Board detailBoard(int boardNo);

	public void increaseViewCount(int boardNo);

	public int deleteBoard(int boardNo);

	public int updateBoard(Board board);

}
