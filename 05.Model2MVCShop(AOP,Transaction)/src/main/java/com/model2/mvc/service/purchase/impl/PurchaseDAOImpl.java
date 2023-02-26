package com.model2.mvc.service.purchase.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseDAO;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.common.util.TransCode;
import com.model2.mvc.service.user.impl.UserServiceImpl;

import com.model2.mvc.service.domain.User;

@Repository
public class PurchaseDAOImpl implements PurchaseDAO{
	//field
	@Autowired
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	//constructor
	public PurchaseDAOImpl() {
		System.out.println("init PurchaseDAOImpl Constructor");
	}
	
	public Purchase findPurchase(int purchaseNo) {
		return sqlSession.selectOne("PurchaseMapper.findPurchase", purchaseNo);
	}
	
	public Purchase insertPurchase(Purchase purchase) {
		sqlSession.insert("PurchaseMapper.insertPurchase", purchase);
		return null;
	}
	
	public Purchase updatePurchase(Purchase purchase) {
		sqlSession.update("PurchaseMapper.updatePurchase", purchase);
		return null;
	}
	
	public List<Purchase> getPurchaseList(Search search) {
		return sqlSession.selectList("PurchaseMapper.getPurchaseList", search);
	}
	
	public List<Purchase> getSaleList(Search search) {
		return sqlSession.selectList("ProductMapper.getProductList", search);
	}
	
	public int getTotalCount(Search search) {
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", search);
	}
	
	public void updateTranCode(Purchase purchase) {
		sqlSession.update("PurchaseMapper.updateTranCode", purchase);
	}
}
