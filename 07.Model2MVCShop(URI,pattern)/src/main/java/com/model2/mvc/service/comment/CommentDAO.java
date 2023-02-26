package com.model2.mvc.service.comment;

import java.util.List;

import com.model2.mvc.service.domain.Comment;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;

public interface CommentDAO {
	
	public Comment getComment(int commentNo);
	
	public void addComment(Comment comment);
	
	public List<Comment> getCommentListByProductNo(int prodNo);
	
	public void deleteComment(int commentNo);
	
	public User findUser(String userId);
		
	public Product findProduct(int prodId);
}
