package com.mira.basicBoard.paging;

import lombok.Data;

@Data
public class Pagination {
	private int totalRecordCount; 	// 전체 데이터 수
	private int totalPageCount; 	// 전체 페이지 수
	private int startPage;			// 첫 페이지 번호
	private int endPage;			// 끝 페이지 번호
	private int limitStart;			// limit 시작 위치
	private boolean existPrevPage;	// 이전 페이지 존재 여부
	private boolean existNextPage;	// 다음 페이지 존재 여부
	
	
}
