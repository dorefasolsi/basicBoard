package com.mira.basicBoard.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	private int boardNo;			//게시글번호
	private String boardTitle;		//게시글제목
	private String boardContent;	//내용
	private Date createDate;		//작성일
	private String status;			//삭제여부
	private String userId;			//작성자
	private int viewCount;			//조회수
	private String attachment;		//첨부파일여부
}
