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

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //send to JSP page
        RequestDispatcher dispatcher= request.getRequestDispatcher("/AdminAddProduct.jsp");
        dispatcher.forward(request, response);
    }

    private void manageOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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


}
