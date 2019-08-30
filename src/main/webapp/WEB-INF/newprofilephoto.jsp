<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
    <title>Instafam</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <!-- Bootstrap CSS -->
    <link href="<c:url value="/css/bootstrap.css" />" rel="stylesheet">
    <!-- css -->
    <link href="<c:url value="/css/style.css" />" rel="stylesheet">

    <style>
        body,
        html {
            height: 100%;
            margin: 0;
        }
    </style>
</head>

<body>
    <div id="cf4a"></div>

    <!-- <div class="bg"></div> -->
    <div class="container py-5 top-position">
        <div class="col-md-5 m-auto">
            <h4 class="py-2">
                <c:out value="${user.firstName}" />, add a profile pic.</legend>
            </h4>
            <!-- form  -->
            <div id="addform" class="p-4 border rounded bg-transparent">
                <form:form modelAttribute="photo" action="/photos/new/profilepic" method="post"
                    enctype="multipart/form-data">
                    <fieldset>
                        <div class="input-group py-2">
                            <div class="custom-file">
                                <form:input type="file" class="custom-file-input" path="image" />
                                <label class="custom-file-label" for="inputGroupFile04">Choose file</label>
                            </div>
                        </div>
                        <form:input type="hidden" id="caption" path="caption"
                            value="profile picture ${user.firstName} ${user.lastName}" />
                        <div class="form-group">
                            <input type="submit" class="btn btn-primary btn-block" value="Upload">
                        </div>
                        <a href="/feed" class="btn btn-warning text-white btn-block">Back</a>
                    </fieldset>
                </form:form>
            </div>
            <!-- form  -->
        </div>
    </div>
</body>

</html>