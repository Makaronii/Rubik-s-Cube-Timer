package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TimePopupController implements Initializable {


    @FXML
    TextField timeField;
    @FXML
    TextArea scrambleTextArea;
    @FXML
    Button plusTwoButton, dnfButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timeField.setText(TimePopup.getTime().toString());
        scrambleTextArea.setText(TimePopup.getScramble());

        if(TimePopup.getTime().isDNF())
            dnfButton.setVisible(false);
        if(TimePopup.getTime().isPlusTwo())
            plusTwoButton.setVisible(false);
    }

    public void applyButtonAction(){
        if (dnfButton.isDisable()){
            TimePopup.getTime().setDNF();
        }

        else if(plusTwoButton.isDisable()){
            TimePopup.getTime().setPlusTwo();
        }

        if(plusTwoButton.isDisable() || dnfButton.isDisable()) {
            Controller.timesList.set(TimePopup.getIndex(), TimePopup.getTime());
            TimePopup.stats.initStatistics();
            closeWindow();
        }

        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Nie wprowadziłeś żadnych zmian!");

            alert.showAndWait();
        }

    }

    public void setDNF(){
        timeField.setText("DNF");
        plusTwoButton.setDisable(true);
        dnfButton.setDisable(true);
    }

    public void closeWindow(){
        TimePopup.stage.close();
    }

    public void setPlusTwo(){
        timeField.setText(TimePopup.getTime().toString() + "(+2)");
        plusTwoButton.setDisable(true);
    }
}
