<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<html>
<head>
<title>상품 목록조회</title>
<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
	// 검색 / page 두가지 경우 모두 Form 전송을 위해 JavaScrpt 이용
	
	function fncGetProductList(currentPage) {
		alert("fncGetProductList");
		$("#currentPage").val(currentPage);
		
		alert($("#currentPage").val());
		$("form[name=detailForm]").attr("action", "/product/listProduct").submit();
		
	}
	
	$(function() {
		$("td.ct_btn01:contains('검색')").on("click", function() {
			fncGetProductList('1');
		});
		
		$("#searchCondition0Btn").on("click", function() {
			var searchKeyword = $("#searchCondition0Btn").val();
			
			var url = "/product/listProduct?searchCondition=0&searchKeyword=" + searchKeyword;
			$(location).attr("href", url);
		});
		
		$("#searchCondition1Btn").on("click", function() {
			var searchKeyword = $("#searchCondition1Btn").val();
			
			var url = "/product/listProduct?searchCondition=1&searchKeyword=" + searchKeyword;
			$(location).attr("href", url);
		});
		
		$("#searchCondition2Btn").on("click", function() {
			var searchKeyword = $("#searchCondition2Btn").val();
			
			var url = "/product/listProduct?searchCondition=2&searchKeyword=" + searchKeyword;
			$(location).attr("href", url);
		});
		
		$("#searchCondition3Btn").on("click", function() {
			var searchKeyword = $("#searchCondition3Btn").val();
			
			var url = "/product/listProduct?searchCondition=3&searchKeyword=" + searchKeyword;
			$(location).attr("href", url);
		});
		
		$("tr.ct_list_pop td.prodName").on("click", function() {
			var prodNo = $(this).attr("id");
			url = "/product/getProduct?prodNo="+prodNo+"&menu=search";
			$(location).attr("href", url); 
		});
		
		$("span.pageNavigator").on("click", function() {
			fncGetProductList($(this).attr("id"));
		});
		
		$("span.beforePageNavigator").on("click", function() {
			var flag = "${ resultPage.currentPage > resultPage.pageUnit }";
			
			if($.parseJSON(flag)) {
				fncGetProductList("${ resultPage.currentPage-1}");
			}
		});
		
		$("span.afterPageNavigator").on("click", function() {
			var flag = "${ resultPage.endUnitPage < resultPage.maxPage }"
			
			if($.parseJSON(flag)) {
				fncGetProductList("${resultPage.endUnitPage+1}");
			}		
		});
		
	})
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
						
							<%="상품 목록조회" %>
					
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		
		<td align="right">
			<input type="text" name="searchKeyword" value="${search.searchKeyword}" class="ct_input_g" style="width:200px; height:19px" />
		</td>
	
		
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						검색
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

	<div align="right">
		<button type="button" id="searchCondition0Btn" value="${search.searchKeyword}">낮은 가격순 </button>
		<button type="button" id="searchCondition1Btn" value="${search.searchKeyword}">높은 가격순 </button>
		<button type="button" id="searchCondition2Btn" value="${search.searchKeyword}">최신순 </button>
		<button type="button" id="searchCondition3Btn" value="${search.searchKeyword}">조회순 </button>
		
		<c:if test="${fn:trim(search.searchCondition) eq '0'}">
			<input name="searchCondition" type="hidden" value="0"/>
		</c:if>
		<c:if test="${fn:trim(search.searchCondition) eq '1'}">
			<input name="searchCondition" type="hidden" value="1"/>
		</c:if>
		<c:if test="${fn:trim(search.searchCondition) eq '2'}">
			<input name="searchCondition" type="hidden" value="2"/>
		</c:if>
		<c:if test="${fn:trim(search.searchCondition) eq '3'}">
			<input name="searchCondition" type="hidden" value="3"/>
		</c:if>
	</div>		
	
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:5px;">
	<tr>
		<td colspan="11" >전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage}  페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">수량</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" >제품 설명</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b" >현재상태</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
			
	<c:set var="i" value="0"/>
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${i+1}"  />
		<tr class="ct_list_pop">
		<td align="center">${i}</td>
		<td></td>
		<td align="center" id ="${product.prodNo}" class="prodName">
			<c:choose>
				<c:when test="${empty product.proTranCode}">
					${product.prodName} 
				</c:when>
				<c:otherwise>
					${product.prodName}
				</c:otherwise>
			</c:choose>
		</td>
		<td></td>
		<td align="center">${product.price}</td>	
		<td></td>
		<td align="center">${product.stock}</td>	
		<td></td>
		<td align="center">
			<div class="productDetail">${product.prodDetail}</div>	
		</td>
		<td></td>
		<td align="center">
		
		<c:if test="${ empty product.proTranCode}">
			${'판매중'}
		</c:if>
		<c:if test="${fn:trim(product.proTranCode) eq '1'}">
			${'구매완료'}
		</c:if>
		<c:if test="${fn:trim(product.proTranCode) eq '2'}">
			${'배송중'}
		</c:if>
		<c:if test="${fn:trim(product.proTranCode) eq '3'}">
			${'재고없음'}
		</c:if>
		</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		<input type="hidden" id="currentPage" name="currentPage" value=""/>
		<jsp:include page="../common/productListPageNavigator.jsp"/>
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>
