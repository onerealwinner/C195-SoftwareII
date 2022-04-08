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
 * Class model for division - closely matches db
 */
public class Division {
    //////////////////////////////////////////////////
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
     * holds COUNTRY_ID
     */
    private int COUNTRY_ID;
    /**
     * gets COUNTRY_ID
     */
    public int getCOUNTRY_ID() { return COUNTRY_ID; }
    /**
     * sets COUNTRY_ID
     */
    public void setCOUNTRY_ID(int val) { this.COUNTRY_ID = val; }

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
     * Get a single Division
     * @param Division_ID
     * @return a division if found
     */
    public static Division getSingleDivision(int Division_ID) {
        ObservableList<Division> retVal = getDivisions(Division_ID);
        
        if(retVal.size() > 0) {
            return retVal.get(0);
        }else {
            Messages.ShowError(UserMessages.NotFound);
            return null;
        }
    }

    /**
     * Gets all the divisions
     * @return all divisions
     */
    public static ObservableList<Division> getAllDivisions() {
            return getDivisions(null);
    }

    /**
     * gets divisions
     * @param Division_ID
     * @return list of divisions
     */
    private static ObservableList<Division> getDivisions(Integer Division_ID) {
        ObservableList<Division> retVal = FXCollections.observableArrayList();

        try {
            PreparedStatement sql = DataAccess.getConn().prepareStatement("" +
                    "SELECT " +
                    "	d.Division_ID" +
                    "	,d.Division" +
                    "	,d.Create_Date" +
                    "	,d.Created_By" +
                    "	,d.Last_Update" +
                    "	,d.Last_Updated_By" +
                    "	,d.COUNTRY_ID" +
                    "	,cc.Country " +
                    "FROM " +
                    "	first_level_divisions AS d " +
                    "	LEFT JOIN countries as cc " +
                    "		ON d.Country_ID = cc.Country_ID " +
                    "WHERE " +
                    "	d.Division_ID = COALESCE(?, d.Division_ID);"
            );
            
            sql.setObject(1, Division_ID != null ? Division_ID : null );

            System.out.println("DEBUG sql : " + sql.toString());

            ResultSet rs = sql.executeQuery();


            while(rs.next()) {
                Division d = new Division();

                d.Division = rs.getString("Division");
                d.Division_ID = rs.getInt("Division_ID");
                d.Country = rs.getString("Country");
                d.COUNTRY_ID = rs.getInt("COUNTRY_ID");
                d.Create_Date = rs.getDate("Create_Date");
                d.Created_By = rs.getString("Created_By");
                d.Last_Update = rs.getDate("Last_Update");
                d.Last_Updated_By = rs.getString("Last_Updated_By");

                retVal.add(d);
            }
        }catch (Exception e) {
//TODO : Handle Exception
            Messages.ShowUnhandledError(e.getMessage());
            System.out.println(e.getMessage());
        }

        return retVal;
    }


}
