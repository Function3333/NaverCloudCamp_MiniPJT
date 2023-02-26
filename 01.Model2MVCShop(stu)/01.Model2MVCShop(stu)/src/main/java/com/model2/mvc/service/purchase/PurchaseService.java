package com.model2.mvc.service.purchase;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public interface PurchaseService {
	public PurchaseVO addPurchaseVO(PurchaseVO purchaseVO);
	public PurchaseVO getPurchase(int purchaseNo);
	public HashMap<String, Object> getPurchaseList(SearchVO searchVO, String param);
	public HashMap<String, Object> getSaleList(SearchVO seraSearchVO);
	public PurchaseVO updatePurchase(PurchaseVO purchaseVO);
	public void updateTransCode(PurchaseVO purchaseVO);
}
