package com.model2.mvc.service.product.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.ProductDAO;
import com.model2.mvc.service.domain.Product;

@Service
public class ProductServiceImpl implements ProductService {
	//field
	@Autowired
	private ProductDAO productDAO;
	
	//constructor
	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	//overriding
	@Override
	public int addProduct(Product productVO) {
		productVO.setManuDate(splitAndConcateManufactureDay(productVO.getManuDate()));
		return productDAO.insertProduct(productVO);
	}

	@Override
	public Product getProduct(int prodNo) {	
		return productDAO.findProduct(prodNo);
	}

	@Override
	public Product updateProduct(Product productVO) {
		return productDAO.updateProduct(productVO);
	}
	
	@Override
	public Map<String, Object> getProductList(Search serach) {
		List<Product> list = productDAO.getProductList(serach);
		int totalCount = productDAO.getTotalCount(serach);
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("totalCount", totalCount);
		
		return map;
	}
	
	public static String splitAndConcateManufactureDay(String manuDay) {
		String result = "";
		
		String[] arr = manuDay.split("-");
		
		for(String s : arr) {
			result += s;
		}
		return result;
	}
}