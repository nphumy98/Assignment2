/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EjbPackage;

import java.util.ArrayList;
import javax.ejb.Stateless;
import noneEJB.Order;

/**
 *
 * @author MY PHU NGUYEN
 */
@Stateless
public class OrderHistory implements OrderHistoryLocal {
    private ArrayList<Order> orderList;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
