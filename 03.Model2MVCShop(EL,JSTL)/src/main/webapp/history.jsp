<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
<title>��� ��ǰ ����</title>
</head>

<body>
	����� ��� ��ǰ�� �˰� �ִ�
<br>
<br>
<%-- <%
	request.setCharacterEncoding("euc-kr");
/* 	String history = null;
	Cookie[] cookies = request.getCookies();
	if (cookies!=null && cookies.length > 0) {
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("history")) {
				history = cookie.getValue();
			}
		}
		if (history != null) {
			String[] h = history.split(",");
			for (int i = 0; i < h.length; i++) {
				if (!h[i].equals("null")) { */
					
	Set<Integer> history = (Set<Integer>)session.getAttribute("history");
				
	for(Integer i : history) {	
		
	System.out.println("history.jsp history = " + i);
%> --%>
	<c:forEach var="product" items="${history}">
		<a href="/getProduct.do?prodNo=${product}&menu=search"	target="rightFrame">${product}</a>
		<br/>
	</c:forEach>


<br>
</body>
</html>