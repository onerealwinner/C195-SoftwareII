package SchedulingApp.Models;

import SchedulingApp.DAO.DataAccess;
import SchedulingApp.Helpers.Messages;
import SchedulingApp.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

/**
 * Closs Model from Customer - closely matches db
 */
public class Customer {

/////////////////////////
    /**
     * holds Customer_ID
     */
    private int Customer_ID;
    /**
     * gets Customer_ID
     */
    public int getCustomer_ID() { return Customer_ID; }
    /**
     * sets Customer_ID
     */
    public void setCustomer_ID(int val) { this.Customer_ID = val; }

    /**
     * holds Customer_Name
     */
    private String Customer_Name;
    /**
     * gets Customer_Name
     */
    public String getCustomer_Name() { return Customer_Name; }
    /**
     * sets Customer_Name
     */
    public void setCustomer_Name(String val) { this.Customer_Name = val; }

    /**
     * holds Address
     */
    private String Address;
    /**
     * gets Address
     */
    public String getAddress() { return Address; }
    /**
     * sets Address
     */
    public void setAddress(String val) { this.Address = val; }

    /**
     * holds Postal_Code
     */
    private String Postal_Code;
    /**
     * gets Postal_Code
     */
    public String getPostal_Code() { return Postal_Code; }
    /**
     * sets Postal_Code
     */
    public void setPostal_Code(String val) { this.Postal_Code = val; }

    /**
     * holds Phone
     */
    private String Phone;
    /**
     * gets Phone
     */
    public String getPhone() { return Phone; }
    /**
     * sets Phone
     */
    public void setPhone(String val) { this.Phone = val; }

    /**
     * holds Create_Date
     */
    private Date Create_Date;
    /**
     * gets Create_Date
     */
    public Date getCreate_Date() { return Create_Date; }
    /**
     * sets Create_Date
     */
    public void setCreate_Date(Date val) { this.Create_Date = val; }

    /**
     * holds Created_By
     */
    private String Created_By;
    /**
     * gets Created_By
     */
    public String getCreated_By() { return Created_By; }
    /**
     * sets Created_By
     */
    public void setCreated_By(String val) { this.Created_By = val; }

    /**
     * holds Last_Update
     */
    private Date Last_Update;
    /**
     * gets Last_Update
     */
    public Date getLast_Update() { return Last_Update; }
    /**
     * sets Last_Update
     */
    public void setLast_Update(Date val) { this.Last_Update = val; }

    /**
     * holds Last_Updated_By
     */
    private String Last_Updated_By;
    /**
     * gets Last_Updated_By
     */
    public String getLast_Updated_By() { return Last_Updated_By; }
    /**
     * sets Last_Updated_By
     */
    public void setLast_Updated_By(String val) { this.Last_Updated_By = val; }

    /**
     * holds Division_ID
     */
    private int Division_ID;
    /**
     * gets Division_ID
     */
    public int getDivision_ID() { return Division_ID; }
    /**
     * sets Division_ID
     */
    public void setDivision_ID(int val) { this.Division_ID = val; }

    /**
     * holds Division
     */
    private String Division;
    /**
     * gets Division
     */
    public String getDivision() { return Division; }
    /**
     * sets Division
     */
    public void setDivision(String val) { this.Division = val; }

    /**
     * holds Country_ID
     */
    private int Country_ID;
    /**
     * gets Country_ID
     */
    public int getCountry_ID() { return Country_ID; }
    /**
     * sets Country_ID
     */
    public void setCountry_ID(int val) { this.Country_ID = val; }

    /**
     * holds Country
     */
    private String Country;
    /**
     * gets Country
     */
    public String getCountry() { return Country; }
    /**
     * sets Country
     */
    public void setCountry(String val) { this.Country = val; }
    
/////////////////////////

    /**
     * Empty Constructor
     */
    public  Customer() {
        this.Customer_ID = 0;
    }

    /**
     * Basic customer constructor
     */
    public  Customer(String name, int id) {
        this.Customer_ID = id;
        this.Customer_Name = name;
    }

    /////////////////////////////////////

    /**
     * saves the customer record
     * Special thanks to https://stackoverflow.com/questions/17112852/get-the-new-record-primary-key-id-from-mysql-insert-query
     * @return
     */
    public boolean Save() {
        if(this.Customer_ID > 0) {
            return Update();
        }else {
            return Insert();
        }
    }

    /**
     * runs the insert from the save function
     * @return true if inserted
     */
    private boolean Insert() {
        try {
            PreparedStatement sql = DataAccess.getConn().prepareStatement("" +
                    "INSERT INTO customers " +
                    "(Customer_Name," +
                    "Address," +
                    "Postal_Code," +
                    "Phone," +
                    "Created_By," +
                    "Last_Updated_By," +
                    "Division_ID) " +
                    " VALUES " +
                    " (?,?,?,?,?,?,?) ;"
            );

            sql.setString(1, this.Customer_Name);
            sql.setString(2, this.Address);
            sql.setString(3, this.Postal_Code);
            sql.setString(4, this.Phone);
            sql.setString(5, Main.LoggedInUser.getUser_Name());
            sql.setString(6, Main.LoggedInUser.getUser_Name());
            sql.setInt(7, this.Division_ID);

            sql.execute();

            PreparedStatement sql1 = DataAccess.getConn().prepareStatement("SELECT LAST_INSERT_ID() AS MyID;");
            ResultSet rs =  sql1.executeQuery();

            while(rs.next()) {
                this.Customer_ID = rs.getInt("MyID");
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
     * runs the update from the save
     * @return true if updated
     */
    private boolean Update() {
        try {
            PreparedStatement sql = DataAccess.getConn().prepareStatement("" +
                    "UPDATE customers " +
                    " SET " +
                    "Customer_Name = ? " +
                    ",Address = ? " +
                    ",Postal_Code = ? " +
                    ",Phone = ? " +
                    ",Last_Updated_By = ? " +
                    ",Division_ID = ? " +
                    "WHERE Customer_ID = ? "
            );

            sql.setString(1, this.Customer_Name);
            sql.setString(2, this.Address);
            sql.setString(3, this.Postal_Code);
            sql.setString(4, this.Phone);
            sql.setString(5, Main.LoggedInUser.getUser_Name());
            sql.setInt(6, this.Division_ID);
            sql.setInt(7, this.Customer_ID);

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
     * Delete the customer record
     * @return true if deleted
     */
    public boolean Delete() {
        try {

            PreparedStatement sql = DataAccess.getConn().prepareStatement("" +
                    "DELETE FROM customers " +
                    "WHERE Customer_ID = ? "
            );

            sql.setInt(1, this.Customer_ID );

            return sql.execute();

        }catch (Exception e) {
            //TODO : Handle Exception
            Messages.ShowUnhandledError(e.getMessage());
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Gets all the customers
     * @return a list of customers
     */
    public static ObservableList<Customer> getCustomers() {
        ObservableList<Customer> retVal = FXCollections.observableArrayList();

        try {
            PreparedStatement sql = DataAccess.getConn().prepareStatement("" +
                    "SELECT " +
                    "	c.Customer_ID," +
                    "	c.Customer_Name," +
                    "	c.Address," +
                    "	c.Postal_Code," +
                    "	c.Phone," +
                    "	c.Create_Date," +
                    "	c.Created_By," +
                    "	c.Last_Update," +
                    "	c.Last_Updated_By," +
                    "	c.Division_ID," +
                    "	d.Division," +
                    "	d.Country_ID," +
                    "	cc.Country " +
                    "FROM " +
                    "	customers AS c " +
                    "   LEFT JOIN first_level_divisions AS d " +
                    "		ON c.Division_ID = d.Division_ID " +
                    "   LEFT JOIN countries AS cc " +
                    "		ON d.Country_ID = cc.Country_ID;"
            );

            ResultSet rs = sql.executeQuery();

            while(rs.next()) {
                Customer c = new Customer();

                c.Customer_ID = rs.getInt("Customer_ID");
                c.Customer_Name = rs.getString("Customer_Name");
                c.Address = rs.getString("Address");
                c.Postal_Code = rs.getString("Postal_Code");
                c.Phone = rs.getString("Phone");
                c.Create_Date = rs.getDate("Create_Date");
                c.Created_By = rs.getString("Created_By");
                c.Last_Update = rs.getDate("Last_Update");
                c.Last_Updated_By = rs.getString("Last_Updated_By");
                c.Division_ID = rs.getInt("Division_ID");
                c.Division = rs.getString("Division");
                c.Country_ID = rs.getInt("Country_ID");
                c.Country = rs.getString("Country");

                retVal.add(c);
            }
        }catch (Exception e) {
//TODO : Handle Exception
            Messages.ShowUnhandledError(e.getMessage());
            System.out.println(e.getMessage());
        }

        return retVal;
    }

    /**
     * check if a customer has appointments
     * @return true if has appointments or errors
     */
    public boolean hasAppointments() {
        boolean retVal = true;
        try {

            PreparedStatement sql = DataAccess.getConn().prepareStatement("" +
                    "SELECT Appointment_ID " +
                    "FROM appointments " +
                    "WHERE Customer_ID = ? "
            );

            sql.setInt(1, this.Customer_ID );

            ResultSet rs = sql.executeQuery();

            if (!rs.next() ) {
                retVal = false;
            }

            return retVal;

        }catch (Exception e) {
            //TODO : Handle Exception
            Messages.ShowUnhandledError(e.getMessage());
            System.out.println(e.getMessage());
            return true;
        }
    }


    /////////////////////////////////////


}
