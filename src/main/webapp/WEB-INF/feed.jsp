<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Instafam</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.7.0/css/all.css'
		integrity='sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ' crossorigin='anonymous'>
	<!-- Bootstrap CSS -->
	<link href="<c:url value="/css/bootstrap.css" />" rel="stylesheet">
	<!-- css -->
	<link href="<c:url value="/css/style.css" />" rel="stylesheet">
<!-- 	<script type="text/javascript" src="js/comment.js"></script>
 --></head>

<body class="card-header">
	<!-- nav bar -->
	<div class="navbar navbar-expand-md fixed-top bg-white border-bottom shadow-sm ">
		<div class="container align-middle py-2">
			<div class="row col-12">
				<div class="col-4">
					<a href="/feed">
						<div class="row">

							<div class="col-3 col-md-2 py-2">
								<i class="fab fa-instagram" style="font-size: 2em;"></i>
							</div>
							<div class="col-8 col-md-6">
								<img src="/images/instafam_logo.png" alt="instafam logo">
							</div>
						</div>
					</a>
				</div>
				<div class="col-4 col-md-2 ml-md-auto">

					<div class="row py-3">
						<div class="col-1">
							<a href="/photos/new">
								<i class="fas fa-plus"></i>
							</a>
						</div>
						<div class="col-1 right">
							<a href="/logout">
								<i class="fas fa-sign-out-alt"></i>
							</a>
						</div>
						<div class="col-1">
							<a href="/profiles/${user.id}">
								<i class="far fa-user"></i>
							</a>
						</div>
					</div>
				</div>
			</div>


		</div>
	</div>
	<!-- end nav bar -->

	<!-- main context -->
	<div class="container my-5">
		<div class="row">
			<!-- photo list -->
			<div class="col-md-7 my-5">
				<!-- each photo -->
				<c:forEach items="${photos}" var="photo">
					<div class="border rounded my-5 bg-white">
						<div class="row py-2 pl-2">
							<div  style="width:55px" class="pl-3">
								<div class="fork-image rounded-circle">
									<c:choose>
										<c:when test="${photo.uploader.profilePic.imageFileName.length() gt 0}">
											<img src="<c:url value="/image/"/>${photo.uploader.profilePic.imageFileName}"/>
										</c:when>
										<c:otherwise>
											<i class="fab fa-instagram ml-3" style="font-size: 2em;"></i>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							
								<h3 class="col-3 col-md-4 py-1">
									<a href="/profiles/${photo.uploader.id}">
										<c:out value="${photo.uploader.firstName}" />
									</a>
								</h3>
							
						</div>
						<img src="<c:url value="/image/"/>${photo.imageFileName}" alt="">

						<div class="container">
							<div class="row py-2">

								<div class="col-1">
									<c:choose>
										<c:when test="${photo.likedUsers.contains(user)}">
											<a href="/likes/${photo.id}/remove">

												<i class='fas fa-heart' style='font-size:1.5em;color:red'></i>
											</a>
										</c:when>
										<c:otherwise>
											<a href="/likes/${photo.id}/add">
												<i class='far fa-heart' style="font-size:1.5em;"></i>
											</a>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="col-1">
									<i class="far fa-comment" style="font-size:1.5em;"></i>
								</div>
							</div>
						</div>

						<div class="container">
							<p>
								<c:out value="${photo.likedUsers.size()}" /> <span>likes</span>
							</p>
							<p>
								<span class="font-weight-bold"><c:out value="${photo.uploader.firstName}" /></span>
								<span class="font-weight-lighter"> <c:out value="${photo.caption}" /> </span>
							</p>
							<div class="border-bottom" id="comment_container">
								<c:forEach items="${photo.comments}" var="comment">
									<p>
										<span class="font-weight-bold"><c:out value="${comment.user.firstName}" /></span>
										<span class="font-weight-lighter"> <c:out value="${comment.comment}" /> </span>
<%-- 										<c:if test="${comment.user eq user}">
											<form action="/comments/${comment.id}" method="post">
											    <input type="hidden" name="_method" value="delete">
											    <input type="submit" value="Delete">
											</form>
										</c:if> --%>
									</p>
								</c:forEach>
							</div>
							<!-- add comment -->
							<div class="py-2">
								<div class="input-group mb-3">
									<form action="/comments" method="POST" class="input-group mb-3">
										<input type="text" class="form-control" name="comment" placeholder="Add a comment...">
										<input type="hidden" name="photo" value="${photo.id}">
										<div class="input-group-append">
											<input class="btn btn-outline-secondary" type="submit" value="Post"
												class="input-group-text" class="comment_button" />
										</div>
									</form>
								</div>
							</div>
							<!-- end add comment -->
						</div>
					</div>
				</c:forEach>
				<!-- end each photo -->
			</div>
			<!-- end photo list -->
			<div class="col-md-3 my-5 position-fixed infor-bar d-none d-sm-none d-md-block">
				<div class="container mt-5">
					<div class="row">
						<div class="col-4">
							<div class="portfolio-image rounded-circle">
								<c:choose>
									<c:when test="${user.profilePic.imageFileName.length() gt 0}">
										<img src="<c:url value="/image/"/>${user.profilePic.imageFileName}"/>
									</c:when>
									<c:otherwise>
										<i class="fab fa-instagram ml-3" style="font-size: 2em;"></i>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="col py-1">
							<a href="/profiles/${user.id}">
								<c:out value="${user.firstName}" />
							</a>
							<p class="small">
								<c:out value="${user.email}" />
							</p>
						</div>
					</div>
				</div>
				<div class="rounded border bg-white mt-3">
					<div class="p-3">
						<div class="py-2 border-bottom">
							<h6>Family Members</h6>
						</div>
						<div class="family-box">
							<c:forEach items="${members}" var="member">
								<c:if test="${member ne user}">
									<div class="row py-3">
										<div class="col-3">
											<div class="fork-image rounded-circle">
										
												<c:choose>
													<c:when test="${member.profilePic.imageFileName.length() gt 0}">
														<img src="<c:url value="/image/"/>${member.profilePic.imageFileName}"/>
													</c:when>
													<c:otherwise>
														<i class="fab fa-instagram ml-2" style="font-size: 2em;"></i>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
										<a href="/profiles/${member.id}">
											<div class="col py-2">
												<c:out value="${member.firstName}" />
											</div>
										</a>
									</div>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>

	</div>
	<!-- end main context -->


</body>

</html>