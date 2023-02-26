package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.TransCode;

public class UpdateProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		ProductService productService = new ProductServiceImpl();
		int prodNo = Integer.parseInt(request.getParameter("prodNO"));
		
		String tranCode = productService.getProduct(prodNo).getProTranCode();
		System.out.println("UpdateProductAction tranCode = " + tranCode);
		
		if(tranCode == null) {
			ProductVO productVO = new ProductVO();
			
			productVO.setProdNo(prodNo);
			productVO.setProdName(request.getParameter("prodName"));
			productVO.setFileName(request.getParameter("fileName"));
			productVO.setManuDate(request.getParameter("manuDate"));
			productVO.setPrice(Integer.parseInt(request.getParameter("price")));
			productVO.setProdDetail(request.getParameter("prodDetail"));
			ProductVO updateVO = productService.updateProduct(productVO);
		}
		
		
		
		String resultPage = "forward:/getProduct.do?prodNo=" + prodNo + "&menu="+"search";
		System.out.println("UpdateProductAction resultPage = " + resultPage);
		return resultPage;
	}

}
