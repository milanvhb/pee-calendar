package Logic;

import database.DBException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Dokter {

    private String email;
    private String surName;
    private String name;
    private LocalDate startDate;
    private String pass;

    public Dokter(String email, String surName, String name, LocalDate startDate, String pass) {
        this.email = email;
        this.surName = surName;
        this.name = name;
        this.startDate = startDate;
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public String getSurName() {
        return surName;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getPass() {
        return pass;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
