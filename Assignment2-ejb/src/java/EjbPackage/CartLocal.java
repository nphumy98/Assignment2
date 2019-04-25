/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EjbPackage;

import java.util.ArrayList;
import javax.ejb.Local;
import noneEJB.Product;

/**
 *
 * @author MY PHU NGUYEN
 */
@Local
public interface CartLocal {
    public ArrayList<Product> getProductList();
    public void addProduct(Product aProduct, int quantity);
     public void removeProduct(int productID);
     public void emptyCart();
}
