/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EjbPackage;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.ejb.Local;
import noneEJB.Order;
import noneEJB.OrderStatusEnum;

/**
 *
 * @author MY PHU NGUYEN
 */
@Local
public interface OrderHistoryLocal {
    public ProductListLocal getProductListBean();
    public void addAnOrder(Order anOrder) throws ClassNotFoundException, SQLException;
    public Order retrieveOrder(int orderID)throws ClassNotFoundException, SQLException;
    public ArrayList<Order> getOrderListFromDB() throws ClassNotFoundException, SQLException;
    public void modifyOrderStatus(int orderID, OrderStatusEnum aStatus) throws ClassNotFoundException, SQLException;
}
