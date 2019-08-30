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

<body class="">
    <div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">

        <div class="masthead mb-auto"></div>
        <!-- login -->
        <main class="inner cover">



            <div class="row ">

                <div class="col-md-6 d-none d-sm-none d-md-block">
                    <img src="images/login_phone.gif" alt="iphone">
                </div>
                <div class="col-md-6">
                    <div class="p-4">
                        <img src="images/instafam_logo.png" alt="instafam logo">
                    </div>
                    <span class="small text-danger">
                        <c:out value="${error}" />
                    </span>
                    <form method="post" action="/login">
                        <div class="form-group">
                            <input type="text" id="email" class="form-control" name="email" placeholder="email" />
                        </div>
                        <div class="form-group">
                            <input type="password" id="password" class="form-control" name="password"
                                placeholder="password" />
                        </div>
                        <div>
                            <input type="submit" class="btn btn-primary btn-block" value="Login" />
                        </div>
                    </form>

                    <div>
                        <p>Don't have an account? <a href="/register">Sign up</a></p>
                    </div>
                </div>
            </div>

        </main>
        <!-- end login -->

        <footer class="mastfoot mt-auto"></footer>
    </div>
</body>

</html>