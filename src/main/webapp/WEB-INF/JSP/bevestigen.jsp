<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib uri='http://vdab.be/tags' prefix='v'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<!doctype html>
<html lang="nl">

<head>
<v:head title="Wereldwijnen" />
</head>
<body>
	<!-- Vlaggen van elk land tonen -->
	<v:menu />

	<!-- Bevestiging tonen met bestelbon nummer -->
	<h1>Je mandje is bevestigd met bestelbon ${param.bestelbonId}</h1>
</body>
</html>