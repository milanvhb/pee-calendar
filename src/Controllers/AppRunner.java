package Controllers;

import database.DBUtil;
import java.sql.SQLException;


public class AppRunner {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DBUtil.getConnection();
    }

}
