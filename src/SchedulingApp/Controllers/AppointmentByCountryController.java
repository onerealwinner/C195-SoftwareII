package SchedulingApp.Controllers;

import SchedulingApp.Helpers.Navigation;
import SchedulingApp.Models.AppointmentCountry;
import SchedulingApp.Models.AppointmentTypeMonth;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Appointments Group by Country View Controller
 */
public class AppointmentByCountryController implements Initializable {
    /**
     * Table for the data to be displayed
     */
    public TableView dataTable;

    /**
     * Button to cancel this action and go back to the main form
     */
    public Button cancelButton;
    
    public TableColumn colCountry;
    public TableColumn colTotal;

    /**
     * Initialize the view
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDataTable();
    }

    /**
     * Load the customer table
     */
    private void loadDataTable() {
        dataTable.setItems(AppointmentCountry.getData());

        colCountry.setCellValueFactory(new PropertyValueFactory<>("Country"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
    }
    
    /**
     * Cancel this action and go back to the main form
     * @param actionEvent actionEvent from button
     */
    public void cancel_OnAction(ActionEvent actionEvent) {
        Navigation.ConfirmCancelSubFormAndOpenMainForm();
    }

}
