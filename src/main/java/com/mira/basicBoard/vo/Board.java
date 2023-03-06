package com.mira.basicBoard.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private Date createDate;
	private String userId;
	private int viewCount;
	private String attachment;
}
