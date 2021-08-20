package Controllers;

import Logic.Patient;
import Logic.PeeRecords;
import Logic.PeeRecordsDAO;
import Warning.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class PatientCalendar implements Initializable {

    public static final String EMPTY_CELL = "calendar-empty-cell";
    public static final String ASSIGNED_CELL = "calendar-assigned-cell";

    private static final int NB_WEEKS_IN_CYCLE = 13;
    private static final int NB_DAYS_IN_WEEK = 7;

    private static final String[] DAYS_OF_WEEK = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private static final String[] WEEKS_IN_CYCLE = new String[NB_WEEKS_IN_CYCLE];

    @FXML
    private Label userLabel;

    @FXML
    private Hyperlink logoutLink;

    @FXML
    private Button saveButton;

    @FXML
    private HBox mainContentPane;

    /*
     * First key = week
     * Second key = day
     */

    private Map<Integer, Map<Integer, Button>> calendarEntries = new HashMap<>();
    private Patient patient;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        for(int i = 1; i <= NB_WEEKS_IN_CYCLE ; i++)
            WEEKS_IN_CYCLE[i-1] = "Week " +i;

        logoutLink.setOnAction(e->logoutAction(e));
        saveButton.setOnAction(e->saveAction(e));
    }

    /**
     * Layout van kalender
     */
    private void layoutCalendar(){

        GridPane calendarLayout = new GridPane();
        calendarLayout.setGridLinesVisible(true);

        displayDays(calendarLayout);
        displayWeeks(calendarLayout, patient.getStartDate());
        fillEmptyCalendar(calendarLayout);

        try {
            List<PeeRecords> peeRecordsList = PeeRecordsDAO.getPeeRecordsByPatientAndStartDate(patient.getId(), patient.getStartDate());
            displayPreviousPeeRecords(peeRecordsList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        HBox.setHgrow(calendarLayout, Priority.ALWAYS);
        mainContentPane.getChildren().add(calendarLayout);
    }

    /**
     * toont weken van de cyclus op eerste rij van GridPane
     * @param calendarLayout
     */
    private void displayWeeks(GridPane calendarLayout, LocalDate startDate){

        int daysDiffFromFirstDayOfWeek = startDate.getDayOfWeek().ordinal() - DayOfWeek.MONDAY.ordinal();

        LocalDate firstWeekStart = startDate.minusDays(daysDiffFromFirstDayOfWeek);

        for(int i = 0 ; i < NB_WEEKS_IN_CYCLE ; i++){
            Label weekLabel = new Label(WEEKS_IN_CYCLE[i]);
            weekLabel.setTextAlignment(TextAlignment.CENTER);
            weekLabel.setAlignment(Pos.CENTER);

            LocalDate lastDayOfWeek = firstWeekStart.plusDays(6);

            Label fromLabel = new Label("From: "+firstWeekStart.format(PeeRecordsDAO.SIMPLE_DATE_FORMAT));

            Label toLabel = new Label("To: "+lastDayOfWeek.format(PeeRecordsDAO.SIMPLE_DATE_FORMAT));

            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);

            vBox.getChildren().addAll(weekLabel, fromLabel, toLabel);

            vBox.getStyleClass().add("week");
            weekLabel.getStyleClass().add("week-label");
            fromLabel.getStyleClass().add("week-date");
            toLabel.getStyleClass().add("week-date");

            GridPane.setHgrow(vBox, Priority.ALWAYS);
            GridPane.setVgrow(vBox, Priority.ALWAYS);
            GridPane.setHalignment(vBox, HPos.CENTER);
            GridPane.setValignment(vBox, VPos.CENTER);
            calendarLayout.add(vBox, i+1, 0);

            firstWeekStart = firstWeekStart.plusDays(7);
        }
    }

    /**
     * Toont dagen van de week in eerste kolom van de gridPane
     * @param calendarLayout
     */
    private void displayDays(GridPane calendarLayout){
        for(int i = 0 ; i < NB_DAYS_IN_WEEK ; i++){
            Label dayLabel = new Label(DAYS_OF_WEEK[i]);
            dayLabel.setMinWidth(100);
            dayLabel.setTextAlignment(TextAlignment.CENTER);
            dayLabel.setAlignment(Pos.CENTER);
            dayLabel.getStyleClass().add("day-label");
            GridPane.setHgrow(dayLabel, Priority.ALWAYS);
            GridPane.setVgrow(dayLabel, Priority.ALWAYS);
            GridPane.setHalignment(dayLabel, HPos.CENTER);
            GridPane.setValignment(dayLabel, VPos.CENTER);
            calendarLayout.add(dayLabel, 0, i+1);
        }
    }

    /**
     * Vul kalender met lege cellen
     * @param calendarLayout
     */
    private void fillEmptyCalendar(GridPane calendarLayout){

        for(int week = 1; week <= NB_WEEKS_IN_CYCLE; week++){

            calendarEntries.put(week, new HashMap<>());

            for(int day = 1; day <= NB_DAYS_IN_WEEK; day++){
                Button calendarCell = new Button();
                calendarCell.getStyleClass().add("calendar-empty-cell");
                calendarCell.getStyleClass().add("calendar-cell");
                calendarCell.setMinSize(90, 60);
                calendarCell.setTooltip(new Tooltip("Click to add new registration or to update existing"));
                calendarCell.setUserData(new CalendarData(day, week, calendarCell, null, patient));

                GridPane.setHalignment(calendarCell, HPos.CENTER);
                GridPane.setValignment(calendarCell, VPos.CENTER);

                GridPane.setVgrow(calendarCell, Priority.ALWAYS);
                GridPane.setHgrow(calendarCell, Priority.ALWAYS);

                calendarLayout.add(calendarCell, week, day);

                calendarEntries.get(week).put(day, calendarCell);

                // Naar registratieform
                calendarCell.setOnAction(event->{

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/RegistrationForm.fxml"));
                        Parent root = fxmlLoader.load();
                        RegistrationForm formController = fxmlLoader.getController();
                        formController.setCalendarCell(calendarCell);
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root, 400, 250));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }


    private void displayPreviousPeeRecords(List<PeeRecords> peeRecordsList){

        Map<Integer, Map<Integer, PeeRecords>> peeRecordsByWeekAndDays = groupRecordsByWeeksAndDays(peeRecordsList);

        peeRecordsByWeekAndDays.keySet().forEach(week->{
            peeRecordsByWeekAndDays.get(week).keySet().forEach(day->{

                System.out.println("Week: "+week+"  day: "+day);
                CalendarData data = (CalendarData) calendarEntries.get(week).get(day).getUserData();
                data.setPeeRecord(peeRecordsByWeekAndDays.get(week).get(day));
            });
        });
    }

    /**
     * @param peeRecordsList
     */
    private Map<Integer, Map<Integer, PeeRecords>> groupRecordsByWeeksAndDays(List<PeeRecords> peeRecordsList){

        /*
         first key : weeknummer ( 0- 12)
         value : lijst van patientenregistraties.
         */
        Map<Integer, Map<Integer, PeeRecords>> peeRecordsByWeekAndDays = new HashMap<>();

        for(PeeRecords record : peeRecordsList){

            int weekDiff = 1+ (int)Math.abs(ChronoUnit.WEEKS.between(record.getPeeDate(), record.getStartDate()));

            if(!peeRecordsByWeekAndDays.containsKey(weekDiff)){
                peeRecordsByWeekAndDays.put(weekDiff, new HashMap<>());
            }

            int dayDiff =  (int)Math.abs(ChronoUnit.DAYS.between(record.getPeeDate(), record.getStartDate()));
            dayDiff = (dayDiff % 7) + 1;

            peeRecordsByWeekAndDays.get(weekDiff).put(dayDiff, record);
        }

        return peeRecordsByWeekAndDays;
    }


    /**
     * Klik op logout, dan naar login
     * @param event
     */
    private void logoutAction(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/PatientLogin.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage)((Hyperlink)event.getSource()).getScene().getWindow();
            stage.hide();
            stage.getScene().setRoot(root);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Patienteninformatie tonen op de kalender
     * @param patient
     */
    public void setAuthenticatedUser(Patient patient){
        this.patient = patient;
        userLabel.setText(patient.getEmail()+" (Start: "+patient.getStartDate().format(PeeRecordsDAO.SIMPLE_DATE_FORMAT)+")");
        layoutCalendar();
    }

    /**
     * save
     * @param event
     */
    private void saveAction(ActionEvent event){

        for (Integer week : calendarEntries.keySet()) {
            for (Integer day : calendarEntries.get(week).keySet()) {
                CalendarData data = (CalendarData) calendarEntries.get(week).get(day).getUserData();

                if(data.deleted && data.peeRecord!= null && data.peeRecord.getId() > 0){
                    PeeRecordsDAO.deletePeeRecord(data.peeRecord);
                }
                else if(data.peeRecord != null && data.peeRecord.getId() == 0){
                    PeeRecordsDAO.savePeeRecord(data.peeRecord);
                }
                else if(data.peeRecord != null && data.peeRecord.getId()>0){
                    PeeRecordsDAO.updatePeeRecord(data.peeRecord);
                }
            }
        }

        Message.alert("Success!", "Calendar updated", "The calendar entries have been saved successfully!");
    }

    /**
     * Kalender Cell data
     *
     */
    public static class CalendarData{

        final int day;


        final int week;
        final String weekLabel;
        final String dayLabel;
        PeeRecords peeRecord;
        final Button layout;
        boolean deleted = false;
        Patient patient;

        public CalendarData(int day, int week, Button layout, PeeRecords data, Patient patient){
            this.day = day;
            this.week = week;
            this.layout = layout;
            this.peeRecord = data;
            this.weekLabel = PatientCalendar.WEEKS_IN_CYCLE[this.week-1];
            this.dayLabel = PatientCalendar.DAYS_OF_WEEK[this.day-1];
            this.patient = patient;
        }

        public void deleteRecord(){
            deleted = true;
            layout.getStyleClass().remove(PatientCalendar.ASSIGNED_CELL);
            layout.getStyleClass().add(PatientCalendar.EMPTY_CELL);
            layout.setText("");
        }

        public void updateRecordType(String recordType){

            layout.getStyleClass().remove(PatientCalendar.EMPTY_CELL);
            layout.getStyleClass().add(PatientCalendar.ASSIGNED_CELL);
            if(this.peeRecord == null){
                this.peeRecord = new PeeRecords(null, null, null);
                peeRecord.setPatientId(patient.getId());
                LocalDate peeDate = patient.getStartDate();
                peeDate = peeDate.plusWeeks(week-1);
                peeDate = peeDate.plusDays(day-1);
                peeRecord.setPeeDate(peeDate);
                peeRecord.setStartDate(patient.getStartDate());
            }

            this.peeRecord.setStatus(recordType);
            layout.setText(this.peeRecord.getStatus());
        }

        public void setPeeRecord(PeeRecords record){

            this.peeRecord = record;
            layout.getStyleClass().remove(PatientCalendar.EMPTY_CELL);
            layout.getStyleClass().add(PatientCalendar.ASSIGNED_CELL);
            layout.setText(this.peeRecord.getStatus());
        }
    }
}
