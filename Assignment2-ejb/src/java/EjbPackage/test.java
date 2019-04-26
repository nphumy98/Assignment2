/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EjbPackage;

import java.sql.SQLException;
import java.util.ArrayList;
import noneEJB.Order;
import noneEJB.OrderStatusEnum;
import noneEJB.Product;
import noneEJB.ProductStatusEnum;

/**
 *
 * @author MY PHU NGUYEN
 */
public class test {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Prints "Hello, World" to the terminal window.
        OrderHistory testorder= new OrderHistory();
//        ArrayList<Product> productList= new ArrayList<Product>();
//        productList.add(new Product(1001, "Java","This book is for Java leaner", 20,1, ProductStatusEnum.Available));
//        productList.add(new Product(1004, "Ruby","This book is for Ruby leaner", 20,2, ProductStatusEnum.Available));
//        
//        Order newOrder= new Order(productList);
//        testorder.addAnOrder(newOrder);
//        testorder.getProductListBean().addQuantity(1001, 2);
//        testorder.getProductListBean().addProductBook(new Product("Cplusplus","awesome",30,10));
//        testorder.getOrderListFromDB();
//        for(Order anOrder: testorder.getOrderListFromDB())
//        {
//            System.out.println(anOrder);
//        }
//        testorder.modifyOrderStatus(2001, OrderStatusEnum.APPROVED);
        testorder.getProductListBean().removeQuantity(1001, 2);
        
        
    }
}
