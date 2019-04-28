<%-- 
    Document   : Cart
    Created on : 25/04/2019, 2:41:18 PM
    Author     : MY PHU NGUYEN
--%>

<%@page import="noneEJB.ProductStatusEnum"%>
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
        <title>Cart Page</title>
    </head>
    <body>
        <h1>Products in Cart</h1>
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
                    <th>Total</th>
                    <th>Remove Product</th>
                </tr>
                <%! int total =0;
                boolean isAllProductAvailable= true;
                %>
                <% for (Product aProduct: productList) {%>
                <% total+=aProduct.calculateTotal(); %>
                <tr>
                    <td> <%= aProduct.getProductID()%> </td>
                    <td> <%= aProduct.getProductName()%> </td>
                    <td> <%= aProduct.getDescription()%> </td>
                    <td> <%= aProduct.getPricePerUnit()%> </td>
                    <td> <%= aProduct.getQuantity()%> </td>
                    <td> <%= aProduct.getProductStatus()%> </td>
                    <td> <%= aProduct.calculateTotal()%> </td>
                    <td> 
                        <form action="http://localhost:8080/Assignment2-war/CartServlet" method="GET">
                            <!--hidden input field to help Servlet controller work-->
                            <input type="hidden" name="userDemand" value="removeProduct" />
                            <input type="hidden" name="productID" value="<%= aProduct.getProductID()%>" />
                            <button type="submit">Remove Product</button>
                        </form>
                    </td>
                    <% if (aProduct.getProductStatus()==ProductStatusEnum.NotAvailable)
                    {
                        isAllProductAvailable= false;
                    }
                    %>
                </tr>
            <%}%>    
            </table>
        </div>
        <h2>Total Cost: <%= total %></h2>
        <% if (isAllProductAvailable==false)
            {
                 out.println("Some Products are not available at the moment in your Cart. However our admin will add more to the stock to fulfill your Order");
            }
        %>
        <!--reset the variable because cart is statefull-->
        <% if (total >0)
        {
            out.println("<p><a href='http://localhost:8080/Assignment2-war/CartServlet?userDemand=checkOut'>Check Out</a></p>");
            
        } %>
        <% total =0; 
         isAllProductAvailable= true;
        %>
        
        <p><a href="http://localhost:8080/Assignment2-war/CustomerServlet?userDemand=customer">Back to Customer HomePage</a></p>
    </body>
</html>
