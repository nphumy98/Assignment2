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
        <style type="text/css">
        body {background-color: #FFF5EE;text-align: justify;}
        table,th,tr,td {border: 1px solid black;}
        #tabletext {font-size: large;font-family:
        "Helvetica",sans-serif;}
        #tableheader {font-weight:bold; text-align: center;}
        </style>
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
        <p><a href="http://localhost:8080/Assignment2-war/CustomerServlet?userDemand=customer">Return to Customer Home Page</a></p>
    </body>
</html>
