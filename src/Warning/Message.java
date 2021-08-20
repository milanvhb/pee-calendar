package Warning;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Message {

    public static void alert(String title, String headerText, String contentText) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setPrefSize(300, 200);
        Optional<ButtonType> result = alert.showAndWait();
    }

    public static void confirmation(String title, String headerText, String contentText) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setPrefSize(300, 200);
        Optional<ButtonType> result = alert.showAndWait();
    }



}
