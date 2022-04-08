package SchedulingApp.Controllers;

import SchedulingApp.Helpers.Messages;
import SchedulingApp.Helpers.Navigation;
import SchedulingApp.Helpers.UserMessages;
import SchedulingApp.Models.Country;
import SchedulingApp.Models.Customer;
import SchedulingApp.Models.Division;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * View Controller for the individual Customer form
 */
public class CustomerController implements Initializable {

    /**
     * Holds a Customer to modify if set
     */
    public static Customer CustomerToModify = null;

    public TextField customerName;
    public TextField customerAddress;
    public TextField customerPostalCode;
    public TextField customerPhoneNumber;
    public ComboBox<Division> customerDivision;
    public ComboBox<Country> customerCountry;
    public Button saveButton;
        /**
     * Button to cancel this action and go back to the main form
     */
    public Button cancelButton;
    public Label CustomerId;

    /**
     * Holds the list of countries
     */
    private ObservableList<Country> countries;
    /**
     * Holds the list of divisions
     */
    private ObservableList<Division> divisions;

    //////////////////////////
    /**
     * Initialize the form
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countries = Country.getAllCountries();
        divisions = Division.getAllDivisions();

        loadCountryComboBox();
        loadDivisionComboBox();

        if (CustomerToModify == null) {
            CustomerId.setText("-");
        }else {
            CustomerId.setText(String.valueOf(CustomerToModify.getCustomer_ID()));

            //load fields
            customerName.setText(String.valueOf(CustomerToModify.getCustomer_Name()));
            customerAddress.setText(String.valueOf(CustomerToModify.getAddress()));
            customerPhoneNumber.setText(String.valueOf(CustomerToModify.getPhone()));
            customerPostalCode.setText(String.valueOf(CustomerToModify.getPostal_Code()));

            Division d = divisions.stream().filter(x->x.getDivision_ID() == CustomerToModify.getDivision_ID()).findFirst().orElse(null);
            Country cs = countries.stream().filter(x->x.getCountry_ID() == CustomerToModify.getCountry_ID()).findFirst().orElse(null);

            customerDivision.getSelectionModel().select(d);
            customerCountry.getSelectionModel().select(cs);
        }

    }

    /**
     * Thanks to https://stackoverflow.com/questions/41634789/javafx-combobox-display-text-but-return-id-on-selection
     */
    private void loadDivisionComboBox() {
        customerDivision.setItems(divisions);

        customerDivision.setConverter(new StringConverter<Division>() {
            @Override
            public String toString(Division d) {
                return d != null ? d.getDivision() : "";
            }
            @Override
            public Division fromString(String s) {
                return customerDivision.getItems().stream().filter(x->x.getDivision().equals(s)).findFirst().orElse(null);
            }
        });

        customerDivision.valueProperty().addListener((obs, oVal, nVal) -> { });
    }


    /**
     * Loads the Country combobox
     * LAMBDA functions to help display the right data from the combobox and to update customer division by a selected country
     */
    private void loadCountryComboBox() {
        customerCountry.setItems(countries);

        customerCountry.setConverter(new StringConverter<Country>() {
            @Override
            public String toString(Country c) {
                return c != null ? c.getCountry() : "";
            }

            @Override
            public Country fromString(String s) {
                return customerCountry.getItems().stream().filter(x->x.getCountry().equals(s)).findFirst().orElse(null);
            }
        });

        //filter district
        customerCountry.valueProperty().addListener((obs, oVal, nVal) -> {
            if(nVal == null) {
                //do nothing
            }else {
                customerDivision.setItems(divisions.filtered(x-> x!= null && x.getCOUNTRY_ID() == nVal.getCountry_ID()));
            }
        });

    }

    /**
     * Save this customer record
     * @param actionEvent
     */
    public void save_OnAction(ActionEvent actionEvent) {
        if(!IsValidForm()) return;

        Customer c = new Customer();

        if(CustomerToModify != null) c.setCustomer_ID(CustomerToModify.getCustomer_ID());

        Division d = (Division)customerDivision.valueProperty().getValue();

        c.setCustomer_Name(customerName.getText());
        c.setAddress(customerAddress.getText());
        c.setPhone(customerPhoneNumber.getText());
        c.setPostal_Code(customerPostalCode.getText());
        c.setDivision_ID(d.getDivision_ID());

        if (c.Save() ) {
            Messages.ShowMessage(Alert.AlertType.INFORMATION, UserMessages.SaveCustomerSuccess);
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
     * validates the form is valid
     * I couldn't find any requirements so I'm just checking a few things
     */
    private boolean IsValidForm() {
        boolean retVal = true;

        //Country is selected
        if(customerName.getText().isEmpty()) {
            Messages.ShowError(UserMessages.NoCustomerNameIsEntered);
            retVal = false;
        }

        //Country is selected
        if(customerCountry.getSelectionModel().isEmpty()) {
            Messages.ShowError(UserMessages.NoCountryIsSelected);
            retVal = false;
        }

        //Division is selected
        if(customerDivision.getSelectionModel().isEmpty()) {
            Messages.ShowError(UserMessages.NoDivisionSelected);
            retVal = false;
        }

        return retVal;
    }
}
