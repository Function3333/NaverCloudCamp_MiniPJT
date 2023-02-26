package com.model2.mvc.view.comment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.comment.CommentService;
import com.model2.mvc.service.comment.impl.CommentServiceImpl;
import com.model2.mvc.service.domain.Comment;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class AddCommentAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		ProductService productService = new ProductServiceImpl();
		Product product = productService.getProduct(prodNo);
		
		User user = (User)request.getSession(false).getAttribute("user");
		
		Comment comment = new Comment();
		comment.setCommentProd(product);
		comment.setCommentUser(user);
		comment.setContent(request.getParameter("content"));
		System.out.println("AddCommentAction comment = " + comment);
		
		CommentService commentService = new CommentServiceImpl();
		commentService.addComment(comment);
		return "forward:/getProduct.do?menu=search&prodNo=" + prodNo;
	}

}
