<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="currentPageNumber" required="true"
	type="java.lang.Integer" description="Sets the number of pages."%>
<%@ attribute name="resultsPerPage" required="true"
	type="java.lang.Integer"
	description="Sets the number of results per page."%>
<%@ attribute name="paginationStart" required="true"
	type="java.lang.Integer"
	description="Sets at which page the pagination starts."%>
<%@ attribute name="paginationFinish" required="true"
	type="java.lang.Integer"
	description="Sets at which page the pagination finishes."%>

<div class="btn-group btn-group-sm pull-right" role="group">
	<form action="" method="GET">
		<input type="hidden" name="page" value="${currentPageNumber}" />
		<button type="submit" name="resultsPerPage" class="btn btn-default"
			value="10">10</button>
		<button type="submit" name="resultsPerPage" class="btn btn-default"
			value="50">50</button>
		<button type="submit" name="resultsPerPage" class="btn btn-default"
			value="100">100</button>
	</form>
</div>
<div class="container text-center">
	<ul class="pagination">
		<li><a
			href="
					<c:url value="">
						<c:param name="resultsPerPage" value="${resultsPerPage}" />
						<c:if test="${currentPageNumber > 1}">
							<c:param name="page" value="${currentPageNumber - 1}" />
						</c:if>
					</c:url>
					"
			aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
		</a></li>
		<c:forEach var="i" begin="${paginationStart}"
			end="${paginationFinish}">
			<li><a
				href="
							<c:url value="">
								<c:param name="resultsPerPage" value="${resultsPerPage}" />
								<c:param name="page" value="${i}" />
							</c:url>">${i}
			</a></li>
		</c:forEach>
		<li><a
			href="
						<c:url value="">
							<c:param name="resultsPerPage" value="${resultsPerPage}" />
							<c:if test="${currentPageNumber < totalPageNumber}">
								<c:param name="page" value="${currentPageNumber + 1}" />
							</c:if>
						</c:url>"
			aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</div>