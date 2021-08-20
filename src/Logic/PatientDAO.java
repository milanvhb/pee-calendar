package Logic;

import database.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class PatientDAO {


    public static void insertPatient (String email, String surName, String firstName, LocalDate startDate, LocalDate dob, String pass, int age) throws SQLException, ClassNotFoundException {
        String updateStmt =

                        "INSERT INTO patients(" +
                         "firstname,"
                        + "surname,"
                        + "age,"
                        + "dob,"
                        + "registration_date,"
                        + "email,"
                        + "password) "
                        + "VALUES('"
                        + firstName + "','" + surName + "'," + age + ",'" + dob + "','" + startDate+ "','" + email + "', '" + pass + "')";

        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    public static boolean verifyUser(String pass, String userName) throws SQLException, ClassNotFoundException {

        String verifyLogin = "SELECT email, password FROM patients WHERE email = '" + userName + "' AND password = '" + pass + "'";
        ResultSet rs = DBUtil.dbExecuteQuery(verifyLogin);
        if (rs.next()) {
            return true;
        } else {
            return false;
        }

    }


    public static void updatePatientStartDate(String email, LocalDate newStartDate) throws SQLException, ClassNotFoundException {

        String updateStartDate = "UPDATE patients SET registration_date = '" + newStartDate + "' WHERE email = '" + email + "'";
        try {
            DBUtil.dbExecuteUpdate(updateStartDate);

        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    public static ObservableList<Patient> searchPatients () throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM patients";
        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsPatients = DBUtil.dbExecuteQuery(selectStmt);
            //Send ResultSet to the getPatientList method and get patient object
            //Return patient object
            ObservableList<Patient> patientList = getPatientList(rsPatients);
            return patientList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }

    public static ObservableList<Patient> getPatientList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Patient objects
        ObservableList<Patient> patientList = FXCollections.observableArrayList();

        while (rs.next()) {
            Patient patient = new Patient();
            patient.setAge(rs.getInt("age"));
            patient.setFirstName(rs.getString("firstname"));
            patient.setEmail(rs.getString("email"));
            patient.setDob(((java.sql.Timestamp)rs.getObject("dob")).toLocalDateTime().toLocalDate());
            patient.setStartDate(((java.sql.Timestamp)rs.getObject("registration_date")).toLocalDateTime().toLocalDate());
            patient.setSurName(rs.getString("surname"));
            patient.setPass(rs.getString("password"));

            //Add patient to the ObservableList
            patientList.add(patient);
        }
        //return patientList (ObservableList of Patients)
        return patientList;
    }




    public static Patient getPatientByUsernameAndPassword(String userName, String pass) throws SQLException, ClassNotFoundException {


        String selectPatients = "SELECT * FROM patients WHERE email = '" + userName + "' AND password = '" + pass + "'";
        ResultSet rs = DBUtil.dbExecuteQuery(selectPatients);
        if (rs.next()) {

            Patient patient = new Patient();

            patient.setId(rs.getInt("id"));
            patient.setAge(rs.getInt("age"));
            patient.setFirstName(rs.getString("firstname"));
            patient.setEmail(rs.getString("email"));
            patient.setDob(((java.sql.Timestamp)rs.getObject("dob")).toLocalDateTime().toLocalDate());
            patient.setStartDate(((java.sql.Timestamp)rs.getObject("registration_date")).toLocalDateTime().toLocalDate());
            patient.setSurName(rs.getString("surname"));
            patient.setPass(rs.getString("password"));

            return patient;
        } else {
            return null;
        }

    }

}
