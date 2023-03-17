<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<title>���� �����ȸ</title>

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
					<td width="93%" class="ct_ttl01">���� �����ȸ</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11" >��ü  ${resultPage.totalCount} �Ǽ�, ���� ${resultPage.currentPage} ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="200">���Ż�ǰ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">ȸ��ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">������ ����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">������ ��ȭ��ȣ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�����Ȳ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">������� ����</td>
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
		
		<td align="center">����
			<c:if test="${ empty vo.tranCode}">
				${'�Ǹ���'} �����Դϴ�.
			</c:if>
			<c:if test="${fn:trim(vo.tranCode) eq '1'}">
				${'���ſϷ�'} �����Դϴ�.
			</c:if>
			<c:if test="${fn:trim(vo.tranCode) eq '2'}">
				${'�����'} �����Դϴ�.
				<a href="/purchase/updateTranCode?tranNo=${vo.tranNo}&tranCode=3">���Ǽ���</a>
			</c:if>
			<c:if test="${fn:trim(vo.tranCode) eq '3'}">
				${'������'} �����Դϴ�.
			</c:if>
		</td>
		<td></td>
		
		<td align="center">
			<c:if test="${fn:trim(vo.tranCode) eq '1'}">
				<a href="/purchase/updatePurchase?tranNo=${vo.tranNo}">����</a>
			</c:if>
			<c:if test="${fn:trim(vo.tranCode) eq '2'}">
				${'���� �Ұ�'}
			</c:if>
			<c:if test="${fn:trim(vo.tranCode) eq '3'}">
				${'���� �Ұ�'}
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

<!--  ������ Navigator �� -->
</form>

</div>

</body>
</html>