package com.model2.mvc.service.product.impl;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductDAO;

@Repository
public class ProductDAOImpl implements ProductDAO{

	//field
	@Autowired
	private SqlSession sqlSession;

	//constructor
	public ProductDAOImpl() {
		System.out.println("[init ProductDAOImpl init]");
	}
	
	public Product findProduct(int prodNo) {
		return sqlSession.selectOne("ProductMapper.getProduct", prodNo);
	}
	
	public Product insertProduct(Product product) {
		sqlSession.insert("ProductMapper.addProduct",product);
		return product;
	}
	
	public List<Product> getProductList(Search search) {
		return sqlSession.selectList("ProductMapper.getProductList",search);
	}
	
	public Product updateProduct(Product product) {
		sqlSession.update("ProductMapper.updateProduct", product);
		return findProduct(product.getProdNo());
	}
	
	public int getTotalCount(Search search) {
		return sqlSession.selectOne("ProductMapper.getTotalCount", search);
	}

	@Override
	public void updateProductStock(Map<String, Object> map) {
		sqlSession.update("ProductMapper.updateStock", map);
	}

	@Override
	public List<String> getAllProdName() {
		return sqlSession.selectList("ProductMapper.getAllProdName");
	}
}
	
