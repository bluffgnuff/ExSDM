<%@ page import="it.distributedsystems.model.dao.*" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="java.util.*" %>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%!
    String printTableRow(Product product, String url) {
        StringBuffer html = new StringBuffer();
        html
                .append("<tr>")
                .append("<td>")
                .append(product.getName())
                .append("</td>")

                .append("<td>")
                .append(product.getProductNumber())
                .append("</td>");

        html
                .append("</tr>");

        return html.toString();
    }

    String printTableRows(List products, String url) {
        StringBuffer html = new StringBuffer();
        Iterator iterator = products.iterator();
        while (iterator.hasNext()) {
            html.append(printTableRow((Product) iterator.next(), url));
        }
        return html.toString();
    }
%>

<html>
<head>
    <title>Custumer page</title>
</head>
<body>
<%
    Context context = new InitialContext();
    DAOFactory daoFactory = DAOFactory.getDAOFactory(application.getInitParameter("dao"));
    CustomerDAO customerDAO = daoFactory.getCustomerDAO();
    PurchaseDAO purchaseDAO = daoFactory.getPurchaseDAO();
    ProductDAO productDAO = daoFactory.getProductDAO();
    String operation = request.getParameter("operation");
    Cart cart;
    cart = (Cart) session.getAttribute("sessionCart");
    if (cart == null) {
        cart = (Cart) context.lookup("java:module/EJB3Cart");
        session.setAttribute("sessionCart", cart);
    }

    if (operation != null && operation.equals("selectCustomer")) {
        int id = Integer.parseInt(request.getParameter("idCustomer"));
        Customer customer = customerDAO.findCustomerById(id);
        cart.setCustomer(customer);
        out.println("<!-- selected customer '" + customer.getName() + "', with id = '" + id + "' -->");
    } else if (operation != null && operation.equals("addToCart")) {
        int id = Integer.parseInt(request.getParameter("idProduct"));

        Product product = productDAO.findProductById(id);
        cart.addProduct(product);
        out.println("<!-- added product '" + product.getName() + "' with id = '" + id + "' -->");
    } else if (operation != null && operation.equals("orderConfirm")) {
        int ran = new Random().nextInt();
        Purchase purchase = new Purchase(ran);
        purchase.setCustomer(cart.getCustomer());
        purchase.setProducts(cart.getProducts());

        purchaseDAO.insertPurchase(purchase);
        cart.setProducts(new HashSet<>());
        out.println("<!-- added purchase '" + purchase.getPurchaseNumber() + "' with id = '" + purchase.getId() + "' -->");
    } else if (operation != null && operation.equals("orderAbort")) {
        cart.setProducts(new HashSet<>());
    }
%>
<%
    List customers = customerDAO.getAllCustomers();
    if (customers.size() > 0) {
%>
<h1>Cart</h1>

<h2>Select Customer</h2>
<div>
    <form>
        Select your name: <select name="idCustomer">
            <%
        Iterator iterator = customers.iterator();
        while (iterator.hasNext()) {
            Customer customer = (Customer) iterator.next();
            %>
        <option value="<%=customer.getId()%>"><%= customer.getName()%>
        </option>
            <%}
    %>
        <input type="hidden" name="operation" value="selectCustomer"/>
        <input type="submit" name="submit" value="Aggiungi"/>
    </form>
    <%
        }// end if
    %>
</div>
<h2> Add product to cart </h2>
<div>
    <%
        List products = productDAO.getAllProducts();
        if (products.size() > 0) {
    %>
    Products:
    <%--Add Product--%>

    <%
        Iterator iteratorP = products.iterator();
        while (iteratorP.hasNext()) {
            Product product = (Product) iteratorP.next();
            if (product.getPurchase() == null) {
    %>
    <form>
        <label><%= product.getName()%>
        </label>&#8195;
        <label> Prezzo: <%= product.getPrice() %>€</label><br>
        <input type="hidden" name="operation" value="addToCart"/>
        <input type="hidden" name="idProduct" value="<%= product.getId()%>"/>
        <input type="submit" name="submit" value="Aggiungi al carrello"/>
    </form>
    <br>
    <%
                }
            }
        }
    %>
</div>

<%if (cart.getCustomer() != null) {%>
<h3>Utente attuale:</h3>
<div>
    <%=cart.getCustomer().getName()%>
</div>
<%}%>

<h3>Products currently in the cartImpl:</h3>
<div>
    <%if (cart.getProducts().size() > 0) {%>
    <table>
        <tr>
            <th>Name</th>
            <th>ProductNumber</th>
            <th></th>
        </tr>
        <%= printTableRows(Arrays.asList(cart.getProducts().toArray()), request.getContextPath()) %>
    </table>
    Prezzo totale: &#8195 <%=cart.getTotalPrice()%>
    <% }%>
</div>
<div>
    <form>
        <input type="hidden" name="operation" value="orderConfirm"/>
        <input type="submit" name="submit" value="Conferma Ordine"/><br>
    </form>
    <form>
        <input type="hidden" name="operation" value="orderAbort"/>
        <input type="submit" name="submit" value="Svuota Carrello"/><br>
    </form>
    <a href="<%= request.getContextPath()%>/customer.jsp">Pulisci URL</a>
</div>
</body>
</html>