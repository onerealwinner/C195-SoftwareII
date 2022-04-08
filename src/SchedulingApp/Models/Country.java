package SchedulingApp.Models;

import SchedulingApp.DAO.DataAccess;
import SchedulingApp.Helpers.Messages;
import SchedulingApp.Helpers.UserMessages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

/**
 * Class model country - closely matches DB
 * FUTURE ENHANCEMENT - allow viewing and modifying this data in a separate form
 */
public class Country {
    //////////////////////////////////////////////////
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
//////////////////////////////////////////////////

    /**
     * Get a single Country
     * @param Country_ID
     * @return a country if found
     */
    public static Country getSingleCountry(int Country_ID) {
        ObservableList<Country> retVal = getCountries(Country_ID);

        if(retVal.size() > 0) {
            return retVal.get(0);
        }else {
            Messages.ShowError(UserMessages.NotFound);
            return null;
        }
    }

    /**
     * Gets all the countries
     * @return all countries
     */
    public static ObservableList<Country> getAllCountries() {
        return getCountries(null);
    }

    /**
     * generic for getting countries
     * @param Country_ID
     * @return list of countries
     */
    private static ObservableList<Country> getCountries(Integer Country_ID) {
        ObservableList<Country> retVal = FXCollections.observableArrayList();

        try {
            PreparedStatement sql = DataAccess.getConn().prepareStatement("" +
                    "SELECT " +
                    "	cc.Country_ID " +
                    "	,cc.Country " +
                    "	,cc.Create_Date " +
                    "	,cc.Created_By " +
                    "	,cc.Last_Update " +
                    "	,cc.Last_Updated_By " +
                    "FROM " +
                    "	countries as cc " +
                    "WHERE " +
                    "	cc.Country_ID = COALESCE(?, cc.Country_ID);"
            );

            sql.setObject(1, Country_ID != null ? String.valueOf(Country_ID) : null );

            ResultSet rs = sql.executeQuery();
            System.out.println("DEBUG sql : " + sql.toString());

            while(rs.next()) {
                Country cc = new Country();

                cc.Country = rs.getString("Country");
                cc.Country_ID = rs.getInt("Country_ID");
                cc.Create_Date = rs.getDate("Create_Date");
                cc.Created_By = rs.getString("Created_By");
                cc.Last_Update = rs.getDate("Last_Update");
                cc.Last_Updated_By = rs.getString("Last_Updated_By");

                retVal.add(cc);
            }
        }catch (Exception e) {
//TODO : Handle Exception
            Messages.ShowUnhandledError(e.getMessage());
            System.out.println(e.getMessage());
        }

        return retVal;
    }

}
