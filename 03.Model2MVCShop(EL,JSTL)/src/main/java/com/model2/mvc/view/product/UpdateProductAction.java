package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.common.util.TransCode;

public class UpdateProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		ProductService productService = new ProductServiceImpl();
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		System.out.println("UpdateProductAction prodNo = " + prodNo);
		String tranCode = productService.getProduct(prodNo).getProTranCode();
		System.out.println("UpdateProductAction tranCode = " + tranCode);
		
		Product product = null;
		if(tranCode == null) {
			product = new Product();
			product.setProdNo(prodNo);
			product.setProdName(request.getParameter("prodName"));
			product.setFileName(request.getParameter("fileName"));
			product.setManuDate(request.getParameter("manuDate"));
			product.setPrice(Integer.parseInt(request.getParameter("price")));
			product.setProdDetail(request.getParameter("prodDetail"));
			
			Product updateProduct = productService.updateProduct(product);
		}else {
			product = productService.getProduct(prodNo);
		}
		
		
		System.out.println("updateProductAction updateProduct = " + product);
		
		request.setAttribute("product", product);
		
		String resultPage = "forward:/getProduct.do?prodNo=" + prodNo + "&menu="+"search";
		System.out.println("UpdateProductAction resultPage = " + resultPage);
		return resultPage;
	}

}
