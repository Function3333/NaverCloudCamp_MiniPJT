package com.model2.mvc.web.comment;

import java.net.http.HttpRequest;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.service.comment.CommentService;
import com.model2.mvc.service.domain.Comment;
import com.model2.mvc.service.domain.User;


@Controller
@RequestMapping("/comment")
public class CommentController {
	//field
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/addComment")
	public String addComment(@RequestParam String content, @RequestParam(name =  "commentProdNo") int prodNo, HttpSession session) {		 
		System.out.println("init commentService");
		String userId = ((User)session.getAttribute("user")).getUserId();
		
		Comment comment = new Comment();
		comment.setProdNo(prodNo);
		comment.setUserId(userId);
		comment.setContent(content);

		commentService.addComment(comment);
		return "forward:/product/getProduct.do?menu=search&prodNo=" + prodNo;
	}
	
	@RequestMapping("/deleteComment")
	public String deleteComment(@RequestParam int commentNo, @RequestParam int prodNo, HttpSession session) {
		commentService.deleteComment(commentNo);
		return "forward:/product/getProduct?menu=search&prodNo=" + prodNo;
	}
	
	@GetMapping("/new")
	public String newHomePage() {
		return "forward:/product/home.jsp";
	}
}
