package com.model2.mvc.service.domain;

import java.sql.Date;

public class Comment {
	//field, constructor
	private int commentNo;
	private int prodNo;
	private String userId;
	private String content;
	private Date regDate;
	
	//method
	public int getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}

	public int getProdNo() {
		return prodNo;
	}

	public void setProdNo(int prodNo) {
		this.prodNo = prodNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	@Override
	public String toString() {
		String result = "commentNo = " + commentNo + "prodNo = " + prodNo + "userId = " + userId 
				+ "content = " + content + "regDate = " + regDate;
		return result;
	}
}
