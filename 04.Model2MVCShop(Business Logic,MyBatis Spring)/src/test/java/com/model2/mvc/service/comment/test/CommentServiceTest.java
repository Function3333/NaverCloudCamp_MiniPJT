package com.model2.mvc.service.comment.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.service.comment.CommentService;
import com.model2.mvc.service.domain.Comment;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class CommentServiceTest {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;

	//@Test
	public void testAddComment() throws Exception {
		//given
		Comment comment = new Comment();
		comment.setProdNo(10021);
		comment.setUserId("user01");
		comment.setContent("Add Comment Test");
		
		//when
		commentService.addComment(comment);
		
		//then
		Assert.assertNotEquals(comment.getCommentNo(), 0);
	}
	
	//@Test
	public void testGetCommentList() {
		//given
		//when
		List<Comment> list = commentService.getCommentListByProdNo(10021);
		
		//then
		Assert.assertEquals(2, list.size());
	}
	
	//@Test
	public void testGetComment() throws Exception {
		//given
		User user = new User();
		user.setUserId("commentTestUser");
		user.setUserName("commentTestUser");
		user.setPassword("password");
		userService.addUser(user);
		
		Product product = new Product();
		product.setProdName("commentTestProduct");
		productService.addProduct(product);
		
		Comment comment = new Comment();
		comment.setProdNo(product.getProdNo());
		comment.setUserId(user.getUserId());
		comment.setContent("commentTestProduct");
		commentService.addComment(comment);
		
		System.out.println("testGetComment commentNo = " + comment.getCommentNo());
		
		//then
		Comment findComment = commentService.getComment(comment.getCommentNo());
		
		//then
		Assert.assertEquals(comment.getCommentNo(), findComment.getCommentNo());
	}
	
	@Test
	public void testDeleteComment() throws Exception {
		//given
		User user = new User();
		user.setUserId("commentTestUser");
		user.setUserName("commentTestUser");
		user.setPassword("password");
		userService.addUser(user);
		
		Product product = new Product();
		product.setProdName("commentTestProduct");
		productService.addProduct(product);
		
		Comment comment = new Comment();
		comment.setProdNo(product.getProdNo());
		comment.setUserId(user.getUserId());
		comment.setContent("commentTestProduct");
		commentService.addComment(comment);
		
		//when
		commentService.deleteComment(comment.getCommentNo());
		
		//then
		Comment findComment = commentService.getComment(comment.getCommentNo());
		Assert.assertNull(findComment);
	}
}
