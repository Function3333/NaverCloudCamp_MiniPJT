package com.model2.mvc.web.comment;

import java.net.http.HttpRequest;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.service.comment.CommentService;
import com.model2.mvc.service.domain.Comment;
import com.model2.mvc.service.domain.User;


@Controller
public class CommentController {
	//field
	@Autowired
	private CommentService commentService;
	
	@RequestMapping("/addCommentAction.do")
	public void addComment(@RequestParam String content, HttpSession session) {		
		//@RequestParam(name =  "commentProdNo") int prodNo, 
		System.out.println("init commentService");
		String userId = ((User)session.getAttribute("user")).getUserId();
		
		System.out.println("==========================beforeAddComment==========================");
		Comment comment = new Comment();
		//comment.setProdNo(prodNo);
		comment.setUserId(userId);
		comment.setContent(content);

		commentService.addComment(comment);
		System.out.println("==========================afterAddComment==========================");
		//return "forward:/getProduct.do?menu=search&prodNo=" + prodNo;
	}
	
	@RequestMapping("/deleteComment.do")
	public String deleteComment(@RequestParam int commentNo, @RequestParam int prodNo, HttpSession session) {
		commentService.deleteComment(commentNo);
		return "forward:/getProduct.do?menu=search&prodNo=" + prodNo;
	}
}
