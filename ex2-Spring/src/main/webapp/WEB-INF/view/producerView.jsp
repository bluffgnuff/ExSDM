<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>

<head>
    <title>Producer Manager</title>

    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="Mon, 01 Jan 1996 23:59:59 GMT"/>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <meta name="Author" content="you">

    <link rel="StyleSheet" href="../../styles/default.css" type="text/css" media="all"/>

</head>

<body>

<h1>Producer Manager</h1>

<div>
    <p>Add Producer:</p>
    <form:form method="POST" action="/insert-producer" modelAttribute="newProducer">
    <form:label path="name">Name:</form:label>
        <form:input type="text" path="name"/>
        <input type="submit" value="Create"/>
    </form:form>
</div>

<div>
    <p>Read Producers:</p>
    <c:forEach var="producer" items="${producers}">
        ${producer.name} <a href="/delete-producers/${producer.id}">Elimina</a>
    </c:forEach>
</div>
<div>
    <a href="<%= request.getContextPath() %>">Ricarica lo stato iniziale di questa pagina</a>
</div>
</body>
</html>