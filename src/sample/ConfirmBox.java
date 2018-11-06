package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 * Our custom Confirm Box class
 */
public class ConfirmBox extends Alert {

    ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

    public ConfirmBox(String title, String headerText, String contentText) {
        super(Alert.AlertType.CONFIRMATION);
        setContentText(contentText);
        setHeaderText(headerText);
        setTitle(title);

       getButtonTypes().setAll(okButton, noButton);
    }


}
