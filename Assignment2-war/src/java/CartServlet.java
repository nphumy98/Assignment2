/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import EjbPackage.CartLocal;
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
import noneEJB.ProductStatusEnum;

/**
 *
 * @author MY PHU NGUYEN
 */
public class CartServlet extends HttpServlet {
    @EJB
    CartLocal cart;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String userDemand= request.getParameter("userDemand");
            if (userDemand=="null")
            {
                userDemand= "viewCart";
            }
            
            switch(userDemand)
            {
                case "viewCart":
                    listProduct(request, response);
                    break;
                case "addProductCart":
                    addProductCart(request,response);
                    break;
                case "removeProduct":
                    removeProductCart(request,response);
                    break;
                case "checkOut":
                    checkOutProductCart(request,response);
                    break;
                default:
                    listProduct(request, response);
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

    private void listProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {     
        //get StudentList from DB
        ArrayList<Product> productList= cart.getProductList();
        //add StudentList to request
        request.setAttribute("PRODUCT_LIST", productList);
        //send to JSP page
        RequestDispatcher dispatcher= request.getRequestDispatcher("/Cart.jsp");
        dispatcher.forward(request, response);
    }

    private void addProductCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get product ID from page
        int productID= Integer.parseInt(request.getParameter("productID"));
        //get quantity
        int quantity= Integer.parseInt(request.getParameter("orderQuantity"));
        String productName= request.getParameter("productName");
        String description= request.getParameter("description");
        int pricePerUnit= Integer.parseInt(request.getParameter("pricePerUnit"));
        String stringProductStatus= request.getParameter("productStatus");
        ProductStatusEnum productStatus= ProductStatusEnum.Available;
        if (stringProductStatus.contains("NotAvailable"))
        {
            productStatus=ProductStatusEnum.NotAvailable;
        }
        Product productInProductTable= new Product(productID,productName,description,pricePerUnit,quantity,productStatus);
        cart.addProduct(productInProductTable, quantity);
        //send to JSP page
        listProduct(request, response);
    }

    private void removeProductCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get product ID from page
        int productID= Integer.parseInt(request.getParameter("productID"));
        //remove the product
        cart.removeProduct(productID);
        //send to JSP page
        listProduct(request, response);
    }

    private void checkOutProductCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //to add order to table
        //empty the cart
        cart.emptyCart();
        //send to JSP page
        listProduct(request, response);
    }


}
