package com.model2.mvc.service.product.impl;


import java.util.List;

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
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
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
	
	@Override
	public Product updateProduct(Product product) {
		sqlSession.update("ProductMapper.updateProduct", product);
		return this.findProduct(product.getProdNo());
	}
	
	public int getTotalCount(Search search) {
		return sqlSession.selectOne("ProductMapper.getTotalCount", search);
	}

	
//	public static String makeCurrentPageSql(String query, Search search) {
//		query = "SELECT vt2.R, vt2.*"
//			+" FROM(SELECT ROWNUM R,vt.* "
//			+" FROM("+ query +") vt) vt2 WHERE vt2.R BETWEEN "+ ((search.getCurrentPage()-1)*search.getPageSize()+1) 
//			+" AND " +search.getCurrentPage()*search.getPageSize();
//		
//		return query;
//	}
	
//	public static String makeConditionSql(String query, Search search) {
//		System.out.println("produceDAO makeConditionSql search.getSearchCondition()" + search.getSearchCondition() + "end");
//		System.out.println("produceDAO makeConditionSql search.getSearchKeyword()" + search.getSearchKeyword() + "end");
//		if(search.getSearchKeyword() == null || search.getSearchKeyword().equals("")) {
//			if(!search.getSearchCondition().equals("")) {
//				if((search.getSearchCondition().trim()).equals("0")) {
//					query += " ORDER BY price";
//				}
//				if((search.getSearchCondition().trim()).equals("1")) {
//					query += " ORDER BY price DESC";
//				}
//				if((search.getSearchCondition().trim()).equals("2")) {
//					query += " ORDER BY reg_date DESC";
//				}
//			}else {
//				query += " ORDER BY p.prod_no";
//			}
//		}
//		
//		if(search.getSearchKeyword() != null && !search.getSearchKeyword().equals("")) {
//			if(search.getSearchCondition() != null) {
//				if((search.getSearchCondition().trim()).equals("0")) {
//					query += " ORDER BY price";
//				}
//				if((search.getSearchCondition().trim()).equals("1")) {
//					query += " ORDER BY price DESC";
//				}
//				if((search.getSearchCondition().trim()).equals("2")) {
//					query += " ORDER BY reg_date DESC";
//				}	
//			}else {
//				query += " AND p.prod_name LIKE '%'||?||'%' ";
//			}
//		}
//				
//		return query;
//	}

	
}
	
