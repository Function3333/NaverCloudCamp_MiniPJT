<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
    <head>
    
    	<style type="text/css">
			.carousel-cell {
			  width: 100%; /* full width */
			  }
		</style>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        
        
        
        
        
        
        <script type="text/javascript" src="../javascript/calendar.js"></script>
        
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
                
        <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script> 
        
        <link rel="stylesheet" href="../javascript/flickity.css" media="screen">
        <script src="../javascript/flickity.pkgd.min.js"></script>	
        
        
		<script type="text/javascript" src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
        <script type="text/javascript">
	   		function createReceiverPhone(phone1, phone2, phone3) {
	   			var receiverPhone = phone1.val() + "-" + phone2.val() + "-" + phone3.val();
	   			return receiverPhone;
	   		}
	    
			 function fncAddPurchase(){			
				/* 핸드폰 번호 만들기 */
				var phone1 = $("select[name=phone1]");
				var phone2 = $("input[name=phone2]");
				var phone3 = $("input[name=phone3]");
				
				var receiverPhone = createReceiverPhone(phone1, phone2, phone3);
				$("input[name=receiverPhone]").val(receiverPhone);
				
				/* prodNo 넘기 */
				var url = new URLSearchParams(location.search);
				var prodNo = url.get("prodNo");
				$("input[name=prodNo]").val(prodNo);
				
						
				$("form[name=detailForm]").attr("method", "POST").attr("action", "/purchase/addPurchase").submit();
			}
			
			function resetData(){
				$("form[name=detailForm]").each(function() {
					this.reset();
				})
			}

        
	        $(function() {	        	
	        	$("#enroll").on("click", function() {
					fncAddPurchase();
				});
				
				$("#cancel").on("click", function() {
					resetData();
				})
				
				$("#calendar").on("click", function() {
					show_calendar('document.detailForm.dlvyDate', $("form input[name=dlvyDate]").val());
				}) 
				
				$('.main-carousel').flickity({
	        		  // options
	        		  cellAlign: 'center',
	        		  contain: true
				});
	        })
       </script>
        
    </head>
    <body>
    	<jsp:include page="/common/userHeader.jsp"/>
    	
    	<!-- product images section -->
    	<section class="py-5">
            <div class="container px-4 px-lg-5 my-5">
                <div class="row gx-4 gx-lg-5 align-items-center">
                    <div class="col-md-6">
	                    <div class="main-carousel">
	           			<c:forEach var="fileName" items="${fileNames}" >
	               			<div class="carousel-cell"><img class="card-img-top mb-5 mb-md-0" src="/images/uploadFiles/${fileName}"/></div>
	               		</c:forEach>
						  <!-- <div class="carousel-cell">...</div>
						  <div class="carousel-cell">...</div>
						  <div class="carousel-cell">...</div>   -->
					 </div>
					 </div>
                    <div class="col-md-6" id="pos">
		<form class="form-horizontal" name="detailForm">
				  
		  <div class="form-group">
		    <label for="userName" class="col-sm-offset-1 col-sm-3 control-label">구매자 이름</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" name="receiverName" placeholder="구매자 이름">
		    </div>
		  </div>
		  
		  
		  <div class="form-group">
		    <label for="paymentOption" class="col-sm-offset-1 col-sm-3 control-label">구매방법 </label>
		    <div class="col-sm-4">
		      <select name="paymentOption" class="ct_input_g" style="width: 100px; height: 19px" maxLength="20">
				<option value="1" selected="selected">현금구매</option>
				<option value="2">신용구매</option>
			</select>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="stock" class="col-sm-offset-1 col-sm-3 control-label">수량</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" name="purchaseStock" placeholder="구매 수량">
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="dlvyAddr" class="col-sm-offset-1 col-sm-3 control-label">구매자 주소</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="addr" name="dlvyAddr" placeholder="주소">
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="dlvyRequest" class="col-sm-offset-1 col-sm-3 control-label">구매요청사항</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" name="dlvyRequest" placeholder="구매 수량">
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="ssn" class="col-sm-offset-1 col-sm-3 control-label">구매자 연락처</label>
		     <div class="col-sm-2">
		      <select class="form-control" name="phone1" id="phone1">
				  	<option value="010" selected="selected">010</option>
					<option value="011" >011</option>
					<option value="016" >016</option>
					<option value="018" >018</option>
					<option value="019" >019</option>
			 </select>
		    </div>
		    <div class="col-sm-2">
		    	<input type="text" class="form-control" id="phone2" name="phone2" placeholder="번호">  
		    </div>
		    <div class="col-sm-2">
		      <input type="text" class="form-control" id="phone3" name="phone3" placeholder="번호">
		    </div>
		    <input type="hidden" name="phone"  />
		  </div>
		  
		   <div class="form-group">
		    <label for="ssn" class="col-sm-offset-1 col-sm-3 control-label">배송희망일자</label>
		    <div class="col-sm-4" id="calendar">
		      <div id="dlvyDate">
				<input 	type="text" readonly="readonly" name="dlvyDate" class="ct_input_g" 
								style="width: 100px; height: 19px" maxLength="20"/>
				<img 	src="../images/ct_icon_date.gif" width="15" height="15"	/> 
				   
			</div>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <div class="col-sm-offset-4  col-sm-4 text-center">
		      <button type="button" class="btn btn-primary" id="enroll" >구  &nbsp;매 </button>
			  <a class="btn btn-primary btn" href="#" role="button" id="cancel">취&nbsp;소</a>
		    </div>
		  </div>
		  <input type="hidden" name="receiverPhone" value ="">
		  <input type="hidden" name="prodNo" value ="">
		</form>
                    </div>
                </div>
            </div>
            
        
        </section>       
        <!-- Footer-->
        <footer class="py-5 bg-dark">
            <div class="container"><p class="m-0 text-center text-white"></p></div>
        </footer>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="/javascript/getProductScripts.js"></script>
    </body>
</html>
