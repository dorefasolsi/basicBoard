package com.mira.basicBoard.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
	private int listCount;		//전체게시글수
	private int currentPage;	//현재페이지
	private int pageLimit;		//한화면에보여질페이지수
	private int boardLimit;		//한페이지당보여질게시글수
	private int maxPage;		//최대페이지
	private int startPage;		//시작페이지(페이징바의 시작)
	private int endPage;		//마지막페이지(페이징바의끝)
	
	
}
