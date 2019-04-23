/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EjbPackage;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
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
      private final String tableName="ProductBook";
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public ProductListBean() throws SQLException, ClassNotFoundException
    {
        //Create StudentList
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
                int pricePerUnit= rs.getInt("pricePerUnit");
                int quantity= rs.getInt("quantity");
                String stringProductStatus= rs.getString("productStatus");
                ProductStatusEnum productStatus= ProductStatusEnum.Available;
                if (stringProductStatus=="NotAvailable")
                {
                    productStatus=ProductStatusEnum.NotAvailable;
                }
                //create Product Object
                Product aProduct= new Product(productID, productName, pricePerUnit,quantity,productStatus);
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
            " productName VARCHAR(20), pricePerUnit INT, quantity INT, productStatus VARCHAR(20))";
            int resultDB = statement.executeUpdate(sqlQuery);
            if(resultDB == 0)
            System.out.println("ProductBook Table is created");
            // Step 5: Inserting a record in the Student table in DMSDB
            sqlQuery = "INSERT INTO "+ tableName +" VALUES" +
            "(1001, 'Java', 20,3,'Available')," +
            "(1002, 'C', 25,2,'Available')," +
            "(1003, 'Python',22,0,'NotAvailable')," +
            "(1004, 'Ruby',20,4,'Available'),"+
            "(1005, 'DMS',21,3,'Available'),"+
            "(1006, 'Matlab',15,2,'Available')";
            resultDB = statement.executeUpdate(sqlQuery);
            if(resultDB == 6)
            System.out.println("6 records are insterted in Product Book Table");
            //add data
            initialiseProductList();
        }
         //close connection
        connection.close();
    }
    
    //this method is to check if the table Student already exist in the database
     private static boolean isTableExisting(String tableName, Connection theConnection) throws SQLException
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
         productList.add(new Product(1001, "Java", 20,3, ProductStatusEnum.Available));
         productList.add(new Product(1002, "C",25,2, ProductStatusEnum.Available));
         productList.add(new Product(1003, "Python", 22,0, ProductStatusEnum.NotAvailable));
         productList.add(new Product(1004, "Ruby", 20,4, ProductStatusEnum.Available));
         productList.add(new Product(1005, "DMS", 21,3, ProductStatusEnum.Available));
         productList.add(new Product(1006, "Matlab", 15,2, ProductStatusEnum.Available));
     }
    //getter and setter
    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }
    
    
}
