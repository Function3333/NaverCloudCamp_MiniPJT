package com.model2.mvc.service.product.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductDAO;

@Repository
public class ProductDAOImpl implements ProductDAO{
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public Product findProduct(int prodNo) {
		return sqlSession.selectOne("ProductMapper.getProduct", prodNo);
	}
	
	public int insertProduct(Product product) {
		return sqlSession.insert("ProductMapper.addProduct",product);
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
	
