package SchedulingApp.Models;

import SchedulingApp.DAO.DataAccess;
import SchedulingApp.Helpers.Messages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Model for the appointments by type and month report
 */
public class AppointmentTypeMonth {
    /**
     * The Appointment Type
     */
    private String AppointmentType;
    /**
     * get The Appointment Type
     */
    public String getAppointmentType() {return this.AppointmentType; }
    /**
     * set The Appointment Type
     */
    public void setAppointmentType(String val) {this.AppointmentType = val;}

    /**
     * The monthof the appointment
     */
    private String MonthOf;
    /**
     * gets The monthof the appointment
     */
    public String getMonthOf() {return this.MonthOf; }
    /**
     * sets The monthof the appointment
     */
    public void setMonthOf(String val) {this.MonthOf = val;}

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
    public static ObservableList<AppointmentTypeMonth> getData() {
        ObservableList<AppointmentTypeMonth> retVal = FXCollections.observableArrayList();

        try {
            PreparedStatement sql = DataAccess.getConn().prepareStatement("" +
                    "SELECT " +
                    "COUNT(Appointment_ID) as Total, Type, MONTH(Start) AS MonthOf " +
                    "FROM appointments GROUP BY Type, MONTH(Start);"
            );

            System.out.println("DEBUG sql : " + sql.toString());
            ResultSet rs = sql.executeQuery();

            while(rs.next()) {
                AppointmentTypeMonth o = new AppointmentTypeMonth();
                o.MonthOf = rs.getString("MonthOf");
                o.Total = rs.getInt("Total");
                o.AppointmentType = rs.getString("Type");
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
