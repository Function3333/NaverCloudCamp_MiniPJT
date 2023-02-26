package com.model2.mvc.view.purchase;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class ListPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserVO userVO = (UserVO)request.getSession(false).getAttribute("user");
		System.out.println("ListPurchaseAction = userVO" + userVO);
		
		SearchVO searchVO=new SearchVO();
		
		int page=1;
		if(request.getParameter("page") != null)
		{
			page=Integer.parseInt(request.getParameter("page"));
		}
			
		
		searchVO.setPage(page);
		searchVO.setSearchCondition(request.getParameter("searchCondition"));
		searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
		
		String pageUnit=getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageUnit));
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		HashMap<String,Object> map=purchaseService.getPurchaseList(searchVO, userVO.getUserId());

		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);

		return "forward:/purchase/listPurchaseView.jsp";
	}
}
