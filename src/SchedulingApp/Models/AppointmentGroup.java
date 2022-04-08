package SchedulingApp.Models;

import SchedulingApp.DAO.DataAccess;
import SchedulingApp.Helpers.Messages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Appointment Aggregation by week or Month class
 */
public class AppointmentGroup {

    /**
     * Holds the filterby can be either Week or MONTH
     * FUTURE ENHANCEMENT make these values an ENUM
     */
    private int FilterBy;
    /**
     * gets the filterby
     */
    public int getFilterBy() {return this.FilterBy; }
    /**
     * sets the filterby
     */
    public void setFilterBy(int val) {this.FilterBy = val;}

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

    /////////////////


    /**
     * Gets appointments grouped by a week or month
     * @return a list of appointments
     */
    public static ObservableList<AppointmentGroup> getAppointmentsBy(String WeekOrMonth) {
        ObservableList<AppointmentGroup> retVal = FXCollections.observableArrayList();

        try {
            PreparedStatement sql = DataAccess.getConn().prepareStatement("" +
                    "SELECT " + WeekOrMonth + "(Start) as Start, COUNT(Appointment_ID) as Total FROM appointments GROUP BY " + WeekOrMonth + "(Start);"
            );

            System.out.println("DEBUG sql : " + sql.toString());
            ResultSet rs = sql.executeQuery();

            while(rs.next()) {
                AppointmentGroup o = new AppointmentGroup();
                o.setFilterBy(rs.getInt("Start"));
                o.setTotal(rs.getInt("Total"));
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