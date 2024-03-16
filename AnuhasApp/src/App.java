import Backend.SQLManager;
import java.util.Map;   


public class App {
    public static void main(String[] args) {
//        dev
//        SQLManager.pushToCategory("Crewneck T-shirts");
//        SQLManager.pushToCategory("V-neck T-shirts");
//        SQLManager.pushToCategory("Polo Shirts");
//        SQLManager.pushToCategory("Polo Shirts");
//        SQLManager.pushToCategory("Graphic T-shirts");
        Map<Integer,String> map = SQLManager.pullAllFromClothCategoryTable();
       
//        SQLManager.truncateTable("cloth_category_table");
        // Object[] array = new Object[]{"polo Shirts"};
        // SQLManager.executeQuery("insert into category (cname) value (?)",array);
             
    }
    
    
}
