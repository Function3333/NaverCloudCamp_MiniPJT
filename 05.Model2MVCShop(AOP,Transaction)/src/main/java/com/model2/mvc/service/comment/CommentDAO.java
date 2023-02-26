package com.model2.mvc.service.comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Comment;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class CommentDAO {
	
	public void addComment(Comment comment) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBUtil.getConnection();

			String query = "INSERT INTO product_comment"
					+" (comment_no, prod_no, user_id, content, reg_date)"
					+" values(seq_comment_comment_no.nextval, ?, ?, ?, SYSDATE)";
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, comment.getCommentProd().getProdNo());
			pstmt.setString(2, comment.getCommentUser().getUserId());
			pstmt.setString(3, comment.getContent());
			
			int result = pstmt.executeUpdate();
			System.out.println("CommentDAO addComment result = " + result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt);
		}
	}
	
	public List<Comment> getCommentListByProductNo(int prodNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Comment> commentList = null;
		
		try {
			con = DBUtil.getConnection();
			
			String query = "SELECT c.comment_no, c.prod_no, c.user_id, c.content, c.reg_date "
						+ " FROM product_comment c, product p"
						+ " WHERE c.prod_no = p.prod_no AND c.prod_no = ?";
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, prodNo);
			
			System.out.println("CommentDAO getCommentListByProdNo = " + query);
			rs = pstmt.executeQuery();
			
			commentList = new ArrayList();
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setCommentNo(rs.getInt("comment_no"));
				comment.setCommentProd(findProduct(rs.getInt("prod_no")));
				comment.setCommentUser(findUser(rs.getString("user_id")));
				comment.setContent(rs.getString("content"));
				comment.setRegDate(rs.getDate("reg_date"));
				
				commentList.add(comment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt, rs);
		}
		
		System.out.println("CommentDAO getCommentListByProdNo commentList = " + commentList);
		return commentList;
	}
	
	public void deleteComment(int commentNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBUtil.getConnection();
			
			String query = "DELETE FROM product_comment"
					+ " where comment_no = ?";
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, commentNo);
			
			
			int result = pstmt.executeUpdate();
			System.out.println("CommentDAO deleteComment result = " + result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt);
		}
	}
	
	
	
	public User findUser(String userId) {
		User User = null;
		try {
			User = new UserServiceImpl().getUser(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("purchaseDAO findUser = " + User);
		return User;
	}
	public Product findProduct(int prodId) {
		Product Product = null;
		try {
			Product = new ProductServiceImpl().getProduct(prodId);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("purchaseDAO findProduct = " + Product);
		return Product;
	}
}
