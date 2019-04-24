/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noneEJB;

import java.util.ArrayList;

/**
 *
 * @author MY PHU NGUYEN
 */
public class Order {
    private static int orderNumber=2000;
    private int orderID;
    private ArrayList<Product> productList;
    private int orderTotal;
    private OrderStatusEnum orderStatus;

    public Order(int orderID, ArrayList<Product> productList, int orderTotal, OrderStatusEnum orderStatus) {
        if (orderID>this.orderNumber)
        {
            this.orderNumber=orderID;
        }
        this.orderID = orderID;
        this.productList = productList;
        this.orderTotal = orderTotal;
        this.orderStatus = orderStatus;
    }
    
    public Order(ArrayList<Product> productList)
    {
        if ((productList!=null)&&(productList.size()>0))
        {
            orderNumber++;
            this.orderID = this.orderNumber;
            this.productList = productList;
            this.orderTotal = calculateOrderTotal();
            this.orderStatus=OrderStatusEnum.PENDING;
        }
    }
    //getter and setter
    public static int getOrdernumber() {
        return orderNumber;
    }

    public static void setOrdernumber(int ordernumber) {
        Order.orderNumber = ordernumber;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public int getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(int orderTotal) {
        this.orderTotal = orderTotal;
    }

    public OrderStatusEnum isOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    //method
    
    public int calculateOrderTotal()
    {
        int orderTotal=0;
        for(Product aProduct: this.productList)
        {
            orderTotal+=aProduct.calculateTotal();
        }
        return orderTotal;
    }

    @Override
    public String toString() {
        return "Order{" + "orderID=" + orderID + ", productList=" + productList + ", orderTotal=" + orderTotal + ", orderStatus=" + orderStatus + '}';
    }
    
    
}
