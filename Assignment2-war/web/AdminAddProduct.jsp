<%-- 
    Document   : AdminAddProduct
    Created on : 26/04/2019, 4:25:01 AM
    Author     : MY PHU NGUYEN
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style type="text/css">
        body {background-color: #FFF5EE;text-align: justify;}
        h1 {background-color: #CDCDCD;font-family: 
        "Helvetica",sans-serif; color: #B9090B; text-transform:
        uppercase;}
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Product</title>
    </head>
    <body>
        <h1>Add New Product</h1>
        <form action="http://localhost:8080/Assignment2-war/AdminServlet" method="GET">
            <!--hidden input field to help Servlet controller work-->
            <input type="hidden" name="userDemand" value="addProduct" />
             <p>Product Name:
                <input type="text" name="productName" required></p>
            <p>Description:
                <input type="text" name="description" required></p>
            <p>Price Per Unit
                <input type="number" name="pricePerUnit" min="0" required></p>
            <p>Quantity:
                <input type="number" name="quantity" min="0" required></p>
            <button type="submit">ADD A PRODUCT</button>
        </form>
        <p><a href="http://localhost:8080/Assignment2-war/AdminServlet?userDemand=admin">Back to Admin Home Page</a></p>
    </body>
</html>
