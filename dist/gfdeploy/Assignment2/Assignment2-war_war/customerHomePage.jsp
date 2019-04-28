<%-- 
    Document   : customerHomePage
    Created on : 22/04/2019, 12:54:46 PM
    Author     : MY PHU NGUYEN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="noneEJB.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style type="text/css">
        body {background-color: #FFF5EE;text-align: justify;}
        table,th,tr,td {border: 1px solid black;}
        #tabletext {font-size: large;font-family:
        "Helvetica",sans-serif;}
        #tableheader {font-weight:bold; text-align: center;}
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer Page</title>
    </head>
    <body>
        <h1>Customer page</h1>
        <% ArrayList<Product> productList= (ArrayList<Product>)request.getAttribute("PRODUCT_LIST");%>
        <div>
            <table>
                <tr>
                    <th>Product ID</th>
                    <th>Product Name</th>
                    <th>Description</th>
                    <th>Price per Unit</th>
                    <th>Product Status</th>
                    <th>Action</th>
                </tr>
                
                <% for (Product aProduct: productList) {%>

                <tr>
                    <td> <%= aProduct.getProductID()%> </td>
                    <td> <%= aProduct.getProductName()%> </td>
                    <td> <%= aProduct.getDescription()%> </td>
                    <td> <%= aProduct.getPricePerUnit()%> </td>
                    <td> <%= aProduct.getProductStatus()%> </td>
                    <td> 
                        <form action="http://localhost:8080/Assignment2-war/CustomerServlet" method="GET">
                            <!--hidden input field to help Servlet controller work-->
                            <input type="hidden" name="userDemand" value="viewProduct" />
                            <input type="hidden" name="productID" value="<%= aProduct.getProductID()%>" />
                            <button type="submit">Buy Product</button>
                        </form>
                    </td>
                </tr>
                <%}%>    
            </table>
        </div>
        <p><a href="http://localhost:8080/Assignment2-war/CustomerServlet?userDemand=listOrder">See Order History</a></p>
        <p><a href="http://localhost:8080/Assignment2-war/CartServlet?userDemand=viewCart">Check out Cart</a></p>
        <p><a href="index.html">Return to Home Page</a></p>
    </body>
</html>
