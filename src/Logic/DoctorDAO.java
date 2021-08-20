package Logic;

import database.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDAO {


    public static boolean verifyUser(String pass, String userName) throws SQLException, ClassNotFoundException {

        String verifyLogin = "SELECT email, password FROM doctors WHERE email = '" + userName + "' AND password = '" + pass + "'";
        ResultSet rs = DBUtil.dbExecuteQuery(verifyLogin);
        if(rs.next()) {
            return true;
        }
        else {
            return false;
        }
    }
}
