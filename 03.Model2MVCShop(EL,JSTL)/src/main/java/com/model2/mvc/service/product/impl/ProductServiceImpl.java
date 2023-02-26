package com.model2.mvc.service.product.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.domain.Product;

public class ProductServiceImpl implements ProductService {
	//field
	private ProductDAO productDAO;
	
	//constructor
	public ProductServiceImpl() {
		productDAO = new ProductDAO();
	}

	//overriding
	@Override
	public Product addProduct(Product productVO) {
		productVO.setManuDate(splitAndConcateManufactureDay(productVO.getManuDate()));
		productDAO.insertProduct(productVO);
		
		return productVO;
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
	public Map<String, Object> getProductList(Search serachVO) {
		Map<String, Object> map = null;
		try {
			map = productDAO.getProductList(serachVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static String splitAndConcateManufactureDay(String manuDay) {
		String result = null;
		
		if(manuDay != null) {
			String[] arr = manuDay.split("-");
			
			for(String s : arr) {
				result += s;
			}
		}
		return result;
	}
}
