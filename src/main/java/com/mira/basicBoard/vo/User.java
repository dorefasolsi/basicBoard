package com.mira.basicBoard.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private String userId;
	private String userPwd;
	private String userName;
	private String role;
	private Date lastLogin;
}
