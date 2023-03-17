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
        
        <title>MVC_SHOP</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
        <link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
		<link href="../css/styles.css" rel="stylesheet" />
				
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
		<script src="../javascript/scripts.js"></script>
		 
		<link rel="stylesheet" href="http://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
		<link rel="icon" href="../../favicon.ico">
    	<link rel="canonical" href="https://getbootstrap.com/docs/3.3/examples/theme/">
		<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <!-- <link href="/css/admin.css" rel="stylesheet" /> -->
        
        
        
        <link rel="stylesheet" href="../javascript/flickity.css" media="screen">
        <script src="../javascript/flickity.pkgd.min.js"></script>
        
        
        
        <script type="text/javascript" src="http://code.jquery.com/jquery-2.1.4.min.js"></script> 
		<script type="text/javascript" src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
		
		<!-- CSS -->
		<link rel="stylesheet" href="https://unpkg.com/flickity@2/dist/flickity.min.css">
		<!-- JavaScript -->
		<script src="https://unpkg.com/flickity@2/dist/flickity.pkgd.min.js"></script>
		
        <script type="text/javascript">
        	
	        $(function() {
	        	var error = '${error}';
	        	
	        	if(error != '') {
	        		alert(error);
	        	}
	        	
	        	$("#purchase").on("click", function(){
	        		var prodNo = ${product.prodNo}
	        		var url = "/purchase/addPurchase?prodNo=" + prodNo;
	        		
	        		$(location).attr("href", url);
	        	})
	        	
	        	
	        	$('.main-carousel').flickity({
	        		  // options
	        		  cellAlign: 'center',
	        		  contain: true
				});
	        });
       </script>
        
    </head>
    <body>
        <c:choose>
    		<c:when test="${fn:trim(user.role) eq 'admin'}">
    			<jsp:include page="/common/adminHeader.jsp"/>
    		</c:when>
    		<c:when test="${fn:trim(user.role) eq 'user'}">
    			<jsp:include page="/common/userHeader.jsp"/>
    		</c:when>
    		<c:otherwise>
    			<jsp:include page="/common/commonHeader.jsp"/>
    		</c:otherwise>
    	</c:choose>

        <!-- Product section-->
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
                  
                  
                    <%-- <div class="col-md-6">
	                    <ul class="slick">
		                    <c:forEach var="fileName" items="${fileNames}" >
		                    	<div><img class="card-img-top mb-5 mb-md-0" src="/images/uploadFiles/${fileName}"/></div>
		                    </c:forEach>
		               </ul>
	                </div> --%>
	                
	                
                    <div class="col-md-6" id="pos">
                        <h1 class="display-5 fw-bolder">${product.prodName}</h1>
                        <div class="small mb-1">가격</div>
                        <div class="fs-5 mb-5">
                            <span>${product.price}</span>
                        </div>
                        <div class="small mb-1">수량</div>
                        <div class="fs-5 mb-5">
                            <span>${product.stock}</span>
                        </div>
                        <div class="small mb-1">제품 설명</div>
                        <div class="fs-5 mb-5">
                            <span>${product.prodDetail}</span>
                        </div>
                        <%-- <p class="lead">${product.prodDetail}
                        </p> --%>
                        <div class="d-flex">
                            <button class="btn btn-outline-dark flex-shrink-0" type="button" id="purchase">
                                <i class="bi-cart-fill me-1"></i>
                                구매하기
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </section>
       
        <!-- Footer-->
        <footer class="py-5 bg-dark">
            <div class="container"><p class="m-0 text-center text-white">Copyright &copy; Your Website 2022</p></div>
        </footer>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="/javascript/getProductScripts.js"></script>
    </body>
</html>
