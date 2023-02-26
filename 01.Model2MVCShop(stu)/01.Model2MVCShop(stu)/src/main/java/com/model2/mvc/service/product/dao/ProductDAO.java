package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.vo.UserVO;

public class ProductDAO {
	//field
	//method
	public ProductVO findProduct(int prodNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ProductVO productVO = new ProductVO();
		
		try {
			String query = "select p.prod_no, p.prod_name, p.prod_detail, p.manufacture_day, p.price, p.image_file, p.reg_date, t.tran_status_code"
					+ " from product p, transaction t"
					+ " where p.prod_no = t.prod_no(+) and p.prod_no = ?";
			con = DBUtil.getConnection();
			
			pstmt = con.prepareStatement(query);	
			pstmt.setInt(1, prodNo);
			
			rs = pstmt.executeQuery();
		
			while(rs.next()) {
				productVO.setFileName(rs.getString("IMAGE_FILE"));
				productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
				productVO.setPrice(rs.getInt("PRICE"));
				productVO.setProdDetail(rs.getString("PROD_DETAIL"));
				productVO.setProdName(rs.getString("PROD_NAME"));
				productVO.setProdNo(rs.getInt("PROD_NO"));
				productVO.setRegDate(rs.getDate("REG_DATE"));
				productVO.setProTranCode(rs.getString("TRAN_STATUS_CODE"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt, rs);
		}
		
		System.out.println("findProduct productVO = " + productVO);
		return productVO;
	}
	
	public void insertProduct(ProductVO productVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			String query = "insert into Product (PROD_NO,PROD_NAME, PROD_DETAIL, MANUFACTURE_DAY, PRICE, IMAGE_FILE, REG_DATE) " 
					+" values(seq_product_prod_no.nextval, ?, ?, ?, ?, ?,sysdate)";
					
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, productVO.getProdName());
			pstmt.setString(2, productVO.getProdDetail());
			pstmt.setString(3, productVO.getManuDate());
			pstmt.setInt(4, productVO.getPrice());
			pstmt.setString(5, productVO.getFileName());
			
			int result = pstmt.executeUpdate();
			System.out.println("Product insert result = " + result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt);
		}
	}
	
	public ProductVO updateProduct(ProductVO productVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		System.out.println("update dat productVO = " + productVO);
		try {
			String query = "update product set PROD_NAME = ?, PROD_DETAIL = ?, MANUFACTURE_DAY = ?, PRICE = ?, IMAGE_FILE=? "
					+ " WHERE PROD_NO=?";
			
			con = DBUtil.getConnection();
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, productVO.getProdName());
			pstmt.setString(2, productVO.getProdDetail());
			pstmt.setString(3, productVO.getManuDate());
			pstmt.setInt(4, productVO.getPrice());
			pstmt.setString(5, productVO.getFileName());
			pstmt.setInt(6, productVO.getProdNo());
			
			int result = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt);
		}
		
		ProductVO findProductVO = findProduct(productVO.getProdNo());
		System.out.println("productVO regdate = " + findProductVO.getRegDate());
		return findProductVO;
	}
	
	public HashMap<String,Object> getProductList(SearchVO searchVO) {
		HashMap<String,Object> map = null;
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
			
			System.out.println("getProductList query = " + query);
			pstmt = con.prepareStatement(query , ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
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
		} finally {
			DBUtil.close(con, pstmt, rs);
		}
		return map;
	}
}
