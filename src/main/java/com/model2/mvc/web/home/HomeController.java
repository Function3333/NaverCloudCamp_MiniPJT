package com.model2.mvc.web.home;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;

@Controller
public class HomeController {

	//field
	@Autowired
	private ProductService productService;
	
	@Value("#{commonProperties['pageUnit'] ?: 2}")
	int pageSize;
		
	//constructor
	public HomeController() {
		System.out.println("[init  ::  HomeController]");
	}
	
	@GetMapping("/")
	public String home(@ModelAttribute Search search, @RequestParam(value="currentPage", defaultValue = "1") int currentPage, Model model) {
		search.setPageSize(pageSize);
		search.setCurrentPage(currentPage);
		
		Map<String,Object> map = productService.getProductList(search);

		model.addAttribute("search", search);
		model.addAttribute("list", map.get("list"));
				
		return "/home.jsp";
	}
	
	@GetMapping("/adminPage")
	public String adminPage() {
		System.out.println("[init  ::  /adminPage]");
		return "/admin/index.jsp";
	}
}
