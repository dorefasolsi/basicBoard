package com.mira.basicBoard.vo;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//@NoArgsConstructor
public class PageInfo {

//	private int listCount;		//전체 게시글 수
	private int currentPage; 	//현재 페이지
	private int recordSize;		//페이지당 출력할 데이터 개수
//	private int skip; 			//스킵
	private int pageSize;		//화면 하단에 출력할 페이지 사이즈(1~10)
	private String keyword;		//검색 키워드
	private String searchType;	//검색타입

	
	//기본생성자
	public PageInfo() {
		this.currentPage = 1;
		this.recordSize = 10;
		this.pageSize = 10;
	}

	//생성자
	public int getOffset() {
		return (currentPage - 1) * recordSize;
	}
	
	
	
	
}
