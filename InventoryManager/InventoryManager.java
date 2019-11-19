package InventoryManager;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Product;
        
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InventoryManager extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        InhousePart part1 = new InhousePart(1, "Part 1", 5.00, 5, 1, 10, 1);
        OutsourcedPart part2 = new OutsourcedPart(2, "Part 2", 10.00, 10, 1, 10, "Microsoft");
        InhousePart part3 = new InhousePart(3, "Part 3", 15.00, 9, 1, 10, 3);
        
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        
        Product prod1 = new Product(1, "Product 1", 5.00, 5, 1, 10);
        Product prod2 = new Product(2, "Product 2", 10.00, 5, 1, 10);
        Product prod3 = new Product(3, "Product 3", 15.00, 9, 1, 10);
                
        Inventory.addProduct(prod1);
        Inventory.addProduct(prod2);
        Inventory.addProduct(prod3);
        
        launch(args);
    }
    
}
