package com.model2.mvc.service.domain;

import java.sql.Date;


public class Purchase {
	private int tranNo;
	private User buyer;
	private Product purchaseProd;
	private String dlvyAddr;
	private String dlvyDate;
	private String dlvyRequest;
	private Date orderDate;
	private String paymentOption;
	private String receiverName;
	private String receiverPhone;
	private String tranCode;
	private int purchaseStock;
	
	public Purchase(){
	}
	
	public Purchase(User user, Product product){
		buyer = user;
		purchaseProd = product;
	}
	

	public int getTranNo() {
		return tranNo;
	}

	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public Product getPurchaseProd() {
		return purchaseProd;
	}

	public void setPurchaseProd(Product purchaseProd) {
		this.purchaseProd = purchaseProd;
	}

	public String getDlvyAddr() {
		return dlvyAddr;
	}

	public void setDlvyAddr(String dlvyAddr) {
		this.dlvyAddr = dlvyAddr;
	}

	public String getDlvyDate() {
		return dlvyDate;
	}

	public void setDlvyDate(String dlvyDate) {
		this.dlvyDate = dlvyDate;
	}

	public String getDlvyRequest() {
		return dlvyRequest;
	}

	public void setDlvyRequest(String dlvyRequest) {
		this.dlvyRequest = dlvyRequest;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getPaymentOption() {
		return paymentOption;
	}

	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public int getPurchaseStock() {
		return purchaseStock;
	}

	public void setPurchaseStock(int purchaseStock) {
		this.purchaseStock = purchaseStock;
	}

	@Override
	public String toString() {
		return "PurchaseVO [buyer=" + buyer + ", dlvyAddr=" + dlvyAddr
				+ ", dlvyDate=" + dlvyDate + ", dlvyRequest=" + dlvyRequest
				+ ", orderDate=" + orderDate + ", paymentOption="
				+ paymentOption + ", purchaseProd=" + purchaseProd
				+ ", receiverName=" + receiverName + ", receiverPhone="
				+ receiverPhone + ", tranCode=" + tranCode + ", tranNo="
				+ tranNo + "]";
	}
}