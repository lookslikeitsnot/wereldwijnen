<%@ page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix="v" uri='http://vdab.be/tags'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<!doctype html>
<html lang='nl'>
<v:head title='Wereldwijnen' />
<body>
	<v:header />
	<a href="<c:url value='/'/>">Terug naar overzicht</a>
	<c:choose>
		<c:when test="${not empty wijn}">
			<dl>
				<dt>Land</dt>
				<dd>${wijn.soort.land.naam}</dd>
				<dt>Soort</dt>
				<dd>${wijn.soort.naam}</dd>
				<dt>Jaar</dt>
				<dd>${wijn.jaar}</dd>
				<dt>Beoordeling</dt>
				<dd>
					<c:forEach begin='1' end='${wijn.beoordeling}'>&#9733;</c:forEach>
				</dd>
				<dt>Prijs</dt>
				<dd>
					<fmt:formatNumber value='${wijn.prijs}' type="currency"
						currencyCode="EUR" />
				</dd>
			</dl>
			<form method="post">
				<label for="aantalflessen">Aantal flessen</label> <input
					type="number" min='1' name="aantalflessen" id="aantalflessen" autofocus required>
					<input type='hidden' name='wijnid' value='${param.wijnid}'>${fouten.aantalflessen}
				<input type="submit" value="Toevoegen">
			</form>
		</c:when>
		<c:otherwise>
			<h1>Wijn niet gevonden</h1>
		</c:otherwise>
	</c:choose>
</body>
</html>