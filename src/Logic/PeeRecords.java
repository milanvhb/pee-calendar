package Logic;

import java.time.LocalDate;

public class PeeRecords {

    private int id;
    private LocalDate startDate;
    private LocalDate peeDate;
    private String status;
    private int patientId;

    public PeeRecords(LocalDate startDate, LocalDate peeDate, String status) {
        this.startDate = startDate;
        this.peeDate = peeDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getPeeDate() {
        return peeDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setPeeDate(LocalDate peeDate) {
        this.peeDate = peeDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
