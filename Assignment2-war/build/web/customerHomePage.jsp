<%-- 
    Document   : customerHomePage
    Created on : 22/04/2019, 12:54:46 PM
    Author     : MY PHU NGUYEN
--%>

<%@page import="noneEJB.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Customer page</h1>
        <% ArrayList<Product> productList= (ArrayList<Product>)request.getAttribute("PRODUCT_LIST");%>
        <div>
            <table>
                <tr>
                    <th>Product ID</th>
                    <th>Product Name</th>
                    <th>Price per Unit</th>
                    <th>Quantity</th>
                    <th>Product Status</th>
                </tr>
                
                <% for (Product aProduct: productList) {%>
                <tr>
                    <td> <%= aProduct.getProductID()%> </td>
                    <td> <%= aProduct.getProductName()%> </td>
                    <td> <%= aProduct.getPricePerUnit()%> </td>
                    <td> <%= aProduct.getQuantity()%> </td>
                    <td> <%= aProduct.getProductStatus()%> </td>
                </tr>
                <%}%>
        
            </table>
        </div>
        <p><a href="orderCustomer.jsp">See Order History</a></p>
        <p><a href="index.html">Check out Cart</a></p>
        <p><a href="index.html">Return to Home Page</a></p>
    </body>
</html>
