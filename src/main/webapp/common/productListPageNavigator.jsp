<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	<span class="beforePageNavigator">
		<c:if test="${ resultPage.currentPage <= resultPage.pageUnit }">
				�� ����
		</c:if>
		<c:if test="${ resultPage.currentPage > resultPage.pageUnit }">
				<%-- <a href="javascript:fncGetProductList('${ resultPage.currentPage-1}')">�� ����</a> --%>
				�� ����
		</c:if>
	</span>
	
	
		<c:forEach var="i"  begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
			<%-- <a href="javascript:fncGetProductList('${ i }');">${ i }</a> --%>
			<span class="pageNavigator" id="${i}">
				${ i }
			</span>
		</c:forEach>
	
	
	
	<span class="afterPageNavigator">
		<c:if test="${ resultPage.endUnitPage >= resultPage.maxPage }">
				���� ��
		</c:if>
		<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
				<%-- <a href="javascript:fncGetProductList('${resultPage.endUnitPage+1}')">���� ��</a> --%>
				���� ��
		</c:if>
	</span>
	