<%-- 
    Document   : AdminManageOrder
    Created on : 26/04/2019, 4:25:15 AM
    Author     : MY PHU NGUYEN
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="noneEJB.Order"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Order</title>
    </head>
    <body>
        <h1>Manage Order</h1>
        <h2>Pending Orders</h2>
        <% ArrayList<Order> orderList= (ArrayList<Order>)request.getAttribute("ORDER_LIST");%>
        <div>
            <table>
                <tr>
                    <th>Order ID</th>
                    <th>Order Total</th>
                    <th>Order Status</th>
                    <th>Action</th>
                </tr>
                
                <% for (Order anOrder: orderList) {%>

                <tr>
                    <td> <%= anOrder.getOrderID()%> </td>
                    <td> <%= anOrder.getOrderTotal()%> </td>
                    <td> <%= anOrder.isOrderStatus()%> </td>
                    <td> 
                        <form action="http://localhost:8080/Assignment2-war/CustomerServlet" method="GET">
                            <!--hidden input field to help Servlet controller work-->
                            <input type="hidden" name="userDemand" value="viewOrder" />
                            <input type="hidden" name="orderID" value="<%= anOrder.getOrderID()%>"/>
                            <button type="submit">View Order</button>
                        </form>
                    </td>
                </tr>
                <%}%>    
            </table>
        </div>
        <h2>Old Orders</h2>
        <p><a href="http://localhost:8080/Assignment2-war/AdminServlet?userDemand=admin">Back to Admin Home Page</a></p>
    </body>
</html>
