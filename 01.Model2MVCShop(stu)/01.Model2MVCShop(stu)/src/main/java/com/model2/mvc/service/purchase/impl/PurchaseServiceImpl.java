/**
 * 
 */
package com.model2.mvc.service.purchase.impl;

import java.sql.SQLException;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class PurchaseServiceImpl implements PurchaseService {
	private PurchaseDAO purchaseDAO = null;
	
	
	public PurchaseServiceImpl() {
		purchaseDAO = new PurchaseDAO();
	}

	@Override
	public PurchaseVO addPurchaseVO(PurchaseVO purchaseVO) {		
		return new PurchaseDAO().insertPurchase(purchaseVO);
	}

	@Override
	public PurchaseVO getPurchase(int purchaseNo) {
		return purchaseDAO.findPurchase(purchaseNo);
	}

	@Override
	public HashMap<String, Object> getPurchaseList(SearchVO searchVO, String param) {
		HashMap<String, Object> map = null;
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
	public HashMap<String, Object> getSaleList(SearchVO seraSearchVO) {
		return null;
	}

	@Override
	public PurchaseVO updatePurchase(PurchaseVO purchaseVO) {
		purchaseDAO.updatePurchase(purchaseVO);
		return purchaseVO;
	}

	@Override
	public void updateTransCode(PurchaseVO purchaseVO) {
		purchaseDAO.updateTransCode(purchaseVO);
	}
}
