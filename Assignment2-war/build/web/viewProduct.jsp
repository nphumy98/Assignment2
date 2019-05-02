<%-- 
    Document   : viewProduct.jsp
    Created on : 24/04/2019, 3:27:03 PM
    Author     : MY PHU NGUYEN
--%>

<%@page import="noneEJB.Product"%>
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
        <title>View Product</title>
    </head>
    <body>
        <% Product aProduct= (Product)request.getAttribute("retrieveProduct");%>
        <div>
            <table>
                <tr>
                    <th>Product ID</th>
                    <th>Product Name</th>
                    <th>Description</th>
                    <th>Price per Unit</th>
                    <th>Product Status</th>
                </tr>

                <tr>
                    <td> <%= aProduct.getProductID()%> </td>
                    <td> <%= aProduct.getProductName()%> </td>
                    <td> <%= aProduct.getDescription()%> </td>
                    <td> <%= aProduct.getPricePerUnit()%> </td>
                    <td> <%= aProduct.getProductStatus()%> </td>
                </tr>
            </table>
        </div>
        <h2>Please Select a quantity to Add to Cart</h2>
        <form action="http://localhost:8080/Assignment2-war/CartServlet" method="GET">
            <!--hidden input field to help Servlet controller work-->
            <input type="hidden" name="userDemand" value="addProductCart"/>
            <input type="hidden" name="productID" value="<%= aProduct.getProductID()%>">
            <input type="hidden" name="productName" value="<%= aProduct.getProductName()%>">
            <input type="hidden" name="description" value="<%= aProduct.getDescription()%>">
            <input type="hidden" name="pricePerUnit" value="<%= aProduct.getPricePerUnit()%>">
            <input type="hidden" name="productStatus" value="<%= aProduct.getProductStatus()%>">
            <input type="number" name="orderQuantity" min="1" required>
            <button type="submit">Add to Cart</button>
        </form>
        <p><a href="http://localhost:8080/Assignment2-war/CustomerServlet?userDemand=customer">Return to Customer Home Page</a></p>
    </body>
</html>
