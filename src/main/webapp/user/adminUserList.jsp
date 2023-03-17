<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
        <script type="text/javascript">
        	function makeTranCodeHtml(tranCode) {
        		var html = "";
        		
        		if($.trim(tranCode) == '1') {
        			html = "구매완료   <a href='/purchase/updatePurchase'>  배송시작</a>";
        		}else if($.trim(tranCode) == '2') {
        			html = "배송중";
        		}else if($.trim(tranCode) == '3') {
        			html = "배송 완료";
        		}else {
        			html = "판매중  <a href='/purchase/updatePurchase'>  상품수정</a>";
        		}
        		
        		return html;
        	}
        
		   	$(function() {
		   		$.get("/user/json/getUserList", function(data) {
		   			var displayValueList = []
		   			
		   			data.forEach(function(el, index) {
		   				var displayValue = "<tr>"
		   					+"<td>"+index+"</td>"
		                   	+"<td>"+el.userId+"</td>"
		                   	+"<td>"+el.userName+"</td>"
		                   	+"<td>"+el.role+"</td>"
		                   	+"<td>"+el.regDate+"</td>"                    
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
                        <th>회원 아이디</th>
                        <th>회원 이름</th>
                        <th>권한</th>
                        <th>가입일자</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
						<th>No</th>
                        <th>회원 아이디</th>
                        <th>회원 이름</th>
                        <th>권한</th>
                        <th>가입일자</th>
                    </tr>
                </tfoot>
                       
          <tbody>
             
         </tbody>
      </table>
  </div>
 </div>
