package SchedulingApp.Models;

import SchedulingApp.DAO.DataAccess;
import SchedulingApp.Helpers.Messages;
import SchedulingApp.Helpers.UserMessages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Model from Contact - closely matches the database
 */
public class Contact {
    /////////////////////////////
    /**
     * holds contact_ID
     */
    private int Contact_ID;
    /**
     * gets contact_ID
     */
    public int getContact_ID() { return Contact_ID; }
    /**
     * sets contact_ID
     */
    public void setContact_ID(int val) { this.Contact_ID = val; }

    /**
     * holds contact_name
     */
    private String Contact_Name;
    /**
     * gets contact_name
     */
    public String getContact_Name() { return Contact_Name; }
    /**
     * sets contact_name
     */
    public void setContact_Name(String val) { this.Contact_Name = val; }

    /**
     * holds the contacts email address
     */
    private String Email;
    /**
     * gets the contacts email address
     */
    public String getEmail() { return Email; }
    /**
     * sets the contacts email address
     */
    public void setEmail(String val) { this.Email = val; }
    /////////////////////////////

    /**
     * Empty Constructor
     */
    public Contact() {}

    /**
     * Constructor
     */
    public Contact(String name, String email, int Id) {
        this.Contact_Name = name;
        this.Contact_ID = Id;
        this.Email = email;
    }

    /////////////////////////////
    /**
     * Get a single Contact
     * @param Contact_ID
     * @return a contact if found
     */
    public static Contact getSingleContact(int Contact_ID) {
        ObservableList<Contact> retVal = getContacts(Contact_ID);

        if(retVal.size() > 0) {
            return retVal.get(0);
        }else {
            Messages.ShowError(UserMessages.NotFound);
            return null;
        }
    }

    /**
     * Gets all the contacts
     * @return all contacts
     */
    public static ObservableList<Contact> getAllContacts() {
        return getContacts(null);
    }

    /**
     * generic for getting contacts
     * @param Contact_ID
     * @return list of contacts
     */
    private static ObservableList<Contact> getContacts(Integer Contact_ID) {
        ObservableList<Contact> retVal = FXCollections.observableArrayList();

        try {
            PreparedStatement sql = DataAccess.getConn().prepareStatement("" +
                    "SELECT " +
                    "	Contact_ID " +
                    "	,Contact_Name " +
                    "	,Email " +
                    "FROM " +
                    "	contacts " +
                    "WHERE " +
                    "	Contact_ID = COALESCE(?, Contact_ID);"
            );

            sql.setObject(1, Contact_ID != null ? Contact_ID : null );

            ResultSet rs = sql.executeQuery();
            System.out.println("DEBUG sql : " + sql.toString());

            while(rs.next()) {
                Contact c = new Contact();

                c.Contact_Name = rs.getString("Contact_Name");
                c.Contact_ID = rs.getInt("Contact_ID");
                c.Email = rs.getString("Email");

                retVal.add(c);
            }
        }catch (Exception e) {
//TODO : Handle Exception
            Messages.ShowUnhandledError(e.getMessage());
            System.out.println(e.getMessage());
        }

        return retVal;
    }

}
