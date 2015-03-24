<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%@ include file="/WEB-INF/partials/css.jsp" %>
</head>
<body>

	<%@ include file="/WEB-INF/partials/header.jsp" %>
	
	<c:if test="${show && showSuccess}">
		<div class="col-xs-8 col-xs-offset-2 alert alert-info" role="alert">${message}</div>
	</c:if>
	<c:if test="${show && !showSuccess}">
		<div class="col-xs-8 col-xs-offset-2 alert alert-danger" role="alert">${message}</div>
	</c:if>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form action="addComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> 
								<input
									type="text" class="form-control" id="computerName"
									name="computerName" placeholder="Computer name">
								<span id="computerNameMessage"></span>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> 
								<input
									type="date" class="form-control" id="introduced"
									name="introduced" placeholder="Introduced date">
								<span id="introducedMessage"></span>
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> 
								<input
									type="date" class="form-control" id="discontinued"
									name="discontinued" placeholder="Discontinued date">
								<span id="discontinuedMessage"></span>
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> 
								<select
									class="form-control" id="companyId" name="companyId">
									<option value="0">--</option>
									<c:forEach items="${companies}" var="company">
										<option value="${company.id}">${company.name}</option>
									</c:forEach>
								</select>
								<span id="companyMessage"></span>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<%@ include file="/WEB-INF/partials/js.jsp" %>
	<script src="../js/addVerification.js"></script>

</body>
</html>