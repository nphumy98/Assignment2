/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EjbPackage;

import java.util.ArrayList;
import javax.ejb.Stateful;
import noneEJB.Product;

/**
 *
 * @author MY PHU NGUYEN
 */
@Stateful
public class Cart implements CartLocal {
    private ArrayList<Product> productList;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
