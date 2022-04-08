package SchedulingApp.DAO;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Data Access Helper
 */
public class DataAccess {

    /**
     * JDBC Url
     */
    private static final String _ConnString = "jdbc:mysql://wgudb.ucertify.com:3306/WJ08SwU";

    /**
     * MYSQL Driver
     */
    private static final String MYSQLJDBCDriver = "com.mysql.jbc.Driver";

    /**
     * UserName for connection
     */
    private static final String UserName = "U08SwU";

    /**
     * Password for connection
     */
    private static final String Password = "53689380338";

    /**
     * The connection object
     */
    private static Connection conn = null;

    /**
     * Get the connection
     */
    public  static Connection getConn() { return conn; }

    ////////////////////////////////////////

    /**
     * Opens the connection to the database
     */
    public static void openConnection() throws SQLException {
        try {
            conn = DriverManager.getConnection(_ConnString + "?connectionTimeZone=SERVER&user=" + UserName + "&password=" + Password);
            System.out.println("DEBUG - DB Open"); //Debug message

        }catch(Exception e) {
            //TODO : Handle exception
            throw e;
        }
    }

    /**
     * Closes the connection to the database
     */
    public static void closeConnection() {
        try{
            conn.close();
            System.out.println("DEBUG - DB Closed"); //Debug message
        }catch (Exception e)
        {
            //TODO : Handle exception
        }
    }


}
