<%@ tag description='head onderdeel van pagina' pageEncoding='UTF-8'%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>

<header>
	<h1>Wereldwijnen</h1>
	<nav class="clearFix">
		<ul>
			<c:forEach items="${landen}" var="land">
				<c:url value='' var='landurl'>
					<c:param name="landid" value='${land.id}' />
				</c:url>
				<li><a href="${landurl}"><img
						src=<c:url value="/images/${land.id}.png"/> alt="${land.naam}"></a></li>
			</c:forEach>
			
		</ul>
		<p>
		<c:if test="${not empty sessionScope.mandje}">
			<a href=''><img src=<c:url value="/images/mandje.png"/> alt="mandje"></a>
		</c:if>
		<p>
	</nav>
</header>