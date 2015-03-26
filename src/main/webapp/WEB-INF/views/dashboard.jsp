<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%@ include file="/WEB-INF/partials/css.jsp" %>
<title>Computer Database</title>
</head>
<body>
	<%@ include file="/WEB-INF/partials/header.jsp" %>

	<section id="main"></section>
	<div class="container">
		<h1 id="homeTitle">${computerCount} Computers found</h1>
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm" action="#" method="GET" class="form-inline">
					<input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" /> 
					<input type="submit" id="searchsubmit" value="Filter by name" class="btn btn-primary" />
				</form>
			</div>
			<div class="pull-right">
				<a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> 
				<a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
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
					Computer name 
					<a href="
					<c:url value="dashboard">
						<c:param name="orderBy" value="name"/>
						<c:param name="asc" value="true" />
					</c:url>
					">
					<i class="fa fa-long-arrow-up"></i>
					</a>
					<a href="
					<c:url value="dashboard">
						<c:param name="orderBy" value="name"/>
						<c:param name="asc" value="false" />
					</c:url>
					">
					<i class="fa fa-long-arrow-down"></i>				
					</a>
					</th>
					<th>
					Introduced date
					<a href="
					<c:url value="dashboard">
						<c:param name="orderBy" value="introduced"/>
						<c:param name="asc" value="true" />
					</c:url>
					">
					<i class="fa fa-long-arrow-up"></i>		
					</a>
					<a href="
					<c:url value="dashboard">
						<c:param name="orderBy" value="introduced"/>
						<c:param name="asc" value="false" />
					</c:url>
					">
					<i class="fa fa-long-arrow-down"></i>				
					</a>
					</th>
					<!-- Table header for Discontinued Date -->
					<th>
					Discontinued date
					<a href="
					<c:url value="dashboard">
						<c:param name="orderBy" value="discontinued"/>
						<c:param name="asc" value="true" />
					</c:url>
					">
					<i class="fa fa-long-arrow-up"></i>		
					</a>
					<a href="
					<c:url value="dashboard">
						<c:param name="orderBy" value="discontinued"/>
						<c:param name="asc" value="false" />
					</c:url>
					">
					<i class="fa fa-long-arrow-down"></i>				
					</a>
					</th>
					<!-- Table header for Company -->
					<th>
					Company
					<a href="
					<c:url value="dashboard">
						<c:param name="orderBy" value="company"/>
						<c:param name="asc" value="true" />
					</c:url>
					">
					<i class="fa fa-long-arrow-up"></i>		
					</a>
					<a href="
					<c:url value="dashboard">
						<c:param name="orderBy" value="company"/>
						<c:param name="asc" value="false" />
					</c:url>
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
						<td><a href="
						<c:url value="editComputer">
							<c:param name="id" value="${computer.id}"/>
						</c:url>						
						">${computer.name}</a></td>
						<td>${computer.introduced}</td>
						<td>${computer.discontinued}</td>
						<td>${computer.company}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<footer class="navbar-fixed-bottom">
		<mylib:pagination currentPageNumber="${currentPageNumber}" resultsPerPage="${resultsPerPage}" 
		paginationStart="${paginationStart}"  paginationFinish="${paginationFinish}" ></mylib:pagination>
	</footer>
	<%@ include file="/WEB-INF/partials/js.jsp" %>
	<script src="../js/dashboard.js"></script>
</body>
</html>