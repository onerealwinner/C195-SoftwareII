package SchedulingApp.Models;

import SchedulingApp.DAO.DataAccess;
import SchedulingApp.Helpers.Messages;
import SchedulingApp.Helpers.MyDate;
import SchedulingApp.Main;
import com.mysql.cj.jdbc.exceptions.MySQLQueryInterruptedException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Standard class for an appointment
 * Matches closely to the database 
 */
public class Appointment {
    /////////////////
    /**
     * The Appointment_ID
     */
    private int Appointment_ID;
    /**
     * get the Appointment_ID
     */
    public int getAppointment_ID() { return Appointment_ID; }
    /**
     * set the Appointment_ID
     */
    public void setAppointment_ID(int val) { this.Appointment_ID = val; }

    /**
     * The Title
     */
    private String Title;
    /**
     * get the Title
     */
    public String getTitle() { return Title; }
    /**
     * set The Title
     */
    public void setTitle(String val) { this.Title = val; }

    /**
     * The Description
     */
    private String Description;
    /**
     * get The Description
     */
    public String getDescription() { return Description; }
    /**
     * set The Description
     */
    public void setDescription(String val) { this.Description = val; }

    /**
     * The Location
     */
    private String Location;
    /**
     * get The Location
     */
    public String getLocation() { return Location; }
    /**
     * set The Location
     */
    public void setLocation(String val) { this.Location = val; }

    /**
     * The Type
     */
    private String Type;
    /**
     * get The Type
     */
    public String getType() { return Type; }
    /**
     * set The Type
     */
    public void setType(String val) { this.Type = val; }

    /**
     * Start Date
     */
    private MyDate Start;
    /**
     * set the Start Date
     */
    public MyDate getStart() { return Start; }
    /**
     * get the Start Date
     */
    public void setStart(MyDate val) { this.Start = val; }
    /**
     * get the Start Date as string used for the property getter in a tableview
     */
    public String getStartAsString() { return Start.ToString(); }

    /**
     * End Date
     */
    private MyDate End;
    /**
     * set the End Date
     */
    public MyDate getEnd() { return End; }
    /**
     * get the End Date
     */
    public void setEnd(MyDate val) { this.End = val; }
    /**
     * get the End Date as string used for the property getter in a tableview
     */
    public String getEndAsString() { return End.ToString(); }

    /**
     * Create_Date of the record
     */
    private MyDate Create_Date;
    /**
     * get the Create_Date of the record
     */
    public MyDate getCreate_Date() { return Create_Date; }
    /**
     * set the Create_Date of the record
     */
    public void setCreate_Date(MyDate val) { this.Create_Date = val; }

    /**
     * Created By of the record
     */
    private String Created_By;
    /**
     * get the Created By of the record
     */
    public String getCreated_By() { return Created_By; }

    /**
     * Last_Update of the record
     */
    private MyDate Last_Update;
    /**
     * get the Last_Update of the record
     */
    public MyDate getLast_Update() { return Last_Update; }


    /**
     * Last_Updated_By of the record
     */
    private String Last_Updated_By;
    /**
     * get the Last_Updated_By of the record
     */
    public String getLast_Updated_By() { return Last_Updated_By; }
    /**
     * set the Last_Updated_By of the record
     */
    public void setLast_Updated_By(String val) { this.Last_Updated_By = val; }

    /**
     * the Contact_Id of this appointment
     */
    private int Contact_ID;
    /**
     * get the Contact_Id of this appointment
     */
    public int getContact_ID() { return Contact_ID; }
    /**
     * set the Contact_Id of this appointment
     */
    public void setContact_ID(int val) { this.Contact_ID = val; }

    /**
     * the Contact_Name for this appointment
     */
    private String Contact_Name;
    /**
     * get the Contact_Name for this appointment
     */
    public String getContact_Name() { return Contact_Name; }
    /**
     * set the Contact_Name for this appointment
     */
    public void setContact_Name(String val) { this.Contact_Name = val; }

    /**
     * the User_ID for this appointment
     */
    private int User_ID;
    /**
     * get the User_ID for this appointment
     */
    public int getUser_ID() { return User_ID; }
    /**
     * set the User_ID for this appointment
     */
    public void setUser_ID(int val) { this.User_ID = val; }

    /**
     * the Customer_Id for this appointment
     */
    private int Customer_ID;
    /**
     * get the Customer_Id for this appointment
     */
    public int getCustomer_ID() { return Customer_ID; }
    /**
     * set the Customer_Id for this appointment
     */
    public void setCustomer_ID(int val) { this.Customer_ID = val; }

    /**
     * a Customer object for the appointment
     */
    private Customer AppointmentCustomer;
    /**
     * get the Customer object for the appointment
     */
    public Customer getAppointmentCustomer() { return AppointmentCustomer; }
    /**
     * set the Customer object for the appointment
     */
    public void setAppointmentCustomer(Customer val) { this.AppointmentCustomer = val; }

    /**
     * a contact object for the appointment
     */
    private Contact AppointmentContact;
    /**
     * get the contact object for the appointment
     */
    public Contact getAppointmentContact() { return AppointmentContact; }
    /**
     * set the  contact object for the appointment
     */
    public void setAppointmentContact(Contact val) { this.AppointmentContact = val; }

    /**
     * a user object for the appointment
     */
    private User AppointmentUser;
    /**
     * get the user object for the appointment
     */
    public User getAppointmentUser() { return AppointmentUser; }
    /**
     * set the user object for the appointment
     */
    public void setAppointmentUser(User val) { this.AppointmentUser = val; }

    /**
     * Gets the week of the appointment
     * @return the week of the appointment
     */
    public int getWeekOf () {
        TemporalField weeks = WeekFields.of(Locale.getDefault()).weekBasedYear();
        return Start.getDateOf().get(weeks);
    }

    /**
     * Gets the month of the appointment
     * @return the month of the appointment
     */
    public int getMonthOf () {
        return Start.getDateOf().getMonthValue();
    }
    //////////////////

    /**
     * A basic all inclusive query for getting appointment information
     */
    private static final String AppointmentSelectAll = "" +
            "SELECT " +
            "	a.Appointment_ID " +
            "	,a.Title " +
            "	,a.Description " +
            "	,a.Location " +
            "	,a.Type " +
            "	,a.Start " +
            "	,a.End " +
            "	,a.Create_Date " +
            "	,a.Created_By " +
            "	,a.Last_Update " +
            "	,a.Last_Updated_By " +
            "	,a.Customer_ID " +
            "	,a.User_ID " +
            "	,a.Contact_ID " +
            "   ,c.Contact_Name " +
            "   ,c.Email " +
            "   ,u.User_Name " +
            "	,cust.Customer_Name " +
            "FROM  " +
            "	WJ08SwU.appointments AS a " +
            "LEFT JOIN WJ08SwU.contacts AS c " +
            "	ON a.Contact_ID = c.Contact_ID " +
            "LEFT JOIN WJ08SwU.customers AS cust " +
            "	ON a.Customer_ID = cust.Customer_ID " +
            "LEFT JOIN WJ08SwU.users AS u " +
            "	ON a.User_ID = u.User_ID";

    /**
     * saves the appointment record
     * @return
     */
    public boolean Save() {
        if(this.Appointment_ID > 0) {
            return Update();
        }else {
            return Insert();
        }
    }

    /**
     * Runs the insert from the save method
     */
    private boolean Insert() {
        try {
            PreparedStatement sql = DataAccess.getConn().prepareStatement("" +
                    "INSERT INTO appointments " +
                    "(Title, " +
                    "Description, " +
                    "Location, " +
                    "Type, " +
                    "Start, " +
                    "End, " +
                    "Created_By, " +
                    "Last_Updated_By, " +
                    "Customer_ID, " +
                    "User_ID, " +
                    "Contact_ID) " +
                    "VALUES " +
                    "(?,?,?,?,?,?,?,?,?,?,?)"
            );

            sql.setString(1, this.Title);
            sql.setString(2, this.Description);
            sql.setString(3, this.Location);
            sql.setString(4, this.Type);
            sql.setString(5, this.Start.ToUTCDateString());
            sql.setString(6, this.End.ToUTCDateString());
            sql.setString(7, Main.LoggedInUser.getUser_Name());
            sql.setString(8, Main.LoggedInUser.getUser_Name());
            sql.setInt(9, this.Customer_ID);
            sql.setInt(10, this.User_ID);
            sql.setInt(11, this.Contact_ID);

            sql.execute();

            PreparedStatement sql1 = DataAccess.getConn().prepareStatement("SELECT LAST_INSERT_ID() AS MyID;");
            ResultSet rs =  sql1.executeQuery();

            while(rs.next()) {
                this.Appointment_ID = rs.getInt("MyID");
            }
            return true;
        }catch (Exception e) {
            //TODO : Handle Exception
            Messages.ShowUnhandledError(e.getMessage());
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Runs the update from the save method
     */
    private boolean Update() {
        try {
            PreparedStatement sql = DataAccess.getConn().prepareStatement("" +
                    "UPDATE appointments " +
                    "SET " +
                    "Title = ?, " +
                    "Description = ?, " +
                    "Location = ?, " +
                    "Type = ?, " +
                    "Start = ?, " +
                    "End = ?, " +
                    "Last_Updated_By = ?, " +
                    "Customer_ID = ?, " +
                    "User_ID = ?, " +
                    "Contact_ID = ? " +
                    "WHERE Appointment_ID = ?; "
            );

            sql.setString(1, this.Title);
            sql.setString(2, this.Description);
            sql.setString(3, this.Location);
            sql.setString(4, this.Type);
            sql.setString(5, this.Start.ToUTCDateString());
            sql.setString(6, this.End.ToUTCDateString());
            sql.setString(7, Main.LoggedInUser.getUser_Name());
            sql.setInt(8, this.Customer_ID);
            sql.setInt(9, this.User_ID);
            sql.setInt(10, this.Contact_ID);
            sql.setInt(11, this.Appointment_ID);

            System.out.println("DEBUG sql : " + sql.toString());
            int updateCount = sql.executeUpdate();
            return true;

        }catch (Exception e) {
            //TODO : Handle Exception
            Messages.ShowUnhandledError(e.getMessage());
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Delete an appointment record
     */
    public boolean Delete() {
        try {

            PreparedStatement sql = DataAccess.getConn().prepareStatement("" +
                    "DELETE FROM appointments " +
                    "WHERE Appointment_ID = ? "
            );

            sql.setInt(1, this.Appointment_ID );

            return sql.execute();

        }catch (Exception e) {
            //TODO : Handle Exception
            Messages.ShowUnhandledError(e.getMessage());
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * gets a list of appointment for a specific week
     * @param weekOf the week to search for
     */
    public static ObservableList<Appointment> getAppointmentsByWeek(int weekOf) {
        return getAppointmentsByWeekOrMonth("WEEK",weekOf);
    }

    /**
     * gets a list of appointment for a specific month
     * @param monthOf the month to search for
     */
    public static ObservableList<Appointment> getAppointmentsByMonth(int monthOf) {
        return getAppointmentsByWeekOrMonth("MONTH",monthOf);
    }

    /**
     * Runs the method for the getAppointmentsByWeek and getAppointmentsByMonth functions
     */
    private static ObservableList<Appointment> getAppointmentsByWeekOrMonth(String WeekOrMonth, int filterBy) {
        ObservableList<Appointment> retVal = FXCollections.observableArrayList();

        String query = AppointmentSelectAll;
        query += " WHERE " + WeekOrMonth + "(Start) = ? ";

        try {
            PreparedStatement sql = DataAccess.getConn().prepareStatement(query);

            sql.setInt(1, filterBy);

            System.out.println("DEBUG sql : " + sql.toString());
            ResultSet rs = sql.executeQuery();

            retVal = getDataFromResultSet(rs);

        }catch (Exception e) {
//TODO : Handle Exception
            Messages.ShowUnhandledError(e.getMessage());
            System.out.println("Get appointments error; " + e.getMessage());
        }
        return retVal;
    }

    /**
     * Gets all the appointments
     * @return a list of appointments
     */
    private static ObservableList<Appointment> getAppointments(String filterByWeekOrMonth, int filterBy) {
        ObservableList<Appointment> retVal = FXCollections.observableArrayList();

        String query = AppointmentSelectAll;

        if(filterByWeekOrMonth!=null) {
            if(filterByWeekOrMonth.equals("MONTH")) {
                query += " WHERE MONTH(Start) = ? ";
            }else if (filterByWeekOrMonth.equals("WEEK")) {
                query += " WHERE WEEK(Start) = ? ";
            }
        }

        try {
            PreparedStatement sql = DataAccess.getConn().prepareStatement(query);

            if(filterByWeekOrMonth!=null) {
                sql.setInt(1, filterBy );
            }

            System.out.println("DEBUG sql : " + sql.toString());
            ResultSet rs = sql.executeQuery();

            retVal = getDataFromResultSet(rs);

        }catch (Exception e) {
//TODO : Handle Exception
            Messages.ShowUnhandledError(e.getMessage());
            System.out.println("Get appointments error; " + e.getMessage());
        }

        return retVal;
    }

    /**
     * Gets all appointments within 15 minutes
     */
    public static ObservableList<Appointment> getAppointmentsInNext15Minutes(int userId) {
        ObservableList<Appointment> retVal = FXCollections.observableArrayList();
        MyDate now = new MyDate();
        MyDate before = new MyDate(now.getDateOf().plusMinutes(-15));
        MyDate later =  new MyDate(now.getDateOf().plusMinutes(15));

        String query = AppointmentSelectAll;
        query += " WHERE a.User_ID = ? AND (Start > ? AND Start < ?) ";

        try {
            PreparedStatement sql = DataAccess.getConn().prepareStatement(query);

            sql.setInt(1, userId);
            sql.setString(2, before.ToUTCDateString());
            sql.setString(3, later.ToUTCDateString());

            System.out.println("DEBUG sql : " + sql.toString());
            ResultSet rs = sql.executeQuery();

            retVal = getDataFromResultSet(rs);

        }catch (Exception e) {
//TODO : Handle Exception
            Messages.ShowUnhandledError(e.getMessage());
            System.out.println("Get appointments error; " + e.getMessage());
        }
        return retVal;
    }

    /**
     * Gets all the appointments
     */
    public static ObservableList<Appointment> getAllAppointments() {
        String query = AppointmentSelectAll;

        try {
            PreparedStatement sql = DataAccess.getConn().prepareStatement(query);

            System.out.println("DEBUG sql : " + sql.toString());
            ResultSet rs = sql.executeQuery();

            return getDataFromResultSet(rs);

        }catch (Exception e) {
//TODO : Handle Exception
            Messages.ShowUnhandledError(e.getMessage());
            System.out.println("Get appointments error; " + e.getMessage());
            return null;
        }
    }

    /**
     * runs the result set and gets the appointment data
     */
    private static ObservableList<Appointment> getDataFromResultSet (ResultSet rs) {
        ObservableList<Appointment> retVal = FXCollections.observableArrayList();
        try{
            while(rs.next()) {
                Appointment c = new Appointment();

                c.Appointment_ID = rs.getInt("Appointment_ID");

                c.Title = rs.getString("Title");
                c.Description = rs.getString("Description");
                c.Location = rs.getString("Location");

                c.Contact_ID = rs.getInt("Contact_ID");
                c.Contact_Name = rs.getString("Contact_Name");
                c.AppointmentContact = new Contact(c.Contact_Name,rs.getString("Email"),c.Contact_ID);

                c.Type = rs.getString("Type");
                c.Customer_ID = rs.getInt("Customer_ID");
                c.AppointmentCustomer = new Customer(rs.getString("Customer_Name"), c.Customer_ID);
                c.User_ID = rs.getInt("User_ID");
                c.AppointmentUser = new User(rs.getString("User_Name"), c.User_ID);

                c.Last_Updated_By = rs.getString("Last_Updated_By");
                c.Created_By = rs.getString("Created_By");

                c.Create_Date = MyDate.MyDateFromUTCDB(rs.getString("Create_Date"));
                c.Last_Update=  MyDate.MyDateFromUTCDB(rs.getString("Last_Update"));
                c.Start = MyDate.MyDateFromUTCDB(rs.getString("Start"));
                c.End =  MyDate.MyDateFromUTCDB(rs.getString("End"));

                retVal.add(c);
            }
        }catch (Exception e) {
            //TODO : Handle Exception
            Messages.ShowUnhandledError(e.getMessage());
            System.out.println("Get appointments error; " + e.getMessage());
        }
        return retVal;
    }

    /**
     * Checks if a date or time would overlap
     * @return a list of appointments
     */
    public static Boolean isOverlappingAppointment(int AppointmentId, int CustomerId, MyDate startDate, MyDate endDate) {
        Boolean retVal = false;

        try {
            PreparedStatement sql = DataAccess.getConn().prepareStatement("" +
                    "SELECT " +
                    "	Appointment_ID  " +
                    "FROM  " +
                    "	appointments  " +
                    "WHERE " +
                    "   Appointment_ID <> ? " +
                    "	AND Customer_ID = ? " +
                    "	AND (Start < ? " +
                    "	AND End >  ? )"
            );

            sql.setInt(1, AppointmentId );
            sql.setInt(2, CustomerId );
            sql.setString(3, endDate.ToUTCDateString() );
            sql.setString(4, startDate.ToUTCDateString() );

            System.out.println("DEBUG sql : " + sql.toString());
            ResultSet rs = sql.executeQuery();

            while(rs.next()) {
                retVal = true;
            }
        }catch (Exception e) {
//TODO : Handle Exception
            Messages.ShowUnhandledError(e.getMessage());
            System.out.println("getAppointmentsBy error; " + e.getMessage());
            retVal = true;
        }

        return retVal;
    }

}
