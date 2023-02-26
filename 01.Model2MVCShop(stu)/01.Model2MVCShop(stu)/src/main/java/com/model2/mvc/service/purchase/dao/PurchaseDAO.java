package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.purchase.vo.TransCode;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class PurchaseDAO {

	public PurchaseVO findPurchase(int purchaseNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PurchaseVO vo = null;

		try {
			String query = "select * from transaction where tran_no = ?";
			
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, purchaseNo);
			ResultSet rs =  pstmt.executeQuery();
			
			vo = new PurchaseVO();
			while(rs.next()) {
				vo.setBuyer(findUserVO(rs.getString("BUYER_ID")));
				vo.setPurchaseProd(findProductVO(rs.getInt("PROD_NO")));
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
		}
		System.out.println("PurchaseDAO findPurchase result = " + vo);
		return vo;
	}
	
	public HashMap<String,Object> getPurchaseList(SearchVO searchVO, String userId) throws SQLException {
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from transaction t where t.buyer_id = ?";
		sql += " order by BUYER_ID";

		PreparedStatement stmt = 
			con.prepareStatement(	sql,
														ResultSet.TYPE_SCROLL_INSENSITIVE,
														ResultSet.CONCUR_UPDATABLE);
		
		stmt.setString(1, userId);
		ResultSet rs = stmt.executeQuery();
		
		rs.last();
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));

		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				System.out.println("Purchase DAO searchVO.getPageUnit() = " + searchVO.getPageUnit());
				PurchaseVO vo = new PurchaseVO();
				vo.setBuyer(findUserVO(rs.getString("BUYER_ID")));
				vo.setPurchaseProd(findProductVO(rs.getInt("PROD_NO")));
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
				System.out.println("pusrchaseDAO getList i = " + i);
				if (!rs.next())
					break;
			}
			System.out.println("purchase DAO getList for문 탈출");
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		for(PurchaseVO purchaseVO : list ) {
			System.out.println("PurchaseDAO getList = " + purchaseVO);
		}
		con.close();
		return map;
	}
	
	public HashMap<String, Object> getSaleList(SearchVO searchVO) {
		HashMap<String, Object> map = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBUtil.getConnection();
			
			String query = "select p.prod_no, p.prod_name, p.prod_detail, p.manufacture_day, "
					+ "p.price, p.image_file, p.reg_date, t.tran_status_code"
							+ " from product p, transaction t"
							+" where p.prod_no = t.prod_no(+)";
			
			if(searchVO.getSearchCondition() != null) {
				if(searchVO.getSearchCondition().equals("0")) {
					query += " and p.PROD_NO = '" + searchVO.getSearchKeyword()+"'";
				}else if(searchVO.getSearchCondition().equals("1")) {
					query += " and p.PROD_NAME = '" + searchVO.getSearchKeyword()+"'";
				}else if(searchVO.getSearchCondition().equals("2")) {
					query += " and p.PRICE = " + searchVO.getSearchKeyword();
				}
			}
			query += " order by PROD_NO";
			
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			rs.last();
			int total = rs.getRow();
			System.out.println("로우의 수:" + total);

			map = new HashMap<String,Object>();
			map.put("count", new Integer(total));

			rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
			System.out.println("searchVO.getPage():" + searchVO.getPage());
			System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

			ArrayList<ProductVO> list = new ArrayList<ProductVO>();
			if (total > 0) {
				for (int i = 0; i < searchVO.getPageUnit(); i++) {
					ProductVO productVO = new ProductVO();
					
					productVO.setProdNo(rs.getInt("PROD_NO"));
					productVO.setProdName(rs.getString("PROD_NAME"));
					productVO.setProdDetail(rs.getString("PROD_DETAIL"));
					productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
					productVO.setPrice(rs.getInt("PRICE"));
					productVO.setFileName(rs.getString("IMAGE_FILE"));
					productVO.setRegDate(rs.getDate("REG_DATE"));
					productVO.setProTranCode(rs.getString("TRAN_STATUS_CODE"));
					
					list.add(productVO);
					if (!rs.next())
						break;
				}
			}
			System.out.println("list.size() : "+ list.size());
			map.put("list", list);
			System.out.println("map().size() : "+ map.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	public PurchaseVO insertPurchase(PurchaseVO purchaseVO) {
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
			
			pstmt.setInt(1,purchaseVO.getPurchaseProd().getProdNo());
			pstmt.setString(2,purchaseVO.getBuyer().getUserId());//BUYER_ID
			pstmt.setString(3,purchaseVO.getPaymentOption());//PAYMENT_OPTION
			pstmt.setString(4,purchaseVO.getReceiverName());//RECEIVER_NAME
			pstmt.setString(5,purchaseVO.getReceiverPhone());//RECEIVER_PHONE
			pstmt.setString(6,purchaseVO.getDivyAddr());//DEMAILADDR
			pstmt.setString(7,purchaseVO.getDivyRequest());//DLVY_REQUEST
			pstmt.setString(8,TransCode.purchaseComplete);//TRAN_STATUS_CODE
			pstmt.setString(9,purchaseVO.getDivyDate());//DLVY_DATE
			
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return purchaseVO;
	}
	
	public PurchaseVO updatePurchase(PurchaseVO purchaseVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PurchaseVO updateVO = null; 
		
		try {
			String query = "update transaction set  PAYMENT_OPTION = ?, RECEIVER_NAME = ?, RECEIVER_PHONE = ? "
					+ " ,DEMAILADDR = ?, DLVY_REQUEST = ?, DLVY_DATE = ? "
					+ " where TRAN_NO = ?";
			
			
			con = DBUtil.getConnection();
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, purchaseVO.getPaymentOption());
			pstmt.setString(2, purchaseVO.getReceiverName());
			pstmt.setString(3, purchaseVO.getReceiverPhone());
			pstmt.setString(4, purchaseVO.getDivyAddr());
			pstmt.setString(5, purchaseVO.getDivyRequest());
			pstmt.setString(6, purchaseVO.getDivyDate());
			pstmt.setInt(7, purchaseVO.getTranNo());
			
			int result = pstmt.executeUpdate();
			System.out.println("purchase DAO updatePurchase result = " + result);
			
			updateVO = findPurchase(purchaseVO.getTranNo());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt);
		}
		
		return updateVO;
	}
	
	public void updateTransCode(PurchaseVO purchaseVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBUtil.getConnection();
			
			String query = "update transaction set TRAN_STATUS_CODE = ?";
			
			if(purchaseVO.getPurchaseProd() != null) {
				query += " where PROD_NO = " + purchaseVO.getPurchaseProd().getProdNo();
			}else {
				query += " where TRAN_NO = " + purchaseVO.getTranNo();
			}
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, purchaseVO.getTranCode());
			
			int result = pstmt.executeUpdate();
			System.out.println("Purchase DAO updateTransCode result = " + result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt);
		}
	}
	
	public UserVO findUserVO(String userId) {
		UserVO userVO = null;
		try {
			userVO = new UserServiceImpl().getUser(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("purchaseDAO findUserVO = " + userVO);
		return userVO;
	}
	public ProductVO findProductVO(int prodId) {
		ProductVO productVO = null;
		
		try {
			productVO = new ProductServiceImpl().getProduct(prodId);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("purchaseDAO findProductVO = " + productVO);
		return productVO;
	}
}
