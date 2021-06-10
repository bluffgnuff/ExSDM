<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="com.model.Customer" %>

<html>

<head>
    <title>Customer Manager</title>
</head>

<body>

<h1>Customer Manager</h1>

<div>
    <p>Add Customer:</p>
    <form:form action="/insert-customer" method="post" modelAttribute="customer">
        Name: <form:input type="text" path="name"/><br/>
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