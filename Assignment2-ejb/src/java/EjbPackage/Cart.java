/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EjbPackage;

import java.util.ArrayList;
import javax.ejb.Stateful;
import noneEJB.Product;
import noneEJB.ProductStatusEnum;

/**
 *
 * @author MY PHU NGUYEN
 */
@Stateful
public class Cart implements CartLocal {
    private ArrayList<Product> productList;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public Cart()
    {
        productList= new ArrayList<Product>();
    }
    
    public void addProduct(Product aProduct, int quantity)
    {
        
        Product checkProduct=isProductInCart(aProduct.getProductID());
        //get infor from product that will be added
        int productID= aProduct.getProductID();
        String productName=aProduct.getProductName();
        String description= aProduct.getDescription();
        int pricePerUnit=aProduct.getPricePerUnit();
        int productQuantity=aProduct.getQuantity();
        ProductStatusEnum productStatus=aProduct.getProductStatus();
        //if product not in the cart yet
        if (checkProduct==null)
        {
           productList.add(new Product(productID,productName,description,pricePerUnit,quantity,productStatus));
        }
        else //if product already in the cart
        {
            //add quantity of product already in the cart
            checkProduct.setQuantity(checkProduct.getQuantity()+quantity);
        }
    }
    
    public void removeProduct(int productID)
    {
        productList.remove(findProduct(productID));
    }
    
    private Product findProduct(int productID)
    {
        for(Product aProduct: productList)
        {
            if (aProduct.getProductID()==productID)
            {
                return aProduct;
            }
        }
        return null;
    }
    
    public void emptyCart()
    {
        productList.clear();
    }
    
    private Product isProductInCart(int productID)
    {
        for(Product aProduct: productList)
        {
            if (aProduct.getProductID()==productID)
            {
                return aProduct;
            }
        }
        return null;
    }
    //getter and setter
    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }
    
    
}
