package com.model2.mvc.service.purchase;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseService {
	public Purchase addPurchase(Purchase Purchase);
	public Purchase getPurchase(int purchaseNo);
	public Map<String, Object> getPurchaseList(Search Search, String param);
	public Map<String, Object> getSaleList(Search seraSearch);
	public Purchase updatePurchase(Purchase Purchase);
	public void updateTransCode(Purchase Purchase);
}
