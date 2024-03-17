package Backend;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class SQLManager {
//    main tables -> 
//          -> category
//          -> stock
//          -> transaction
//          -> inventory

    
//    Defining essential attributes for sql Connection
    private static final String url = "jdbc:mysql://localhost:3306/cloth_shop_test";
    private static final String userName = "root";
    private static final String password = "lamidu@123";
    private static Connection connection;
    private static PreparedStatement statement;
    
    
//    method to create the connection with mysql database
//    use try catch when you call this method to handle the throwing SQLexception
//    do not forget to close the connection after doing the process
    private static Connection getConnection()throws SQLException{
        if (connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(url,userName,password);
        }
        return connection;
    } 

//  this will execute any number of cols in tables not multiple raws at a time. 
    private static void insertDataToTable(String query, Object[] array) {
        // testing perpose===============================================
        // for (Object object : array) {
        //     System.out.print(object +" "+ object.getClass());
        //     if (object instanceof String) System.out.println("  its String!!!");
        //     else if (object instanceof Double) System.out.println("  its Double!!!");
        //     else if (object instanceof Integer) System.out.println("  its Integer!!!");
        //     else System.out.println("  unknown data type!!!");
        // }

        try{
            statement = getConnection().prepareStatement(query);
            int i=1;
            for (Object object : array) {
                if (object instanceof String) statement.setString(i,(String) object);
                else if (object instanceof Double) statement.setDouble(i,(Double) object);
                else if (object instanceof Integer)statement.setInt(i,(Integer) object);
                else System.out.println("  unknown data type!!!");
                i++;     
            }
            int x = statement.executeUpdate();
            System.out.println(x+" Rows has been updated");
            connection.close();
        }
        catch(SQLException exc){
            System.out.println(exc.getMessage());
            System.out.println("\n---- stack trace ----");
            exc.printStackTrace();
        }   
    }

//    gets the buying price for the sid;
    public static Object getSellingPrice(int sid){
        String query = "select selling_price from stock where sid = ?";
        double selling_price = 0;
        try{
            statement = getConnection().prepareStatement(query);
            statement.setInt(1,sid);
            ResultSet dataSet = statement.executeQuery();

            while (dataSet.next()) {
                selling_price = dataSet.getDouble("selling_price");
            }
            connection.close();
            return selling_price;
        }
        catch(SQLException exc){
            System.out.println(exc.getMessage());
            System.out.println("\n---- stack trace ----");
            exc.printStackTrace();
            return null;
        }   
    }






    //     PUSHING DATA INTO TABLES=========================================>
    
    //     category ->
    public static void pushToCategory(String categoryName){
        String query = "insert into category(cname) values (?)";
        insertDataToTable(query, new Object[]{categoryName});
    }

    //     stock ->
    public static void pushToStock(int cid, String size, int stock_quantity, double buying_price, double selling_price, String buying_date){
        String query = "insert into stock(cid,size,squantity,buying_price,selling_price,buying_date) values (?,?,?,?,?,?)";
        insertDataToTable(query, new Object[] {cid,size,stock_quantity,buying_price,selling_price,buying_date});
    }

    //     inventory ->
    // write the code to add the stock into the inventory when you add stocks

    //     transaction -> 
    public static void pushToTransactions(String date,String customer_name,String customer_address,int customer_tel_number,int sid,int count){
        String query = "insert into transactions(date,customer_name,customer_address,customer_tel_number,sid,count,amount) values (?,?,?,?,?,?,?)";
        double buying_price = (double) getSellingPrice(sid);
        double amount = count*buying_price;
        insertDataToTable(query, new Object[] {date,customer_name,customer_address,customer_tel_number,sid,count,amount});
    }
    // create method to be called when you do the transaction for decrement the quantity of the stocks that is sold from the invetory table 


    
    
    

    //     PULLING DATA FROM TABLES=========================================>
    
    // gets only the category name according to the category id 
    public static Object[] getCategoryName(int category_id){
        String query = "select * from category where cid = ?";
        Object[] dataArray = new Object[2];
        try{
            statement = getConnection().prepareStatement(query);
            statement.setInt(1,category_id);
            ResultSet dataSet = statement.executeQuery();
            while (dataSet.next()) {
                dataArray[0] = dataSet.getInt("cid");
                dataArray[1] = dataSet.getString("cname");
            }
            return dataArray;
        }
        catch(SQLException exc){
            System.out.println(exc.getMessage());
            System.out.println("\n---- stack trace ----");
            exc.printStackTrace();
            return null;
        }
        finally{
            if (connection!=null){
                try{
                    connection.close();
                    System.out.println("connection has been closed");
                }
                catch(SQLException exc){
                    System.out.println(exc.getMessage());
                    exc.printStackTrace();
                }
            }
        }
    } 

    //  gets all the categories from category table 
    public static Map<Integer,String> getAllCategories(){
        String query = "select * from category";
        Map <Integer,String> categorySet = new HashMap<>();
        try{
            statement = getConnection().prepareStatement(query);
            ResultSet dataSet = statement.executeQuery();
            
            while(dataSet.next()){
                categorySet.put(dataSet.getInt("cid"),dataSet.getString("cname"));
            }
            return categorySet;
        }
        catch(SQLException exc){
            System.out.println(exc.getMessage());
            System.out.println("\n---- stack trace ----");
            exc.printStackTrace();
            return null;
        }
        finally{
            if (connection!=null){
                try{
                    connection.close();
                    System.out.println("connection has been closed");
                }
                catch(SQLException exc){
                    System.out.println(exc.getMessage());
                    exc.printStackTrace();
                }
            }
        }
    } 
    
    
    

    

    
//     DELETE DATA FROM TABLES=========================================>

//  red zone!!!
//  truncates table
public static void truncateTable(String tableName){
    
        try{
            String query = "truncate table " + tableName; 
            statement = getConnection().prepareStatement(query);
            int x = statement.executeUpdate();
            System.out.println(x+" rows has been cleaned");
            connection.close();
        }
        catch(SQLException exc){
            System.out.println(exc.getMessage());
            System.out.println("\n---- stack trace ----");
            exc.printStackTrace();
        }
    }

}

