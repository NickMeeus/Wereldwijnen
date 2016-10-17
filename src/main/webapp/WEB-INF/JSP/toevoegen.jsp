<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib uri='http://vdab.be/tags' prefix='v'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt'%>

<!doctype html>
<html lang="nl">

<head>
<v:head title="Toevoegen aan mandje"></v:head>
</head>
<body>
	<!-- Vlaggen van elk land tonen -->
	<v:menu />


	<c:if test="${not empty param.wijnId}">
		<h2>Wijn toevoegen aan mandje</h2>

		<c:url var="urlTerugNaarIndexMetParams" value="index.htm">
			<c:param name="landId" value="${wijn.soort.land.id}" />
			<c:param name="soortId" value="${wijn.soort.id}" />
		</c:url>
		<a href="${urlTerugNaarIndexMetParams}"> terug naar overzicht </a>

		<!-- Info over wijn tonen -->
		<table>
			<tr>
				<th>Land</th>
				<td>${wijn.soort.land.naam}</td>
			</tr>
			<tr>
				<th>Soort</th>
				<td>${wijn.soort.naam}</td>
			</tr>
			<tr>
				<th>Jaar</th>
				<td>${wijn.jaar}</td>
			</tr>
			<tr>
				<th>Beoordeling</th>
				<td><c:forEach begin='1' end='${wijn.beoordeling}'>&#9733;</c:forEach></td>
			</tr>
			<tr>
				<th>Prijs</th>
				<td><fmt:formatNumber>${wijn.prijs}</fmt:formatNumber></td>
			</tr>
		</table>

		<!-- Info over wijn tonen -->
		<form method="post">
			<label>Aantal flessen <span class="foutmelding">${fout}</span> <input
				type="number" name="aantalFlessen" autofocus required
				value="${alInMandje}">
			<input type="submit" name="toevoegen" value="toevoegen"></label> 
			
		</form>
	</c:if>
</body>
</html>