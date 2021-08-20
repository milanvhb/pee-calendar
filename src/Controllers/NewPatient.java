package Controllers;


import Logic.Dokter;
import Logic.PatientDAO;
import Warning.Message;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewPatient implements Initializable {

    private Dokter model;

    @FXML
    private DatePicker datePicker1;

    @FXML
    private DatePicker datePicker2;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtSurname;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPass;

    @FXML
    private Button btnPatient;

    @FXML
    void actionAddPatient(javafx.event.ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            PatientDAO.insertPatient(txtEmail.getText(),txtSurname.getText(), txtFirstName.getText(), datePicker2.getValue() , datePicker1.getValue(), txtPass.getText(), Integer.parseInt(txtAge.getText()));
            Message.confirmation("New patient added to the database", "PATIENT ADDED", "Succesfully added a new patient");

        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
