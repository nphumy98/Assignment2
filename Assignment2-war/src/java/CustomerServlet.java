/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import EjbPackage.ProductListBean;
import EjbPackage.ProductListLocal;
import java.io.IOException;
import java.io.PrintWriter;
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
public class CustomerServlet extends HttpServlet {

    @EJB
    ProductListLocal aProductList;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
//            String userDemand= request.getParameter("userDemand");
//            if (userDemand=="null")
//            {
//                userDemand= "startDB";
//            }
//            
//            switch(userDemand)
//            {
//                case "startDB":
////                    this.init();
//                    getStudentListDB(request, response);
//                    break;
//                case "addStudent":
//                    getAddStudent(request,response);
//                    break;
//                case "viewStudent":
//                    viewStudent(request,response);
//                    break;
//                default:
//                    getStudentListDB(request, response);
//            }
            getProductListDB(request, response);
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

    private void getProductListDB(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        //get StudentList from DB
        ArrayList<Product> productList= aProductList.getProductList();
        //add StudentList to request
        request.setAttribute("PRODUCT_LIST", productList);
        //send to JSP page
        RequestDispatcher dispatcher= request.getRequestDispatcher("/customerHomePage.jsp");
        dispatcher.forward(request, response);
    }

}
