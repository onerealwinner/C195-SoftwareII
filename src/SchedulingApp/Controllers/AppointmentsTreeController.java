package SchedulingApp.Controllers;

import SchedulingApp.Helpers.Messages;
import SchedulingApp.Helpers.MyDate;
import SchedulingApp.Helpers.Navigation;
import SchedulingApp.Helpers.UserMessages;
import SchedulingApp.Main;
import SchedulingApp.Models.Appointment;
import SchedulingApp.Models.AppointmentGroup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * View Controller for showing all the appointments in Tree View
 * This is not used, but may required... i don't know
 */
public class AppointmentsTreeController implements Initializable {

    ////////////////////////
    public TreeTableView appointmentTable;
    public TreeTableColumn columnAppointmentId;
    public TreeTableColumn columnAppointmentTitle;
    public TreeTableColumn columnAppointmentDescription;
    public TreeTableColumn columnAppointmentLocation;
    public TreeTableColumn columnAppointmentContactName;
    public TreeTableColumn columnAppointmentType;
    public TreeTableColumn colWeekOf;
    public TreeTableColumn colMonthOf;
    public TreeTableColumn colAll;
    public TreeTableColumn<MyDate, String> columnAppointmentStart;
    public TreeTableColumn<MyDate, String> columnAppointmentEnd;
    public TreeTableColumn columnAppointmentCustomerID;
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

    private ObservableList<Appointment> AllAppointments = FXCollections.observableArrayList();

    ////////////////////////

    /**
     * Initialize the form
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AllAppointments = Appointment.getAllAppointments();
        loadAppointmentTable();
    }

    /**
     * Load the table to filter appointments by month or week
     *
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
}*/

    /**
     * Load the appointment table
     */
    private void loadAppointmentTable() {
        /*
        TreeItem<Appointment> root = new TreeItem<>(new Appointment(), "");


        AllAppointments.stream().forEach((a) -> {

        });

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

         */
    }

    /**
     * Deletes and appointment
     */
    public void deleteAppointment_OnAction(ActionEvent actionEvent) {
        if(appointmentTable.getSelectionModel().getSelectedItem() == null) {
            Messages.ShowError(UserMessages.NoItemSelected);
            return;
        }

        if(Messages.Confirm(UserMessages.ConfirmDeleteCustomer)) {
            Appointment a = (Appointment)appointmentTable.getSelectionModel().getSelectedItem();
            a.Delete();

            //remove from the table
            //appointmentTable.getItems().remove(a);

            String msg = Main.resourceBundle.getString(UserMessages.DeleteAppointmentSuccess.toString()).replace("{0}"
                    ,String.valueOf(a.getAppointment_ID())).replace("{1}",a.getType());
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
     *
    public void filterBy_OnAction(ActionEvent actionEvent) {
        loadFilterTable();
    }*/

    /**
     * Cancel this action and go back to the main form
     * @param actionEvent actionEvent from button
     */
    public void cancel_OnAction(ActionEvent actionEvent) {
        Navigation.ConfirmCancelSubFormAndOpenMainForm();
    }

}
