/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import EjbPackage.OrderHistoryLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import noneEJB.Order;
import noneEJB.OrderStatusEnum;
import noneEJB.Product;

/**
 *
 * @author MY PHU NGUYEN
 */
public class AdminServlet extends HttpServlet {
    @EJB
    OrderHistoryLocal anOrderList;


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                try {
            String userDemand= request.getParameter("userDemand");
            if (userDemand=="null")
            {
                userDemand= "admin";
            }
            
            switch(userDemand)
            {
                case "admin":
                    manageProduct(request,response);
                    break;
                case "addProduct":
                    addProduct(request, response);
                    break;
                case "manageOrder":
                    manageOrder(request,response);
                    break;
                case "decideOrder":
                    decideOrder(request,response);
                    break;
                case "submitDecideOrder":
                    submitDecideOrder(request,response);
                    break;
                case "addQuantity":
                    updateQuantityProduct(request,response);
                    break;
                default:
                    manageProduct(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
       
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void manageProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
       //get productList from DB
        ArrayList<Product> productList= anOrderList.getProductListBean().getDataProductListFromDB();
        //add productList to request
        request.setAttribute("PRODUCT_LIST", productList);
        //send to JSP page
        RequestDispatcher dispatcher= request.getRequestDispatcher("/AdminManageProduct.jsp");
        dispatcher.forward(request, response);
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
        //get product ID from page
        String productName= request.getParameter("productName");
        String description= request.getParameter("description");
        //get quantity from page
        int pricePerUnit= Integer.parseInt(request.getParameter("pricePerUnit"));
        int quantity=Integer.parseInt(request.getParameter("quantity"));
        //make a Product and add to database
        Product newProduct= new Product(productName,description,pricePerUnit,quantity);
        anOrderList.getProductListBean().addProductBook(newProduct);
        //send to JSP page
        manageProduct(request, response);
    }

    private void manageOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
        //get StudentList from DB
        ArrayList<Order> orderList= anOrderList.getOrderListFromDB();
        //add StudentList to request
        request.setAttribute("ORDER_LIST", orderList);
        //send to JSP page
        RequestDispatcher dispatcher= request.getRequestDispatcher("/AdminManageOrder.jsp");
        dispatcher.forward(request, response);
    }

    private void updateQuantityProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
         //get product ID from page
        int productID= Integer.parseInt(request.getParameter("productID"));
        //get quantity from page
        int addedQuantity= Integer.parseInt(request.getParameter("quantity"));
        //update quantity
        anOrderList.getProductListBean().addQuantity(productID, addedQuantity);
        //display result
        manageProduct(request, response);
    }

    private void decideOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
        //get order ID from page
        int orderID= Integer.parseInt(request.getParameter("orderID"));
        //get Order from database
        Order retrieveOrder= anOrderList.retrieveOrder(orderID);
        //add retrieveProduct to request
        request.setAttribute("retrieveOrder", retrieveOrder);
        //send to JSP page
        RequestDispatcher dispatcher= request.getRequestDispatcher("/AdminViewOrder.jsp");
        dispatcher.forward(request, response);
    }
    
        protected void confirmationOrderPage(HttpServletRequest request, HttpServletResponse response, ArrayList<Product> runningOutStockProduct)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<style type='text/css'>");
            out.println("body {background-color: #FFF5EE;text-align: justify;}");
            out.println("table,th,tr,td {border: 1px solid black;}");
            out.println("#tabletext {font-size: large;font-family:");
            out.println("'Helvetica',sans-serif;}");
            out.println("#tableheader {font-weight:bold; text-align: center;}");
            out.println("</style>");
            out.println("<title>Servlet NewServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Some Products below needed to be stocked so this order could be completed </h1>");

            out.println("<ul>");
            for(Product aProduct:runningOutStockProduct )
            {
                out.println("<li>"+aProduct.getProductID()+": "+aProduct.getProductName()+"</li>");
            }
            out.println("</ul>");
            out.println("<p><a href=\"http://localhost:8080/Assignment2-war/AdminServlet?userDemand=manageOrder\">Back to Manage Order</a></p>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private void submitDecideOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
        //get order ID from page
        int orderID= Integer.parseInt(request.getParameter("orderID"));
        String stringOrderStatus= request.getParameter("orderStatus");
        if (stringOrderStatus.contains("APPROVED")) //if order is approved
        {
            ArrayList<Product> productRunOutOfStock=checkIfOrderOk(orderID);
            if (productRunOutOfStock.size()>0) // if some products run out of stock
            {
                confirmationOrderPage(request,response,productRunOutOfStock);
            }
            else // if no product run out of stock
            {
                Order approvedOrder= anOrderList.retrieveOrder(orderID);
                //updatequantity
                for(Product aProduct: approvedOrder.getProductList())
                {
                    anOrderList.getProductListBean().removeQuantity(aProduct.getProductID(),aProduct.getQuantity());
                }
                
                //modify orderStatus
                anOrderList.modifyOrderStatus(orderID, OrderStatusEnum.APPROVED);
                //display order page
                manageOrder(request, response);
            }
        }
        else //if order is rejected
        {
            //modify orderStatus
            anOrderList.modifyOrderStatus(orderID, OrderStatusEnum.REJECTED);
            //display order page
            manageOrder(request, response);
        }
        
    }
    
    private ArrayList<Product> checkIfOrderOk(int orderID) throws ClassNotFoundException, SQLException
    {
        ArrayList<Product> productRunOutOfStock= new ArrayList<Product>();
        Order anOrder= anOrderList.retrieveOrder(orderID);
        for(Product aProduct: anOrder.getProductList())
        {
            Product productInTable=anOrderList.getProductListBean().retrieveProduct(aProduct.getProductID());
            if (productInTable.getQuantity()<aProduct.getQuantity())
            {
                productRunOutOfStock.add(productInTable);
            }
        }
        return productRunOutOfStock;
    }

}
