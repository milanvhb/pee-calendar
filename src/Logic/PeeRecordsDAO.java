package Logic;

import database.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PeeRecordsDAO {

    public static DateTimeFormatter SIMPLE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static List<PeeRecords> getPeeRecordsByPatientAndStartDate(int patientId, LocalDate startDate) throws SQLException, ClassNotFoundException {

        String query = "SELECT * FROM pee_records WHERE start_date = ':sdate' AND patient_id = :patientId";

        query = query
                .replace(":sdate", startDate.format(SIMPLE_DATE_FORMAT))
                .replace(":patientId", patientId+"");

        ResultSet rs = DBUtil.dbExecuteQuery(query);

        List<PeeRecords> peeRecords = new ArrayList<>();

        while(rs.next()) {

            PeeRecords record = new PeeRecords(null, null, null);
            record.setStatus(rs.getString("status"));
            record.setPeeDate(((java.sql.Timestamp)rs.getObject("pee_date")).toLocalDateTime().toLocalDate());
            record.setStartDate(((java.sql.Timestamp)rs.getObject("start_date")).toLocalDateTime().toLocalDate());
            record.setId(rs.getInt("id"));
            record.setPatientId(rs.getInt("patient_id"));
            peeRecords.add(record);
        }

        return peeRecords;
    }

    public static void deletePeeRecord(PeeRecords record){

        String deleteStmt = "DELETE FROM pee_records WHERE id = " +record.getId();

        try {
            DBUtil.dbExecuteUpdate(deleteStmt);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.print("Error occurred while Delete Operation: " + e);
        }
    }


    public static void updatePeeRecord(PeeRecords record){

        String s = "UPDATE pee_records SET start_date = '%s', pee_date ='%s', status= '%s' WHERE id = %d";
        String updateStmt = String.format(s, record.getStartDate().format(SIMPLE_DATE_FORMAT), record.getPeeDate().format(SIMPLE_DATE_FORMAT), record.getStatus(), record.getId());

        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.print("Error occurred while Update Operation: " + e);
        }
    }


    public static void savePeeRecord(PeeRecords record){

        String s = " INSERT INTO pee_records(start_date, patient_id, pee_date, status) VALUES('%s', '%d', '%s', '%s')";
        String updateStmt = String.format(s, record.getStartDate().format(SIMPLE_DATE_FORMAT), record.getPatientId(), record.getPeeDate().format(SIMPLE_DATE_FORMAT),record.getStatus() );

        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.print("Error occurred while Inserting peeRecord Operation: " + e);
        }
    }
}

