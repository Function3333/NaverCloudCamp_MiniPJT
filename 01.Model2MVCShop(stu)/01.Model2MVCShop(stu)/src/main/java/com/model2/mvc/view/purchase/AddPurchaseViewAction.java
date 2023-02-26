package com.model2.mvc.view.purchase;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class AddPurchaseViewAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProductService productService = new ProductServiceImpl();
		
		ProductVO productVO = productService.getProduct(Integer.parseInt(request.getParameter("prod_no")));
		UserVO userVO = (UserVO)request.getSession().getAttribute("user");
		
		System.out.println("AddPurchaseViewAction productVO = " + productVO);
		System.out.println("AddPurchaseViewAction userVO = " + userVO);
		
		PurchaseVO purchaseVO = new PurchaseVO();
		purchaseVO.setPurchaseProd(productVO);
		purchaseVO.setBuyer(userVO);
		
		request.setAttribute("productVO", productVO);
		return "forward:/purchase/addPurchaseView.jsp";
	}


}
