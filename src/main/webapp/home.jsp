<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
                
        <title>Model2 MVC Shop</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
        <link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
		<link href="../css/styles.css" rel="stylesheet" />
				
		<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
		<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
		<script src="../javascript/scripts.js"></script>
		
		<link rel="stylesheet" href="/code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
  		<link rel="stylesheet" href="/resources/demos/style.css">
        
        
        
		<script type="text/javascript">
			var currentPage = 0;
			var searchKeyword = '${search.searchKeyword}';
			
			// 검색 
			function fncGetProductList() {
				$("form[name=detailForm]").attr("action", "/product/listProduct").submit();
			}
						
			/* 상품의 이미지가 여러개인 경우 첫번째 이미지 이름 반환 */
			function getFirstImageName(uuidFileName) {
				var imageName = uuidFileName.split("%")[0];
				
				if(imageName == '') {
					return "noImg.gif";
				}
				
				return imageName;
			}
			
				
			//무한 스크롤을 위해 특정 이벤트 발생시 다음 표시할 상품 리스트를 요청 
			function getProductListByAjax(inputCurrentPage) {
				$.ajax(
					{
						url : "/product/json/getList",
						method : "POST",
						headers : {
							"Accept" : "application/json",
							"Content-Type" : "application/json"
						},
						data : JSON.stringify({
							currentPage : inputCurrentPage,
							searchKeyword : searchKeyword,
						}), 
						success : function(data, status) {
							var displayValueList = [];
							
							data.forEach(function(el, index){
								var displayValue = "<div class='col mb-5'>"
											+"<div class='card h-100'>"
		                        			+"<img class='card-img-top' src='/images/uploadFiles/"+getFirstImageName(el.fileName)+"' alt='...' />"	
	                            			+"<div class='card-body p-4'>"
	                            			+"<div class='text-center'>"
	                            			+"<h5 class='fw-bolder'>"+el.prodName+"</h5>"
	                            			+el.stock+"개 남았어요<br/>"
	                            			+ el.price + "원"
	                                		+"</div></div>"
	                                    	+"<div class='card-footer p-4 pt-0 border-top-0 bg-transparent'>"
	                                		+"<div class='text-center'><a class='btn btn-outline-dark mt-auto' href='/product/getProduct?prodNo="+el.prodNo+"&menu=search'>제품 상세보기</a></div>"
	                            			+"</div></div></div>" 
									
								displayValueList.push(displayValue);
							});
							$("#displayMenu").append(displayValueList);
						}
					}
				)
			}
			
			$(function() {				
				/* 검색 후 이미 화면이 꽉차 스크롤 못하면 자동으로 무한 스크롤 시작  */
				if($(window).scrollTop() == $(document).height() - $(window).height()) {
					currentPage++;
					getProductListByAjax(currentPage);
				}  
				
				/* AutoComplete을 위해 미리 prodNameList 가져온다 */
				prodNameList = [];
				$.get("/product/json/getList", function(data) {
					data.forEach(function(el, index){
						prodNameList.push(el);
					})
				})

				/* 검색버튼 매핑 */
				$("form.d-flex button").on("click", function() {
					var searchKeyword = $("input[name='searchKeyword']").val();
					fncGetProductList(searchKeyword);
				});
				
				/*정렬 버튼 매핑*/
				$("#searchCondition0Btn").on("click", function() {
					var searchKeyword = $("#searchCondition0Btn").val();
					
					var url = "/product/listProduct?searchCondition=0&searchKeyword=" + searchKeyword;
					$(location).attr("href", url);
				});
				
				$("#searchCondition1Btn").on("click", function() {
					var searchKeyword = $("#searchCondition1Btn").val();
					
					var url = "/product/listProduct?searchCondition=1&searchKeyword=" + searchKeyword;
					$(location).attr("href", url);
				});
				
				$("#searchCondition2Btn").on("click", function() {
					var searchKeyword = $("#searchCondition2Btn").val();
					
					var url = "/product/listProduct?searchCondition=2&searchKeyword=" + searchKeyword;
					$(location).attr("href", url);
				});
				
				$("#searchCondition3Btn").on("click", function() {
					var searchKeyword = $("#searchCondition3Btn").val();
					
					var url = "/product/listProduct?searchCondition=3&searchKeyword=" + searchKeyword;
					$(location).attr("href", url);
				});
				
				/* append된 상품 상세보기 */
				/* $(document).on("click", ".prodName" ,function() {
					var prodNo = getProductId($(this));
					
					url = "/product/getProduct?prodNo="+prodNo+"&menu=search";
					$(location).attr("href", url);
				}); */ 
										
				/* 검색창 클릭시 AutoComplete */
				$("form.d-flex input").on("click", function() {
					$("form.d-flex input").autocomplete({
						source : prodNameList
					});
				});
				
				/*무한 스크롤을 위해 커서의 위치가 하단으로 위치하면 이벤트 발생 */
				$(window).scroll(function() {
					if($(window).scrollTop() == $(document).height() - $(window).height()) {
						currentPage++;
						getProductListByAjax(currentPage, searchKeyword);
					}  
				})
			})
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
    	
        
        <!-- Section-->
        <section class="py-5">
            <div class="container px-4 px-lg-5 mt-5">
                <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center" id="displayMenu">
            		<!-- Ajax 사용해서 동적으로 상품 추가  -->
        </section>
        <!-- Footer-->
        <footer class="py-5 bg-dark">
            
        </footer>
    </body>
</html>
