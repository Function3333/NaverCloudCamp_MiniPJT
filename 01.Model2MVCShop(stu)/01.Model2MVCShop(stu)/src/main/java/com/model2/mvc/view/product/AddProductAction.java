package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;

public class AddProductAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProductVO productVO = new ProductVO();
		
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setFileName(request.getParameter("fileName"));
		productVO.setManuDate(request.getParameter("manuDate"));
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		productVO.setProdDetail(request.getParameter("prodDetail"));

		
		ProductService productService = new ProductServiceImpl();
		productService.addProduct(productVO);
		
		
		request.setAttribute("productVO", productVO);
		return "forward:product/addProductResultView.jsp";
	}
}
