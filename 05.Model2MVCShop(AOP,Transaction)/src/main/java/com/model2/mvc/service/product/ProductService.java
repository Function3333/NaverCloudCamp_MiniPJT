package com.model2.mvc.service.product;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

public interface ProductService {
	public int addProduct(Product productVO);
	public Product getProduct(int prodNo);
	public Map<String, Object> getProductList(Search serachVO);
	public Product updateProduct(Product productVO);

}
