package SchedulingApp.Controllers;

import SchedulingApp.Helpers.Messages;
import SchedulingApp.Helpers.Navigation;
import SchedulingApp.Helpers.UserMessages;
import SchedulingApp.Models.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * View controller for the list of customers
 */
public class CustomersController implements Initializable {

    public TableView customerTable;
    public TableColumn columnCustomer_Name;
    public TableColumn columnPhone;
    public TableColumn columnAddress;
    public TableColumn columnDivision;
    public TableColumn columnCountry;
    public Button addCustomerButton;
    public Button deleteCustomerButton;
    public Button modifyCustomerButton;

        /**
     * Button to cancel this action and go back to the main form
     */
    public Button cancelButton;

    /**
     * Initialize the form
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCustomerTable();
    }

    /**
     * Load the customer table
     */
    private void loadCustomerTable() {
        customerTable.setItems(Customer.getCustomers());

        columnCustomer_Name.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        columnDivision.setCellValueFactory(new PropertyValueFactory<>("Division"));
        columnCountry.setCellValueFactory(new PropertyValueFactory<>("Country"));
    }

    /**
     * Open the add customer form
     */
    public void addCustomer_OnAction(ActionEvent actionEvent) {
        CustomerController.CustomerToModify = null;
        Navigation.OpenNewForm("CustomerView");
    }

    /**
     * Open the modify customer form
     */
    public void modifyCustomer_OnAction(ActionEvent actionEvent) {
        //Check if a customer is selected
        if(customerTable.getSelectionModel().getSelectedItem() == null) {
            Messages.ShowError(UserMessages.NoItemSelected);
            return;
        }

        CustomerController.CustomerToModify = (Customer)customerTable.getSelectionModel().getSelectedItem();
        Navigation.OpenNewForm("CustomerView");
    }

    /**
     * Delete a customer - validate the customer has no appointments
     */
    public void deleteCustomer_OnAction(ActionEvent actionEvent) {
        //Check if a customer is selected
        if(customerTable.getSelectionModel().getSelectedItem() == null) {
            Messages.ShowError(UserMessages.NoItemSelected);
            return;
        }

        Customer c = (Customer)customerTable.getSelectionModel().getSelectedItem();

        if(c.hasAppointments()) {
            Messages.ShowError(UserMessages.CustomerHasAppointments);
            return;
        }

        if(Messages.Confirm(UserMessages.ConfirmDeleteCustomer)) {
            c.Delete();

            //remove from the table
            customerTable.getItems().remove(c);

            Messages.ShowMessage(Alert.AlertType.INFORMATION, UserMessages.DeleteCustomerSuccessful);
        }
    }

    /**
     * Cancel this action and go back to the main form
     * @param actionEvent actionEvent from button
     */
    public void cancel_OnAction(ActionEvent actionEvent) {
        Navigation.ConfirmCancelSubFormAndOpenMainForm();
    }
}
