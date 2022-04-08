package SchedulingApp.Models;

import SchedulingApp.DAO.DataAccess;
import SchedulingApp.Helpers.Messages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    /**
     * The user Id - generated from the database
     * property name matches DB
     */
    private int User_ID;

    /**
     * UserName of the user
     * property name matches DB
     */
    private String User_Name;

    /**
     * Password for the user
     * property name matches DB
     */
    private String Password;

    /**
     * get user_id
     * @return user_id
     */
    public int getUser_Id() { return this.User_ID; }

    /**
     * set user_id
     */
    public void setUser_Id(int userId) { this.User_ID= userId;}

    /**
     * get userName
     * @return userName
     */
    public String getUser_Name() { return User_Name; }

    /**
     * sets username
     * @param userName new username of the object
     */
    public void setUser_Name(String userName) { this.User_Name= userName;}

    /**
     * gets the password
     * @return the password
     */
    public String getPassword() { return Password; }

    /**
     * set the password
     * @param password the new password
     */
    public void setPassword(String password) { this.Password= password;}

    /////////////////////////////////////

    /**
     * Empty Constructor
     */
    public User() {
        this.User_ID = 0;
        this.User_Name = "";
        this.Password = "";
    }
    /**
     * Constructor with specified values
     */
    public User(String userName, int Id){
        this.User_ID = Id;
        this.User_Name = userName;
    }

    /**
     * Constructor with specified values
     */
    public User(int userId, String userName, String password){
        this.User_ID = userId;
        this.User_Name = userName;
        this.Password = password;
    }

    /////////////////////////////////////

    /**
     * Get all users
     * @return list of users
     */
    public static ObservableList<User> getUsers() {
        ObservableList<User> retVal = FXCollections.observableArrayList();

        try {
            PreparedStatement sql = DataAccess.getConn().prepareStatement("" +
                    "SELECT " +
                    "	User_ID " +
                    "	,User_Name " +
                    "FROM " +
                    "	users "
            );

            ResultSet rs = sql.executeQuery();
            System.out.println("DEBUG sql : " + sql.toString());

            while(rs.next()) {
                User u = new User();

                u.User_Name = rs.getString("User_Name");
                u.User_ID = rs.getInt("User_ID");

                retVal.add(u);
            }
        }catch (Exception e) {
//TODO : Handle Exception
            Messages.ShowUnhandledError(e.getMessage());
            System.out.println(e.getMessage());
        }

        return retVal;
    }

    /**
     * Determines if a user can login, returns the record if they can
     * @return User object if they login - else null
     */
    public static User canLogin(String userName, String password) {
        User retVal = null;

        try {
            PreparedStatement loginSql = DataAccess.getConn().prepareStatement("" +
                    "SELECT User_ID, User_Name, Password " +
                    "FROM users " +
                    "WHERE " +
                    "User_Name = ? " +
                    "AND Password = ? " +
                    "LIMIT 1"); //added limit 1 though the db should have a constraint so no username are identical.

            loginSql.setString(1,userName);
            loginSql.setString(2,password);

            ResultSet rs = loginSql.executeQuery();

            System.out.println("DEBUG - Login " + userName + "  - " + password);

            while(rs.next()) {
                retVal = new User(rs.getInt("User_ID"), rs.getString("User_Name"), rs.getString("Password"));
            }

        }catch (Exception e) {
//TODO : Handle Exception
            Messages.ShowUnhandledError(e.getMessage());
            System.out.println(e.getMessage());
        }

        return retVal;
    }

}
