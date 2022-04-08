package SchedulingApp.Controllers;

import SchedulingApp.Helpers.Navigation;
import SchedulingApp.Main;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main Navigational form for the app
 */
public class MainController implements Initializable {

    /**
     * Initialize the form
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Open the list of customers form
     */
    public void customerButton_OnAction(ActionEvent actionEvent) {
        Navigation.OpenNewForm("CustomersView");
    }

    /**
     * Open the list of appointments form
     */
    public void appointmentButton_OnAction(ActionEvent actionEvent) {
        Navigation.OpenNewForm("AppointmentsView");
    }

    /**
     * Exit the application
     */
    public void exitButton_OnAction(ActionEvent actionEvent) {
        Main.ConfirmAndCloseApplication();
    }

    /**
     * Open the customers by month report
     */
    public void customerByMonthButton_OnAction(ActionEvent actionEvent) {
        Navigation.OpenNewForm("CustomerAppointmentsByTypeAndMonthView");
    }

    /**
     * Open the schedule by contact report
     */
    public void scheduleByContactButton_OnAction(ActionEvent actionEvent) {
        Navigation.OpenNewForm("ScheduleByContactView");
    }

    /**
     * Open the appointments by country report
     */
    public void appointmentByCountryButton_OnAction(ActionEvent actionEvent) {
        Navigation.OpenNewForm("AppointmentByCountryView");
    }
}
