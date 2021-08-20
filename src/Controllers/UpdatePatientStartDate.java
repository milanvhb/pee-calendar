package Controllers;

import Logic.Patient;
import Logic.PatientDAO;
//import com.mysql.cj.protocol.Resultset;
import Warning.Message;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdatePatientStartDate implements Initializable {

    ObservableList<String> patients;

    @FXML
    private ComboBox<Patient> cbPatients;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button btnUpdate;

    @FXML
    void updateButtonAction(javafx.event.ActionEvent event) throws SQLException, ClassNotFoundException {
        PatientDAO.updatePatientStartDate(cbPatients.getSelectionModel().getSelectedItem().getEmail(), datePicker.getValue());
        Message.confirmation("Update confirmed", "UPDATE", "The startDate has been updated");

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            cbPatients.setItems(PatientDAO.searchPatients());
            convertComboDisplayList();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

// this method converts the combobox to readible lines
    private void convertComboDisplayList() {
        cbPatients.setConverter(new StringConverter<Patient>() {
            @Override
            public String toString(Patient patient) {
                return patient.getEmail();
            }

            @Override
            public Patient fromString(final String string) {
                return cbPatients.getItems().stream().filter(patient -> patient.getEmail().equals(string)).findFirst().orElse(null);
            }
        });
    }








}
