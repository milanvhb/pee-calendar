package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TimeZone;
import com.sun.rowset.CachedRowSetImpl;


public class DBUtil {

    protected static final String DB_URL = "jdbc:mysql://localhost:3306/pee-calendar?serverTimezone=" + TimeZone.getDefault().getID();
    protected static final String USER = "root";
    protected static final String PASSWORD = "project_group40";

    private static Connection conn = null;

    protected static PreparedStatement ps;
    protected static Statement st;
    protected static ResultSet res;


public static void getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("connecting to db");
        conn = DriverManager.getConnection(DB_URL,USER,PASSWORD);
}

    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e){
            throw e;
        }
    }


    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {

        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;
        try {
            //Connect to DB
            getConnection();
            System.out.println("Select statement: " + queryStmt + "\n");
            //Create statement
            stmt = conn.createStatement();
            //Execute select (query) operation
            resultSet = stmt.executeQuery(queryStmt);
            //CachedRowSet Implementation
            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
        //Return CachedRowSet
        return crs;
    }

    public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
        //Declare statement as null
        Statement stmt = null;
        try {
            //Connect to DB
            getConnection();
            //Create Statement
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                //Close statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
    }
}




