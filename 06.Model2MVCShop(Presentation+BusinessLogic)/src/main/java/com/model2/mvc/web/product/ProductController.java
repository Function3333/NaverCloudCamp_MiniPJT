package com.model2.mvc.web.product;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.service.comment.CommentService;
import com.model2.mvc.service.comment.impl.CommentServiceImpl;
import com.model2.mvc.service.domain.Comment;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

@Controller
public class ProductController {
	//field
	@Autowired
	private ProductService productService;
	@Autowired
	private CommentService commentService;
	
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	@Value("#{commonProperties['pageUnit'] ?: 2}")
	int pageSize;
	
	//constructor
	public ProductController() {
		System.out.println("[init :: ProductController]");
	}
	
	@RequestMapping("/addProduct.do")
	public String addProduct(@ModelAttribute Product product, Model model) {
		System.out.println("[addProdcut.do Controller init]");
		
		System.out.println("addProduct.do product = " + product);
		Product addProduct = productService.addProduct(product);
		
		model.addAttribute("product", product);
		return "forward:product/addProductResultView.jsp";
	}
	
	@RequestMapping("/getProduct.do")
	public String getProduct(@RequestParam("prodNo") int prodNo, @RequestParam("menu") String menu, Model model, HttpSession session) {
		System.out.println("[getProdcut.do Controller init]");
		
		Product findProduct = productService.getProduct(prodNo);
		List<Comment> commentList = commentService.getCommentListByProdNo(prodNo);
		
		model.addAttribute("product", findProduct);
		model.addAttribute("commentList", commentList);
		
		String resultPage = null;
		if(menu != null) {
			if(menu.equals("search")) {
				resultPage = "forward:/product/getProductView.jsp";
			}
			else if(menu.equals("manage")) {
				resultPage = "forward:/product/updateProduct.jsp";
			}
		}
		
		Set<Integer> history = (Set<Integer>) session.getAttribute("history");
		if(history == null) {
			history = new TreeSet();
			session.setAttribute("history", history);
		}
		history.add(prodNo);
		
		System.out.println("==============history==============");
		for(int i : history) {
			System.out.println("history prodNo = " + history);
		}
		return resultPage;
	}
	
	@RequestMapping("/listProduct.do")
	public String listProduct(HttpServletRequest request, @ModelAttribute("search") Search search, @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
								@RequestParam(value = "menu", required = false) String menu, Model model) {
		System.out.println("[listProduct.do Controller init]");		
		
		search.setPageSize(pageSize);
		search.setCurrentPage(currentPage);
		
		System.out.println("listProduct.do search = "  + search);
		System.out.println("listProduct.do currentPage = "  + currentPage);
		Map<String,Object> map = productService.getProductList(search);

		Page resultPage = new Page(currentPage, (Integer)map.get("totalCount"), pageUnit, pageSize);

		model.addAttribute("search", search);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
	
		String requestURI = request.getRequestURI();
		return fowardDirection(menu, requestURI);
	}
	
	@RequestMapping("/updateProductView.do")
	public String updateProductView(@RequestParam("prodNo") int prodNo, Model model) {
		System.out.println("[updateProductView.do Controller init]");		
		
		Product product = productService.getProduct(prodNo);
		model.addAttribute("product", product);
		
		return "forward:/product/updateProduct.jsp";
	}
	
	@RequestMapping("/updateProduct.do")
	public String updateProduct(@ModelAttribute Product product, Model model) {
		System.out.println("[/updateProduct.do Controller init]");	
			
		Product findProduct = productService.getProduct(product.getProdNo());
		
		System.out.println("updateProduct.do findProduct = " + findProduct);
		System.out.println("updateProduct.do product = " + product);
		
		if(findProduct.getProTranCode() == null) {
			findProduct.setProdName(product.getProdName());
			findProduct.setFileName(product.getFileName());
			findProduct.setManuDate(product.getManuDate());
			findProduct.setPrice(product.getPrice());
			findProduct.setProdDetail(product.getProdDetail());
			productService.updateProduct(findProduct);
		}

		String resultPage = "forward:/getProduct.do?&menu=search";
		return resultPage;
	}
	
	public static String fowardDirection(String menu, String requestURI) {
		String direction = "";
		
		System.out.println("fowardDirection URI = " + requestURI);
		if(menu == null) {
			if(requestURI.contains("/listProduct.do")) {
				direction =  "forward:/product/listProduct.jsp";
			}else {
				direction = "forward:/listSale.do";
			}
		}else {
			if(menu.equals("manage")) {
				direction = "forward:/listSale.do";
			}else {
				direction = "forward:/product/listProduct.jsp";
			}
		}	
		System.out.println("ListProductionAction forwardDircetion = " + direction);
		return direction;
	}
}
