package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.common.util.TransCode;


public class UpdatePurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("UpdatePurchaseAction init");
		
		String userId = request.getParameter("buyerId");
		int tranNo =  Integer.parseInt(request.getParameter("pruchaseId"));
		
		System.out.println("UpdatePurchaseAction userId = " + userId);
		System.out.println("UpdatePurchaseAction tranNo = " + tranNo);
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		String tranCode = purchaseService.getPurchase(tranNo).getTranCode();
		
		System.out.println("UpdatePurchaseAction tranCode = " + tranCode);
		if((tranCode.trim()).equals(TransCode.purchaseComplete)) {
			PurchaseDAO purchaseDAO = new PurchaseDAO();
			Purchase updateVO = new Purchase();
			
			updateVO.setBuyer(purchaseDAO.findUser(userId));
			updateVO.setPurchaseProd(purchaseDAO.findProduct(tranNo));
			updateVO.setDivyAddr(request.getParameter("receiverAddr"));
			updateVO.setDivyDate(request.getParameter("divyDate"));
			updateVO.setDivyRequest(request.getParameter("receiverRequest"));
			updateVO.setDivyDate(request.getParameter("divyDate"));
			updateVO.setPaymentOption(request.getParameter("paymentOption"));
			updateVO.setReceiverName(request.getParameter("receiverName"));
			updateVO.setReceiverPhone(request.getParameter("receiverPhone"));
			updateVO.setTranNo(Integer.parseInt(request.getParameter("pruchaseId")));
			
			purchaseService.updatePurchase(updateVO);
		}
		return "forward:/getPurchase.do?tranNo=" + tranNo;
	}

}
