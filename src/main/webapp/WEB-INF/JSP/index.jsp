<%@ page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix="v" uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<v:head title='Wereldwijnen' />
<body>
	<v:header />
	<c:if test='${not empty soorten}'>
		<h2>Soorten uit ${land.naam}</h2>
		<c:forEach items='${soorten}' var='soort'>
			<c:url value='' var='soorturl'>
				<c:param name="landid" value='${param.landid}' />
				<c:param name="soortid" value='${soort.id}' />
			</c:url>
			<a href='${soorturl}'>${soort.naam}</a>
		</c:forEach>
	</c:if>
	<c:if test="${not empty wijnen}">
		<h2>Wijnen uit ${soort.naam}</h2>
		<ul>
			<c:forEach items="${wijnen}" var="wijn">
				<c:url value='/wijn.htm' var='wijnurl'>
					<c:param name="wijnid" value="${wijn.id}"/>
				</c:url>
				<li><a href='${wijnurl}'> ${wijn.jaar} <c:forEach begin='1'
							end="${wijn.beoordeling}">&#9733;</c:forEach>
				</a></li>
			</c:forEach>
		</ul>
	</c:if>
</body>
</html>