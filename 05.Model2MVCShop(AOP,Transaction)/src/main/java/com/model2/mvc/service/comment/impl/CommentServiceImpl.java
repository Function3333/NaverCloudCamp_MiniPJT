package com.model2.mvc.service.comment.impl;

import java.util.List;

import com.model2.mvc.service.comment.CommentService;
import com.model2.mvc.service.comment.CommentDAO;
import com.model2.mvc.service.domain.Comment;

public class CommentServiceImpl implements CommentService {
	private CommentDAO commentDAO;
	
	
	public CommentServiceImpl() {
		commentDAO = new CommentDAO();
	}

	@Override
	public void addComment(Comment comment) {
		commentDAO.addComment(comment);
	}

	@Override
	public void deleteComment(int commentNo) {
		commentDAO.deleteComment(commentNo);
	}

	@Override
	public List<Comment> getCommentListByProdNo(int prodNo) { 
		return commentDAO.getCommentListByProductNo(prodNo);
	}

	@Override
	public void updateComment(int commentNo) {
		// TODO Auto-generated method stub

	}

}
