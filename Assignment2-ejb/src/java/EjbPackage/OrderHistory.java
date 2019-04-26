/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EjbPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import noneEJB.Order;
import noneEJB.OrderStatusEnum;
import noneEJB.Product;
import noneEJB.ProductStatusEnum;

/**
 *
 * @author MY PHU NGUYEN
 */
@Stateless
public class OrderHistory implements OrderHistoryLocal {
    @EJB
    private ProductListLocal productListBean;
    private ArrayList<Order> orderList;
    
    // The driverURL to contain the Database Driver
      private static final String driverURL = "org.apache.derby.jdbc.EmbeddedDriver";
    // The dbURL to contain the Database URL
      private static final String dbURL = "jdbc:derby://localhost:1527/DMSDB;" + 
                "create=true;user=dms;password=dms2018";
      private static final String orderTableName="OrderTable";
      private static final String orderHasProductTableName="OrderHasProduct";
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    //constructor
    public OrderHistory() throws ClassNotFoundException, SQLException
    {
        productListBean= new ProductListBean();
        //Create OrderList
        orderList= new ArrayList<Order>();
        //Create Connection
        Connection connection= connectDatabaseSchema();
        // Creating the SQL Statement
        Statement statement = connection.createStatement();
        //if connect sucessfully
     
        //if studentDB table exist 
        if (ProductListBean.isTableExisting(orderTableName,connection))
        {
            System.out.println("table existed");
            //Reading records from Product Table
            ResultSet rs=statement.executeQuery("SELECT * FROM "+orderTableName);

            while(rs.next())
            {
                //read orderID, orderTotal and orderStatus
                int orderID= rs.getInt("orderID"); //read productID
                //create Order Object    
                Order anOrder= this.retrieveOrder(orderID);
                //add Product to productList
                orderList.add(anOrder);
                System.out.println("one Order has been added");
            }
        }
        else //if order table not exist
        {
             System.out.println("table is not existed");
             //CREATE AND INSERT DATA FOR TABLE ORDER
            // Step 4: Creating a new STUDENTDB table in DMSDB
            String sqlQuery = "CREATE TABLE "+orderTableName + " (orderID INT PRIMARY KEY," +
            "orderTotal INT, orderStatus VARCHAR(20))";
            int resultDB = statement.executeUpdate(sqlQuery);
            if(resultDB == 0)
            System.out.println("Order Table is created");
            // Step 5: Inserting a record in the Student table in DMSDB
            sqlQuery = "INSERT INTO "+ orderTableName +" VALUES" +
            "(2001,62,'PENDING')," +
            "(2002,70,'APPROVED')," +
            "(2003,185,'REJECTED')";
            resultDB = statement.executeUpdate(sqlQuery);
            if(resultDB == 3)
            System.out.println("3 records are insterted in Order Table");
            
            //CREATE AND INSERT DATA FOR TABLE OrderHasProduct
            // Step 4: Creating a new STUDENTDB table in DMSDB
            sqlQuery = "CREATE TABLE "+orderHasProductTableName + " (orderID INT," +
            "productID INT, pricePerUnit INT, quantity INT, CONSTRAINT PK_OrderHasProduct PRIMARY KEY (orderID, productID))";
            resultDB = statement.executeUpdate(sqlQuery);
            if(resultDB == 0)
            System.out.println("OrderHasProduct Table is created");
            // Step 5: Inserting a record in the Student table in DMSDB
            sqlQuery = "INSERT INTO "+ orderHasProductTableName +" VALUES" +
            "(2001,1001,20,2)," +
            "(2001,1003,22,1)," +
            "(2002,1002,25,1)," +
            "(2002,1006,15,3)," +
            "(2003,1003,22,5)," +
            "(2003,1006,15,5)";
            resultDB = statement.executeUpdate(sqlQuery);
            if(resultDB == 6)
            System.out.println("6 records are insterted in OrderHasProduct Table");
            //add data
            initialiseOrderList();
        }
         //close connection
        connection.close();
    }
    
    public void addAnOrder(Order anOrder) throws ClassNotFoundException, SQLException
    {
        this.orderList.add(anOrder);
         //Create Connection
        Connection connection= connectDatabaseSchema();
        // Creating the SQL Statement
        Statement statement = connection.createStatement();
        String sqlQuery = "INSERT INTO "+ orderTableName +" VALUES (" + 
                anOrder.getOrderID()+" ,"+anOrder.getOrderTotal()+",'"+returnOrderStatus(anOrder.isOrderStatus())+"')";
        statement.executeUpdate(sqlQuery);
        System.out.println("Order has been added");
        for(Product aProduct: anOrder.getProductList())
        {
            sqlQuery = "INSERT INTO "+ orderHasProductTableName +" VALUES (" + 
            anOrder.getOrderID()+" ,"+aProduct.getProductID()+" ,"+aProduct.getPricePerUnit()+","+aProduct.getQuantity()+")";
            statement.executeUpdate(sqlQuery);
            System.out.println("Order has Product has been added");
        }
        //close connection
        connection.close();
    }
    
    public Order retrieveOrder(int orderID) throws ClassNotFoundException, SQLException
    {
        Order anOrder=null;
        //Create Connection
        Connection connection= connectDatabaseSchema();
        // Creating the SQL Statement
        Statement statement = connection.createStatement();
        String sqlQuery = "SELECT * FROM "+orderTableName+" WHERE orderID="+orderID;
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        // Step 7: Reading data from the ResultSet
        while (resultSet.next())
        {
            int id= resultSet.getInt("orderID"); //read productID
            int orderTotal= resultSet.getInt("orderTotal");
            String stringOrderStatus= resultSet.getString("orderStatus");
            OrderStatusEnum orderStatus= OrderStatusEnum.PENDING;
            if (stringOrderStatus.contains("APPROVED"))
            {
                orderStatus=OrderStatusEnum.APPROVED;
            }
            else if (stringOrderStatus.contains("REJECTED"))
            {
                orderStatus=OrderStatusEnum.REJECTED;
            }
            //make a productList
            ArrayList<Product> productList= new ArrayList<Product>();

            String sqlQuery2 = "SELECT * FROM "+orderHasProductTableName+" WHERE orderID="+orderID;
            Statement statement2 = connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery(sqlQuery2);
            // Step 7: Reading data from the ResultSet
            while (resultSet2.next())
            {
                int productID= resultSet2.getInt("productID"); //read productID
                int pricePerUnit= resultSet2.getInt("pricePerUnit");
                int quantity= resultSet2.getInt("quantity");
                Product productFromProductTable= productListBean.retrieveProduct(productID);
                Product productInOrder= new Product(productID,productFromProductTable.getProductName(),productFromProductTable.getDescription(),pricePerUnit,quantity,productFromProductTable.getProductStatus());
                productList.add(productInOrder);
            }
            //create Order Object
            anOrder= new Order(orderID,productList,orderTotal,orderStatus);
        }
        //close connection
        connection.close();
        return anOrder;
    }
    
    public void modifyOrderStatus(int orderID, OrderStatusEnum aStatus) throws ClassNotFoundException, SQLException
    {
        String orderStatus= returnOrderStatus(aStatus);
        Connection connection= connectDatabaseSchema();
        //get quantity first to check if it is more than 0
        Statement statement = connection.createStatement();
        // Creating the SQL Statement
        String sqlQuery = "UPDATE "+orderTableName+" SET orderStatus='"+orderStatus+"' WHERE orderID="+orderID;
        PreparedStatement ps = connection.prepareStatement(sqlQuery);
        ps.executeUpdate();
        System.out.println("Order Status has been updated");
        //close connection
        connection.close();
    }
    
    public ArrayList<Order> getOrderListFromDB() throws ClassNotFoundException, SQLException
    {
        this.orderList.clear();
        //Create Connection
        Connection connection= connectDatabaseSchema();
        // Creating the SQL Statement
        Statement statement = connection.createStatement();
        String sqlQuery = "SELECT * FROM "+orderTableName;
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        // Step 7: Reading data from the ResultSet
        while (resultSet.next())
        {
            int id= resultSet.getInt("orderID"); //read productID
            int orderTotal= resultSet.getInt("orderTotal");
            String stringOrderStatus= resultSet.getString("orderStatus");
            OrderStatusEnum orderStatus= OrderStatusEnum.PENDING;
            if (stringOrderStatus.contains("APPROVED"))
            {
                orderStatus=OrderStatusEnum.APPROVED;
            }
            else if (stringOrderStatus.contains("REJECTED"))
            {
                orderStatus=OrderStatusEnum.REJECTED;
            }
            //make a productList
            ArrayList<Product> productList= new ArrayList<Product>();

            String sqlQuery2 = "SELECT * FROM "+orderHasProductTableName+" WHERE orderID="+id;
            Statement statement2 = connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery(sqlQuery2);
            // Step 7: Reading data from the ResultSet
            while (resultSet2.next())
            {
                int productID= resultSet2.getInt("productID"); //read productID
                int pricePerUnit= resultSet2.getInt("pricePerUnit");
                int quantity= resultSet2.getInt("quantity");
                Product productFromProductTable= productListBean.retrieveProduct(productID);
                Product productInOrder= new Product(productID,productFromProductTable.getProductName(),productFromProductTable.getDescription(),pricePerUnit,quantity,productFromProductTable.getProductStatus());
                productList.add(productInOrder);
            }
            //create Order Object
            Order anOrder= new Order(id,productList,orderTotal,orderStatus);
            //add Order to list
            this.orderList.add(anOrder);
        }
        //close connection
        connection.close();
        return orderList;
    }
    
    
    private void initialiseOrderList()
    {
         //make 3 productList
         ArrayList<Product> productList1= new ArrayList<Product>();
         ArrayList<Product> productList2= new ArrayList<Product>();
         ArrayList<Product> productList3= new ArrayList<Product>();
         //add to productList
         productList1.add(new Product(1001, "Java","This book is for Java leaner", 20,2, ProductStatusEnum.Available));
         productList1.add(new Product(1003, "Python","This book is for Python leaner", 22,1, ProductStatusEnum.Available));
         
         productList2.add(new Product(1002, "C","This book is for C leaner",25,1, ProductStatusEnum.Available));
         productList2.add(new Product(1006, "Matlab","This book is for Matlab leaner", 15,3, ProductStatusEnum.Available));
         
         productList3.add(new Product(1003, "Python","This book is for Python leaner", 22,5, ProductStatusEnum.Available));
         productList3.add(new Product(1006, "Matlab","This book is for Matlab leaner", 15,5, ProductStatusEnum.Available));
         
         
         orderList.add(new Order(2001, productList1,62,OrderStatusEnum.PENDING));
         orderList.add(new Order(2002, productList2,70, OrderStatusEnum.APPROVED));
         orderList.add(new Order(2003, productList3,185 ,OrderStatusEnum.REJECTED));
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
    
    
    private String returnOrderStatus (OrderStatusEnum aStatus)
    {
        if (aStatus==OrderStatusEnum.APPROVED)
        {
            return "APPROVED";
        }
        else if (aStatus==OrderStatusEnum.REJECTED)
        {
            return "REJECTED";
        }
        else
        {
            return "PENDING";
        }
    }
    //getter and setter

    public ProductListLocal getProductListBean() {
        return productListBean;
    }

    public void setProductListBean(ProductListLocal productListBean) {
        this.productListBean = productListBean;
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }

    public static String getOrderTableName() {
        return orderTableName;
    }

    public static String getOrderHasProductTableName() {
        return orderHasProductTableName;
    }
    
    
}
