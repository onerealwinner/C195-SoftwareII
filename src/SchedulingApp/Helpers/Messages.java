package SchedulingApp.Helpers;

import SchedulingApp.Main;
import SchedulingApp.Models.Appointment;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ResourceBundle;

/**
 * Helper to handle messages displayed to the user
 */
public class Messages {

    /**
     * Generic confirmation function
     * @param message the message for the confirmation box
     * @return true if 'yes'
     */
    public static boolean Confirm(UserMessages message) {
        return Messages.Confirm(Main.resourceBundle.getString(message.toString()));
    }

    /**
     * Generic confirmation function
     * @param message the message for the confirmation box
     * @return true if 'yes'
     */
    public static boolean Confirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        return alert.getResult() == ButtonType.YES;
    }

    /**
     * Generic show an error
     * @param message the information to show
     */
    public static void ShowError(UserMessages message) {
        Messages.ShowMessage(Alert.AlertType.ERROR, message);
    }

    /**
     * Generic show an error
     * @param error to show user
     */
    public static void ShowUnhandledError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR, Main.resourceBundle.getString(UserMessages.UnhandledException.toString()) + "\r\n\r\n" + error, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Generic show Information
     * @param message the information to show
     */
    public static void ShowMessage(Alert.AlertType aType, UserMessages message) {
        Alert alert = new Alert(aType, Main.resourceBundle.getString(message.toString()), ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Generic show upcoming appointment message
     * @param a the appointment
     */
    public static void ShowUpcomingAppointments(Appointment a) {
        String msg = Main.resourceBundle.getString("UpcomingAppointments");
        String appointmentId = String.valueOf(a.getAppointment_ID());
        String dateAndTime = a.getStart().ToString();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg.replace("{0}",appointmentId).replace("{1}", dateAndTime), ButtonType.OK);
        alert.showAndWait();
    }

}
