package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.common.util.TransCode;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.domain.User;

public class PurchaseDAO {

	public Purchase findPurchase(int purchaseNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Purchase vo = null;

		try {
			String query = "select * from transaction where tran_no = ?";
			
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, purchaseNo);
			ResultSet rs =  pstmt.executeQuery();
			
			vo = new Purchase();
			while(rs.next()) {
				vo.setBuyer(findUser(rs.getString("BUYER_ID")));
				vo.setPurchaseProd(findProduct(rs.getInt("PROD_NO")));
				vo.setDivyAddr(rs.getString("DEMAILADDR"));
				vo.setDivyDate(rs.getString("DLVY_DATE"));
				vo.setDivyRequest(rs.getString("DLVY_REQUEST"));
				vo.setOrderDate(rs.getDate("ORDER_DATA"));
				vo.setPaymentOption(rs.getString("PAYMENT_OPTION"));
				vo.setReceiverName(rs.getString("RECEIVER_NAME"));
				vo.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				vo.setTranCode(rs.getString("TRAN_STATUS_CODE"));
				vo.setTranNo(rs.getInt("TRAN_NO"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt);
		}
		System.out.println("PurchaseDAO findPurchase result = " + vo);
		return vo;
	}
	
	public Map<String,Object> getPurchaseList(Search search, String userId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String, Object> map = null;
		
		try {
			con = DBUtil.getConnection();
			
			String query = "SELECT * FROM transaction t WHERE t.buyer_id = '"+userId+"' ORDER BY t.prod_no";
			
			int totalCount = getTotalCount(query, search);
			System.out.println("PurchaseDAO getPurchaseList totalCount = " + totalCount);
			
			query = makeCurrentPageSql(query, search);
			System.out.println("PurchaseDAO getPurchaseList final query = " + query);
			
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			List<Purchase> list = new ArrayList<>();
			while(rs.next()) {
				Purchase vo = new Purchase();
				vo.setBuyer(findUser(rs.getString("BUYER_ID")));
				vo.setPurchaseProd(findProduct(rs.getInt("PROD_NO")));
				vo.setDivyAddr(rs.getString("DEMAILADDR"));
				vo.setDivyDate(rs.getString("DLVY_DATE"));
				vo.setDivyRequest(rs.getString("DLVY_REQUEST"));
				vo.setOrderDate(rs.getDate("ORDER_DATA"));
				vo.setPaymentOption(rs.getString("PAYMENT_OPTION"));
				vo.setReceiverName(rs.getString("RECEIVER_NAME"));
				vo.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				vo.setTranCode(rs.getString("TRAN_STATUS_CODE"));
				vo.setTranNo(rs.getInt("TRAN_NO"));
			
				list.add(vo);				
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
	
	//판매관리, 상품검색의 데이터는 같다
	public Map<String, Object> getSaleList(Search search) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String, Object> map = null;
		
		try {
			System.out.println("getSaleList search = " + search);
			con = DBUtil.getConnection();
			
			String query = "SELECT  p.*, t.tran_status_code FROM product p, transaction t WHERE p.prod_no = t.prod_no(+)";
						
			if(search.getSearchKeyword() != null && !search.getSearchKeyword().equals("")) {
				query += " AND p.prod_name LIKE '%'||?||'%' ";	
			}
			
			
			query = makeConditionSql(query, search);			
			System.out.println("PurchaseDAO makeConditionSql query = " + query);

			int totalCount = getTotalCount(query, search);
			System.out.println("PurchaseDAO totalCount = " + totalCount);
			
			query = makeCurrentPageSql(query, search);
			System.out.println("PurchaseDAO makeCurrentPageSql query =  " + query);
			
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
	

	public Purchase insertPurchase(Purchase Purchase) {
		System.out.println("init purchase VO");
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			String query = "insert into transaction" 
					+"(TRAN_NO, PROD_NO, BUYER_ID, PAYMENT_OPTION, RECEIVER_NAME, RECEIVER_PHONE "
					+",DEMAILADDR, DLVY_REQUEST,TRAN_STATUS_CODE, ORDER_DATA, DLVY_DATE) "		
					+" values(seq_transaction_tran_no.nextval, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?)";
			
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1,Purchase.getPurchaseProd().getProdNo());
			pstmt.setString(2,Purchase.getBuyer().getUserId());//BUYER_ID
			pstmt.setString(3,Purchase.getPaymentOption());//PAYMENT_OPTION
			pstmt.setString(4,Purchase.getReceiverName());//RECEIVER_NAME
			pstmt.setString(5,Purchase.getReceiverPhone());//RECEIVER_PHONE
			pstmt.setString(6,Purchase.getDivyAddr());//DEMAILADDR
			pstmt.setString(7,Purchase.getDivyRequest());//DLVY_REQUEST
			pstmt.setString(8,TransCode.purchaseComplete);//TRAN_STATUS_CODE
			pstmt.setString(9,Purchase.getDivyDate());//DLVY_DATE
			
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt);
		}
		
		return Purchase;
	}
	
	public Purchase updatePurchase(Purchase Purchase) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Purchase updateVO = null; 
		
		try {
			String query = "update transaction set  PAYMENT_OPTION = ?, RECEIVER_NAME = ?, RECEIVER_PHONE = ? "
					+ " ,DEMAILADDR = ?, DLVY_REQUEST = ?, DLVY_DATE = ? "
					+ " where TRAN_NO = ?";
			
			
			con = DBUtil.getConnection();
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, Purchase.getPaymentOption());
			pstmt.setString(2, Purchase.getReceiverName());
			pstmt.setString(3, Purchase.getReceiverPhone());
			pstmt.setString(4, Purchase.getDivyAddr());
			pstmt.setString(5, Purchase.getDivyRequest());
			pstmt.setString(6, Purchase.getDivyDate());
			pstmt.setInt(7, Purchase.getTranNo());
			
			int result = pstmt.executeUpdate();
			System.out.println("purchase DAO updatePurchase result = " + result);
			
			updateVO = findPurchase(Purchase.getTranNo());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt);
		}
		
		return updateVO;
	}
	
	public void updateTransCode(Purchase Purchase) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBUtil.getConnection();
			
			String query = "update transaction set TRAN_STATUS_CODE = ?";
			
			if(Purchase.getPurchaseProd() != null) {
				query += " where PROD_NO = " + Purchase.getPurchaseProd().getProdNo();
			}else {
				query += " where TRAN_NO = " + Purchase.getTranNo();
			}
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, Purchase.getTranCode());
			
			int result = pstmt.executeUpdate();
			System.out.println("Purchase DAO updateTransCode result = " + result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt);
		}
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
		
		System.out.println("PurchaseDAO getTotalCount = " + totalCount);
		return totalCount;
	}
	
	public static String makeCurrentPageSql(String query, Search search) {
		query = "SELECT vt2.R, vt2.*"
			+" FROM(SELECT ROWNUM R,vt.* "
			+" FROM("+ query +") vt) vt2 WHERE vt2.R BETWEEN "+ ((search.getCurrentPage()-1)*search.getPageSize()+1) 
			+" AND " +search.getCurrentPage()*search.getPageSize();
		
		return query;
	}
	
	public User findUser(String userId) {
		User User = null;
		try {
			User = new UserServiceImpl().getUser(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("purchaseDAO findUser = " + User);
		return User;
	}
	public Product findProduct(int prodId) {
		Product Product = null;
		try {
			Product = new ProductServiceImpl().getProduct(prodId);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("purchaseDAO findProduct = " + Product);
		return Product;
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
