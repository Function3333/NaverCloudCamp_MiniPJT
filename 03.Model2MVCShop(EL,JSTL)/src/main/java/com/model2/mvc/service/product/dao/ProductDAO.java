package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;

public class ProductDAO {
	//field
	//method
	public Product findProduct(int prodNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Product product = new Product();
		
		try {
			String query = "select p.prod_no, p.prod_name, p.prod_detail, p.manufacture_day, p.price, p.image_file, p.reg_date, t.tran_status_code"
					+ " from product p, transaction t"
					+ " where p.prod_no = t.prod_no(+) and p.prod_no = ?";
			con = DBUtil.getConnection();
			
			pstmt = con.prepareStatement(query);	
			pstmt.setInt(1, prodNo);
			
			rs = pstmt.executeQuery();
		
			while(rs.next()) {
				product.setFileName(rs.getString("IMAGE_FILE"));
				product.setManuDate(rs.getString("MANUFACTURE_DAY"));
				product.setPrice(rs.getInt("PRICE"));
				product.setProdDetail(rs.getString("PROD_DETAIL"));
				product.setProdName(rs.getString("PROD_NAME"));
				product.setProdNo(rs.getInt("PROD_NO"));
				product.setRegDate(rs.getDate("REG_DATE"));
				product.setProTranCode(rs.getString("TRAN_STATUS_CODE"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt, rs);
		}
		
		System.out.println("findproduct product = " + product);
		return product;
	}
	
	public void insertProduct(Product product) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			String query = "insert into product (PROD_NO,PROD_NAME, PROD_DETAIL, MANUFACTURE_DAY, PRICE, IMAGE_FILE, REG_DATE) " 
					+" values(seq_product_prod_no.nextval, ?, ?, ?, ?, ?,sysdate)";
					
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, product.getProdName());
			pstmt.setString(2, product.getProdDetail());
			pstmt.setString(3, product.getManuDate());
			pstmt.setInt(4, product.getPrice());
			pstmt.setString(5, product.getFileName());
			
			int result = pstmt.executeUpdate();
			System.out.println("product insert result = " + result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt);
		}
	}
	
	public Product updateProduct(Product product) {
		Connection con = null;
		PreparedStatement pstmt = null;
		System.out.println("update dat product = " + product);
		try {
			String query = "update product set PROD_NAME = ?, PROD_DETAIL = ?, MANUFACTURE_DAY = ?, PRICE = ?, IMAGE_FILE=? "
					+ " WHERE PROD_NO=?";
			
			con = DBUtil.getConnection();
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, product.getProdName());
			pstmt.setString(2, product.getProdDetail());
			pstmt.setString(3, product.getManuDate());
			pstmt.setInt(4, product.getPrice());
			pstmt.setString(5, product.getFileName());
			pstmt.setInt(6, product.getProdNo());
			
			int result = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt);
		}
		
		Product findproduct = findProduct(product.getProdNo());
		System.out.println("product regdate = " + findproduct.getRegDate());
		return findproduct;
	}
	
	public Map<String,Object> getProductList(Search search) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String, Object> map = null;
		
		try {
			System.out.println("getProductList search = " + search);
			con = DBUtil.getConnection();
			
			String query = "SELECT  p.*, t.tran_status_code FROM product p, transaction t WHERE p.prod_no = t.prod_no(+)"
					+" and t.tran_status_code is null";

			System.out.println("ProductDAO search = " + search);
						
			if(search.getSearchKeyword() != null && !search.getSearchKeyword().equals("")) {
				query += " AND p.prod_name LIKE '%'||?||'%' ";	
			}
			

			
			query = makeConditionSql(query, search);			
			System.out.println("ProductDAO getProductList query = " + query);

			int totalCount = getTotalCount(query, search);
			System.out.println("ProductDAO totalCount = " + totalCount);
			
			query = makeCurrentPageSql(query, search);
			System.out.println("ProductDAO makeCurrentPageSql query =  " + query);
			
			pstmt = con.prepareStatement(query);
			
			if(search.getSearchKeyword() != null) {
				if(!search.getSearchKeyword().equals("")) {
					pstmt.setString(1, search.getSearchKeyword());
				}
			}
			
			rs = pstmt.executeQuery();
			
			List<Product> list = new ArrayList<>();
			while(rs.next()) {
				Product product = new Product();
				product.setProdNo(rs.getInt("prod_no"));
				product.setProdName(rs.getString("prod_name"));
				product.setProdDetail(rs.getString("prod_detail"));
				product.setManuDate(rs.getString("MANUFACTURE_DAY"));
				product.setRegDate(rs.getDate("REG_DATE"));
				product.setFileName(rs.getString("image_file"));
				product.setPrice(rs.getInt("price"));
				product.setProTranCode(rs.getString("TRAN_STATUS_CODE"));
				
				System.out.println("ProductDAO getList product = " + product);
				list.add(product);
			}
			
			map = new HashMap<>();
			map.put("totalCount", totalCount);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt, rs);
		}
		return map;
	}
	
	public static int getTotalCount(String query, Search search) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount = 0;
		
		try {
			con = DBUtil.getConnection();
			
			query = "SELECT COUNT(*) FROM (" + query + ") vt";
			pstmt = con.prepareStatement(query);
			
			if(search.getSearchKeyword() != null && !search.getSearchKeyword().equals("")) {
				System.out.println("ProductDAO getTotalCount keyword= " + search.getSearchKeyword());
				pstmt.setString(1,search.getSearchKeyword());
			}
			
			System.out.println("ProductDAO getTotalCount query =" + query);
			rs = pstmt.executeQuery();
			
			totalCount = 0;
			while(rs.next()) {
				totalCount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt, rs); 
		}
		
		System.out.println("ProductDAO getTotalCount = " + totalCount);
		return totalCount;
	}
	
	public static String makeCurrentPageSql(String query, Search search) {
		query = "SELECT vt2.R, vt2.*"
			+" FROM(SELECT ROWNUM R,vt.* "
			+" FROM("+ query +") vt) vt2 WHERE vt2.R BETWEEN "+ ((search.getCurrentPage()-1)*search.getPageSize()+1) 
			+" AND " +search.getCurrentPage()*search.getPageSize();
		
		return query;
	}
	
	public static String makeConditionSql(String query, Search search) {
		System.out.println("produceDAO makeConditionSql search.getSearchCondition()" + search.getSearchCondition() + "end");
		System.out.println("produceDAO makeConditionSql search.getSearchKeyword()" + search.getSearchKeyword() + "end");
		if(search.getSearchKeyword() == null || search.getSearchKeyword().equals("")) {
			if(!search.getSearchCondition().equals("")) {
				if((search.getSearchCondition().trim()).equals("0")) {
					query += " ORDER BY price";
				}
				if((search.getSearchCondition().trim()).equals("1")) {
					query += " ORDER BY price DESC";
				}
				if((search.getSearchCondition().trim()).equals("2")) {
					query += " ORDER BY reg_date DESC";
				}
			}else {
				query += " ORDER BY p.prod_no";
			}
		}
		
		if(search.getSearchKeyword() != null && !search.getSearchKeyword().equals("")) {
			if(search.getSearchCondition() != null) {
				if((search.getSearchCondition().trim()).equals("0")) {
					query += " ORDER BY price";
				}
				if((search.getSearchCondition().trim()).equals("1")) {
					query += " ORDER BY price DESC";
				}
				if((search.getSearchCondition().trim()).equals("2")) {
					query += " ORDER BY reg_date DESC";
				}	
			}else {
				query += " AND p.prod_name LIKE '%'||?||'%' ";
			}
		}
				
		return query;
	}
}
	
