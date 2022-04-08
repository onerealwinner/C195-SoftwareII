package SchedulingApp.Models;

import SchedulingApp.DAO.DataAccess;
import SchedulingApp.Helpers.Messages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Model for Appointment by country report
 */
public class AppointmentCountry {
    /**
     * Holds the country value
     */
    private String Country;
    /**
     * gets the country value
     */
    public String getCountry() {return this.Country; }
    /**
     * sets the country value
     */
    public void setCountry(String val) {this.Country = val;}

    /**
     * Holds the total
     */
    private int Total;
    /**
     * gets the total
     */
    public int getTotal() {return this.Total;}
    /**
     * sets the total
     */
    public void setTotal(int val) {this.Total = val;}
    //////////////////////////


    /**
     * Gets total appointments by type and month
     * @return a list of appointments
     */
    public static ObservableList<AppointmentCountry> getData() {
        ObservableList<AppointmentCountry> retVal = FXCollections.observableArrayList();

        try {
            PreparedStatement sql = DataAccess.getConn().prepareStatement("" +
                    "SELECT " +
                    "    cc.Country, COUNT(Appointment_ID) AS TOTAL " +
                    "FROM " +
                    "    appointments a " +
                    "    LEFT JOIN customers c " +
                    "        ON a.Customer_ID = c.Customer_ID " +
                    "    LEFT JOIN first_level_divisions f " +
                    "        ON c.Division_ID = f.Division_ID " +
                    "    LEFT JOIN countries cc " +
                    "        ON f.COUNTRY_ID = cc.Country_ID " +
                    "GROUP BY " +
                    "   cc.Country;"
            );

            System.out.println("DEBUG sql : " + sql.toString());
            ResultSet rs = sql.executeQuery();

            while(rs.next()) {
                AppointmentCountry o = new AppointmentCountry();
                o.Total = rs.getInt("Total");
                o.Country = rs.getString("Country");
                retVal.add(o);
            }
        }catch (Exception e) {
//TODO : Handle Exception
            Messages.ShowUnhandledError(e.getMessage());
            System.out.println("getAppointmentsBy error; " + e.getMessage());
        }

        return retVal;
    }

}
