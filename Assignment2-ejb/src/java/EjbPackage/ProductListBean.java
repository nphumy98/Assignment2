/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EjbPackage;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.ejb.Stateless;
import noneEJB.Order;
import noneEJB.Product;
import noneEJB.ProductStatusEnum;

/**
 *
 * @author MY PHU NGUYEN
 */
@Stateless
public class ProductListBean implements ProductListLocal {
    private ArrayList<Product> productList;
    // The driverURL to contain the Database Driver
      private final String driverURL = "org.apache.derby.jdbc.EmbeddedDriver";
    // The dbURL to contain the Database URL
      private final String dbURL = "jdbc:derby://localhost:1527/DMSDB;" + 
                "create=true;user=dms;password=dms2018";
      private static final String tableName="ProductBook";
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public ProductListBean() throws SQLException, ClassNotFoundException
    {
        //Create ProductList
        productList= new ArrayList<Product>();
        //Create Connection
        Connection connection= connectDatabaseSchema();
        // Creating the SQL Statement
        Statement statement = connection.createStatement();
        //if connect sucessfully
     
        //if studentDB table exist 
        if (isTableExisting(tableName,connection))
        {
            System.out.println("table existed");
            //Reading records from Product Table
            ResultSet rs=statement.executeQuery("SELECT * FROM "+tableName);

            while(rs.next())
            {
                int productID= rs.getInt("productID"); //read productID
                String productName= rs.getString("productName"); //read productName
                String description= rs.getString("description");
                int pricePerUnit= rs.getInt("pricePerUnit");
                int quantity= rs.getInt("quantity");
                String stringProductStatus= rs.getString("productStatus");
                ProductStatusEnum productStatus= ProductStatusEnum.Available;
                if (stringProductStatus.contains("NotAvailable"))
                {
                    productStatus=ProductStatusEnum.NotAvailable;
                }
                //create Product Object
                Product aProduct= new Product(productID, productName,description, pricePerUnit,quantity,productStatus);
                //add Product to productList
                productList.add(aProduct);
                System.out.println("one product has been added");
            }
        }
        else //if product table not exist
        {
             System.out.println("table is not existed");
            // Step 4: Creating a new STUDENTDB table in DMSDB
            String sqlQuery = "CREATE TABLE "+tableName + " (productID INT PRIMARY KEY," +
            " productName VARCHAR(20), description VARCHAR(50), pricePerUnit INT, quantity INT, productStatus VARCHAR(20))";
            int resultDB = statement.executeUpdate(sqlQuery);
            if(resultDB == 0)
            System.out.println("ProductBook Table is created");
            // Step 5: Inserting a record in the Student table in DMSDB
            sqlQuery = "INSERT INTO "+ tableName +" VALUES" +
            "(1001, 'Java','This book is for Java leaner', 20,3,'Available')," +
            "(1002, 'C','This book is for C leaner', 25,2,'Available')," +
            "(1003, 'Python','This book is for Python leaner',22,0,'NotAvailable')," +
            "(1004, 'Ruby','This book is for Ruby leaner',20,4,'Available'),"+
            "(1005, 'DMS','This book is for DMS leaner',21,3,'Available'),"+
            "(1006, 'Matlab','This book is for Matlab leaner',15,2,'Available')";
            resultDB = statement.executeUpdate(sqlQuery);
            if(resultDB == 6)
            System.out.println("6 records are insterted in Product Book Table");
            //add data
            initialiseProductList();
        }
         //close connection
        connection.close();
    }
    
    //this method is to add a product book into a database
    public void addProductBook(Product aProduct) throws ClassNotFoundException, SQLException
    {
        String stringProductStatus= "Available";
        //check if quantity is less than 0 or more
        if (aProduct.getQuantity()<=0)
        {
            aProduct.setProductStatus(ProductStatusEnum.NotAvailable);
            stringProductStatus="NotAvailable";
        }
        this.productList.add(aProduct);
         //Create Connection
        Connection connection= connectDatabaseSchema();
        // Creating the SQL Statement
        Statement statement = connection.createStatement();
        String sqlQuery = "INSERT INTO "+ tableName +" VALUES (" + 
                aProduct.getProductID()+" ,'"+aProduct.getProductName()+"' ,'"+aProduct.getDescription()+"',"+aProduct.getPricePerUnit()+" ,"+aProduct.getQuantity()+" ,'"+stringProductStatus+"')";
        statement.executeUpdate(sqlQuery);
        System.out.println("a Product has been added");
        //close connection
        connection.close();
    }
    
    public ArrayList<Product> getDataProductListFromDB() throws ClassNotFoundException, SQLException
    {
        productList.clear();
         Connection connection= connectDatabaseSchema();
        // Creating the SQL Statement
        Statement statement = connection.createStatement();
        //Reading records from Product Table
        ResultSet rs=statement.executeQuery("SELECT * FROM "+tableName);

            while(rs.next())
            {
                int productID= rs.getInt("productID"); //read productID
                String productName= rs.getString("productName"); //read productName
                String description= rs.getString("description");
                int pricePerUnit= rs.getInt("pricePerUnit");
                int quantity= rs.getInt("quantity");
                String stringProductStatus= rs.getString("productStatus");
                ProductStatusEnum productStatus= ProductStatusEnum.Available;
                if (stringProductStatus.contains("NotAvailable"))
                {
                    productStatus=ProductStatusEnum.NotAvailable;
                }
                //create Product Object
                Product aProduct= new Product(productID, productName,description, pricePerUnit,quantity,productStatus);
                //add Product to productList
                productList.add(aProduct);
                System.out.println("one product has been added");
            }
            return productList;
    }
    
    public void addQuantity(int productID, int addedQuantity) throws ClassNotFoundException, SQLException
    {
        Connection connection= connectDatabaseSchema();
        //get quantity first to check if it is more than 0
        Statement statement = connection.createStatement();
        // Creating the SQL Statement
        String sqlQuery = "UPDATE "+tableName+" SET quantity= quantity+"+addedQuantity+", productStatus='Available'"+" WHERE productID="+productID;
        PreparedStatement ps = connection.prepareStatement(sqlQuery);
        ps.executeUpdate();
        System.out.println("Quantity has been updated");
        //close connection
        connection.close();
    }
    
    public boolean removeQuantity(int productID, int removedQuantity) throws ClassNotFoundException, SQLException
    {
        Connection connection= connectDatabaseSchema();
        //check if quantity is ok
        // Creating the SQL Statement
        Statement statement = connection.createStatement();
        //Reading records from Product Table
        String sqlQuery = "SELECT * FROM "+tableName+" WHERE productID="+productID;
        ResultSet rs=statement.executeQuery(sqlQuery);
        rs.next();
        int currentQuantity= rs.getInt("quantity");
        int resultQuantity= currentQuantity-removedQuantity;
        if (resultQuantity<0)
        {
            connection.close();
            return false;
        }
        else //update
        {
            String productStatus="Available";
            if (resultQuantity==0)
            {
                productStatus="NotAvailable";
            }
            // Creating the SQL Statement
            sqlQuery = "UPDATE "+tableName+" SET quantity="+resultQuantity+", productStatus='"+productStatus+"' WHERE productID="+productID;
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.executeUpdate();
            System.out.println("Quantity has been updated");
            //close connection
            connection.close();
            return true;
        }
    }
    
    public Product retrieveProduct(int productID)throws ClassNotFoundException, SQLException
    {
        Product aProduct=null;
        //Create Connection
        Connection connection= connectDatabaseSchema();
        // Creating the SQL Statement
        Statement statement = connection.createStatement();
        String sqlQuery = "SELECT * FROM "+tableName+" WHERE productID="+productID;
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        // Step 7: Reading data from the ResultSet
        while (resultSet.next())
        {
            int id= resultSet.getInt("productID"); //read productID
            String productName= resultSet.getString("productName"); //read productName
            String description= resultSet.getString("description");
            int pricePerUnit= resultSet.getInt("pricePerUnit");
            int quantity= resultSet.getInt("quantity");
            String stringProductStatus= resultSet.getString("productStatus");
            ProductStatusEnum productStatus= ProductStatusEnum.Available;
            if (stringProductStatus.contains("NotAvailable"))
            {
                productStatus=ProductStatusEnum.NotAvailable;
            }
            //create Product Object
            aProduct= new Product(id, productName,description, pricePerUnit,quantity,productStatus);
        }
        //close connection
        connection.close();
        return aProduct;
    }
    
    
    //this method is to check if the table product table already exist in the database
    public static boolean isTableExisting(String tableName, Connection theConnection) throws SQLException
    {
        DatabaseMetaData theMetaData = theConnection.getMetaData();
        
        ResultSet existingTable = theMetaData.getTables(null, null, tableName.toUpperCase(), null);
        
        if(existingTable.next())
        {
                return true;
        }
        return false;
    }
     
    private Connection connectDatabaseSchema() throws ClassNotFoundException, SQLException
    {

           // Step 1: Loading the drivers for JAVA DB
           Class.forName(driverURL);
           // Network Driver both will work with this example.
           // You can use any one of them
           //Class.forName("org.apache.derby.jdbc.ClientDriver");

           // Step 2: Connecting to sample Database in Java DB
           Connection connection = DriverManager.getConnection(dbURL);
           System.out.println("Database is connected...");
           return connection;
    }
    
    private void initialiseProductList()
     {
         productList.add(new Product(1001, "Java","This book is for Java leaner", 20,3, ProductStatusEnum.Available));
         productList.add(new Product(1002, "C","This book is for C leaner",25,2, ProductStatusEnum.Available));
         productList.add(new Product(1003, "Python","This book is for Python leaner", 22,0, ProductStatusEnum.NotAvailable));
         productList.add(new Product(1004, "Ruby","This book is for Ruby leaner", 20,4, ProductStatusEnum.Available));
         productList.add(new Product(1005, "DMS","This book is for DMS leaner", 21,3, ProductStatusEnum.Available));
         productList.add(new Product(1006, "Matlab","This book is for Matlab leaner", 15,2, ProductStatusEnum.Available));
     }
    //getter and setter
    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public static String getTableName() {
        return tableName;
    }
    
    
}
