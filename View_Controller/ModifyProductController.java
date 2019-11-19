package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ModifyProductController implements Initializable {
    
    Stage stage;
    Parent scene;
    Product product;

    @FXML
    private TextField modProdIDTxt;
    @FXML
    private TextField modProdNameTxt;
    @FXML
    private TextField modProdInvTxt;
    @FXML
    private TextField modProdPriceTxt;
    @FXML
    private TextField modProdMaxTxt;
    @FXML
    private TextField modProdMinTxt;
    @FXML
    private TableView<Part> table1View;
    @FXML
    private TableColumn<Part, Integer> table1ID;
    @FXML
    private TableColumn<Part, String> table1Name;
    @FXML
    private TableColumn<Part, Integer> table1Inv;
    @FXML
    private TableColumn<Part, Double> table1Price;
    @FXML
    private TableView<Part> table2View;
    @FXML
    private TableColumn<Part, Integer> table2ID;
    @FXML
    private TableColumn<Part, String> table2Name;
    @FXML
    private TableColumn<Part, Integer> table2Inv;
    @FXML
    private TableColumn<Part, Double> table2Price;
    @FXML
    private TextField modProdSearchTxt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        table1View.setItems(Inventory.getAllParts());
        table1ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        table1Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table1Inv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table1Price.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    

    @FXML
    private void onActionModSearchProd(ActionEvent event) {
        String searchText = modProdSearchTxt.getText();
                
        try{
            int itemNumber = Integer.parseInt(searchText);
            table1View.getSelectionModel().select(Inventory.lookupPart(itemNumber));
            System.out.println("This is Part #"+ itemNumber);
        }
        catch(NumberFormatException e){
            table1View.getSelectionModel().select(Inventory.lookupPart(searchText));
            System.out.println("This is Part #"+ Inventory.lookupPart(searchText).getId());
        }   
    }

    @FXML
    private void onActionModAddProd(ActionEvent event) {
        Part part = table1View.getSelectionModel().getSelectedItem();
        product.addAssociatedPart(part);
    }

    @FXML
    private void onActionModDeleteProd(ActionEvent event) {
        Part part = table2View.getSelectionModel().getSelectedItem();
        product.deleteAssociatedPart(part);
    }

    @FXML
    private void onActionModCancelProd(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("This will delete any input and return to main screen.");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK){
            showScreen(event,"/View_Controller/main.fxml");
        }
    }

    @FXML
    private void onActionModSaveProd(ActionEvent event) throws IOException {
        try{
            int id = Integer.parseInt(modProdIDTxt.getText());
            product.setId(Integer.parseInt(modProdIDTxt.getText()));
            product.setName(modProdNameTxt.getText());
            product.setStock(Integer.parseInt(modProdInvTxt.getText()));
            product.setPrice(Double.parseDouble(modProdPriceTxt.getText()));
            product.setMax(Integer.parseInt(modProdMaxTxt.getText()));
            product.setMin(Integer.parseInt(modProdMinTxt.getText()));
            
            if(product.getStock() > product.getMax() || product.getStock() < product.getMin())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("Inventory must be between min and max.");
                alert.showAndWait();
            }
            else{
                Inventory.updateProduct(id, product);
                showScreen(event,"/View_Controller/main.fxml");
            }
        }
        catch(NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error!");
            alert.setContentText("Please enter valid data.");
            alert.showAndWait();
        }
    }
    
    public void setProduct(Product product) {      
        modProdIDTxt.setText(Integer.toString(product.getId()));
        modProdNameTxt.setText(product.getName());
        modProdInvTxt.setText(Integer.toString(product.getStock()));
        modProdPriceTxt.setText(Double.toString(product.getPrice()));
        modProdMaxTxt.setText(Integer.toString(product.getMax()));
        modProdMinTxt.setText(Integer.toString(product.getMin()));
        this.product = product;
        
        table2View.setItems(product.getAllAssociatedParts());
        table2ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        table2Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table2Inv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table2Price.setCellValueFactory(new PropertyValueFactory<>("price"));
     }
    
    private void showScreen(ActionEvent event, String screen) throws IOException{
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(screen));
        stage.setScene(new Scene(scene));
        stage.show();        
    } 
}
