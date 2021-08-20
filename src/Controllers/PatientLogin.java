package Controllers;

import Logic.Patient;
import Logic.PatientDAO;
import Warning.Message;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PatientLogin implements Initializable {

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPassHidden;

    @FXML
    private Button btnLogin;

    @FXML
    void handleLoginAction(javafx.event.ActionEvent event) throws SQLException, ClassNotFoundException {
        String user = txtUser.getText();
        String pass = txtPassHidden.getText();
        Patient patient = PatientDAO.getPatientByUsernameAndPassword(user, pass);
        if(patient != null) {
            try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/PatientCalendar.fxml"));
                Parent root = fxmlLoader.load();

                ((PatientCalendar)fxmlLoader.getController()).setAuthenticatedUser(patient);

                Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                stage.hide();
                stage.getScene().setRoot(root);
                stage.setTitle("pee-calendar schedule");
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Message.alert("Warning!", "Wrong password/user is entered", "Try Again");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
