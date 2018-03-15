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
	<c:choose>
		<c:when test="${not empty sessionScope.mandje}">
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
				<label for='naam'>Naam</label> ${fouten.naam}<br> <input
					type='text' name="naam" id='naam' maxlength="50" autofocus required><br>
				<label for='straat'>Straat</label> ${fouten.straat}<br> <input
					type='text' name="straat" id='straat' maxlength="50" required><br>
				<label for='huisnummer'>Huisnummer</label> ${fouten.huisnummer}<br>
				<input type='text' name="huisnummer" id='huisnummer' maxlength="50"
					required><br> <label for='postcode'>Postcode</label>
				${fouten.postcode}<br> <input type='text' name="postcode"
					id='postcode' maxlength="50" required><br> <label
					for='gemeente'>Gemeente</label> ${fouten.gemeente}<br> <input
					type='text' name="gemeente" id='gemeente' maxlength="50" required><br>
				<c:if test="${not empty fouten.bestelwijze}">Kies een bestelwijze<br>
				</c:if>
				<input type="radio" name='bestelwijze' value='afhalen' required>Afhalen<br>
				<input type="radio" name='bestelwijze' value='opsturen'>Opsturen<br>
				<input type="submit" value='Als bon bevestigen' id="bevestigknop">
			</form>
			<script>
				document.getElementById('bevestigknop').onsubmit = function() {
					document.getElementById('bevestigknop').disabled = true;
				};
			</script>
		</c:when>
		<c:when
			test="${empty sessionScope.mandje && not empty sessionScope.bestelbonnummer}">
			<h2>Je mandje is bevestigd als bestelbon
				${sessionScope.bestelbonnummer}</h2>
			<c:remove var="bestelbonnummer" scope="session" />
		</c:when>
		<c:otherwise>
			<h2>Mandje is leeg!</h2>
		</c:otherwise>
	</c:choose>
</body>
</html>