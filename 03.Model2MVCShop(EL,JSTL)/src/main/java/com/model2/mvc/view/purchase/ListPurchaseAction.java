package com.model2.mvc.view.purchase;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.User;

public class ListPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User User = (User)request.getSession(false).getAttribute("user");
		System.out.println("ListPurchaseAction = User" + User);
		
		Search search=new Search();
		
		int currentPage=1;

		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
			
		search.setCurrentPage(currentPage);
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		Map<String,Object> map=purchaseService.getPurchaseList(search, User.getUserId());

		Page resultPage	= new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		request.setAttribute("list", map.get("list"));
		request.setAttribute("search", search);
		request.setAttribute("resultPage", resultPage);

		return "forward:/purchase/listPurchaseView.jsp";
	}
}
