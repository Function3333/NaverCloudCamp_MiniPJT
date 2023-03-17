package com.model2.mvc.service.product;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

public interface ProductService {
	public Product addProduct(Product product);
	public Product getProduct(int prodNo);
	public Map<String, Object> getProductList(Search serach);
	public Product updateProduct(Product product);
	public void updateProductStock(int stock, int prodNo);
}
