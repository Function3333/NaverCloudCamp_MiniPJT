package com.model2.mvc.service.product;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.product.vo.ProductVO;

public interface ProductService {
	public ProductVO addProduct(ProductVO productVO);
	public ProductVO getProduct(int prodNo);
	public HashMap<String, Object> getProductList(SearchVO serachVO);
	public ProductVO updateProduct(ProductVO productVO);

}
