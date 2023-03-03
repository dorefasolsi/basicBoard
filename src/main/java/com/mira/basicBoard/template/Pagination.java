package com.mira.basicBoard.template;

import com.mira.basicBoard.vo.PageInfo;

public class Pagination {
	public static PageInfo getPageInfo(int listCount, int currentPage, int pageLimit, int boardLimit) {
		//최대페이지 = 전체 게시글 수 / 최대 게시판 보여지는 글 수 
		int maxPage = (int)Math.ceil((double)listCount / boardLimit);
		int startPage = (currentPage - 1) / pageLimit * pageLimit +1;
		int endPage = startPage + pageLimit -1;
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		return new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
	}
}
