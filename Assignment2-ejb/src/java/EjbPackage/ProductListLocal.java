/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EjbPackage;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.ejb.Local;
import noneEJB.Product;

/**
 *
 * @author MY PHU NGUYEN
 */
@Local
public interface ProductListLocal {

    public ArrayList<Product> getProductList();

    public Product retrieveProduct(int productID)throws ClassNotFoundException, SQLException;
    
    public void addProductBook(Product aProduct)throws ClassNotFoundException, SQLException;
    
}
