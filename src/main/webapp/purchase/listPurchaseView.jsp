<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<title>구매 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
	function fncGetPurchaseList(currentPage) {
		alert("currentpage = " + currentPage)
		$("currentPage").val(currentPage);
		$("form[name=detailForm]").attr("action", "/purchase/listPurchase").submit();
		
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">구매 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11" >전체  ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="200">구매상품</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">구매자 성함</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">수량</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">구매자 전화번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송정보 수정</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<c:set var="i" value="0"/>
	<c:forEach var="vo" items="${list}">
		<c:set var="i" value="${i+1}"/>
		
		<tr class="ct_list_pop">
		<td align="center">
			<a href="/purchase/getPurchase?tranNo=${vo.tranNo}">${vo.purchaseProd.prodName}</a>
		</td>
		<td></td>
		
		<td align="center">
			<a href="/user/getUser?userId=${vo.buyer.userId}">${vo.buyer.userId}</a>
		</td>
		<td></td>
		
		<td align="center">${vo.receiverName}</td>
		<td></td>
		
		<td align="center">${vo.purchaseStock}</td>
		<td></td>
		
		<td align="center">${vo.receiverPhone}</td>
		<td></td>
		
		<td align="center">현재
			<c:if test="${ empty vo.tranCode}">
				${'판매중'} 상태입니다.
			</c:if>
			<c:if test="${fn:trim(vo.tranCode) eq '1'}">
				${'구매완료'} 상태입니다.
			</c:if>
			<c:if test="${fn:trim(vo.tranCode) eq '2'}">
				${'배송중'} 상태입니다.
				<a href="/purchase/updateTranCode?tranNo=${vo.tranNo}&tranCode=3">물건수령</a>
			</c:if>
			<c:if test="${fn:trim(vo.tranCode) eq '3'}">
				${'재고없음'} 상태입니다.
			</c:if>
		</td>
		<td></td>
		
		<td align="center">
			<c:if test="${fn:trim(vo.tranCode) eq '1'}">
				<a href="/purchase/updatePurchase?tranNo=${vo.tranNo}">수정</a>
			</c:if>
			<c:if test="${fn:trim(vo.tranCode) eq '2'}">
				${'수정 불가'}
			</c:if>
			<c:if test="${fn:trim(vo.tranCode) eq '3'}">
				${'수정 불가'}
			</c:if>
		</td>
		<td></td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	</c:forEach>
	


</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
		<input type="hidden" id="currentPage" name="currentPage" value=""/>
		<jsp:include page="../common/listPurchasePageNavigator.jsp"/>
    	</td>
	</tr>
</table>

<!--  페이지 Navigator 끝 -->
</form>

</div>

</body>
</html>