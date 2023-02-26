package com.model2.mvc.service.purchase;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseService {
	public Purchase addPurchase(Purchase purchase);
	public Purchase getPurchase(int purchaseNo);
	public Map<String, Object> getPurchaseList(Search Search);
	public Map<String, Object> getSaleList(Search seraSearch);
	public Purchase updatePurchase(Purchase purchase);
	public void updateTranCode(Purchase purchase);
	public void updateTranCodeByProdNo(Purchase purchase);
}
