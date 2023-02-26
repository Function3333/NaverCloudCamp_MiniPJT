package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml" })
public class ProductServiceTest {
	
	@Autowired
	private ProductService productService;
	
	//@Test
	public void testGetProduct() {
		//given
		Product prodcut = createProduct("getProduct", 200, "getProduct", "getProduct", "20230216");
		productService.addProduct(prodcut);
		
		//when
		Product getProduct = productService.getProduct(prodcut.getProdNo());
		
		//then
		Assert.assertEquals("getProduct", getProduct.getProdName());
	}
	
	//@Test
	public void testCreateProduct() {
		//given
		Product product = createProduct("createProduct", 100, "제품 생성", "테스트 이미지", "20230216");
		
		//when
		productService.addProduct(product);
		
		//then
		System.out.println(product.getProdNo());
	}
	
	//@Test
	public void testUpdateProduct() {
		//given
		Product product = createProduct("testProduct", 200, "updateProduct", "updateProduct", "20230216");
		productService.addProduct(product);
		
		product.setProdName("updateProduct");
		
		//when
		productService.updateProduct(product);
		
		
		//then
		Product updateProduct = productService.getProduct(product.getProdNo());
		Assert.assertEquals("updateProduct", updateProduct.getProdName());
	}
	
	//@Test
	public void testGetProductList() {
		//given
		Search search = new Search();
		search.setPageSize(5);
		search.setCurrentPage(1);
		
		//when
		Map<String, Object> map  = (Map<String, Object>)productService.getProductList(search);
		List<Product> list = (List<Product>)map.get("list");
		
		//then
		Assert.assertEquals(5, list.size());
	}
	
	//@Test
	public void testGetProductListBySearchKeyword() {
		//given
		Search search = new Search();
		search.setPageSize(5);
		search.setCurrentPage(1);
		search.setSearchKeyword("보");
		
		//when
		Map<String, Object> map  = (Map<String, Object>)productService.getProductList(search);
		List<Product> list = (List<Product>)map.get("list");
		
		//then
		Assert.assertEquals(2, list.size());
	}
	
	//@Test
	public void testGetSortProductListByPrice() {
		//given
		Search search = new Search();
		search.setPageSize(5);
		search.setCurrentPage(1);
		search.setSearchCondition("1");
		
		
		//when
		System.out.println("serach = " + search);
		Map<String, Object> map  = (Map<String, Object>)productService.getProductList(search);
		List<Product> list = (List<Product>)map.get("list");
		System.out.println("list size = " + list.size());
	
		//then
		Assert.assertEquals(100, list.get(0).getPrice());
	}
		
	public Product createProduct(String prodName, int price, String prodDetail, String fileName, String manuDate) {
		Product product = new Product();
		product.setProdName(prodName);
		product.setPrice(price);
		product.setProdDetail(prodDetail);
		product.setFileName(fileName);
		product.setManuDate(manuDate);
		
		return product;
	}
}
