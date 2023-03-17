package com.model2.mvc.service.purchase.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDAO;


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
		System.out.println("[init PurchaseDAOImpl init]");
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
		return sqlSession.selectList("PurchaseMapper.getSaleList", search);
	}
	
	public int getTotalCount(Search search) {
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", search);
	}
	
	public void updateTranCode(Purchase purchase) {
		sqlSession.update("PurchaseMapper.updateTranCode", purchase);
	}

	public void updateTranCodeByProdNo(Purchase purchase) {
		sqlSession.update("PurchaseMapper.updateTransCodeByProdNo", purchase);
	}

	public int getPurchaseAmount(String userId) {
		return sqlSession.selectOne("PurchaseMapper.getPurchaseAmount", userId);
	}

	@Override
	public List<Purchase> getSaleListJson() {
		return sqlSession.selectList("PurchaseMapper.getJsonSaleList");
	}
}
