import Backend.SQLManager;
import java.util.Map;   


public class App {
    public static void main(String[] args) {
//        dev
//        SQLManager.pushToClothCategoryTable(0, "Crewneck T-shirts");
//        SQLManager.pushToClothCategoryTable(1, "V-neck T-shirts");
//        SQLManager.pushToClothCategoryTable(2, "Polo Shirts");
//        SQLManager.pushToClothCategoryTable(3, "Polo Shirts");
//        SQLManager.pushToClothCategoryTable(4, "Graphic T-shirts");
        Map<Integer,String> map = SQLManager.pullAllFromClothCategoryTable();
        System.out.println(map);
//        SQLManager.truncateTable("cloth_category_table");
        
             
    }
    
    
}
