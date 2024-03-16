package Backend;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class SQLManager {
//    main tables -> 
//          -> category
//          -> stock
//          -> transactio
//          -> inventory

    
//    Defining essential attributes for sql Connection
    private static final String url = "jdbc:mysql://localhost:3306/anuhasdatabase";
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

//      pushing data into tables

//      category ->
    public static void pushToCategory(String categoryName){
        String query = "insert into category(c_name) values (?)";
        try{
            statement = getConnection().prepareStatement(query);
            statement.setString(1,categoryName);
            int x = statement.executeUpdate();
            connection.close();
            System.out.println(x+" Rows has been updated");
        }
        catch(SQLException exc){
            System.out.println(exc.getMessage());
            System.out.println("\n---- stack trace ----");
            exc.printStackTrace();
        }
    }
//     CREATE TABLE Stock (
//     sid INT AUTO_INCREMENT PRIMARY KEY,
//     cid INT,
//     size VARCHAR(50) NOT NULL,
//     stock_quantity INT NOT NULL,
//     buying_price DECIMAL(10, 2) NOT NULL,
//     selling_price DECIMAL(10, 2) NOT NULL,
//     buying_date DATE NOT NULL,
//     FOREIGN KEY (cid) REFERENCES category(cid)
// );
//      category ->
    public static void pushToStock(int cid, String size, int stock_quantity, int buying_price, int selling_price, String buying_date){
        String query = "insert into category() values (?)";
        try{
            statement = getConnection().prepareStatement(query);
            int x = statement.executeUpdate();
            connection.close();
            System.out.println(x+" Rows has been updated");
        }
        catch(SQLException exc){
            System.out.println(exc.getMessage());
            System.out.println("\n---- stack trace ----");
            exc.printStackTrace();
        }
    }








    public static ResultSet pullFromClothCategoryTable(int category_id){
        String query = "select * from cloth_category where category_id = ?";
        Map <Integer,String> categorySet = new HashMap<>();
        try{
            statement = getConnection().prepareStatement(query);
            statement.setInt(1,category_id);
            ResultSet dataSet = statement.executeQuery();
            categorySet.put(dataSet.getInt("category_id"),dataSet.getString("category_name"));
            connection.close();
            return dataSet;
        }
        catch(SQLException exc){
            System.out.println(exc.getMessage());
            System.out.println("\n---- stack trace ----");
            exc.printStackTrace();
            return null;
        }
    } 
    public static Map<Integer,String> pullAllFromClothCategoryTable(){
        String query = "select * from cloth_category";
        Map <Integer,String> categorySet = new HashMap<>();
        try{
            statement = getConnection().prepareStatement(query);
            ResultSet dataSet = statement.executeQuery();
            
            while(dataSet.next()){
                categorySet.put(dataSet.getInt("category_id"),dataSet.getString("category_name"));
            }
            System.out.println(categorySet);
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
    
//    --------------------  table --------------------
    
//    --------------------  table --------------------
    
    
    
    public static void truncateTable(String tableName){
        try{
            String query = "truncate table "+tableName; 
            statement = getConnection().prepareStatement(query);
            int x = statement.executeUpdate();
            System.out.println(x+" rows has been cleaned");
        }
        catch(SQLException exc){
            System.out.println(exc.getMessage());
            System.out.println("\n---- stack trace ----");
            exc.printStackTrace();
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
}

