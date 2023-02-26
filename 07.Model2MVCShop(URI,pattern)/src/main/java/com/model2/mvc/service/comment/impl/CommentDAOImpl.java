package com.model2.mvc.service.comment.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model2.mvc.service.domain.Comment;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.impl.UserServiceImpl;

@Repository
public class CommentDAOImpl implements com.model2.mvc.service.comment.CommentDAO{
	//field
	@Autowired
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public void addComment(Comment comment) {
		sqlSession.insert("CommentMapper.addComment", comment);
	}

	@Override
	public List<Comment> getCommentListByProductNo(int prodNo) {
		return sqlSession.selectList("CommentMapper.getCommentList", prodNo);
	}

	@Override
	public void deleteComment(int commentNo) {
		sqlSession.delete("CommentMapper.deleteComment", commentNo);
	}

	@Override
	public User findUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product findProduct(int prodId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment getComment(int commentNo) {
		return sqlSession.selectOne("CommentMapper.getComment", commentNo);
	}

}
