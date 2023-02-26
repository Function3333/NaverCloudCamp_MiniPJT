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
		<div>댓글 <span id="count">${i}</span></div>
        <textarea name="content" placeholder="댓글을 입력해 주세요." rows="5" cols="100"></textarea>
        <button id="submit">등록</button>
	</form>
	
	<table width="70%" border="0" cellspacing="0" cellpadding="0" style="margin-top:5px;">
	<tr>
		<td class="ct_list_b" width="100">작성자</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">내용</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="100">등록일</td>
		<td class="ct_line02"></td>
<!-- 		<td class="ct_list_b" width="100">수정</td>	
		<td class="ct_line02"></td> -->
		<td class="ct_list_b" width="100">삭제</td>	
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
				<a href="/updateCommentView.do?commentNo=${comment.commentNo}&prodNo=${product.prodNo}">수정</a>
			</c:if>
		</td>	
		<td></td> --%>
		<td align="center">
			<c:choose>
				<c:when test="${(user.userId == comment.userId) or (user.role eq 'admin')}">
					<a href="/deleteComment.do?commentNo=${comment.commentNo}&prodNo=${product.prodNo}">삭제</a>
				</c:when>
			<%-- 	<c:when test="${}">
					<a href="/deleteComment.do?commentNo=${comment.commentNo}&prodNo=${product.prodNo}">삭제</a>
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

