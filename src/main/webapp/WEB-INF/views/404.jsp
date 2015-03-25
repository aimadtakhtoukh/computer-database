<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<%@ include file="/WEB-INF/partials/css.jsp" %>
</head>
<body>

	<%@ include file="/WEB-INF/partials/header.jsp" %>

    <section id="main">
        <div class="container">
            <div class="alert alert-danger">
                Error 404: Page not found. Too bad bitch!
                <br/>
                <!-- stacktrace -->
            </div>
        </div>
    </section>

	<%@ include file="/WEB-INF/partials/js.jsp" %>
    <script src="../js/dashboard.js"></script>

</body>
</html>