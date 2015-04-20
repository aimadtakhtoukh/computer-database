<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">		
		<a class="navbar-brand" href="dashboard"> <spring:message code="application.title"/> </a>
			
		<img class="icon" onclick="updateQueryStringParameter('lang','fr')" src="/images/france.png"/>
		<img class="icon" onclick="updateQueryStringParameter('lang','en')" src="/images/etats-unis.png"/>
		
	</div>
</header>