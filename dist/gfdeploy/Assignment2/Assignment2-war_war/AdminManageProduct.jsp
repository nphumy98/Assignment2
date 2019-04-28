<%-- 
    Document   : AdminManageProduct
    Created on : 26/04/2019, 4:24:46 AM
    Author     : MY PHU NGUYEN
--%>

<%@page import="java.util.ArrayList"%>
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
        <title>Manage Product</title>
    </head>
    <body>
        <h1>Manage Product</h1>
        <% ArrayList<Product> productList= (ArrayList<Product>)request.getAttribute("PRODUCT_LIST");%>
        <div>
            <table>
                <tr>
                    <th>Product ID</th>
                    <th>Product Name</th>
                    <th>Description</th>
                    <th>Price per Unit</th>
                    <th>Quantity</th>
                    <th>Product Status</th>
                    <th>Add Quantity</th>
                </tr>
                
                <% for (Product aProduct: productList) {%>

                <tr>
                    <td> <%= aProduct.getProductID()%> </td>
                    <td> <%= aProduct.getProductName()%> </td>
                    <td> <%= aProduct.getDescription()%> </td>
                    <td> <%= aProduct.getPricePerUnit()%> </td>
                    <td> <%= aProduct.getQuantity()%> </td>
                    <td> <%= aProduct.getProductStatus()%> </td>
                    <td> 
                        <form action="http://localhost:8080/Assignment2-war/AdminServlet" method="GET">
                            <!--hidden input field to help Servlet controller work-->
                            <input type="hidden" name="userDemand" value="addQuantity" />
                            <input type="hidden" name="productID" value="<%= aProduct.getProductID()%>" />
                            <input type="number" name="quantity" min="1" required>
                            <button type="submit">Update Stock</button>
                        </form>
                    </td>
                </tr>
                <%}%>    
            </table>
        </div>
        <p><a href="AdminAddProduct.jsp">Add New Product</a></p>
        <p><a href="http://localhost:8080/Assignment2-war/AdminServlet?userDemand=manageOrder">Manage Order</a></p>
        <p><a href="index.html">Return to Home Page</a></p>
    </body>
</html>
