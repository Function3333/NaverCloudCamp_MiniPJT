package com.model2.mvc.service.product;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

public interface ProductDAO {
	public Product findProduct(int prodNo); 
	public Product insertProduct(Product product);
	public Product updateProduct(Product product);
	public List<Product> getProductList(Search search);
	public int getTotalCount(Search search);
}
