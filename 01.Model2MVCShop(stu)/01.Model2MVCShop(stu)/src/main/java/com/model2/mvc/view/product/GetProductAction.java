package com.model2.mvc.view.product;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class GetProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		String menu = request.getParameter("menu");
		
		System.out.println("GetProductAction prodNo = " + prodNo);
		System.out.println("GetProductAction");
		String resultPage = null;
		
		ProductService productService = new ProductServiceImpl();
		request.setAttribute("productVO", productService.getProduct(prodNo));
		System.out.println("GetProductAction productVo = " + productService.getProduct(prodNo));
		
		if(menu != null) {
			if(menu.equals("search")) {
				resultPage = "forward:/product/getProductView.jsp";
			}
			else if(menu.equals("manage")) {
				resultPage = "forward:/product/updateProduct.jsp";
			}
		}
		
		Set<Integer> history = (Set<Integer>) request.getSession(false).getAttribute("history");
		history.add(prodNo);
		
		return resultPage;
	}
}
