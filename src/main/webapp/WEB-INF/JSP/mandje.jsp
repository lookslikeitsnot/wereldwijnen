<%@ page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix="v" uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<v:head title='Mandje' />
<body>
	<v:header />
	<h2>Mandje</h2>
	<a href="<c:url value='/'/>">Terug naar overzicht</a>
	<c:if test="${not empty sessionScope.mandje}">
		<table>
			<tr>
				<th>Wijn</th>
				<th>Prijs</th>
				<th>Aantal</th>
				<th>Te betalen</th>
			</tr>
			<c:forEach items="${mandje}" var="mandjeLijn">
				<tr>
					<td>${mandjeLijn.wijn.soort.land.naam}
						${mandjeLijn.wijn.soort.naam} ${mandjeLijn.wijn.jaar}</td>
					<td>${mandjeLijn.wijn.prijs}</td>
					<td>${mandjeLijn.aantal}</td>
					<td>${mandjeLijn.prijs}</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="3"></td>
				<td>Totaal:${mandjePrijs}</td>
			</tr>
		</table>
		<form method="post">
			<label for='naam'>Naam</label>${fouten.naam}<br>
			<input type='text' name="naam" id='naam' autofocus required><br>
			<label for='straat'>Straat</label>${fouten.straat}<br>
			<input type='text' name="straat" id='straat' required><br>
			<label for='huisnummer'>Huisnummer</label>${fouten.huisnummer}<br>
			<input type='text' name="huisnummer" id='huisnummer' required><br>
			<label for='postcode'>Postcode</label>${fouten.postcode}<br>
			<input type='text' name="postcode" id='postcode' required><br>
			<label for='gemeente'>Gemeente</label>${fouten.gemeente}<br>
			<input type='text' name="gemeente" id='gemeente' required><br>
			<input type="radio" name='bestelwijze' value='afhalen' required>Afhalen<br>
			<input type="radio" name='bestelwijze' value='opsturen'>Opsturen<br>
			<input type="submit" value='Als bon bevestigen'>
		</form>
	</c:if>
	<c:if test="${empty sessionScope.mandje && not empty sessionScope.bestelbonnummer}">
	<h2>Je mandje is bevestigd als bestelbon ${sessionScope.bestelbonnummer}</h2>
	</c:if>
</body>
</html>