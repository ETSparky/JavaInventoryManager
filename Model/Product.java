package Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product
{
    //Variables
    private String name;
    private double price;
    private int id, stock, min, max;  
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    static int prodID = 1;
    
    //Constructor
    public Product(int id, String name, double price, int stock, int min, int max) {      
        this.id += prodID;
        prodID++;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    
    //Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
    
    //Methods
    public void addAssociatedPart(Part part){
        this.associatedParts.add(part);
    }
    
    public void deleteAssociatedPart(Part part){
        this.associatedParts.remove(part);
    }
    
    public ObservableList<Part> getAllAssociatedParts(){
        return this.associatedParts;
    }   
}
