//dev branch
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
//    push data to the db
//    pull data from the db
//    delete data from the db
    
//    Defining essential attributes for sql Connection
    private static final String url = "jdbc:mysql://localhost:3306/anuhasdatabase";
    private static final String userName = "root";
    private static final String password = "lamidu@123";
    private static Connection connection;
    private static PreparedStatement statement;
    
    SQLManager(){
        System.out.println("sql manager is activated");
    }
    
//    method to create the connection with mysql database
//    use try catch when you call this method to handle the throwing SQLexception
//    do not forget to close the connection after doing the process
    private static Connection getConnection()throws SQLException{
        if (connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(url,userName,password);
        }
        return connection;
    } 
    
//    -------------------- cloth category table --------------------
//    updating the cloth category table with category_id and category name and returns the updated rows
    public static void pushToClothCategoryTable(int category_id, String category_name){
        String query = "insert into cloth_category_table values (?,?)";
        try{
            statement = getConnection().prepareStatement(query);
            statement.setInt(1,category_id);
            statement.setString(2,category_name);
            int x = statement.executeUpdate();
            connection.close();
            System.out.println(x+" rows has been updated");
        }
        catch(SQLException exc){
            System.out.println(exc.getMessage());
            System.out.println("\n---- stack trace ----");
            exc.printStackTrace();
        }
    }
    public static ResultSet pullFromClothCategoryTable(int category_id){
        String query = "select * from cloth_category_table where category_id = ?";
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
        String query = "select * from cloth_category_table";
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

