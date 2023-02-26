package com.model2.mvc.view.product;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class ListProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("euc-kr");
		Search search = new Search();
		
		int currentPage=1;
		
		if(request.getParameter("currentPage") != null ) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		String searchCondition = request.getParameter("searchCondition");
		String searchKeyword = request.getParameter("searchKeyword");
		
		if(!CommonUtil.null2str(searchKeyword).equals("")) {
			search.setSearchKeyword(searchKeyword);
		}
		
		search.setSearchCondition(CommonUtil.null2str(searchCondition));
		search.setCurrentPage(currentPage);

		
		int pageSize = Integer.parseInt(this.getServletContext().getInitParameter("pageSize"));
		int pageUnit = Integer.parseInt(this.getServletContext().getInitParameter("pageUnit"));
		
		search.setPageSize(pageSize);
		
		ProductService service = new ProductServiceImpl();
		System.out.println("ListProductAction result search = " + search);
		Map<String,Object> map = service.getProductList(search);

		Page resultPage = new Page(currentPage, (Integer)map.get("totalCount"), pageUnit, pageSize);
		System.out.println("ListProductAction page = " + resultPage);
		
		request.setAttribute("search", search);
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
	
		String menu = CommonUtil.null2str(request.getParameter("menu"));
		
		System.out.println("ListProductAction = search " + search);
		return fowardDirection(menu);
	}
	

	public static String fowardDirection(String menu) {
		String direction = "";
		if(menu.equals("manage")) {
			direction = "forward:/listSale.do";
		}else {
			direction = "forward:/product/listProduct.jsp";
		}
		System.out.println("ListProductionAction forwardDircetion = " + direction);
		return direction;
	}
}
