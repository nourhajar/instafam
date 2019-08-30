<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    
        <div class="container py-5 mt-5">
	        <div class="col-md-5 m-auto">
	        		<p class="small">Edit your info here, <c:out value="${profileUser.firstName}"/></p>
	                <span class="small text-danger">
	                    <form:errors path="user.*" />
	                </span>
	
	                <form:form method="POST" action="/profiles/${user.id}/update" modelAttribute="user">
	                    <div class="form-group">
	                        <form:input type="text" class="form-control" path="firstName" value="${profileUser.firstName}" />
	                    </div>
	                    <div class="form-group">
	                        <form:input type="text" class="form-control" path="lastName" value="${profileUser.lastName}" />
	                    </div>
	                    <div class="form-group">
	                        <form:input type="email" class="form-control" path="email" value="${profileUser.email}" />
	                    </div>
	                    <form:input type="hidden" path="password" value="${profileUser.password}"/>
	                    <input type="submit" class="btn btn-primary btn-block" value="Edit" />
	                </form:form>
	        </div>
        </div>
    
</body>

</html>