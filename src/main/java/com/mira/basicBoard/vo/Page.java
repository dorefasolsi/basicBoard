package com.mira.basicBoard.vo;

import lombok.Data;

@Data
public class Page {
	private int pageStart;		//페이지시작번호
	private int pageEnd;		//페이지끝번호
	private boolean next;		//이전
	private boolean prev;		//다음버튼 존재유무
	private int recordTotal;	//행 전체 개수
	
	private PageInfo pi;		//현재페이지, 행표시수, 검색키워드, 검색종류
	
	
	
	//생성자(클래스 호출 시 각 변수값 초기화)
	public Page(PageInfo pi, int recordTotal) {
		//pi, recordTotal
		this.pi = pi;
		this.recordTotal = recordTotal;
		
		//페이지끝번호
		this.pageEnd = (int)(Math.ceil(pi.getCurrentPage() / 10.0)) * 10;
		
		//페이지 시작번호
		this.pageStart = this.pageEnd - 9;
		
		//전체 마지막 페이지 번호
		int realEnd = (int)(Math.ceil(recordTotal * 1.0 / pi.getRecordSize()));
		
		
		//페이지 끝 번호 유효성 체크
		if(realEnd < pageEnd) {
			this.pageEnd = realEnd;
		}
		
		//이전 버튼 값 초기화
		this.prev = this.pageStart > 1;
		
		//다음버튼 값 초기화
		this.next = this.pageEnd < realEnd;
		
	}
	
	
	
}
