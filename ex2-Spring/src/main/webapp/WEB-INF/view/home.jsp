<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Home Page</title>
</head>

<body>
<div>
    <form method="post">
        <a href="/customerView">Gestione Clienti</a><br>
        <a href="/producerView">Gestione Produttori</a><br>
        <a href="/productView">Gestione Prodotti</a><br>
        <a href="/purchaseView">Gestione Acquisti</a><br>
    </form>
</div>

<form:form action="/" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <div><input type="submit" value="Sign In"/></div>
</form:form>

</body>
</html>