package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.common.util.TransCode;
import com.model2.mvc.service.domain.User;

public class AddPurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Purchase purchase = new Purchase();
		ProductService productService = new ProductServiceImpl();
		
		
		User user = (User)request.getSession(true).getAttribute("user");
		Product product = productService.getProduct(Integer.parseInt(request.getParameter("prodNO")));
		
		System.out.println("AddPurchaseAction User = " + user);
		System.out.println("AddPurchaseAction Product = " + product);
		
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchase.setTranCode(TransCode.purchaseComplete);
		
		new PurchaseServiceImpl().addPurchase(purchase);
		
		request.setAttribute("purchase", purchase);
		return "forward:/purchase/addPurchaseResultView.jsp";
	}
	
}
