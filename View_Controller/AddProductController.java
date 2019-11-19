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

public class AddProductController implements Initializable {
    
    Stage stage;
    Parent scene;
    Product temp = new Product(1, "Temp", 1.0, 1, 1, 1);

    @FXML
    private TextField addProdNameTxt;
    @FXML
    private TextField addProdInvTxt;
    @FXML
    private TextField addProdPriceTxt;
    @FXML
    private TextField addProdMaxTxt;
    @FXML
    private TextField addProdMinTxt;
    @FXML
    private TableView<Part> prodAddTable1View;
    @FXML
    private TableColumn<Part, Integer> table1ID;
    @FXML
    private TableColumn<Part, String> table1Name;
    @FXML
    private TableColumn<Part, Integer> table1Inv;
    @FXML
    private TableColumn<Part, Double> table1Price;
    @FXML
    private TableView<Part> prodAddTable2View;
    @FXML
    private TableColumn<Part, Integer> table2ID;
    @FXML
    private TableColumn<Part, String> table2Name;
    @FXML
    private TableColumn<Part, Integer> table2Inv;
    @FXML
    private TableColumn<Part, Double> table2Price;
    @FXML
    private TextField addProdSearchTxt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prodAddTable1View.setItems(Inventory.getAllParts());
        table1ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        table1Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table1Inv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table1Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        prodAddTable2View.setItems(temp.getAllAssociatedParts());
        table2ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        table2Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table2Inv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table2Price.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    

    @FXML
    private void onActionSearchProd(ActionEvent event) {
        String searchText = addProdSearchTxt.getText();
                
        try{
            int itemNumber = Integer.parseInt(searchText);
            prodAddTable1View.getSelectionModel().select(Inventory.lookupPart(itemNumber));
            System.out.println("This is Part #"+ itemNumber);
        }
        catch(NumberFormatException e){
            prodAddTable1View.getSelectionModel().select(Inventory.lookupPart(searchText));
            System.out.println("This is Part #"+ Inventory.lookupPart(searchText).getId());
        }   
    }

    @FXML
    private void onActionAddProd(ActionEvent event) {
        Part part = prodAddTable1View.getSelectionModel().getSelectedItem();
        temp.addAssociatedPart(part);
    }

    @FXML
    private void onActionDeleteProd(ActionEvent event) {
        Part part = prodAddTable2View.getSelectionModel().getSelectedItem();
        temp.deleteAssociatedPart(part);
    }

    @FXML
    private void onActionCancelProd(ActionEvent event) throws IOException {
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
    private void onActionSaveProd(ActionEvent event) throws IOException {
        try{
            temp.setName(addProdNameTxt.getText());
            temp.setStock(Integer.parseInt(addProdInvTxt.getText()));
            temp.setPrice(Double.parseDouble(addProdPriceTxt.getText()));
            temp.setMax(Integer.parseInt(addProdMaxTxt.getText()));
            temp.setMin(Integer.parseInt(addProdMinTxt.getText()));
            
            if(temp.getStock() > temp.getMax() || temp.getStock() < temp.getMin())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("Inventory must be between min and max.");
                alert.showAndWait();
            }
            else{
                Inventory.addProduct(temp);
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

    private void showScreen(ActionEvent event, String screen) throws IOException{
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(screen));
        stage.setScene(new Scene(scene));
        stage.show();        
    }
}