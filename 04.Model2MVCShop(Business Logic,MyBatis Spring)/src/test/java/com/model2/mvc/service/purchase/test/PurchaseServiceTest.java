package com.model2.mvc.service.purchase.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class PurchaseServiceTest {
	
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	
	//@Test
	public void testFindPurchase() throws Exception {
		//given
		Product product = productService.getProduct(10006);
		User user = userService.getUser("user19");
		Purchase purchase = createPurchase("findTest", "findTest", "0", "findTest", "20230217", "findTest", user, product);
		
		purchaseService.addPurchase(purchase);
		
		//when
		Purchase findPurchase = purchaseService.getPurchase(purchase.getTranNo());
		
		//then
		Assert.assertEquals(findPurchase.getTranNo(), purchase.getTranNo());
		Assert.assertEquals(findPurchase.getBuyer().getUserId(), purchase.getBuyer().getUserId());
		Assert.assertEquals(findPurchase.getPurchaseProd().getProdNo(), purchase.getPurchaseProd().getProdNo());
	}
	
	//@Test
	public void testInsertPurchase() throws Exception {	
		//given
		Product product = productService.getProduct(10006);
		User user = userService.getUser("user19");
		Purchase purchase = createPurchase("insertTest", "insertTest", "0", "insertTest", "20230217", "insertTest", user, product);
		
		//when
		purchaseService.addPurchase(purchase);
		 
		//then
		Assert.assertNotEquals(0, purchase.getTranNo());
	}
	
	//@Test
	public void testUpdatePurchase() throws Exception {
		//given
		User user = new User();
		user.setUserId("testUser");
		user.setUserName("testUser");
		user.setPassword("testUser");
		userService.addUser(user);
		
		Product product = new Product();
		product.setProdName("testProduct");
		product.setPrice(100);
		product.setManuDate("2023-02-03");
		productService.addProduct(product);
		
		Purchase purchase = createPurchase("beforeUpdate", "beforeUpdate", "1", "beforeUpdate", "20230217", "beforeUpdate", user, product);
		purchaseService.addPurchase(purchase);
		
		purchase.setReceiverName("afterUpdate");
		purchase.setdlvyRequest("afterUpdate");
		purchase.setReceiverPhone("afterUpdate");
		
		//when
		purchaseService.updatePurchase(purchase);
		
		Purchase updatePurchase = purchaseService.getPurchase(purchase.getTranNo());
		
		//then
		Assert.assertEquals("afterUpdate", purchase.getReceiverName());
	}
	
	//@Test
	public void testGetPurchaseList() {
		//given
		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setUserId("user19");
		
		//when
		Map<String, Object> map = purchaseService.getPurchaseList(search);
		
		List<Purchase> list = (List<Purchase>) map.get("list");
		int totalCount = (int) map.get("totalCount");
		
		//then
		Assert.assertEquals(2, list.size());
		Assert.assertEquals(2, totalCount);
	}
	
	//@Test
	public void testGetSaleList() {
		//given
		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(10);

		//when
		Map<String, Object> map = purchaseService.getSaleList(search);
		
		List<Purchase> list = (List<Purchase>) map.get("list");
		int totalCount = (int) map.get("totalCount");
		
		//then
		Assert.assertEquals(8, list.size());
		Assert.assertEquals(8, totalCount);
	}
	
	@Test
	public void testUpdateTransCode() {
		//given
		Purchase purchase = purchaseService.getPurchase(10081);
		
		//when
		purchase.setTranCode("2");
		purchaseService.updateTranCode(purchase);
		
		//then
		Purchase findPurchsae = purchaseService.getPurchase(purchase.getTranNo());
		Assert.assertEquals("2", findPurchsae.getTranCode().trim());
	}
	
	public Purchase createPurchase(String receiverPhone, String receiverName, String paymentOption, 
			String dlvyRequest, String dlvyDate, String dlvyAddr, User user, Product product ) {
		Purchase purchase = new Purchase();
		purchase.setReceiverPhone(receiverPhone);
		purchase.setReceiverName(receiverName);
		purchase.setPaymentOption(paymentOption);
		purchase.setdlvyRequest(dlvyRequest);
		purchase.setdlvyDate(dlvyDate);
		purchase.setdlvyAddr(dlvyAddr);
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		
		return purchase;
	}
}
