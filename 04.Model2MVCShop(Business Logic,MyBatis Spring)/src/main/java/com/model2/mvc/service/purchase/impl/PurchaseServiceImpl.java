package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.PurchaseDAO;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductDAO;

@Service
public class PurchaseServiceImpl implements PurchaseService {
	
	@Autowired
	private PurchaseDAO purchaseDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	
	public void setPurchaseDAO(PurchaseDAO purchaseDAO) {
		this.purchaseDAO = purchaseDAO;
	}


	@Override
	public Purchase addPurchase(Purchase purchase) {
		purchaseDAO.insertPurchase(purchase);
		return purchase;
	}


	@Override
	public Purchase getPurchase(int purchaseNo) {
		return purchaseDAO.findPurchase(purchaseNo);
	}
	
	@Override
	public Purchase updatePurchase(Purchase Purchase) {
		return purchaseDAO.updatePurchase(Purchase);
	}


	@Override
	public Map<String, Object> getPurchaseList(Search search) {
		Map<String, Object> map = new HashMap<>();
		map.put("list", purchaseDAO.getPurchaseList(search));
		map.put("totalCount", purchaseDAO.getTotalCount(search));
		
		return map;
	}


	@Override
	public Map<String, Object> getSaleList(Search search) {
		Map<String, Object> map = new HashMap<>();
		map.put("list", productDAO.getProductList(search));
		map.put("totalCount", productDAO.getTotalCount(search));
		return map;
	}

	@Override
	public void updateTranCode(Purchase purchase) {
		purchaseDAO.updateTranCode(purchase);
	}
}
