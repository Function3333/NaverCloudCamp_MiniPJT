package com.model2.mvc.service.comment;

import java.util.List;

import com.model2.mvc.service.domain.Comment;

public interface CommentService {
	public void addComment(Comment comment);
	public void deleteComment(int commentNo);
	public List<Comment> getCommentListByProdNo(int prodNo);
	public void updateComment(int commentNo);
	
}
