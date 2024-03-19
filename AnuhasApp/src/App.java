import Backend.SQLManager;
import java.util.Map;   
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class App {
    public static void main(String[] args) {
        // dev branch

        // state
        // insertDataToTable() working!
        // getSellingPrice() working!

        // pushToCategory() working!
        // pushToStock() working!
        // pushToTransaction() working!

        // getCategoryName() working!
        // getAllCategories() working!

        // Object[] myMap = SQLManager.getCategoryName(1); working!
        // System.out.println(myMap[0]);
        // System.out.println(myMap[1]);

        // Map<Integer,String> myMap = SQLManager.getAllCategories(); working!
        // System.out.println(myMap);

        // SQLManager.truncateTable("stock");

        SQLManager.pushToStock(3, "M", 10, 20, 25, getDate());
        SQLManager.pushToStock(1, "L", 20, 15, 20, getDate());



         

        
             
    }
    public static String getDate(){
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);
        return formattedDate;
    }
    
    
}
