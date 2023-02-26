package com.model2.mvc.service.purchase.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.domain.Purchase;

public class PurchaseServiceImpl implements PurchaseService {
	private PurchaseDAO purchaseDAO = null;
	
	
	public PurchaseServiceImpl() {
		purchaseDAO = new PurchaseDAO();
	}

	@Override
	public Purchase addPurchase(Purchase Purchase) {		
		return new PurchaseDAO().insertPurchase(Purchase);
	}

	@Override
	public Purchase getPurchase(int purchaseNo) {
		return purchaseDAO.findPurchase(purchaseNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search searchVO, String param) {
		Map<String, Object> map = null;
		try {
			map = purchaseDAO.getPurchaseList(searchVO, param);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("purchaseServiceImpl map = " + map);
		return map;
	}

	@Override
	public Map<String, Object> getSaleList(Search search) {
		return purchaseDAO.getSaleList(search);
	}

	@Override
	public Purchase updatePurchase(Purchase Purchase) {
		purchaseDAO.updatePurchase(Purchase);
		return Purchase;
	}

	@Override
	public void updateTransCode(Purchase Purchase) {
		purchaseDAO.updateTransCode(Purchase);
	}
}
