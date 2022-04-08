package SchedulingApp.Helpers;


import SchedulingApp.Main;

import java.security.PublicKey;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Helper class for a datetime
 * FUTURE ENHANCEMENT - make the timezones more generic
 */
public class MyDate {

    /**
     * The standard date format used
     * FUTURE ENHANCEMENT - enable this by locale
     */
    public static final String AppDateFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * Holds the DateTime
     */
    private LocalDateTime DateOf;
    /**
     * Gets the DateTime
     */
    public LocalDateTime getDateOf() { return this.DateOf; }
    /**
     * Sets the DateTime
     */
    public void setDateOf(LocalDateTime val) { this.DateOf = val; }

    ////////////////////////////////
    /**
     * Empty constructor -sets date to now
     */
    public MyDate() {
        this.DateOf = Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime();
    }

    /**
     * Construct and set date to a specific value
     */
    public MyDate(LocalDateTime val) {
        this.DateOf = val;
    }

    /**
     * Construct and set date to a specific value from a string
     * Typically used from database
     * FUTURE ENHANCEMENT - make more versatile with different patterns
     */
    public MyDate(String val) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(MyDate.AppDateFormat);
        this.DateOf = LocalDateTime.parse(val, dateFormat);
    }

    ////////////////////////////////

    /**
     * Gets a LocalDate from a UTC Date
     */
    public static MyDate MyDateFromUTCDB(String utcDate) {
        MyDate dbDate = new MyDate(utcDate);
        MyDate retVal = new MyDate(dbDate.FromUTC());
        return retVal;
    }

    /**
     * Gets a MyDate for the Start of Business Hours
     */
    public static MyDate BusinessHoursStart() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.of(today.getYear(),today.getMonth(),today.getDayOfMonth(), 8,0,0);

        ZonedDateTime utcTime = startTime.atZone(ZoneId.of("US/Eastern"));
        ZonedDateTime zdt = utcTime.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        LocalDateTime ldt = zdt.toLocalDateTime();

        return new MyDate(ldt);
    }

    /**
     * Gets a MyDate for the End of Business Hours
     */
    public static MyDate BusinessHoursEnd() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.of(today.getYear(),today.getMonth(),today.getDayOfMonth(), 22,0,0);

        ZonedDateTime utcTime = startTime.atZone(ZoneId.of("US/Eastern"));
        ZonedDateTime zdt = utcTime.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        LocalDateTime ldt = zdt.toLocalDateTime();

        return new MyDate(ldt);
    }

    ////////////////////////////////
    /**
     * Formats the output to a string with our standard date format
     */
    public String ToString() {
        return DateOf.format(DateTimeFormatter.ofPattern(MyDate.AppDateFormat));
    }

    /**
     * Formats the date as a string in Eastern time
     * Used for testing
     */
    public String ToEasternDateString() {
        LocalDateTime ldt = Timestamp.valueOf(DateOf).toLocalDateTime();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime estTime = zdt.withZoneSameInstant(ZoneId.of("US/Eastern"));

        return estTime.format(DateTimeFormatter.ofPattern(AppDateFormat));
    }

    /**
     * Formats the date as a string in UTC Time
     * Be sure to call this when saving a date to a database
     */
    public String ToUTCDateString() {
        LocalDateTime ldt = Timestamp.valueOf(DateOf).toLocalDateTime();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utcTime = zdt.withZoneSameInstant(ZoneId.of("UTC"));

        return utcTime.format(DateTimeFormatter.ofPattern(AppDateFormat));
    }

    /**
     * Formats the date as a string in UTC time
     * Used for testing
     */
    public String FromUTCToString() {
        ZonedDateTime utcTime = DateOf.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdt = utcTime.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        LocalDateTime ldt = zdt.toLocalDateTime();

        return ldt.format(DateTimeFormatter.ofPattern(AppDateFormat));
    }

    /**
     * Gets a Local date in UTC timezone
     * Used for testing
     */
    public ZonedDateTime ToUTC() {
        LocalDateTime ldt = Timestamp.valueOf(DateOf).toLocalDateTime();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utcTime = zdt.withZoneSameInstant(ZoneId.of("UTC"));

        return utcTime;
    }

    /**
     * Gets a Local date in Eastern timezone
     * Used for testing
     */
    public ZonedDateTime ToEasternDate() {
        LocalDateTime ldt = Timestamp.valueOf(DateOf).toLocalDateTime();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime estTime = zdt.withZoneSameInstant(ZoneId.of("US/Eastern"));

        return estTime;
    }

    /**
     * Gets a Local date from a UTC timezone
     */
    public LocalDateTime FromUTC() {
        ZonedDateTime utcTime = DateOf.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdt = utcTime.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        LocalDateTime ldt = zdt.toLocalDateTime();

        return ldt;
    }


}
