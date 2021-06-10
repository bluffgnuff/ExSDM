<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>

<head>
    <title>Customer Manager</title>

    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="Mon, 01 Jan 1996 23:59:59 GMT"/>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <meta name="Author" content="you">

    <link rel="StyleSheet" href="../../styles/default.css" type="text/css" media="all"/>

</head>

<body>

<h1>Customer Manager</h1>

<div>
    <p>Add Customer:</p>
    <form:form method="post" action="/insert-customer" modelAttribute="newCustomer">
        Name: <form:input path="name"/><br/>
        <input type="submit" value="Create"/>
    </form:form>
</div>

<div>
    <p>Read Customers:</p>
    <c:forEach var="customer" items="${customers}">
        ${customer.name} <a href="/delete-customers/${customer.id}">Elimina</a>
    </c:forEach>
</div>
<div>
    <a href="<%= request.getContextPath() %>">Ricarica lo stato iniziale di questa pagina</a>
</div>
</body>
</html>