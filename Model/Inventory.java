package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public static void addPart(Part part){
        allParts.add(part);
    }
    
    public static void addProduct(Product product){
        allProducts.add(product);
    }  
    
    public static Part lookupPart(int partID){ 
        for(Part p: Inventory.allParts){   
            if(p.getId()==partID)
                return p;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Error!");
        alert.setContentText("Part not found");
        alert.showAndWait();
                
        return null;
    }
    
    public static Product lookupProduct (int productID){
        for(Product p: Inventory.allProducts){   
            if(p.getId()==productID)
                return p;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Error!");
        alert.setContentText("Part not found");
        alert.showAndWait();
                
        return null;
    }    
    
    public static Part lookupPart(String partName){
        for(Part p: Inventory.allParts){   
            if(p.getName().equals(partName))
                return p;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Error!");
        alert.setContentText("Part not found");
        alert.showAndWait();
                
        return null;    
    }
    
    public static Product lookupProduct (String productName){
        for(Product p: Inventory.allProducts){   
            if(p.getName().equals(productName))
                return p;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Error!");
        alert.setContentText("Part not found");
        alert.showAndWait();
                
        return null;    
    }
    
    public static void updatePart (int index, Part part){     
        allParts.remove(lookupPart(index));
        allParts.add(part);
    }
  
    public static void updateProduct (int index, Product product){
        allProducts.remove(lookupProduct(index));
        allProducts.add(product);
    }
    
    public static void deletePart(Part part){
        allParts.remove(part);
    }
    
    public static void deleteProduct(Product product){
        allProducts.remove(product);
    }
    
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}

