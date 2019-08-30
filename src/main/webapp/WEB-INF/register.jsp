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
    <!-- Bootstrap CSS -->
    <link href="<c:url value="/css/bootstrap.css" />" rel="stylesheet">
    <!-- css -->
    <link href="<c:url value="/css/style.css" />" rel="stylesheet">
    <link href="<c:url value="/css/cover.css" />" rel="stylesheet">
</head>

<body>
    <div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">

        <div class="masthead mb-auto"></div>
        <!-- registration -->
        <div class="row">

            <div class="col-md-6 d-none d-sm-none d-md-block">
                <img src="images/login_phone.gif" alt="iphone">
            </div>
            <div class="col-md-6">
                <img src="images/instafam_logo.png" alt="iphone">
                <p class="small">Sign up to see photos and videos from your family.</p>
                <span class="small text-danger">
                    <form:errors path="user.*" />
                </span>

                <form:form method="POST" action="/registration" modelAttribute="user">
                    <div class="form-group">
                        <form:input type="text" class="form-control" path="firstName" placeholder="First Name" />
                    </div>
                    <div class="form-group">
                        <form:input type="text" class="form-control" path="lastName" placeholder="Last Name" />
                    </div>
                    <div class="form-group">
                        <form:input type="email" class="form-control" path="email" placeholder="Email" />
                    </div>
                    <div class="form-group">
                        <form:password path="password" class="form-control" placeholder="Password" />
                    </div>
                    <div class="form-group">
                        <form:password path="passwordConfirmation" class="form-control"
                            placeholder="Confirm Password" />
                    </div>
                    <input type="submit" class="btn btn-primary btn-block" value="Sign up" />
                </form:form>
                <div>
                    <p>Have an account? <a href="/">Log in</a></p>
                </div>
            </div>
        </div>
        <!-- registration -->
        <footer class="mastfoot mt-auto"></footer>
    </div>
</body>

</html>