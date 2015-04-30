<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%@ include file="/WEB-INF/partials/css.jsp" %>
<title><spring:message code="application.page_title"/></title>
</head>
<body>
	<%@ include file="/WEB-INF/partials/header.jsp" %>

	<section id="main"></section>
	<div class="container">
		<h1 id="homeTitle">${computerCount} <spring:message code="dashboard.computer_found"/></h1>
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm" action="dashboard" method="GET" class="form-inline">
					<input type="hidden" name="resultsPerPage" value="${resultsPerPage}" />
					<input type="hidden" name="page" value="${page}" />
					<input type="hidden" name="orderBy" value="${orderBy}" />
					<input type="hidden" name="asc" value="${asc}" />
					<input type="search" id="searchbox" name="search" class="form-control" 
					placeholder="<spring:message code="dashboard.search_placeholder"/>" value="${searchString}" />
					<input type="submit" id="searchsubmit" value="<spring:message code="dashboard.filter_by_name"/>" class="btn btn-primary" />
				</form>
			</div>
			<div class="pull-right">
				<a class="btn btn-success" id="addComputer" href="addComputer"><spring:message code="dashboard.add_computer"/></a> 
				<a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="dashboard.edit"/></a>
			</div>
		</div>
	</div>

	<form id="deleteForm" action="deleteComputer" method="POST">
		<input type="hidden" name="selection" value="">
	</form>

	<div class="container" style="margin-top: 10px;">
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<!-- Table header for Computer Name -->

					<th class="editMode" style="width: 60px; height: 22px;"><input
						type="checkbox" id="selectall" /> 
						<span style="vertical-align: top;"> - 
							<a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
								<i class="fa fa-trash-o fa-lg"></i>
							</a>
						</span>
					</th>
					
					<th>
					<spring:message code="computer_name"/> 
					<a href="
						<mylib:link 
							target="dashboard" 
							resultsPerPage="${resultsPerPage}" 
							search="${searchString}" 
							page="${currentPageNumber}"  
							orderBy="name"  
							ascendent="true"
						/>
					">
					<i class="fa fa-long-arrow-up"></i>
					</a>
					<a href="		
						<mylib:link 
							target="dashboard" 
							resultsPerPage="${resultsPerPage}" 
							search="${searchString}" 
							page="${currentPageNumber}"  
							orderBy="name"  
							ascendent="false"
						/>
					">
					<i class="fa fa-long-arrow-down"></i>				
					</a>
					</th>
					<th>
					<spring:message code="introduced_date"/>
					<a href="		
						<mylib:link 
							target="dashboard" 
							resultsPerPage="${resultsPerPage}" 
							search="${searchString}" 
							page="${currentPageNumber}"  
							orderBy="introduced"  
							ascendent="true"
						/>
					">
					<i class="fa fa-long-arrow-up"></i>		
					</a>
					<a href="		
						<mylib:link 
							target="dashboard" 
							resultsPerPage="${resultsPerPage}" 
							search="${searchString}" 
							page="${currentPageNumber}"  
							orderBy="introduced"  
							ascendent="false"
						/>
					">
					<i class="fa fa-long-arrow-down"></i>				
					</a>
					</th>
					<!-- Table header for Discontinued Date -->
					<th>
					<spring:message code="discontinued_date"/>
					<a href="		
						<mylib:link 
							target="dashboard" 
							resultsPerPage="${resultsPerPage}" 
							search="${searchString}" 
							page="${currentPageNumber}"  
							orderBy="discontinued"  
							ascendent="true"
						/>
					">
					<i class="fa fa-long-arrow-up"></i>		
					</a>
					<a href="		
						<mylib:link 
							target="dashboard" 
							resultsPerPage="${resultsPerPage}" 
							search="${searchString}" 
							page="${currentPageNumber}"  
							orderBy="discontinued"  
							ascendent="false"
						/>
					">
					<i class="fa fa-long-arrow-down"></i>				
					</a>
					</th>
					<!-- Table header for Company -->
					<th>
					<spring:message code="company"/>
					<a href="		
						<mylib:link 
							target="dashboard" 
							resultsPerPage="${resultsPerPage}" 
							search="${searchString}" 
							page="${currentPageNumber}"  
							orderBy="company"  
							ascendent="true"
						/>
					">
					<i class="fa fa-long-arrow-up"></i>		
					</a>
					<a href="		
						<mylib:link 
							target="dashboard" 
							resultsPerPage="${resultsPerPage}" 
							search="${searchString}" 
							page="${currentPageNumber}"  
							orderBy="company"  
							ascendent="false"
						/>
					">
					<i class="fa fa-long-arrow-down"></i>				
					</a>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${items}" var="computer">
					<tr>
						<td class="editMode">
							<input type="checkbox" name="cb" class="cb" value="${computer.id}">
						</td>
						<td>
						<a href="
							<c:url value="editComputer">
								<c:param name="id" value="${computer.id}"/>
							</c:url>						
						">
						${computer.name}</a></td>
						<td>${computer.introduced}</td>
						<td>${computer.discontinued}</td>
						<td>${computer.company.name}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<footer class="navbar-fixed-bottom">
		<mylib:pagination currentPageNumber="${currentPageNumber}" resultsPerPage="${resultsPerPage}" 
		paginationStart="${paginationStart}"  paginationFinish="${paginationFinish}"></mylib:pagination>
	</footer>
<%@ include file="/WEB-INF/partials/js.jsp" %>
<%@ include file="/WEB-INF/partials/dashboard.jsp" %>
</body>
</html>