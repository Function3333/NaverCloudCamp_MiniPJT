package com.model2.mvc.web.Purchase;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.common.util.TransCode;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;



@Controller
@RequestMapping("/purchase")
public class PurchaseController {
	//field
	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private ProductService productService;
	
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageUnit'] ?: 2}")
	int pageSize;
	
	//constructor
	public PurchaseController() {
		System.out.println("[init :: PurchaseController]");
	}
	
	
	@GetMapping("/addPurchase")
	public String addPurchaseView(@RequestParam("prod_no") int prodNo, HttpSession session, Model model) {
		System.out.println("[addPurchaseView.do Controller init]");
		
		Product product = productService.getProduct(prodNo);
		User user = (User)session.getAttribute("user");
				
		Purchase purchase = new Purchase();
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		
		model.addAttribute("product", product);
		return "forward:/purchase/addPurchaseView.jsp";
	}
	
	@PostMapping("/addPurchase")
	public String addPurchase(HttpSession session, @ModelAttribute Purchase purchase , @RequestParam("prodNo") int prodNo, Model model) {
		System.out.println("[addPurchase.do Controller init]");
		
		User user = (User)session.getAttribute("user");
		Product product = productService.getProduct(prodNo);
		
		if(purchase.getPurchaseStock() > product.getStock()) {
			model.addAttribute("product", product);
			model.addAttribute("error", "재고부족");
			return "/purchase/addPurchaseView.jsp";
		}
		
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		purchase.setTranCode(TransCode.purchaseComplete);
		
		purchaseService.addPurchase(purchase);
		
		model.addAttribute("purchase", purchase);
		return "forward:/purchase/addPurchaseResultView.jsp";
	}
	
	@RequestMapping("/getPurchase")
	public String getPurchase(@RequestParam("tranNo") int tranNo, Model model) {
		System.out.println("[getPurchase.do Controller init]");
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/getPurchaseView.jsp";
	}
	
	@RequestMapping("/listPurchase")
	public String listPurchase(HttpSession session, @RequestParam(value="currentPage", defaultValue = "1") int currentPage
								,@ModelAttribute Search search, Model model) {
		System.out.println("[listPurchase.do Controller init]");
		
		String userId = ((User)session.getAttribute("user")).getUserId();
			
		search.setCurrentPage(currentPage);
		search.setPageSize(pageSize);
		search.setUserId(userId);
		
		Map<String,Object> map=purchaseService.getPurchaseList(search);
		Page resultPage	= new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		List<Purchase> list = (List<Purchase>)map.get("list");
		for(Purchase purchase : list) {
			System.out.println("listPurchase.do purchase = " + purchase);
		}
		
		model.addAttribute("list", list);
		model.addAttribute("search", search);
		model.addAttribute("resultPage", resultPage);

		return "forward:/purchase/listPurchaseView.jsp";
	}
	
	@GetMapping("/updatePurchase")
	public String updatePurchaseView(@RequestParam("tranNo") int tranNo, Model model) {
		Purchase purchase = purchaseService.getPurchase(tranNo);
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}
	
	@PostMapping("/updatePurchase")
	public String updatePurchase(@ModelAttribute Purchase purchase, @RequestParam("purchaseNo") int tranNo) {
		System.out.println("[init :: updatePurchase.do]");
			
		String tranCode = (purchaseService.getPurchase(tranNo)).getTranCode();
		
		if((tranCode.trim()).equals(TransCode.purchaseComplete)) {
			purchase.setTranNo(tranNo);
			System.out.println("/updatePurchase.do purchase = " + purchase);
			purchaseService.updatePurchase(purchase);
		}
		
		return "forward:/purchase/getPurchase?tranNo=" + tranNo;
	}
	
	@RequestMapping("/listSale")
	public String listSale(@RequestParam(value="currentPage", defaultValue = "1") int currentPage, @ModelAttribute Search search, 
							Model model) {
		System.out.println("[init :: /listSale.do]");
		
		search.setCurrentPage(currentPage);
		search.setPageSize(pageSize);
		
		Map<String , Object> map = purchaseService.getSaleList(search);

		Page resultPage	= new Page(currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/purchase/listSaleView.jsp";
	}
	
	@GetMapping("/updateTranCode")
	public String updateTranCode(@RequestParam("tranNo") int tranNo, @RequestParam("tranCode") String tranCode) {	
		Purchase purchase = new Purchase();
		purchase.setTranNo(tranNo);
		purchase.setTranCode(tranCode);
		
		purchaseService.updateTranCode(purchase);		
		return "forward:/purchase/listPurchase";
	}
	
	@GetMapping("/updateTranCodeByProd")
	public String updateTranCodeByProd(@RequestParam("currentPage") int currentPage, @ModelAttribute Search search, Model model
									,@RequestParam("prodNo") int prodNo, @RequestParam("tranCode") String tranCode) {
		search.setCurrentPage(currentPage);
		search.setPageSize(pageSize);
		
		Map<String , Object> map = purchaseService.getSaleList(search);

		Page resultPage	= new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);

		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		Purchase purchase = new Purchase();
		purchase.setPurchaseProd(productService.getProduct(prodNo));
		purchase.setTranCode(tranCode);
		
		purchaseService.updateTranCodeByProdNo(purchase);
		return "forward:/purchase/listSale";
	}
}
