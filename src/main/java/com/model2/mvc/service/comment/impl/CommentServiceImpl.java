package com.model2.mvc.service.comment.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model2.mvc.service.comment.CommentService;
import com.model2.mvc.service.comment.CommentDAO;
import com.model2.mvc.service.domain.Comment;

@Service
public class CommentServiceImpl implements CommentService {
	
	//field
	@Autowired
	private CommentDAO commentDAO;
	
	//constructor
	public CommentServiceImpl(CommentDAO commentDAO) {
		this.commentDAO = commentDAO;
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

	@Override
	public Comment getComment(int commentNo) {
		return commentDAO.getComment(commentNo);
	}

}
