package com.model2.mvc.view.comment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.comment.CommentService;
import com.model2.mvc.service.comment.impl.CommentServiceImpl;

public class DeleteCommentAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("init DeleteCommentAction");
		
		int commentNo = Integer.parseInt(request.getParameter("commentNo"));
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		System.out.println("DeleteCommentAction commentNo = " + commentNo);
		System.out.println("DeleteCommentAction prodNo = " + prodNo);
		
		CommentService commentService = new CommentServiceImpl();
		commentService.deleteComment(commentNo);
		
		return "forward:/getProduct.do?menu=search&prodNo=" + prodNo;
	}

}
