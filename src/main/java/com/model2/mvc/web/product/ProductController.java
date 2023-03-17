package com.model2.mvc.web.product;

import java.io.UnsupportedEncodingException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.UUID;
import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.service.comment.CommentService;
import com.model2.mvc.service.comment.impl.CommentServiceImpl;
import com.model2.mvc.service.domain.Comment;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.form.ProductForm;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

@Controller
@RequestMapping("/product")
public class ProductController {
	//field
	@Autowired
	private ProductService productService;
	@Autowired
	private CommentService commentService;
	
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	@Value("#{commonProperties['pageUnit'] ?: 2}")
	int pageSize;
	@Value("#{commonProperties['imagePath']}")
	String imagePath; 
	
	//constructor
	public ProductController() {
		System.out.println("[init :: ProductController]");
	}
	
	@PostMapping("/addProduct")
	public String addProduct(@ModelAttribute ProductForm productForm, Model model) throws Exception {
		List<MultipartFile> images = productForm.getFileName();
		
		String imageName = saveImageAndReturnUUID(images);
		
		Product product = productForm.formToVO(productForm, imageName);
		System.out.println("addProduct iamges = " + product.getFileName());
		productService.addProduct(product);
		
		model.addAttribute("product", product);		
		model.addAttribute("imageNames", product.getFileName().split("%"));
		return "forward:/product/addProductResultView.jsp";
	}
	 
	
	@PostMapping("/updateProduct")
	public String updateProduct(@ModelAttribute ProductForm productForm, Model model) throws Exception {
		System.out.println("[/updateProduct.do Controller init]");	
			
		Product findProduct = productService.getProduct(productForm.getProdNo());
		
		
		if(findProduct.getProTranCode() == null) {
			findProduct.setProdName(productForm.getProdName());
			findProduct.setFileName(saveImageAndReturnUUID(productForm.getFileName()));
			findProduct.setManuDate(productForm.getManuDate());
			findProduct.setPrice(productForm.getPrice());
			findProduct.setProdDetail(productForm.getProdDetail());
			findProduct.setStock(productForm.getStock());
			
			Product product = productService.updateProduct(findProduct);
			model.addAttribute("product", product);
		}
		return "forward:/product/getProduct?&menu=search"; 
	}

	
	@RequestMapping("/getProduct")
	public String getProduct(@RequestParam("prodNo") int prodNo, @RequestParam("menu") String menu, Model model, HttpSession session) {
		System.out.println("[getProdcut.do Controller init]");
		
		
		System.out.println("[getProduct prodNo = " + prodNo + " ]");
		Product findProduct = productService.getProduct(prodNo);
		List<Comment> commentList = commentService.getCommentListByProdNo(prodNo);
		
		String[] arr = findProduct.getFileName().split("%");
		
		for(String str : arr) {
			System.out.println("getProduct = " + str);
		}
		
		model.addAttribute("product", findProduct);
		model.addAttribute("commentList", commentList);
		model.addAttribute("fileNames", arr);
		
		String resultPage = null;
		if(menu != null) {
			if(menu.equals("search")) {
				resultPage = "forward:/product/getProductView.jsp";
			}
			else if(menu.equals("manage")) {
				resultPage = "forward:/product/updateProduct.jsp";
			}
		}
		
		Set<Integer> history = (Set<Integer>) session.getAttribute("history");
		if(history == null) {
			history = new TreeSet();
			session.setAttribute("history", history);
		}
		history.add(prodNo);
		
		System.out.println("==============history==============");
		for(int i : history) {
			System.out.println("history prodNo = " + history);
		}
		return resultPage;
	}
	
	@RequestMapping("/listProduct")
	public String listProduct(HttpServletRequest request, @ModelAttribute("search") Search search, 
								@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
								@RequestParam(value = "menu", required = false) String menu, Model model) {
		System.out.println("[listProduct.do Controller init]");		
		System.out.println("listProduct search = " + search);
		search.setPageSize(pageSize);
		search.setCurrentPage(currentPage);
		
		Map<String,Object> map = productService.getProductList(search);

		Page resultPage = new Page(currentPage, (Integer)map.get("totalCount"), pageUnit, pageSize);

		model.addAttribute("search", search);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
	
		String requestURI = request.getRequestURI();
		return fowardDirection(menu, requestURI);
	}
	
	@GetMapping("/updateProduct")
	public String updateProductView(@RequestParam("prodNo") int prodNo, Model model) {
		System.out.println("[updateProductView.do Controller init]");		
		
		Product product = productService.getProduct(prodNo);
		model.addAttribute("product", product);
		
		return "forward:/product/updateProduct.jsp";
	}
	
	public String saveImageAndReturnUUID(List<MultipartFile> images) throws Exception {
		StringBuilder sb = new StringBuilder();
		
		for(MultipartFile image : images) {
			if(image.getSize() != 0) {
				String uuid = UUID.randomUUID().toString() +"."+ extractFileExtension(image);
				File file = new File(imagePath, uuid);
				image.transferTo(file);
				
				sb.append(uuid);
				sb.append("%");
			}
		}
		
		return sb.toString();
	}
	
	public String extractFileExtension(MultipartFile image) {
		int pos = image.getOriginalFilename().lastIndexOf(".");
		
		String fileExtenstion = image.getOriginalFilename().substring(pos + 1);
		System.out.println(fileExtenstion);
		
		return fileExtenstion;
	}
	
	
	public static String fowardDirection(String menu, String requestURI) {
		String direction = "";
		
		System.out.println("fowardDirection URI = " + requestURI);
		if(menu == null) {
			if(requestURI.contains("/listProduct")) {
				direction =  "forward:/product/listProduct.jsp";
			}else {
				direction = "forward:/purchase/listSale";
			}
		}else {
			if(menu.equals("manage")) {
				direction = "forward:/purchase/listSale";
			}else {
				direction = "forward:/product/listProduct.jsp";
			}
		}	
		System.out.println("ListProductionAction forwardDircetion = " + direction);
		return direction;
	}
}
