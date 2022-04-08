package SchedulingApp;

import SchedulingApp.DAO.DataAccess;
import SchedulingApp.Helpers.Messages;
import SchedulingApp.Helpers.Navigation;
import SchedulingApp.Helpers.UserMessages;
import SchedulingApp.Models.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * The starting point for the application
 */
public class Main extends Application {

    /**
     * The User object of the logged in user
     */
    public static User LoggedInUser = null;

    /**
     * The ResourceBundle for the application
     */
    public static ResourceBundle resourceBundle = null;

    public static Locale locale;
    public static TimeZone timeZone;

     /**
     * Start the application and show the first form
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        locale = Locale.getDefault();
        timeZone = TimeZone.getDefault();

        //testTimeZones();
        //testFrench();

        DataAccess.openConnection();
        resourceBundle = ResourceBundle.getBundle("Languages/rb");

        //open the first form
        Navigation.setStage(primaryStage);

        Navigation.OpenLoginForm();
        //Navigation.OpenNewForm("CustomerView");
    }

    /**
     * Application close event - make sure db connection is closed
     */
    @Override
    public void stop() {
        DataAccess.closeConnection();
    }

    /**
     * Call this to close application from any form
     */
    public static void ConfirmAndCloseApplication()  {
        if(Messages.Confirm(UserMessages.ConfirmExitApplication)) {
            DataAccess.closeConnection();
            Platform.exit();
            System.exit(0);
        }
    }

    public static void main(String[] args) { launch(args);}

    ////////////////////////////////////

    /**
     * Run this to test for the French Language
     */
    public static void testFrench() {
        Locale.setDefault(new Locale("fr"));
    }


    /**
     * Testing for different timezones
     */
    private static void testTimeZones() {
        //Pacific/Honolulu - 10
        //Canada/Mountain -6
        //Asia/Shanghai + 8
        //US/Eastern
        timeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("US/Eastern")));
    }




}
