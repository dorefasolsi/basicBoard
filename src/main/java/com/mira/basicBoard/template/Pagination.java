package com.mira.basicBoard.template;

import com.mira.basicBoard.vo.PageInfo;

public class Pagination {
	public static PageInfo getPageInfo(int listCount, int currentPage, int pageLimit, int boardLimit) {
		//최대페이지 = 전체 게시글 수 / 최대 게시판 보여지는 글 수 
		int maxPage = (int)Math.ceil((double)listCount / boardLimit);
		
		//시작페이지
		//(현재페이지 -1) / 1페이지당보여줄게시글수 * 1페이지당보여줄게시글수+1
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		
		//끝페이지
		//시작페이지 + 한페이지당보여줄게시글수 - 1;
		int endPage = startPage + pageLimit -1;
		
		//마지막페이지번호가 최대 페이지를 넘지 않도록
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		return new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
	}
}
