<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- 페이지 구성에 필요한 link start -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<link href="/css/styles.css" rel="stylesheet" />
<!-- 페이지 구성에 필요한 link end -->


<!-- jQuery CDN -->
<!-- <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script> -->
<script type="text/javascript">
	$(function() {
		
		$.get("/user/json/purchaseAmount", {userId:'${user.userId}'}, function(data){
			$("#amount").append(data.amount);
		})
	})
</script>
 
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container px-4 px-lg-5">
                <span class="material-symbols-outlined">
					gite  
				</span>
				<a class="navbar-brand" href="/">Model2MvcShop</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                        <li class="nav-item"><a class="nav-link" href="/user/getUser">개인정보조회</a></li>
                        <li class="nav-item"><a class="nav-link" href="/user/logout">로그아웃</a></li>
                    </ul>
                    
                    <nav class="navbar bg-body-tertiary">
					  <div class="container-fluid">
					    <form class="d-flex" role="search">
					      <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search" name="searchKeyword">
					      <button class="btn btn-outline-success" type="submit">Search</button>
					    </form>
					  </div>
					</nav>
                    <form class="d-flex">
                        <button class="btn btn-outline-dark" type="button" onclick="location.href = '../purchase/testListPurchaseView.jsp';">
                            <i class="bi-cart-fill me-1"></i>
                            구매이력조회
                            <span class="badge bg-dark text-white ms-1 rounded-pill" id="amount"></span>
                        </button>
                    </form>
                </div>
            </div>
        </nav>
        <!-- Header-->
        <header class="bg-dark py-5">
            <div class="container px-4 px-lg-5 my-5">
                <div class="text-center text-white">
                    <h1 class="display-4 fw-bolder">Model2 MVC Shop</h1>
                    <p class="lead fw-normal text-white-50 mb-0">
                    	<audio autoplay controls>
						    <source src="/music/bgm.mp3" type="audio/mp3">
						</audio>
                    </p>
                </div>
            </div>
        </header>
</body>
</html>