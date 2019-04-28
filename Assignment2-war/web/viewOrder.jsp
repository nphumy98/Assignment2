<%-- 
    Document   : viewOrder
    Created on : 25/04/2019, 4:24:28 AM
    Author     : MY PHU NGUYEN
--%>

<%@page import="noneEJB.Product"%>
<%@page import="noneEJB.Order"%>
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
        <title>View Order</title>
    </head>
    <body>
        <% Order anOrder= (Order)request.getAttribute("retrieveOrder");%>
        <h1>View Order</h1>
        <div>
            <table>
                <tr>
                    <th>Order ID</th>
                    <th>Order Total</th>
                    <th>Order Status</th>
                </tr>
                <tr>
                    <td> <%= anOrder.getOrderID()%> </td>
                    <td> <%= anOrder.getOrderTotal()%> </td>
                    <td> <%= anOrder.isOrderStatus()%> </td>
                </tr>   
            </table>
        </div>
        <h1>Products of the Order</h1>    
        <div>
            <table>
                <tr>
                    <th>Product ID</th>
                    <th>Product Name</th>
                    <th>Description</th>
                    <th>Price per Unit</th>
                    <th>Quantity</th>
                    <th>Product Status</th>
                    <th>Total</th>
                </tr>
                
                <% for (Product aProduct: anOrder.getProductList()) {%>

                <tr>
                    <td> <%= aProduct.getProductID()%> </td>
                    <td> <%= aProduct.getProductName()%> </td>
                    <td> <%= aProduct.getDescription()%> </td>
                    <td> <%= aProduct.getPricePerUnit()%> </td>
                    <td> <%= aProduct.getQuantity()%> </td>
                    <td> <%= aProduct.getProductStatus()%> </td>
                    <td> <%= aProduct.calculateTotal()%> </td>
                </tr>
                <%}%>    
            </table>
        </div>
        <p><a href="http://localhost:8080/Assignment2-war/CustomerServlet?userDemand=listOrder">Back to Order History</a></p>
    </body>
</html>
