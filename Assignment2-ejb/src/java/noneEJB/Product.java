/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noneEJB;

/**
 *
 * @author MY PHU NGUYEN
 */
public class Product {
    private static int productNumber=1000;
    private ProductStatusEnum productStatus;
    private int productID;
    private String productName;
    private String description;
    private int pricePerUnit;
    private int quantity;
    
    
    //constructor
    public Product(int productID, String productName, String description, int pricePerUnit, int quantity, ProductStatusEnum productStatus) {
        if (productID>this.productNumber)
        {
            this.productNumber=productID;
        }
        this.productID = productID;
        this.productName= productName;
        this.description=description;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
        this.productStatus= productStatus;
    }
    
    public Product(String productName,int pricePerUnit)
    {
        this(++productNumber,productName,"This is a good book",pricePerUnit,0,ProductStatusEnum.NotAvailable);
        
    }
    
    public Product(String productName,String description, int pricePerUnit, int quantity)
    {
        
        productNumber++;
        this.productID = productNumber;
        this.productName= productName;
        this.description=description;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
        if (this.quantity>0)
        {
            this.productStatus= ProductStatusEnum.Available;
        }
        else
        {
            this.productStatus= ProductStatusEnum.NotAvailable;
        }
    }
    //getter and setter
    public static int getProductNumber() {
        return productNumber;
    }

    public static void setProductNumber(int productNumber) {
        Product.productNumber = productNumber;
    }

    public ProductStatusEnum getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatusEnum productStatus) {
        this.productStatus = productStatus;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(int pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    //method
    public int calculateTotal()
    {
        return this.pricePerUnit*this.quantity;
    }

    @Override
    public String toString() {
        return "Product{" + "productStatus=" + productStatus + ", productID=" + productID + ", productName=" + productName + ", description=" + description + ", pricePerUnit=" + pricePerUnit + ", quantity=" + quantity + '}';
    }
}
