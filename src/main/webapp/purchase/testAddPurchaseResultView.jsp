<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<script type="text/javascript" src="../javascript/calendar.js"></script>
	<!-- <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script> -->
	<!-- <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script> -->
	
	<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" > -->
	<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" > -->
	
	
	<style>
       body > div.container{
        	border: 3px solid #D6CDB7;
            margin-top: 10px;
           	align-content: flex-start;
        }
    </style>
</head>

<body>
	<jsp:include page="/common/userHeader.jsp"/>
<!-- model.addAttribute("purchase", purchase); -->
<!--  화면구성 div Start /////////////////////////////////////-->
	<div class="container">	
		<!-- form Start /////////////////////////////////////-->
		<form class="form-horizontal" name="detailForm">
				  
		  <div class="form-group">
		    <label for="userName" class="col-sm-offset-1 col-sm-3 control-label">구매자 이름</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" name="receiverName" placeholder="${purchase.buyer.userId}">
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="paymentOption" class="col-sm-offset-1 col-sm-3 control-label">구매방법</label>
		    <div class="col-sm-4">
		    	<c:choose>
		    		<c:when test="${fn:trim(purchase.paymentOption) eq 1}">현금구매</c:when>
		    		<c:when test="${fn:trim(purchase.paymentOption) eq 2}">신용구매</c:when>
		    	</c:choose>
		      	
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="stock" class="col-sm-offset-1 col-sm-3 control-label">수량</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" name="purchaseStock" placeholder="${purchase.purchaseStock}">
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="dlvyAddr" class="col-sm-offset-1 col-sm-3 control-label">구매자 주소</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="addr" name="dlvyAddr" placeholder="${purchase.dlvyAddr}">
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="dlvyRequest" class="col-sm-offset-1 col-sm-3 control-label">구매요청사항</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" name="dlvyRequest" placeholder="${purchase.dlvyRequest}">
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="ssn" class="col-sm-offset-1 col-sm-3 control-label">구매자 연락처</label>
		     <div class="col-sm-2">
		      ${purchase.receiverPhone}
		    </div>
		    <input type="hidden" name="phone"  />
		  </div>
		  
		   <div class="form-group">
		    <label for="ssn" class="col-sm-offset-1 col-sm-3 control-label">배송희망일자</label>
		    <div class="col-sm-4" id="calendar">
		      <div id="dlvyDate">
				${purchase.dlvyDate}
			</div>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <div class="col-sm-offset-4  col-sm-4 text-center">
			  <a class="btn btn-primary btn" href="/" role="button" id="cancel"> 확 &nbsp;인 </a>
		    </div>
		  </div>
		  <input type="hidden" name="receiverPhone" value ="">
		  <input type="hidden" name="prodNo" value ="">
		</form>
		<!-- form Start /////////////////////////////////////-->
		
 	</div>
	<!--  화면구성 div end /////////////////////////////////////-->


<%-- 


<form name="updatePurchase" action="/purchase/updatePurchase?tranNo=0" method="post">

다음과 같이 구매가 되었습니다.

<table border=1>
	<tr>
		<td>물품번호</td>
		<td>${purchase.purchaseProd.prodNo}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자아이디</td>
		<td>${purchase.buyer.userName}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매방법</td>
		<td>
			${purchase.paymentOption}
		</td>
		<td></td>
	</tr>
	<tr>
		<td>구매수</td>
		<td>
			${purchase.purchaseStock}
		</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자이름</td>
		<td>${purchase.receiverName}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자연락처</td>
		<td>${purchase.receiverPhone}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자주소</td>
		<td>${purchase.dlvyAddr}</td>
		<td></td>
	</tr>
		<tr>
		<td>구매요청사항</td>
		<td>${purchase.dlvyRequest}</td>
		<td></td>
	</tr>
		<tr>
		<td>배송희망일자</td>
		<td>${purchase.dlvyDate}</td>
		<td></td>
	</tr>
</table>
</form> --%>

</body>
</html>