<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

<title>��� ��ǰ ����</title>

</head>
<body>
	����� ��� ��ǰ�� �˰� �ִ�
<br>
<br>


<c:forEach var="value" items="${sessionScope.history}">
	 <a href="product/getProduct?prodNo=${value}&menu=search" target="rightFrame">${value}</a><br>
</c:forEach>


</body>
</html>