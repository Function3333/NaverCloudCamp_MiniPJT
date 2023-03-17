<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<title>���� �����ȸ</title>

<!-- <link rel="stylesheet" href="/css/admin.css" type="text/css"> -->
	<link href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,700' rel='stylesheet' type='text/css'>

	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	
	<link rel="stylesheet" href="/css/listPurchaseStyle.css">


<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
	var currentPage = 0;
	var purchaseIndex = 0;
	
	function getPurchaseList(inputCurrentPage) {
		$.ajax(
			{
				url : "/purchase/json/getPurchaseList",
				method : "POST",
				headers : {
					"Accept" : "application/json",
					"Content-Type" : "application/json"
				}, 
				data : JSON.stringify({
					currentPage : inputCurrentPage
				}),
				success : function(data, status) {		
					var displayPurchaseList = [];
					
					data.forEach(function(el, index){
						var tranCode = '';
												
						if(el.tranCode == '') {
							tranCode = "�Ǹ���";	
						}else if(el.tranCode == '1') {
							tranCode = "���ſϷ�";	
						}else if(el.tranCode == '2'){
							tranCode = "�����  <a href='/purchase/updateTranCode?tranNo="+el.tranNo+"&tranCode=3'>��ǰ ����</a>";
						}else {
							tranCode = "��ۿϷ�";
						}
						
						var displayPurchaseValue = "<tr>"
						  +"<th scope='row'>" + (++purchaseIndex) + "</th>"
						  +"<td><a href='/user/getUser'>" + el.buyer.userId + "</a></td>"
						  +"<td><a href='/purchase/getPurchase?tranNo="+ el.tranNo +"'>"+el.purchaseProd.prodName+"<a></td>"
					      +"<td>" +el.purchaseStock+ "</td>"
					      +"<td>" +el.purchaseStock * el.purchaseProd.price+"��</td>"
					      +"<td>" +el.dlvyAddr+ "</td>"
					      +"<td>" +el.orderDate+ "</td>"
					      +"<td>" +tranCode+ "</td>"
					      +"</tr>"
					      
						displayPurchaseList.push(displayPurchaseValue);
					});
					$("#displayPurchaseList").append(displayPurchaseList);
				}
			}
		)
	}
	
	$(function() {
		
		
		/* ��ũ�� Ŀ�� �ϴ� ������ �̺�Ʈ �߻�  */
		$(window).scroll(function() {
			if($(window).scrollTop() == $(document).height() - $(window).height()) {
				currentPage++;
				getPurchaseList(currentPage);
			}  
		});
		
		/* �˻� �� �̹� ȭ���� ���� ��ũ�� ���ϸ� �ڵ����� ���� ��ũ�� ����  */
		if($(window).scrollTop() == $(document).height() - $(window).height()) {
			currentPage++;
			getPurchaseList(currentPage);
		} 
		
	})
</script>
</head>

<body bgcolor="#ffffff" text="#000000">
<jsp:include page="/common/userHeader.jsp"/>
<div style="width: 98%; margin-left: 10px;">
	<section class="ftco-section">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-6 text-center mb-5">
					<h2 class="heading-section">���� ���</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="table-wrap">
						<table class="table">
						  <thead class="thead-primary">
						    <tr>
						      <th>��ȣ</th>
						      <th>������ �̸�</th>
						      <th>��ǰ �̸�</th>
						      <th>���� ����</th>
						      <th>�� ���� ����</th>
						      <th>������ �ּ�</th>
						      <th>�ֹ� ����</th>
						      <th>��� ����</th>
						    </tr>
						  </thead>
						  <tbody id="displayPurchaseList">
						  </tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</section>

	<script src="/javascript/jquery.min.js"></script>
  <script src="/javascript/popper.js"></script>
  <script src="/javascript/bootstrap.min.js"></script>
  <script src="/javascript/main.js"></script>

<!--  ������ Navigator �� -->
</div>

</body>
</html>