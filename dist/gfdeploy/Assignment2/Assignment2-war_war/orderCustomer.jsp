<%-- 
    Document   : orderCustomer
    Created on : 24/04/2019, 12:00:56 AM
    Author     : MY PHU NGUYEN
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="noneEJB.Order"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Customer Page</title>
    </head>
    <body>
        <h1>Order History</h1>
        <% ArrayList<Order> orderList= (ArrayList<Order>)request.getAttribute("ORDER_LIST");%>
        <div>
            <table>
                <tr>
                    <th>Order ID</th>
                    <th>Order Total</th>
                    <th>Order Status</th>
                </tr>
                
                <% for (Order anOrder: orderList) {%>

                <tr>
                    <td> <%= anOrder.getOrderID()%> </td>
                    <td> <%= anOrder.getOrderTotal()%> </td>
                    <td> <%= anOrder.isOrderStatus()%> </td>
                </tr>
                <%}%>    
            </table>
        </div>
    </body>
</html>
