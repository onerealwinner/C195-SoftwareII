package SchedulingApp.Controllers;

import SchedulingApp.Helpers.Messages;
import SchedulingApp.Helpers.MyDate;
import SchedulingApp.Helpers.Navigation;
import SchedulingApp.Helpers.UserMessages;
import SchedulingApp.Main;
import SchedulingApp.Models.Appointment;
import SchedulingApp.Models.AppointmentGroup;
import SchedulingApp.Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * View Controller for showing all the appointments
 */
public class AppointmentsController implements Initializable {

    ////////////////////////
    public TableView appointmentTable;
    public TableColumn columnAppointmentId;
    public TableColumn columnAppointmentTitle;
    public TableColumn columnAppointmentDescription;
    public TableColumn columnAppointmentLocation;
    public TableColumn columnAppointmentContactName;
    public TableColumn columnAppointmentType;
    public TableColumn<MyDate, String> columnAppointmentStart;
    public TableColumn<MyDate, String> columnAppointmentEnd;
    public TableColumn columnAppointmentCustomerID;
    public Button addAppointmentButton;
    public Button deleteAppointmentButton;
    public Button modifyAppointmentButton;
    public Label createdDate;

    public TableView<AppointmentGroup> filterWeekMonthTable;
    public TableColumn filterBySelector;
    public TableColumn appointmentCount;
    public RadioButton filterByMonth;
    public RadioButton filterByWeek;
    public RadioButton filterShowAll;
    public ToggleGroup filterBy;

    ////////////////////////

    /**
     * Initialize the form
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAppointmentTable();
    }

    /**
     * Load the table to filter appointments by month or week
     */
    private void loadFilterTable() {
    ObservableList<AppointmentGroup> myItems = FXCollections.observableArrayList();
    if(filterShowAll.isSelected()) {
        filterWeekMonthTable.setItems(null);
        appointmentTable.setItems(Appointment.getAllAppointments());
    }

    if(filterByMonth.isSelected()) {
        myItems = AppointmentGroup.getAppointmentsBy("MONTH");
        filterBySelector.setText(Main.resourceBundle.getString("Month"));
    }else if (filterByWeek.isSelected()) {
        myItems = AppointmentGroup.getAppointmentsBy("WEEK");
        filterBySelector.setText(Main.resourceBundle.getString("Week"));
    }

    filterWeekMonthTable.setItems(myItems);
    filterBySelector.setCellValueFactory(new PropertyValueFactory<>("FilterBy"));
    appointmentCount.setCellValueFactory(new PropertyValueFactory<>("Total"));

    filterWeekMonthTable.getSelectionModel().selectedItemProperty().addListener((obs, oVal, nVal) -> {
        if (nVal != null) {
            //filter table appointments
            ObservableList<Appointment> items = FXCollections.observableArrayList();
            AppointmentGroup ag = (AppointmentGroup)filterWeekMonthTable.getSelectionModel().getSelectedItem();

            if(filterByMonth.isSelected()) {
                items = Appointment.getAppointmentsByMonth(ag.getFilterBy());
            }else if (filterByWeek.isSelected()) {
                items = Appointment.getAppointmentsByWeek(ag.getFilterBy());
            }

            appointmentTable.setItems(items);
        }
    });
}
    /**
     * Load the appointment table
     */
    private void loadAppointmentTable() {
        appointmentTable.setItems(Appointment.getAllAppointments());

        columnAppointmentId.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        columnAppointmentTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        columnAppointmentDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        columnAppointmentLocation.setCellValueFactory(new PropertyValueFactory<>("Location"));
        columnAppointmentContactName.setCellValueFactory(new PropertyValueFactory<>("Contact_Name"));
        columnAppointmentType.setCellValueFactory(new PropertyValueFactory<>("Type"));

        columnAppointmentStart.setCellValueFactory(new PropertyValueFactory<MyDate, String>("StartAsString"));
        columnAppointmentEnd.setCellValueFactory(new PropertyValueFactory<MyDate, String>("EndAsString"));

        columnAppointmentCustomerID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
    }

    /**
     * Deletes and appointment
     * This is where the requirement applies;
     * •  A custom message is displayed in the user interface with the Appointment_ID and type of appointment canceled.
     */
    public void deleteAppointment_OnAction(ActionEvent actionEvent) {
        if(appointmentTable.getSelectionModel().getSelectedItem() == null) {
            Messages.ShowError(UserMessages.NoItemSelected);
            return;
        }

        Appointment a = (Appointment)appointmentTable.getSelectionModel().getSelectedItem();

        String confirmMsg = Main.resourceBundle.getString(UserMessages.ConfirmDeleteAppointment.toString()).replace("{0}"
                ,String.valueOf(a.getAppointment_ID())).replace("{1}",a.getType());

        if(Messages.Confirm(confirmMsg)) {
            a.Delete();
            //remove from the table
            appointmentTable.getItems().remove(a);

            String msg = Main.resourceBundle.getString(UserMessages.DeleteAppointmentSuccess.toString()).replace("{0}"
                    ,String.valueOf(a.getAppointment_ID())).replace("{1}",a.getType());

            Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Opens form to modify an appointment
     */
    public void modifyAppointment_OnAction(ActionEvent actionEvent) {
        if(appointmentTable.getSelectionModel().getSelectedItem() == null) {
            Messages.ShowError(UserMessages.NoItemSelected);
            return;
        }

        AppointmentController.AppointmentToModify = (Appointment)appointmentTable.getSelectionModel().getSelectedItem();
        Navigation.OpenNewForm("AppointmentView");
    }

    /**
     * Opens form to add new appointment
     */
    public void addAppointment_OnAction(ActionEvent actionEvent) {
        AppointmentController.AppointmentToModify = null;
        Navigation.OpenNewForm("AppointmentView");
    }

    /**
     * Filters the appointments in the table
     */
    public void filterBy_OnAction(ActionEvent actionEvent) {
        loadFilterTable();
    }

    /**
     * Cancel this action and go back to the main form
     * @param actionEvent actionEvent from button
     */
    public void cancel_OnAction(ActionEvent actionEvent) {
        Navigation.ConfirmCancelSubFormAndOpenMainForm();
    }

}
