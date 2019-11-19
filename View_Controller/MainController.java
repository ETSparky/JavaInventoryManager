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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainController implements Initializable {
    //Variables
    Stage stage;
    Parent scene;
    
    //Parts Table
    @FXML
    private TextField mainPartsSearchTxt;
    @FXML
    private TableView<Part> mainPartsTableView;
    @FXML
    private TableColumn<Part, Integer> mainPartIDCol;
    @FXML
    private TableColumn<Part, String> mainPartNameCol;
    @FXML
    private TableColumn<Part, Integer> mainPartInvCol;
    @FXML
    private TableColumn<Part, Double> mainPartPriceCol;
    @FXML
    private Button modifyPartBtn;
            
    //Products Table
    @FXML
    private TextField mainProdSearchTxt;
    @FXML
    private TableView<Product> mainProdTableView;
    @FXML
    private TableColumn<Product, Integer> mainProdIDCol;
    @FXML
    private TableColumn<Product, String> mainProdNameCol;
    @FXML
    private TableColumn<Product, Integer> mainProdInvCol;
    @FXML
    private TableColumn<Product, Double> mainProdPriceCol;
    @FXML
    private Button modifyProdBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainPartsTableView.setItems(Inventory.getAllParts());
        mainPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        mainProdTableView.setItems(Inventory.getAllProducts());
        mainProdIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainProdNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainProdInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    

    @FXML
    private void onActionSearchPart(ActionEvent event) {   
        String searchText = mainPartsSearchTxt.getText();
                
        try{
            int itemNumber = Integer.parseInt(searchText);
            mainPartsTableView.getSelectionModel().select(Inventory.lookupPart(itemNumber));
            System.out.println("This is Part #"+ itemNumber);
        }
        catch(NumberFormatException e){
            mainPartsTableView.getSelectionModel().select(Inventory.lookupPart(searchText));
            System.out.println("This is Part #"+ Inventory.lookupPart(searchText).getId());
        }
    }
    
    @FXML
    private void onActionAddPart (ActionEvent event) throws IOException{
        showScreen(event, "/View_Controller/addPart.fxml");
    }

    @FXML
    private void onActionDeletePart(ActionEvent event) {        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("This will permanently delete " + mainPartsTableView.getSelectionModel().getSelectedItem().getName());
        alert.setContentText("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK){
            Part part = mainPartsTableView.getSelectionModel().getSelectedItem();
            Inventory.deletePart(part);
        }
    }

    @FXML
    private void onActionModifyPart(ActionEvent event) throws IOException {       
        Parent root;       
        stage=(Stage) modifyPartBtn.getScene().getWindow();                
        Part part = mainPartsTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/modifyPart.fxml"));
        root =loader.load();      
        ModifyPartController controller = loader.getController();
        controller.setPart(part);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }      

    @FXML
    private void onActionSearchProducts(ActionEvent event) {
        String searchText = mainProdSearchTxt.getText();
                
        try{
            int itemNumber = Integer.parseInt(searchText);
            mainProdTableView.getSelectionModel().select(Inventory.lookupProduct(itemNumber));
            System.out.println("This is Part #"+ itemNumber);
        }
        catch(NumberFormatException e){
            mainProdTableView.getSelectionModel().select(Inventory.lookupProduct(searchText));
            System.out.println("This is Part #"+ Inventory.lookupPart(searchText).getId());
        }
    }

    @FXML
    private void onActionDeleteProducts(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("This will permanently delete " + mainProdTableView.getSelectionModel().getSelectedItem().getName());
        alert.setContentText("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK){
            Product product = mainProdTableView.getSelectionModel().getSelectedItem();
            Inventory.deleteProduct(product);
        }
    }

    @FXML
    private void onActionModifyProducts(ActionEvent event) throws IOException {
        Parent root;       
        stage=(Stage) modifyProdBtn.getScene().getWindow();                
        Product product = mainProdTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/modifyProduct.fxml"));
        root =loader.load();      
        ModifyProductController controller = loader.getController();
        controller.setProduct(product);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onActionAddProduct(ActionEvent event) throws IOException {
        showScreen(event, "/View_Controller/addProduct.fxml");
    }

    @FXML
    private void onActionExitProgram(ActionEvent event) {
        System.exit(0);
    }
    
    private void showScreen(ActionEvent event, String screen) throws IOException{
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(screen));
        stage.setScene(new Scene(scene));
        stage.show();        
    }
}
