package SchedulingApp.Helpers;

import SchedulingApp.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Handles the navigation of the app
 */
public class Navigation {

    /**
     * The stage object
     */
    private static Stage CurrentStage = null;

    /**
     * Gets the Stage
     */
    public static Stage getStage() {return CurrentStage; }

    /**
     * Set the Stage
     * @param thisStage the stage to set
     */
    public static void setStage(Stage thisStage) {
        CurrentStage = thisStage;
    }

    ///////

    /**
     * Open the main form of the application
     */
    public static void OpenLoginForm() {
        Navigation.OpenNewForm("LoginView");
    }

    /**
     * Confirms a cancel and opens the main form
     */
    public static void ConfirmCancelSubFormAndOpenMainForm() {
        if(Messages.Confirm(UserMessages.ConfirmCancelOpenMainForm)) {
            Navigation.OpenNewForm("MainView");
        }
    }

    /**
     * Open the main form of the application
     */
    public static void OpenMainForm() {
        Navigation.OpenNewForm("MainView");
    }

    /**
     * Opens a new window
     * @param formName the form name to open - must be in the Views folder
     */
    public static void OpenNewForm(String formName) {
        try {
            FXMLLoader loader = new FXMLLoader(SchedulingApp.Main.class.getResource("Views/" + formName + ".fxml"));
            loader.setResources(Main.resourceBundle);

            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = CurrentStage;

            stage.setTitle(Main.resourceBundle.getString(formName +"Title"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            //TODO : Handle Exception
            System.out.println("DEBUG ERROR - " + e.getMessage());
        }
    }


}
