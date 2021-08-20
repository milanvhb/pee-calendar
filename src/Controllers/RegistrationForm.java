package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationForm implements Initializable {

    @FXML
    private TextField dayField, weekField;

    @FXML
    private ComboBox<String> typeCombobox;

    @FXML
    private Button deleteRegistrationButton, addRegistrationButton;

    @FXML
    private Label title;

    private Button calendarCell;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        typeCombobox.getItems().addAll("Dry Night", "Few Drops", "Wet Night");
        typeCombobox.getSelectionModel().selectFirst();

        //enkel indien zichtbaar
        deleteRegistrationButton.managedProperty().bind(deleteRegistrationButton.visibleProperty());

        deleteRegistrationButton.setOnAction(e->deleteRegistrationAction(e));
        addRegistrationButton.setOnAction(e->addRegistrationAction(e));
        deleteRegistrationButton.setVisible(false);
    }

    private void deleteRegistrationAction(ActionEvent event){

        PatientCalendar.CalendarData data = (PatientCalendar.CalendarData)calendarCell.getUserData();

        data.deleteRecord();

        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    private void addRegistrationAction(ActionEvent event){

        PatientCalendar.CalendarData data = (PatientCalendar.CalendarData)calendarCell.getUserData();

        data.updateRecordType(typeCombobox.getValue());

        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    public void setCalendarCell(Button cell){
        this.calendarCell = cell;

        PatientCalendar.CalendarData data = (PatientCalendar.CalendarData)cell.getUserData();

        if(data != null){
            dayField.setText(data.dayLabel);
            weekField.setText(data.weekLabel);

            if(data.peeRecord != null){
                typeCombobox.getSelectionModel().select(data.peeRecord.getStatus());

                // je kan enkel deleten als er eerst iets werd geregistreerd
                deleteRegistrationButton.setVisible(true);

                title.setText("Update Registration");
                addRegistrationButton.setText("Update");
            }
        }
    }
}
