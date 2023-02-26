package com.model2.mvc.view.product;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.comment.CommentService;
import com.model2.mvc.service.comment.impl.CommentServiceImpl;
import com.model2.mvc.service.domain.Comment;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class GetProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		String menu = request.getParameter("menu");
		
		System.out.println("GetProductAction prodNo = " + prodNo);
		String resultPage = null;
		
		ProductService productService = new ProductServiceImpl();
		request.setAttribute("product", productService.getProduct(prodNo));
		System.out.println("GetProductAction productVo = " + productService.getProduct(prodNo));
		
		CommentService commentService = new CommentServiceImpl();
		List<Comment> commentList = commentService.getCommentListByProdNo(prodNo);
		request.setAttribute("commentList", commentList);
		
		if(menu != null) {
			if(menu.equals("search")) {
				resultPage = "forward:/product/getProductView.jsp";
			}
			else if(menu.equals("manage")) {
				resultPage = "forward:/product/updateProduct.jsp";
			}
		}
		
		HttpSession session =  request.getSession(false);
		Set<Integer> history = (Set<Integer>) session.getAttribute("history");
		
		if(history == null) {
			history = new TreeSet();
			session.setAttribute("history", history);
		}
		
		history.add(prodNo);
		
		return resultPage;
	}
}
