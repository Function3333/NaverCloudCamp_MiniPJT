package com.model2.mvc.view.purchase;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;

public class AddPurchaseViewAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProductService productService = new ProductServiceImpl();
		
		Product product = productService.getProduct(Integer.parseInt(request.getParameter("prod_no")));
		User user = (User)request.getSession().getAttribute("user");
		
		System.out.println("AddPurchaseViewAction Product = " + product);
		System.out.println("AddPurchaseViewAction User = " + user);
		
		Purchase purchase = new Purchase();
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		
		request.setAttribute("product", product);
		return "forward:/purchase/addPurchaseView.jsp";
	}


}
