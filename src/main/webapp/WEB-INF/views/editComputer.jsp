<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
<title><spring:message code="application.page_title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%@ include file="/WEB-INF/partials/css.jsp" %>
</head>
<body>

	<%@ include file="/WEB-INF/partials/header.jsp" %>
	
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${computerId}
                    </div>
                    <h1><spring:message code="editComputer.edit_computer"/></h1>

                    <form action="editComputer" method="POST">
                        <input type="hidden" name="computerId" value="${computerId}"/>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="computer_name"/></label>
                                <input type="text" class="form-control" 
                                id="computerName" placeholder="Computer name"
                                name="computerName" value="${computerName}">
								<span id="computerNameMessage"></span>
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="introduced_date"/></label>
                                <input type="date" class="form-control" 
                                id="introduced" placeholder="Introduced date"
                                name="introduced" value="${computerIntroduced}">
								<span id="introducedMessage"></span>
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="discontinued_date"/></label>
                                <input type="date" class="form-control" 
                                id="discontinued" placeholder="Discontinued date"
                                name="discontinued" value="${computerDiscontinued}">
								<span id="discontinuedMessage"></span>
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="company"/></label>
                                <select class="form-control" id="companyId" name="companyId">
                                    <option value="0">--</option>
									<c:forEach items="${companies}" var="company">
										<c:choose>
											<c:when test="${company.id == companySelectedId}">
												<option value="${company.id}" selected="selected">${company.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${company.id}">${company.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
                                </select>
                            </div>
							<span id="companyMessage"></span>
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<spring:message code="editComputer.edit"/>" class="btn btn-primary">
                            <spring:message code="application.or"/> 
                            <a href="dashboard" class="btn btn-default"><spring:message code="application.cancel"/></a>
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