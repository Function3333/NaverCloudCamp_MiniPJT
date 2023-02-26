package com.model2.mvc.service.domain;

import java.sql.Date;

public class Comment {
	//field, constructor
	private int commentNo;
	private Product commentProd;
	private User commentUser;
	private String content;
	private Date regDate;
	
	//method
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
	public Product getCommentProd() {
		return commentProd;
	}
	public void setCommentProd(Product commentProd) {
		this.commentProd = commentProd;
	}
	public User getCommentUser() {
		return commentUser;
	}
	public void setCommentUser(User commentUser) {
		this.commentUser = commentUser;
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
		String result = "commentNo = " + commentNo + "commentProd = " + commentProd + "commentUser = " + commentUser 
				+ "content = " + content + "regDate = " + regDate;
		return result;
	}
	
	
}
