/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EjbPackage;

import java.util.ArrayList;
import javax.ejb.Local;
import noneEJB.Order;

/**
 *
 * @author MY PHU NGUYEN
 */
@Local
public interface OrderHistoryLocal {
    public ArrayList<Order> getOrderList();
    public ProductListLocal getProductListBean();
}
