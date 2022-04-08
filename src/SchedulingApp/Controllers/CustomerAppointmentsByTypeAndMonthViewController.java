package SchedulingApp.Controllers;

import SchedulingApp.Helpers.Navigation;
import SchedulingApp.Models.AppointmentTypeMonth;
import SchedulingApp.Models.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * View controller for the report to View appointments by type and month
 */
public class CustomerAppointmentsByTypeAndMonthViewController implements Initializable {
    public TableView dataTable;
        /**
     * Button to cancel this action and go back to the main form
     */
    public Button cancelButton;
    public TableColumn colType;
    public TableColumn colTotal;
    public TableColumn colMonth;

    /**
     * Initialize the form
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
loadDataTable();
    }

    /**
     * Load the data into a  table
     */
    private void loadDataTable() {
        dataTable.setItems(AppointmentTypeMonth.getData());

        colType.setCellValueFactory(new PropertyValueFactory<>("AppointmentType"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
        colMonth.setCellValueFactory(new PropertyValueFactory<>("MonthOf"));

    }

    /**
     * Cancel this action and go back to the main form
     * @param actionEvent actionEvent from button
     */
    public void cancel_OnAction(ActionEvent actionEvent) {
        Navigation.ConfirmCancelSubFormAndOpenMainForm();
    }
}
