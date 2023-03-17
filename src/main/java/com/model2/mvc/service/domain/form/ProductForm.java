package com.model2.mvc.service.domain.form;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.model2.mvc.service.domain.Product;

public class ProductForm {
	private List<MultipartFile> fileName;
	private String manuDate;
	private int price;
	private String prodDetail;
	private String prodName;
	private int prodNo;
	private Date regDate;
	private String proTranCode;
	private int stock;
	
	public Product formToVO(ProductForm form, String imageName) {
		Product product = new Product();
		product.setManuDate(form.getManuDate());
		product.setPrice(form.getPrice());
		product.setProdDetail(form.getProdDetail());
		product.setProTranCode(form.getProTranCode());
		product.setProdName(form.getProdName());
		product.setStock(form.getStock());
		product.setFileName(imageName);
		
		System.out.println("ProductForm.getFileName = " + product.getFileName());
		return product;
	}
		
	public List<MultipartFile> getFileName() {
		return fileName;
	}

	public void setFileName(List<MultipartFile> fileName) {
		this.fileName = fileName;
	}

	public String getManuDate() {
		return manuDate;
	}

	public void setManuDate(String manuDate) {
		this.manuDate = manuDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getProdDetail() {
		return prodDetail;
	}

	public void setProdDetail(String prodDetail) {
		this.prodDetail = prodDetail;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public int getProdNo() {
		return prodNo;
	}

	public void setProdNo(int prodNo) {
		this.prodNo = prodNo;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getProTranCode() {
		return proTranCode;
	}

	public void setProTranCode(String proTranCode) {
		this.proTranCode = proTranCode;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String toString() {
		return "ProductVO : [fileName]" + fileName
				+ "[manuDate]" + manuDate+ "[price]" + price + "[prodDetail]" + prodDetail
				+ "[prodName]" + prodName + "[prodNo]" + prodNo + "[proTranCode]" + proTranCode + "[stock]" + stock
				+ "[fileName]" + fileName;
	}
}
