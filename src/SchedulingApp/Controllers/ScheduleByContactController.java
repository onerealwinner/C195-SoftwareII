package SchedulingApp.Controllers;

import SchedulingApp.Helpers.MyDate;
import SchedulingApp.Helpers.Navigation;
import SchedulingApp.Models.Appointment;
import SchedulingApp.Models.Contact;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * View Controller for the Schedule By Contract Report
 */
public class ScheduleByContactController implements Initializable {

    public ComboBox<Contact> ContactCombo;
    public TableColumn columnAppointmentId;
    public TableColumn columnAppointmentTitle;
    public TableColumn columnAppointmentDescription;
    public TableColumn columnAppointmentType;
    public TableColumn<MyDate, String> columnAppointmentStart;
    public TableColumn<MyDate, String> columnAppointmentEnd;
    public TableColumn columnAppointmentCustomerID;
        /**
     * Button to cancel this action and go back to the main form
     */
    public Button cancelButton;
    public TableView appointmentTable;

    /**
     * Holds all the contacts
     */
    private ObservableList<Contact> AllContacts;
    /**
     * Holds all the appointments
     */
    private ObservableList<Appointment> AllAppointments;

    /**
     * Initalize the form
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AllContacts = Contact.getAllContacts();
        AllAppointments = Appointment.getAllAppointments();

        loadContactComboBox();
        loadAppointmentTable();

        ContactCombo.getSelectionModel().select(0);

    }

    /**
     * Load the appointment table
     */
    private void loadAppointmentTable() {
        appointmentTable.setItems(AllAppointments);

        columnAppointmentId.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        columnAppointmentTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        columnAppointmentDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        columnAppointmentType.setCellValueFactory(new PropertyValueFactory<>("Type"));

        columnAppointmentStart.setCellValueFactory(new PropertyValueFactory<MyDate, String>("StartAsString"));
        columnAppointmentEnd.setCellValueFactory(new PropertyValueFactory<MyDate, String>("EndAsString"));

        columnAppointmentCustomerID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
    }

    /**
     * Load contact combo box
     */
    private void loadContactComboBox() {
        ContactCombo.setItems(AllContacts);

        ContactCombo.setConverter(new StringConverter<Contact>() {
            @Override
            public String toString(Contact d) {
                return d != null ? d.getContact_Name() : "";
            }
            @Override
            public Contact fromString(String s) {
                return ContactCombo.getItems().stream().filter(x->x.getContact_Name().equals(s)).findFirst().orElse(null);
            }
        });

        ContactCombo.valueProperty().addListener((obs, oVal, nVal) -> {
            if(nVal != null ) {
                appointmentTable.setItems(AllAppointments.filtered(x->x.getContact_ID() == nVal.getContact_ID()));

            }else {
                appointmentTable.setItems(AllAppointments);
            }
        });
    }

    /**
     * Cancel this action and go back to the main form
     * @param actionEvent actionEvent from button
     */
    public void cancel_OnAction(ActionEvent actionEvent) {
        Navigation.ConfirmCancelSubFormAndOpenMainForm();
    }
}
