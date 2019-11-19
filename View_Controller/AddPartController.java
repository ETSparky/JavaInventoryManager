package View_Controller;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddPartController implements Initializable {
    
    Stage stage;
    Parent scene;

    @FXML
    private RadioButton InhouseBtn;
    @FXML
    private RadioButton OutsourceBtn;
    @FXML
    private Text CompanyLabel;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField invTxt;
    @FXML
    private TextField priceTxt;
    @FXML
    private TextField maxTxt;
    @FXML
    private TextField companyLabelTxt;
    @FXML
    private TextField minTxt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        InhouseBtn.setSelected(true);
        CompanyLabel.setText("Machine ID");
        companyLabelTxt.setPromptText("Machine ID");
    }    

    @FXML
    private void onActionInhouse(ActionEvent event) {
        CompanyLabel.setText("Machine ID");
        companyLabelTxt.setPromptText("Machine ID");
    }

    @FXML
    private void onActionOutsource(ActionEvent event) {
        CompanyLabel.setText("Company Name");
        companyLabelTxt.setPromptText("Company Name");
    }

    @FXML
    private void onActionSave(ActionEvent event) throws IOException {
        try{
            int id = 1;
            String name = nameTxt.getText();
            int inv = Integer.parseInt(invTxt.getText());
            double price = Double.parseDouble(priceTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            
            if (inv > max || inv < min) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("Inventory must be between min and max.");
                alert.showAndWait();
            }
            
            else{
                if (InhouseBtn.isSelected()){
                    int machineID = Integer.parseInt(companyLabelTxt.getText());
                    Inventory.addPart(new InhousePart(id, name, price, inv, min, max, machineID));
                    showScreen(event,"/View_Controller/main.fxml");
                }
                else if (OutsourceBtn.isSelected()){
                    String companyName = companyLabelTxt.getText();
                    Inventory.addPart(new OutsourcedPart(id, name, price, inv, min, max, companyName));
                    showScreen(event,"/View_Controller/main.fxml");
                }
            }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error!");
            alert.setContentText("Please enter valid data.");
            alert.showAndWait();
        }
    }

    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("This will delete any input and return to main screen.");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK){
            showScreen(event,"/View_Controller/main.fxml");
        }
    }
    
    private void showScreen(ActionEvent event, String screen) throws IOException{
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(screen));
        stage.setScene(new Scene(scene));
        stage.show();        
    }
}