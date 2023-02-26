package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.vo.ProductVO;

public class ProductServiceImpl implements ProductService {
	//field
	private ProductDAO productDAO;
	
	//constructor
	public ProductServiceImpl() {
		productDAO = new ProductDAO();
	}

	//overriding
	@Override
	public ProductVO addProduct(ProductVO productVO) {
		productVO.setManuDate(splitAndConcateManufactureDay(productVO.getManuDate()));
		productDAO.insertProduct(productVO);
		
		return productVO;
	}

	@Override
	public ProductVO getProduct(int prodNo) {	
		return productDAO.findProduct(prodNo);
	}

	@Override
	public ProductVO updateProduct(ProductVO productVO) {
		return productDAO.updateProduct(productVO);
	}
	
	@Override
	public HashMap<String, Object> getProductList(SearchVO serachVO) {
		return productDAO.getProductList(serachVO);
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
