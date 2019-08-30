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
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.7.0/css/all.css'
		integrity='sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ' crossorigin='anonymous'>
	<!-- Lightbox -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.8.1/baguetteBox.min.css">
	<link href="<c:url value="/css/Lightbox.css" />" rel="stylesheet">

	<!-- Bootstrap CSS -->
	<link href="<c:url value="/css/bootstrap.css" />" rel="stylesheet">
	<!-- css -->
	<link href="<c:url value="/css/style.css" />" rel="stylesheet">


</head>

<body class="bg-gray">
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

	<!-- user infor bar -->
	<div class="container py-5 mt-5">
		<div class="row col-12 col-md-8 m-auto py-5">
			<div class="col-12 col-md-4">
				<div class="big-portfolio-image rounded-circle">
					<c:choose>
						<c:when test="${profileUser eq user}">
							<c:choose>
								<c:when test="${profileUser.profilePic.imageFileName.length() gt 0}">
									<a href="/photos/update/profilepic"><img
											src="<c:url value="/image/" />${profileUser.profilePic.imageFileName}"/>
									</a>
								</c:when>
								<c:otherwise>
									<a href="/photos/new/profilepic" class="text-center ml-5 mt-5">
									<i class="fab fa-instagram ml-3 mt-4" style="font-size: 2em;"></i>
									<p class="small">Add profile pic</p>
									</a>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:if test="${profileUser.profilePic.imageFileName.length() ne 0}">
								<div>
									<img src="<c:url value="/image/" />${profileUser.profilePic.imageFileName}"/> </div>
							</c:if>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="col-12 col-md-6 py-2">
				<div class="py-1">
					<div class="row">
						<div class="col-8 font-weight-light user-name">
							<c:out value="${profileUser.firstName}" />
						</div>
						<div class="col-4 align-middle">
							<c:if test="${profileUser eq user}">
								<a href="/profiles/${user.id}/update">
									<i class="far fa-edit" style="font-size:.8em;"></i>
									edit
								</a>
							</c:if>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-4">
						<p class="align-middle">
							<span class="font-weight-bold"><c:out value="${profileUser.photos.size()}" /></span> posts
						</p>
					</div>
					<div class="col-4">
						<c:set var="totalLikes" value="${0}"/>
						<c:forEach items="${profileUser.photos}" var="photo">
							<c:set var="total" value="${total + photo.likedUsers.size()}" />
						</c:forEach>
						<c:if test="${total gt 0}">
							<span class="font-weight-bold"> <c:out value="${total}" /></span> likes
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- end user infor bar-->

	<!-- photo wall -->
	<div class="container px-3">
		<div class="tz-gallery">
			<div class="row">
				<c:forEach items="${photos}" var="photo">
					<div class="col-md-4">
						<div class="card">
							<a class="lightbox" href="<c:url value="/image/" />${photo.imageFileName}"/>
							<div class="box9">
								<img src="<c:url value="/image/" />${photo.imageFileName}"/>
								<div class="box-content">
									<h3 class="title">
										<c:out value="${photo.caption}" />
									</h3>
									<ul class="icon">
										
										
										<c:choose>
											<c:when test="${photo.likedUsers.contains(user)}">
												<a href="/profiles/likes/${photo.id}/remove">
	
													<li class="px-2"><i class="fas fa-heart"></i>
														<c:out value="${photo.likedUsers.size()}" />
													</li>
												</a>
											</c:when>
											<c:otherwise>
												<a href="/profiles/likes/${photo.id}/add">
													<li class="px-2"><i class="fas fa-heart"></i>
														<c:out value="${photo.likedUsers.size()}" />
									
													</li>
												</a>
											</c:otherwise>
										</c:choose>
										
										<li><i class="fas fa-comment"></i>
											<c:out value="${photo.comments.size()}" />
										</li>
										
										
									
									</ul>
								</div>
							</div>
							</a>
						</div>
					</div>
				</c:forEach>

			</div>
		</div>
	</div>
	<!--end  photo wall -->

	<!-- test -->


	<!-- test -->


	</div>
</body>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.8.1/baguetteBox.min.js"></script>
<script>
	baguetteBox.run('.tz-gallery');
</script>


</html>