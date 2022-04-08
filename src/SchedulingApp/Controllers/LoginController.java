package SchedulingApp.Controllers;

import SchedulingApp.Helpers.*;
import SchedulingApp.Main;
import SchedulingApp.Models.Appointment;
import SchedulingApp.Models.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.swing.text.WrappedPlainView;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * View controller for the application login
 */
public class LoginController implements Initializable {

    //region JavaFX Form Components
    public TextField userNameText;
    public TextField passwordText;
    public Label currentLocationLabel;
    public Label userNameLabel;
    public Label passwordLabel;
    public Button loginButton;
    public Button exitButton;
    public Label formTitleLabel;

    public Label myCurrentTime;
    public Label startBusiness;
    public Label endBusiness;

    //endregion

    /**
     * Initialize the Login Form
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MyDate now = new MyDate();
        MyDate sb = MyDate.BusinessHoursStart();
        MyDate eb = MyDate.BusinessHoursEnd();

        myCurrentTime.setText(now.ToString());
        startBusiness.setText(sb.ToString());
        endBusiness.setText(eb.ToString());

        //show the user location
        currentLocationLabel.setText("\r\nCountry: " + Main.locale.getDisplayCountry() + " \r\nTimeZone : " + Main.timeZone.getDisplayName());
    }

    /**
     * Check if the user can login
     * @param actionEvent
     */
    public void loginButton_OnAction(ActionEvent actionEvent) {
        User myUser = User.canLogin(userNameText.getText(), passwordText.getText());

        if (myUser == null) {
           Messages.ShowError(UserMessages.InvalidUserNameOrPassword);
            LogHelper.LogLoginAttempt(userNameText.getText(), false);
        }else {
            LogHelper.LogLoginAttempt(userNameText.getText(), true);
            //continue
            Main.LoggedInUser = myUser;

            //Check appointments
            ObservableList<Appointment> fifteen = Appointment.getAppointmentsInNext15Minutes(myUser.getUser_Id());
            if(fifteen.size() > 0) {
                fifteen.forEach(Messages::ShowUpcomingAppointments);
            } else {
                Messages.ShowMessage(Alert.AlertType.INFORMATION, UserMessages.NoUpcomingAppointments);
            }

            Navigation.OpenMainForm();
        }
    }

    /**
     * Exit the application
     * @param actionEvent action
     */
    public void exitButton_OnAction(ActionEvent actionEvent) {
        Main.ConfirmAndCloseApplication();
    }

}
