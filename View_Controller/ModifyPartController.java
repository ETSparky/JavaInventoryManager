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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ModifyPartController implements Initializable {
    
    Stage stage;
    Parent scene;
    Part part;

    @FXML
    private RadioButton InhouseBtn;
    @FXML
    private RadioButton OutsourceBtn;
    @FXML
    private TextField IDtxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField invTxt;
    @FXML
    private TextField priceTxt;
    @FXML
    private TextField maxTxt;
    @FXML
    private Text CompanyLabel;
    @FXML
    private TextField companyLabelTxt;
    @FXML
    private TextField minTxt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {     

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
            int id = Integer.parseInt(IDtxt.getText());
            String name = nameTxt.getText();
            int inv = Integer.parseInt(invTxt.getText());
            double price = Double.parseDouble(priceTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            
            if (inv > max || inv < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("Inventory must be between min and max.");
                alert.showAndWait();
            }
            else{
                if(InhouseBtn.isSelected()){
                    int machineID = Integer.parseInt(companyLabelTxt.getText());
                    InhousePart part = new InhousePart(id, name, price, inv, min, max, machineID);
                    Inventory.updatePart(id, part);
                }
                else if (OutsourceBtn.isSelected()){
                    String companyName = companyLabelTxt.getText();
                    OutsourcedPart part = new OutsourcedPart(id, name, price, inv, min, max, companyName);
                    Inventory.updatePart(id, part);
                }
                showScreen(event,"/View_Controller/main.fxml");
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("This will delete any input and return to main screen.");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK){
            showScreen(event,"/View_Controller/main.fxml");
        }
    }
    
    public void setPart(Part part) {      
        IDtxt.setText(Integer.toString(part.getId()));
        nameTxt.setText(part.getName());
        invTxt.setText(Integer.toString(part.getStock()));
        priceTxt.setText(Double.toString(part.getPrice()));
        maxTxt.setText(Integer.toString(part.getMax()));
        minTxt.setText(Integer.toString(part.getMin()));
        this.part = part;
                
        if(part instanceof InhousePart){
            companyLabelTxt.setText(Integer.toString(((InhousePart) part).getMachineID()));
            InhouseBtn.setSelected(true);
            CompanyLabel.setText("Machine ID");
            companyLabelTxt.setPromptText("Machine ID");
        }
        else if (part instanceof OutsourcedPart) {
            companyLabelTxt.setText(((OutsourcedPart)part).getCompanyName());
            OutsourceBtn.setSelected(true);
            CompanyLabel.setText("Company Name");
            companyLabelTxt.setPromptText("Company Name");
        }
     }
    
    private void showScreen(ActionEvent event, String screen) throws IOException{
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(screen));
        stage.setScene(new Scene(scene));
        stage.show();        
    } 
}