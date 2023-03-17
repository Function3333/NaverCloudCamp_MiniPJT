package com.model2.mvc.web.Purchase;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;

@RestController
@RequestMapping("/purchase")
public class PurchaseRestController {
	
	@Autowired
	private PurchaseService purchaseService;
	
	@Value("#{commonProperties['pageUnit'] ?: 2}")
	int pageSize;
	
	@PostMapping("/json/getPurchaseList")
 	public List<Purchase> getPurchaseList(@RequestBody Search search, HttpSession session) {		
		search.setPageSize(pageSize);
		search.setUserId(((User)session.getAttribute("user")).getUserId());
		
		Map<String, Object> map = purchaseService.getPurchaseList(search);
		List<Purchase> list = (List<Purchase>)map.get("list");
				
		System.out.println("jsonGetPurchaseList");
		for(Purchase purchase : list) {
			System.out.println("purchase orderDate = " + purchase.getOrderDate());
		}
		return list;
	}
	
	@GetMapping("/json/getSaleList")
	public List<Purchase> getPurchaseList() {
		return purchaseService.getSaleListJson();
	}
}
