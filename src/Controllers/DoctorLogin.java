package Controllers;

import Logic.DoctorDAO;

import Warning.Message;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class DoctorLogin {


    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPassHidden;

    @FXML
    private Button btnLogin;

    @FXML
    void loginAction(javafx.event.ActionEvent event) throws SQLException, ClassNotFoundException {

        String user = txtUser.getText();
        String pass = txtPassHidden.getText();
        if(DoctorDAO.verifyUser(pass, user)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/DoctorHome.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setOpacity(1);
                stage.setTitle("DoctorHome");
                stage.setScene(new Scene(root, 600, 400));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else {
            Message.alert("Warning!", "Wrong password", "You entered a wrong password or username");
        }

    }
}
