/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import EjbPackage.OrderHistoryLocal;
import EjbPackage.ProductListBean;
import EjbPackage.ProductListLocal;
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
import noneEJB.Product;

/**
 *
 * @author MY PHU NGUYEN
 */
public class CustomerServlet extends HttpServlet {

    @EJB
    OrderHistoryLocal anOrderList;
    
    private static ArrayList<Order> orderCart= new ArrayList<Order>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String userDemand= request.getParameter("userDemand");
            if (userDemand=="null")
            {
                userDemand= "customer";
            }
            
            switch(userDemand)
            {
                case "customer":
                    getProductListDB(request, response);
                    break;
                case "viewProduct":
                    viewProduct(request,response);
                    break;
                case "listOrder":
                    listOrder(request,response);
                    break;
                case "viewOrder":
                    viewOrder(request,response);
                    break;
                default:
                    getProductListDB(request, response);
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
        doGet(request,response);
    }

    private void getProductListDB(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException 
    {
        //get StudentList from DB
        ArrayList<Product> productList= anOrderList.getProductListBean().getDataProductListFromDB();
        //add StudentList to request
        request.setAttribute("PRODUCT_LIST", productList);
        //send to JSP page
        RequestDispatcher dispatcher= request.getRequestDispatcher("/customerHomePage.jsp");
        dispatcher.forward(request, response);
    }

    private void viewProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException 
    {
        //get product ID from page
        int productID= Integer.parseInt(request.getParameter("productID"));
        //get product from database
        Product retrieveProduct= anOrderList.getProductListBean().retrieveProduct(productID);
        //add retrieveProduct to request
        request.setAttribute("retrieveProduct", retrieveProduct);
        //send to JSP page
        RequestDispatcher dispatcher= request.getRequestDispatcher("/viewProduct.jsp");
        dispatcher.forward(request, response);
    }

    private void listOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException 
    {
        //check orderCart checkout or not
        addOrderCart();
        //get OrderList from DB
        ArrayList<Order> orderList= anOrderList.getOrderListFromDB();
        //add OrderList to request
        request.setAttribute("ORDER_LIST", orderList);
        //send to JSP page
        RequestDispatcher dispatcher= request.getRequestDispatcher("/orderCustomer.jsp");
        dispatcher.forward(request, response);
    }

    private void viewOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
        //get order ID from page
        int orderID= Integer.parseInt(request.getParameter("orderID"));
        //get product from database
        Order retrieveOrder= anOrderList.retrieveOrder(orderID);
        //add retrieveProduct to request
        request.setAttribute("retrieveOrder", retrieveOrder);
        //send to JSP page
        RequestDispatcher dispatcher= request.getRequestDispatcher("/viewOrder.jsp");
        dispatcher.forward(request, response);
    }
    
    //private method to check if the orderCart in order history or not
    private boolean checkOrderCart(Order anOrder) throws ClassNotFoundException, SQLException
    {
        if (anOrder==null)
        {
            return true;
        }
        for(Order anotherOrder: anOrderList.getOrderListFromDB())
        {
            if (anotherOrder.getOrderID()==anOrder.getOrderID())
            {
                return true;
            }
                
        }
        return false;
    }
    
    //private method is to add all orderin cart that has not been in order list
    private void addOrderCart() throws ClassNotFoundException, SQLException
    {
        for(Order anOrder: orderCart)
        {
            if (checkOrderCart(anOrder)==false)
            {
                anOrderList.addAnOrder(anOrder);
            }
        }
    }
    //getter and setter

    public OrderHistoryLocal getAnOrderList() {
        return anOrderList;
    }

    public void setAnOrderList(OrderHistoryLocal anOrderList) {
        this.anOrderList = anOrderList;
    }

    public static ArrayList<Order> getOrderCart() {
        return orderCart;
    }

    public static void setOrderCart(ArrayList<Order> orderCart) {
        CustomerServlet.orderCart = orderCart;
    }
}
