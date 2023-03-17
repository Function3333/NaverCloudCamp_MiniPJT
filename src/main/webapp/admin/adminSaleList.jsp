<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
        <script type="text/javascript">
        	function makeTranCodeHtml(el) {
        		var html = "";
        		
        		if($.trim(el.purchaseProd.proTranCode) == '1') {
        			html = "구매완료   <a href='/purchase/updateTranCodeByProd?prodNo="+el.purchaseProd.prodNo+"&tranCode=2'>  배송시작</a>";
        		}else if($.trim(el.purchaseProd.proTranCode) == '2') {
        			html = "배송중";
        		}else if($.trim(el.purchaseProd.proTranCode) == '3') {
        			html = "배송 완료";
        		}else {
        			html = "판매중  <a href='/product/updateProduct?prodNo="+el.purchaseProd.prodNo+"'>  상품수정</a>";
        		}
        		
        		return html;
        	}
        
		   	$(function() {
		   		$.get("/purchase/json/getSaleList", function(data) {
		   			var displayValueList = []
		   			
		   			data.forEach(function(el, index) {
		   				var displayValue = "<tr>"
		   					+"<td>"+index+"</td>"
		                   	+"<td>"+el.purchaseProd.prodName+"</td>"
		                   	+"<td>"+el.purchaseProd.price+"</td>"
		                   	+"<td>"+el.purchaseProd.stock+"</td>"
		                   	+"<td>"+el.purchaseProd.prodDetail+"</td>"
							+"<td>"+makeTranCodeHtml(el)+"</td>"                    
		                   	+"</tr>"
		   				displayValueList.push(displayValue);
		   			});
		   			$("tbody").html(displayValueList);
		   		})
		   	});
    	</script> 

	
		<div class="card-body">
           <table id="datatablesSimple">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>상품명</th>
                        <th>가격</th>
                        <th>수량</th>
                        <th>제품 설명</th>
                        <th>현재 상태</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
						<th>No</th>
	                    <th>상품명</th>
	                    <th>가격</th>
	                    <th>수량</th>
	                    <th>제품 설명</th>
	                    <th>현재 상태</th>
                    </tr>
                </tfoot>
                       
          <tbody>
             
         </tbody>
      </table>
  </div>
 </div>
