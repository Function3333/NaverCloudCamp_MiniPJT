<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<c:set var="i" value="0"/>
		<c:forEach var="comment" items="${commentList}" >
			<c:set var="i" value="${i+1}" />
		</c:forEach>
	

	<form method="post" action="/addCommentAction.do?commentProdNo=${product.prodNo}">
		<div>��� <span id="count">${i}</span></div>
        <textarea name="content" placeholder="����� �Է��� �ּ���." rows="5" cols="100"></textarea>
        <button id="submit">���</button>
	</form>
	
	<table width="70%" border="0" cellspacing="0" cellpadding="0" style="margin-top:5px;">
	<tr>
		<td class="ct_list_b" width="100">�ۼ���</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="100">�����</td>
		<td class="ct_line02"></td>
<!-- 		<td class="ct_list_b" width="100">����</td>	
		<td class="ct_line02"></td> -->
		<td class="ct_list_b" width="100">����</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
			

	<c:forEach var="comment" items="${commentList}">
		<tr class="ct_list_pop">
		<td align="center">${comment.userId}</td>
		<td></td>
		<td align="center">${comment.content}</td>
		<td></td>
		<td align="center">${comment.regDate}</td>	
		<td></td>
<%-- 		<td align="center">
			<c:if test="${user.userId == comment.commentUser.userId}">
				<a href="/updateCommentView.do?commentNo=${comment.commentNo}&prodNo=${product.prodNo}">����</a>
			</c:if>
		</td>	
		<td></td> --%>
		<td align="center">
			<c:choose>
				<c:when test="${(user.userId == comment.userId) or (user.role eq 'admin')}">
					<a href="/deleteComment.do?commentNo=${comment.commentNo}&prodNo=${product.prodNo}">����</a>
				</c:when>
			<%-- 	<c:when test="${}">
					<a href="/deleteComment.do?commentNo=${comment.commentNo}&prodNo=${product.prodNo}">����</a>
				</c:when> --%>
				<c:otherwise></c:otherwise>
			</c:choose>
		</td>	
		<td></td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	</c:forEach>
</table>
		

</body>
</html>

