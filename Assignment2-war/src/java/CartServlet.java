/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import EjbPackage.CartLocal;
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

    private void checkOutProductCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
        
        ArrayList<Product> copyCart= new ArrayList<Product>();
        for(Product aProduct: cart.getProductList())
        {
            copyCart.add(new Product(aProduct.getProductID(),aProduct.getProductName(),aProduct.getDescription(),aProduct.getPricePerUnit(), aProduct.getQuantity(), aProduct.getProductStatus()));
        }
        Order anOrder= new Order(copyCart);
        CustomerServlet.getOrderCart().add(anOrder);
        //empty the cart
        cart.emptyCart();
        //send to JSP page
        confirmationCartPage(request,response);
    }
    
        protected void confirmationCartPage(HttpServletRequest request, HttpServletResponse response)
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
            out.println("<title>Confirmation Order</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Your Cart will be added to the Order History. Click the link to see new Order </h1>");

            out.println("<ul>");
            out.println("</ul>");
            out.println("<p><a href=\"http://localhost:8080/Assignment2-war/CustomerServlet?userDemand=listOrder\">Back to Manage Order</a></p>");
            out.println("</body>");
            out.println("</html>");
        }
    }


}
