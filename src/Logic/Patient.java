package Logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Patient {

    private int id;
    private String email;
    private String surName;
    private String firstName;
    private LocalDate startDate;
    private LocalDate dob;
    private String pass;
    private int age;

    public Patient(String email, String surName, String firstName, LocalDate startDate, LocalDate dob, String pass, int age) {
        this.email = email;
        this.surName = surName;
        this.firstName = firstName;
        this.startDate = startDate;
        this.dob = dob;
        this.pass = pass;
        this.age = age;
    }

    public Patient() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getSurName() {
        return surName;
    }

    public String getFirstName() {
        return firstName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getPass() {
        return pass;
    }

    public int getAge() {
        return age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
