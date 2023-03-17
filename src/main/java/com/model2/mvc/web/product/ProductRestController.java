package com.model2.mvc.web.product;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@RestController
@RequestMapping("/product")
public class ProductRestController {

	//field
	@Autowired
	ProductService productService;
	
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	@Value("#{commonProperties['pageUnit'] ?: 2}")
	int pageSize;
	
	//constructor
	public ProductRestController() {
		System.out.println("[init  ::  ProductRestController]");
	}
	
	@GetMapping("json/getList")
	public List<String> getProductNameList() {
		List<String> prodNameList = productService.getProductName();
		
		return prodNameList;
	}
	
	@PostMapping("json/getList")
	public List<Product> getList(@RequestBody Search search) {
		search.setPageSize(pageSize);
		Map<String, Object> map = productService.getProductList(search);
		
		List<Product> list = (List<Product>) map.get("list");
		
		return list;
	}
}
