package SchedulingApp.Controllers;

import SchedulingApp.Helpers.Messages;
import SchedulingApp.Helpers.MyDate;
import SchedulingApp.Helpers.Navigation;
import SchedulingApp.Helpers.UserMessages;
import SchedulingApp.Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * View Controller for individual appointments
 */
public class AppointmentController implements Initializable {

    public static Appointment AppointmentToModify = null;
    /**
     * Button to save this form
     */
    public Button saveButton;
    /**
     * Button to cancel this action and go back to the main form
     */
    public Button cancelButton;
    
    public Label AppointmentId;
    public ComboBox<Contact> appointmentContact;
    public ComboBox<Customer> appointmentCustomer;
    public ComboBox<User> appointmentUser;
    public DatePicker appointmentDateOf;
    public ComboBox<String> startTimeHour;
    public ComboBox<String> endTimeHour;
    public ComboBox<String> startTimeMinute;
    public ComboBox<String> endTimeMinute;

    public TextField appointmentLocation;
    public TextField appointmentDescription;
    public TextField appointmentTitle;
    public TextField appointmentType;

    /**
     * holds All Users from the database
     */
    private ObservableList<User> AllUsers;
    /**
     * holds All Contacts from the database
     */
    private ObservableList<Contact> AllContacts;
    /**
     * holds All Customers from the database
     */
    private ObservableList<Customer> AllCustomers;
    /**
     * holds A list of available times
     */
    private ObservableList<String> AllTimes;
    /**
     * A list of minutes for time picker
     */
    private ObservableList<String> AllMinutes;

    /**
     * Initialize the view
     * if modifying loads the data into the fields
     * LAMBDA functions to get the correct customer and contact information for the combo box when modfying a record
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AllUsers = User.getUsers();
        AllContacts = Contact.getAllContacts();
        AllCustomers = Customer.getCustomers();
        initBusinessHours();
        initMinutes();

        loadTimeStartTime();
        endTimeHour.setItems(AllTimes);
        startTimeMinute.setItems(AllMinutes);
        endTimeMinute.setItems(AllMinutes);
        loadUserComboBox();
        loadContactComboBox();
        loadCustomerComboBox();

        if (AppointmentToModify == null) {
            AppointmentId.setText("-");

            startTimeHour.getSelectionModel().select(0);
            endTimeHour.getSelectionModel().select(0);
            startTimeMinute.getSelectionModel().select(0);
            endTimeMinute.getSelectionModel().select(0);

        }else {
            AppointmentId.setText(String.valueOf(AppointmentToModify.getAppointment_ID()));
            appointmentTitle.setText(String.valueOf(AppointmentToModify.getTitle()));
            appointmentDescription.setText(String.valueOf(AppointmentToModify.getDescription()));
            appointmentLocation.setText(String.valueOf(AppointmentToModify.getLocation()));
            appointmentType.setText(String.valueOf(AppointmentToModify.getType()));

            User user = AllUsers.stream().filter(x->x.getUser_Id() == AppointmentToModify.getUser_ID()).findFirst().orElse(null);

            Customer customer = AllCustomers.stream().filter(x->x.getCustomer_ID() == AppointmentToModify.getAppointment_ID()).findFirst().orElse(null);
            Contact contact = AllContacts.stream().filter(x->x.getContact_ID() == AppointmentToModify.getContact_ID()).findFirst().orElse(null);

            appointmentUser.getSelectionModel().select(user);
            appointmentCustomer.getSelectionModel().select(customer);
            appointmentContact.getSelectionModel().select(contact);

            MyDate startDate = AppointmentToModify.getStart();
            MyDate endDate = AppointmentToModify.getEnd();

            appointmentDateOf.setValue(startDate.getDateOf().toLocalDate());
            startTimeHour.getSelectionModel().select(String.valueOf(startDate.getDateOf().getHour()));
            endTimeHour.getSelectionModel().select(String.valueOf(endDate.getDateOf().getHour()));

            startTimeMinute.getSelectionModel().select(String.valueOf(startDate.getDateOf().getMinute()));
            endTimeMinute.getSelectionModel().select(String.valueOf(endDate.getDateOf().getMinute()));
        }
    }

    /**
     * Sets the start time combo box
     * LAMBDA to set end values based on the selected start time
     */
    private void loadTimeStartTime() {
        startTimeHour.setItems(AllTimes);

        //Filter the end times
        startTimeHour.valueProperty().addListener((obs, oVal, nVal) -> {
            if(nVal == null) {
                //do nothing
            }else {
                if(!endTimeHour.getSelectionModel().isEmpty()) {
                    int eHour = Integer.parseInt(endTimeHour.getValue());

                    endTimeHour.setItems(AllTimes.filtered(x -> x != null && Integer.parseInt(x) >= Integer.parseInt(nVal)));

                    if ((Integer.parseInt(nVal) > eHour)) {
                        endTimeHour.getSelectionModel().select(0);
                    }
                }
            }
        });
    }

    /**
     * Initializes the minutes that can be used to pick from
     */
    private void initMinutes() {
        AllMinutes = FXCollections.observableArrayList();
        AllMinutes.add("00");
        AllMinutes.add("15");
        AllMinutes.add("30");
        AllMinutes.add("45");
    }

    /**
     * Initializes the AllHours from business hours
     */
    private void initBusinessHours() {
        MyDate businessStart = MyDate.BusinessHoursStart();
        MyDate businessEnd = MyDate.BusinessHoursEnd();

        //make time in 1hr intervals
        int startHour = businessStart.getDateOf().getHour();
        int endHour = businessEnd.getDateOf().getHour();
        int curHour = startHour;

        AllTimes = FXCollections.observableArrayList();
        while(curHour<= endHour) {
            AllTimes.add(String.valueOf(curHour));
            curHour += 1;
            System.out.println("DEBUg - test 1");
        }
    }

    /**
     * Load Users combo box
     */
    private void loadUserComboBox() {
        appointmentUser.setItems(AllUsers);

        appointmentUser.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User d) {
                return d != null ? d.getUser_Name() : "";
            }
            @Override
            public User fromString(String s) {
                return appointmentUser.getItems().stream().filter(x->x.getUser_Name().equals(s)).findFirst().orElse(null);
            }
        });

        appointmentUser.valueProperty().addListener((obs, oVal, nVal) -> { });
    }

    /**
     * Load customers combo box
     */
    private void loadCustomerComboBox() {
        appointmentCustomer.setItems(AllCustomers);

        appointmentCustomer.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer d) {
                return d != null ? d.getCustomer_Name() : "";
            }
            @Override
            public Customer fromString(String s) {
                return appointmentCustomer.getItems().stream().filter(x->x.getCustomer_Name().equals(s)).findFirst().orElse(null);
            }
        });

        appointmentCustomer.valueProperty().addListener((obs, oVal, nVal) -> { });
    }

    /**
     * Load contact combo box
     */
    private void loadContactComboBox() {
        appointmentContact.setItems(AllContacts);

        appointmentContact.setConverter(new StringConverter<Contact>() {
            @Override
            public String toString(Contact d) {
                return d != null ? d.getContact_Name() : "";
            }
            @Override
            public Contact fromString(String s) {
                return appointmentContact.getItems().stream().filter(x->x.getContact_Name().equals(s)).findFirst().orElse(null);
            }
        });

        appointmentContact.valueProperty().addListener((obs, oVal, nVal) -> { });
    }


    /**
     * Save this customer record
     * @param actionEvent
     */
    public void save_OnAction(ActionEvent actionEvent) {
        if(!IsValidForm()) return;

        Appointment a = new Appointment();

        if(AppointmentToModify != null) a.setAppointment_ID(AppointmentToModify.getAppointment_ID());

        User user = (User)appointmentUser.valueProperty().getValue();
        Contact contact = (Contact)appointmentContact.valueProperty().getValue();
        Customer customer = (Customer)appointmentCustomer.valueProperty().getValue();

        //////////////////////
        a.setTitle(appointmentTitle.getText());
        a.setDescription(appointmentDescription.getText());
        a.setLocation(appointmentLocation.getText());
        a.setContact_ID(contact.getContact_ID());
        a.setType(appointmentType.getText());

        MyDate startDate = getStartDateFromForm();
        MyDate endDate = getEndDateFromForm();

        a.setStart(startDate);
        a.setEnd(endDate);

        a.setCustomer_ID(customer.getCustomer_ID());
        a.setUser_ID(user.getUser_Id());

        if (a.Save() ) {
            Messages.ShowMessage(Alert.AlertType.INFORMATION, UserMessages.SaveAppointmentSuccess);
            Navigation.OpenMainForm();
        }
    }

    /**
     * Cancel this action and go back to the main form
     * @param actionEvent actionEvent from button
     */
    public void cancel_OnAction(ActionEvent actionEvent) {
        Navigation.ConfirmCancelSubFormAndOpenMainForm();
    }

    /**
     * Uses fields from form to make a start date
     */
    private MyDate getStartDateFromForm() {
        LocalDateTime startLdt = LocalDateTime.of(appointmentDateOf.getValue()
                , LocalTime.of(Integer.parseInt(startTimeHour.getValue())
                        ,Integer.parseInt(startTimeMinute.getValue())));
        return new MyDate(startLdt);
    }

    /**
     * Uses fields from form to make a end date
     */
    private MyDate getEndDateFromForm() {
        LocalDateTime endLdt = LocalDateTime.of(appointmentDateOf.getValue()
                , LocalTime.of(Integer.parseInt(endTimeHour.getValue())
                        ,Integer.parseInt(endTimeMinute.getValue())));
        return new MyDate(endLdt);
    }

    /**
     * Ensures the form is valid
     */
    private boolean IsValidForm() {
        boolean retVal = true;

        //Contact is selected
        if(appointmentContact.getSelectionModel().isEmpty()) {
            Messages.ShowError(UserMessages.NoAppointmentContactSelected);
            retVal = false;
        }

        //User is selected
        if(appointmentUser.getSelectionModel().isEmpty()) {
            Messages.ShowError(UserMessages.NoAppointmentUserSelected);
            retVal = false;
        }

        //Customer is selected
        if(appointmentCustomer.getSelectionModel().isEmpty()) {
            Messages.ShowError(UserMessages.NoAppointmentCustomerSelected);
            retVal = false;
        }

        //Date is selected
        if(appointmentDateOf.getValue() == null) {
            Messages.ShowError(UserMessages.NoAppointmentValidDateSelected);
            retVal = false;
        } else {
            //If I have value entered make sure its valid
            try{
                Object o = appointmentDateOf.getConverter().fromString(appointmentDateOf.getEditor().getText());
            }catch (Exception e) {
                Messages.ShowError(UserMessages.NoAppointmentValidDateSelected);
                retVal = false;
            }
        }

        //Continue checking if we are good so far
        if (retVal == true) {
            Customer c = (Customer) appointmentCustomer.getSelectionModel().getSelectedItem();
            MyDate startDate = getStartDateFromForm();
            MyDate endDate = getEndDateFromForm();

            //make sure end time is after start time
            if (endDate.getDateOf().isBefore(startDate.getDateOf())) {
                Messages.ShowError(UserMessages.AppointmentEndIsBeforeAppointmentStart);
                retVal = false;
            }

            //make sure appointment doesn't overlap for customer
            int aptId = AppointmentToModify == null ? 0 : AppointmentToModify.getAppointment_ID();

            if (Appointment.isOverlappingAppointment(aptId, c.getCustomer_ID(), startDate, endDate)) {
                Messages.ShowError(UserMessages.AppointmentOverlapsForCustomer);
                retVal = false;
            }
        }

        return retVal;
    }

}
