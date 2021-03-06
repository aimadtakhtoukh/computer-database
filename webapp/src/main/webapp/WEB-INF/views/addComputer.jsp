<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<spring:message code="placeholder.computer_name" var="computerName" />
<spring:message code="placeholder.introduced_date" var="introducedDate" />
<spring:message code="placeholder.discontinued_date" var="discontinuedDate" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="application.page_title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%@ include file="/WEB-INF/partials/css.jsp" %>
</head>
<body>

	<%@ include file="/WEB-INF/partials/header.jsp" %>
	
	<c:if test="${show && showSuccess}">
		<div class="col-xs-8 col-xs-offset-2 alert alert-info" role="alert">
			<c:forEach items="${message}" var="m" >
				<p>${m}</p>
			</c:forEach>
		</div>
	</c:if>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1><spring:message code="addComputer.add_computer"/></h1>
					<form:form action="addComputer" method="POST" commandName="computerDTO">
						<fieldset>
							<div class="form-group row">
								<form:label path="name" for="computerName"><spring:message code="computer_name"/></form:label> 
								<form:input path="name" 
									type="text" class="form-control" id="computerName"
									name="computerName" placeholder="${computerName}"></form:input>
								<div id="computerNameMessageDiv" class="col-xs-8 col-xs-offset-2 alert alert-danger" role="alert">
									<span id="computerNameMessage">${nameErrorMessage}</span>
								</div>
							</div>
							<div class="form-group row">
								<form:label path="introduced" for="introduced"><spring:message code="introduced_date"/></form:label> 
								<form:input path="introduced"
									type="date" class="form-control" id="introduced"
									name="introduced" placeholder="${introducedDate}"></form:input>
								<div id="introducedMessageDiv" class="col-xs-8 col-xs-offset-2 alert alert-danger" role="alert">
									<span id="introducedMessage">${introducedErrorMessage}</span>
								</div>
							</div>
							<div class="form-group row">
								<form:label path="discontinued" for="discontinued"><spring:message code="discontinued_date"/></form:label> 
								<form:input path="discontinued"
									type="date" class="form-control" id="discontinued"
									name="discontinued" placeholder="${discontinuedDate}"></form:input>
								<div id="discontinuedMessageDiv" class="col-xs-8 col-xs-offset-2 alert alert-danger" role="alert">
									<span id="discontinuedMessage">${discontinuedErrorMessage}</span>
								</div>
							</div>
							<div class="form-group row">
								<form:label path="company.id" for="companyId"><spring:message code="company"/></form:label> 
								<form:select path="company.id"
									class="form-control" id="companyId" name="companyId">
									<option value="0">--</option>
									<c:forEach items="${companies}" var="company">
										<option value="${company.id}">${company.name}</option>
									</c:forEach>
								</form:select>
								<span id="companyMessage"></span>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message code="addComputer.add"/>" class="btn btn-primary">
							<spring:message code="application.or"/> 
							<a href="dashboard" class="btn btn-default"><spring:message code="application.cancel"/></a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
<%@ include file="/WEB-INF/partials/js.jsp" %>
<%@ include file="/WEB-INF/partials/addVerification.jsp" %>
</body>
</html>