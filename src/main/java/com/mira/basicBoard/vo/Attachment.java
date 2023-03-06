package com.mira.basicBoard.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Attachment {
	private int fileNo;
	private String originName;	//원래 파일명
	private String storedName;  //변경된 파일명
	private String path;		//파일 저장경로
	private Date uploadDate;	//파일 업로드 날짜
	private String attached;	//파일 첨부여부
	private String status;		//삭제여부
	private int boardNo;		//글번호
	private String userId;		//작성자
	
}
