package com.model2.mvc.service.purchase;


import java.util.List;
import java.util.Map;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;


public interface PurchaseDAO {
	public Purchase findPurchase(int purchaseNo);
	
	public List<Purchase> getPurchaseList(Search search); 
	
	public List<Purchase> getSaleList(Search search); 	
	
	public Purchase insertPurchase(Purchase Purchase);	
	
	public Purchase updatePurchase(Purchase Purchase);
	
	public void updateTranCodeByProdNo(Purchase purchase);
	
	public void updateTranCode(Purchase Purchase); 
	
	public int getTotalCount(Search search);	
}
