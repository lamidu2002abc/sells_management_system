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
    
    
// main functions >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //  sets connection between the application and the mysql server 
    private static Connection getConnection()throws SQLException{
        if (connection == null || connection.isClosed()) {connection = DriverManager.getConnection(url,userName,password);}
        return connection;
    } 

    //  executes any number of cols in tables not multiple raws at a time. 
    private static int updateTable(String query, Object[] array) {
        int generatedKey = -1;
        try{
            statement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int i=1;
            for (Object object : array) {
                if (object instanceof String) statement.setString(i,(String) object);
                else if (object instanceof Double) statement.setDouble(i,(Double) object);
                else if (object instanceof Integer)statement.setInt(i,(Integer) object);
                else System.out.println("  unknown data type!!!");
                i++;     
            }
            int updatedRowsCount = statement.executeUpdate();
            System.out.println(updatedRowsCount+" Rows has been updated");
            //auto generated keys
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {generatedKey = resultSet.getInt(1);}    
            connection.close();
        }
        catch(SQLException exc){
            System.out.println(exc.getMessage());
            System.out.println("\n---- stack trace ----");
            exc.printStackTrace();
        }
        return generatedKey;   
    }

    
// system procedurs >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 
    //  --adding new category to the system--
    public static void addNewCategory(String categoryName){
        String query = "insert into category(cname) values (?)";
        updateTable(query, new Object[]{categoryName});
    }

    //  --adding new stock to the system--
    public static void addNewStock(int cid, String size, int squantity, double buying_price, double selling_price, String buying_date){
        String query = "insert into stock(cid,size,squantity,buying_price,selling_price,buying_date) values (?,?,?,?,?,?)";
        int sid = updateTable(query, new Object[] {cid,size,squantity,buying_price,selling_price,buying_date});
        addStocksToInventory(sid, squantity);//updates inventory aswell! 
    }
    //  adding new stock to the inventory 
    private static void addStocksToInventory(int sid, int squantity){
        String query = "insert into inventory(sid,squantity) values (?,?) ON DUPLICATE KEY UPDATE squantity = squantity + VALUES(squantity);";
        updateTable(query, new Object[] {sid,squantity});
    }

    //  --doing new transaction--
    public static void doTransaction(String date,String customer_name,String customer_address,int customer_tel_number,int sid,int count){
        String query = "insert into transactions(date,customer_name,customer_address,customer_tel_number,sid,count,amount) values (?,?,?,?,?,?,?)";
        double buying_price = (double) getSellingPrice(sid);
        double amount = count*buying_price;
        updateTable(query, new Object[] {date,customer_name,customer_address,customer_tel_number,sid,count,amount});
        removeItemsFromInventory(sid,count);
    }
    //  gets the buying price for the sid;
    private static Object getSellingPrice(int sid){
        String query = "select selling_price from stock where sid = ?";
        double selling_price = 0;
        try{
            statement = getConnection().prepareStatement(query);
            statement.setInt(1,sid);
            ResultSet dataSet = statement.executeQuery();
            while (dataSet.next()) {selling_price = dataSet.getDouble("selling_price");}
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
    // removing items from the inventory
    private static void removeItemsFromInventory(int sid,int count){
        String query = " ";
        updateTable(query,new Object[]{sid,count});
    }


    
    
    

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

