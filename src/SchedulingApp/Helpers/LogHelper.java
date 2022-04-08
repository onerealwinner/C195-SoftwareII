package SchedulingApp.Helpers;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Helper to log things to files
 */
public class LogHelper {

    private static final String loginFileName = "login_activity.txt";

    public static void LogLoginAttempt(String userName, boolean wasSuccess) {
        Logger log = Logger.getLogger(loginFileName);
        MyDate now = new MyDate();

        try {
            FileHandler fh = new FileHandler(loginFileName, true);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            log.addHandler(fh);

            String result = wasSuccess ? "successful" : "invalid";

            log.log( wasSuccess ? Level.INFO : Level.WARNING,
                    "login attempt by " + userName + " on " + now.ToUTCDateString() + " UTC was " + result);

        } catch (IOException ex) {
            //TODO : Handle Exception

        } catch (SecurityException ex) {
            //TODO : Handle Exception

        }
    }

}
