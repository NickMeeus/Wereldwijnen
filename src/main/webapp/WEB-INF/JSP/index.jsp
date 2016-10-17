<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt'%>
<%@taglib uri='http://vdab.be/tags' prefix='v'%>

<!doctype html>
<html lang="nl">

<head>
<v:head title="Wereldwijnen" />

</head>
<body>
	<!-- Vlaggen van elk land tonen -->
	<v:menu />

	<!-- Soorten tonen na land geselecteerd is -->
	<c:if test="${not empty param.landId}">
		<div class="soorten">
			<h2>Soorten uit ${gekozenLand.naam}</h2>
			<ol>
				<c:forEach var="soort" items="${gekozenLand.soorten}">
					<c:url var="urlMetIdLandEnSoort" value=''>
						<c:param name="landId" value="${gekozenLand.id}" />
						<c:param name="soortId" value="${soort.id}" />
					</c:url>
					<li><a href="${urlMetIdLandEnSoort}">${soort.naam}</a></li>

				</c:forEach>
			</ol>

		</div>
	</c:if>

	<!-- Wijnen tonen na soort is geselecteerd -->
	<c:if test="${not empty param.soortId}">
		<div class="wijnen">
			<h2>Wijnen uit ${gekozenSoort.naam}</h2>
			<ol>
				<c:forEach var="wijn" items="${gekozenSoort.wijnen}">
					<c:url var="urlToevoegen" value='toevoegen.htm'>
						<c:param name="wijnId" value="${wijn.id}" />
					</c:url>
					<li><a href="${urlToevoegen}"> ${wijn.jaar} <c:forEach
								begin='1' end='${wijn.beoordeling}'>&#9733;</c:forEach>
					</a></li>
				</c:forEach>
			</ol>
		</div>
	</c:if>
</body>
</html>