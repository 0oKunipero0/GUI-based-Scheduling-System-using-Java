package Model;

import Controller.LoginController;
import DBConnection.DBConnection;
import Controller.LoginController.appointmentAlert;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Controller.MainFormController.timeDisplayConvertion;
import javax.swing.*;
import java.time.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javafx.scene.control.Alert;

/**
 * this class represents the appointment objects. 
 */
public class Appointment {
    /**
     * id of an appointment. 
     */
    private int appointmentID;
    /**
     * title of an appointment. 
     */
    private String title;
    /**
     * description of an appointment. 
     */
    private String description;
    /**
     * location of an appointment. 
     */
    private String location;
    /**
     * type of an appointment. 
     */
    private String type;
    /**
     * start date/time of an appointment. 
     */
    private Timestamp start;
    /**
     * end date/time of an appointment. 
     */
    private Timestamp end;
    /**
     * customer id of an appointment. 
     */
    private int customerID;
    /**
     * user id of an appointment. 
     */
    private int userID;
    /**
     * contact id of an appointment. 
     */
    private int contactID;
    /**
     * a contact object. 
     */
    private Contact contact;
    
    

    /**
     * this method constructs the appointment objects from database. 
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID
     */
    public Appointment(int appointmentID, String title, String description, String location, String type, Timestamp start, Timestamp end, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }
    

    /**
     *it gets the appointment id.
     * @return appointment id
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     *it sets appointment id. 
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     *it gets the title of the appointment
     * @return appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     *it sets the title of an appointment.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *it gets the description of an appointment.
     * @return description of an appointment
     */
    public String getDescription() {
        return description;
    }

    /**
     *it sets the description of an appointment. 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *it gets the location of an appointment.
     * @return location of an appointment
     */
    public String getLocation() {
        return location;
    }

    /**
     *it sets the location of an appointment.
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *it gets the type of an appointment.
     * @return the type of an appointment
     */
    public String getType() {
        return type;
    }

    /**
     *it sets the type of an appointment. 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *it gets the start timestamp of an appointment.
     * @return the start timestamp of an appointment
     */
    public Timestamp getStart() {
        return start;
    }

    /**
     *it sets the start timestamp of an appointment. 
     * @param start
     */
    public void setStart(Timestamp start) {
        this.start = start;
    }

    /**
     *it gets the end timestamp of an appointment. 
     * @return the end timestamp of an appointment
     */
    public Timestamp getEnd() {
        return end;
    }

    /**
     * it sets the end timestamp of an appointment. 
     * @param end
     */
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    /**
     *it gets the customer id of an appointment. 
     * @return the customer id of an appointment
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     *it sets the customer id of an appointment.
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     *it gets the user id of an appointment. 
     * @return the user id of an appointment
     */
    public int getUserID() {
        return userID;
    }

    /**
     *it sets the user id of an appointment.
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     *it gets the contact id of an appointment. 
     * @return the contact id of an appointment
     */
    public int getContactID() {
        return contactID;
    }

    /**
     *it sets the contact id of an appointment. 
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
    
    /**
     *it converts the memory address of the type of an appointment into string. 
     * @return type of an appointment in string
     */
    public String toString()
    {
        return type;
    }
    
    /**
     * this Observablelist gets all the appointment records.
     * @return all appointments
     */
    public static ObservableList<Appointment> getAllAptm()
    {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        try
        {
            String allAptmSQL = "SELECT * FROM appointments";
            PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(allAptmSQL);
            ResultSet rSet = pStatement.executeQuery();
            while (rSet.next())
            {
                int appointmentID = rSet.getInt("Appointment_ID");
                String appointmentTitle = rSet.getString("Title");
                String appointmentDescription = rSet.getString("Description");
                String appointmentLocation = rSet.getString("Location");
                String appointmentType = rSet.getString("Type");
                Timestamp appointmentStart = rSet.getTimestamp("Start");
                appointmentStart = Appointment.userTime(appointmentStart);
                Timestamp appointmentEnd = rSet.getTimestamp("End");
                appointmentEnd = Appointment.userTime(appointmentEnd);
                int customerID = rSet.getInt("Customer_ID");
                int userID = rSet.getInt("User_ID");
                int contactID = rSet.getInt("Contact_ID");
                Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, appointmentStart, appointmentEnd, customerID, userID, contactID);
                appointmentList.add(appointment);
            }
        }
        catch (SQLException throwables){throwables.printStackTrace();}
        return appointmentList;
    }
    
     /**
     *it retrieves all user objects from database. 
     * @return all users
     */
    public static ObservableList<User> getAllUsers()
    {ObservableList<User> userList = FXCollections.observableArrayList();
        try
        {String allUsersSQL = "SELECT * FROM users";
         PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(allUsersSQL);
            ResultSet rSet = pStatement.executeQuery();
            while (rSet.next())
            {int userID = rSet.getInt("User_ID");
             String username = rSet.getString("User_Name");
             String password = rSet.getString("Password");
             User u = new User(userID, username, password);
             userList.add(u);}
        }
        catch (SQLException throwables){throwables.printStackTrace();}
        return userList;
    }
    
    /**
     *this Observablelist gets all customer records. 
     * @return all customers
     */
    public static ObservableList<Customer> getAllCustomers()
    {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try
        {
            String allCusSQL = "SELECT * FROM customers";
            PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(allCusSQL);
            ResultSet rSet = pStatement.executeQuery();
            while (rSet.next())
            {
                Integer customerID = rSet.getInt("Customer_ID");
                String customerName = rSet.getString("Customer_Name");
                String customerAddress = rSet.getString("Address");
                String customerPostalCode = rSet.getString("Postal_Code");
                String customerPhone = rSet.getString("Phone");
                Integer divisionID = rSet.getInt("Division_ID");
                Customer customer = new Customer(customerID, customerName, customerAddress, customerPostalCode, customerPhone, divisionID);
                customerList.add(customer);
            }
        }
        catch (SQLException throwables){throwables.printStackTrace();}
        return customerList;
    }

    /**
     * it automates an appointment id. 
     * @return appointment id
     */
    public static int generateAppointmentID()
    {
        int max = 0;
        ObservableList<Appointment> appointments = getAllAptm();
        for (Appointment a: appointments)
        {
            if (a.getAppointmentID() > max)
                max = a.getAppointmentID();
        }
        return max + 1;
    }

    /**
     * it converts the user's timezone to EST. 
     * @param tStamp
     * @return timezone in EST
     */
    public static Timestamp eastST(Timestamp tStamp)
    {
        tStamp = td.timeDisplay(tStamp, ZoneId.systemDefault().toString(), "US/Eastern");
        return tStamp;
    }
    /**
     * converts the UTC to the user's timezone. 
     * @param tStamp
     * @return user's timezone
     */
    public static Timestamp userTime(Timestamp tStamp)
    {
        tStamp = td.timeDisplay(tStamp, "UTC", ZoneId.systemDefault().toString());
        return tStamp;
    }

    /**
     * converts timestamp into UTC.
     * @param tStamp
     * @return timestamp in UTC
     */
    public static Timestamp coordinatedUT(Timestamp tStamp)
    {
        tStamp = td.timeDisplay(tStamp, ZoneId.systemDefault().toString(), "UTC");
        return tStamp;
    }

    /**
     * prevents the user making overlapping appointments for a customer. 
     * @return true if overlaps, false if not
     */
    private boolean overlappingAppointments()
    {
        boolean overlapping = false;
        ObservableList<Appointment> appointmentsList = getAllAptm();
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
        for (Appointment a: appointmentsList)
        {
            if(customerID == a.getCustomerID() && appointmentID != a.appointmentID)
                customerAppointments.add(a);
        }
        for (Appointment a: customerAppointments)
        {
            if((start.after(a.getStart()) && start.before(a.getEnd())) || (end.after(a.getStart()) && end.before(a.getEnd())) || start.equals(a.getStart())
            || start.equals(a.getEnd()) || end.equals(a.getEnd()) || (start.before(a.getStart()) && end.after(a.getStart())))
                overlapping = true;
        }
        return overlapping;
    }

    /**
     * to validate input for an appointment in the appointment add/modification form.
     * @return true if valid, false if not
     */
    public boolean isValid()
    {
        boolean valid = true;
        LocalTime open = LocalTime.of(7, 59);
        LocalTime close = LocalTime.of(23, 1);
        LocalDateTime startDate = eastST(start).toLocalDateTime();
        LocalDateTime endDate = eastST(end).toLocalDateTime();
        LocalTime startTime = LocalTime.of(eastST(start).toLocalDateTime().getHour(), start.toLocalDateTime().getMinute());
        LocalTime endTime = LocalTime.of(eastST(end).toLocalDateTime().getHour(), end.toLocalDateTime().getHour());

        if (startDate.isAfter(endDate))
        {valid = false;
         invalidEntry(1);}
        if (location.equals(""))
        {valid = false;
        invalidEntry(2);}
        if (title.equals(""))
        {valid = false;
        invalidEntry(3);}
        if (type.equals(""))
        {valid = false;
        invalidEntry(4);}
        if (description.equals(""))
        {valid = false;
        invalidEntry(5);}
        if (!(startTime.isAfter(open) && startTime.isBefore(close)))
        {valid = false;
        invalidEntry(6);}
        if (!(endTime.isAfter(open) && endTime.isBefore(close)))
        {valid = false;
        invalidEntry(7);}
        if (overlappingAppointments())
        {valid = false;
        invalidEntry(9);}
        if (customerID >= 0)
        {
            ObservableList<Customer> cusL = FXCollections.observableArrayList();
            boolean matched = false;
            cusL = getAllCustomers();
            for (Customer cus: cusL)
            {
                if(cus.getCustomerID() == customerID)
                {matched = true;}
            }
            if (!matched)
            {valid = false;
            invalidEntry(8);}
        }

        if (userID >= 0) {
            ObservableList<User> ulist = FXCollections.observableArrayList();
            boolean foundMatch = false;
            ulist = getAllUsers();
            for (User u : ulist) {
                if (u.getUserID() == userID) {
                    foundMatch = true;
                }
            }
            if (!foundMatch) {
                valid = false;
                invalidEntry(10);
            }
        }
        return valid;
    }
    
    /**
    * LAMBDA EXPRESSION appointmentAlert is USED ==> used to reduce lines of codes while create error messages (make lines easier to read).
     * @param inValid
    */
     public void invalidEntry (int inValid) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        LoginController.appointmentAlert aAlert = () -> "INVALID ENTRY: ";
        switch (inValid) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("TimeStamp ERROR");
                alert.setContentText(aAlert.alert() + "Appointment start time MUST be before end time.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Location ERROR");
                alert.setContentText(aAlert.alert() + "Appointment location CANNOT be empty!");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Title ERROR");
                alert.setContentText(aAlert.alert() + "Appointment title CANNOT be empty!");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Type ERROR");
                alert.setContentText(aAlert.alert() + "Appointment type CANNOT be empty!");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("Description ERROR");
                alert.setContentText(aAlert.alert() + "Appointment description CANNOT be empty!");
                alert.showAndWait();
                break;
            case 6:
                alert.setTitle("Error");
                alert.setHeaderText("StartTime ERROR");
                alert.setContentText(aAlert.alert() + "Appointment start time MUST be between 8:00 and 22:00 EST");
                alert.showAndWait();
                break;
            case 7:
                alert.setTitle("Error");
                alert.setHeaderText("EndTime ERROR");
                alert.setContentText(aAlert.alert() + "Appointment end time MUST be end between 8:00 and 22:00 EST");
                alert.showAndWait();
                break;
            case 8:
                alert.setTitle("Error");
                alert.setHeaderText("Customer ID ERROR");
                alert.setContentText(aAlert.alert() + "ID NOT FOUND: please enter a valid Customer ID!");
                alert.showAndWait();
                break;
            case 9:
                alert.setTitle("Error");
                alert.setHeaderText("Time Overlap ERROR");
                alert.setContentText(aAlert.alert() + "The appointment time of this customer overlaps with their existing appointment!");
                alert.showAndWait();
                break;
            case 10:
                alert.setTitle("Error");
                alert.setHeaderText("User ID ERROR");
                alert.setContentText(aAlert.alert() + "ID NOT FOUND: please enter a valid User ID!");
                alert.showAndWait();
                break;
        }
    }
    

    /**
     * LAMBDA EXPRESSION (from MainFormController) is USED ==> here a lambda expression is applied to reduces the amount of codes when converting timezones in different places. 
     * It provides a more clean code. 
     * @param inputTimeZone
     * @param outputTimeZone
     * @return
     */

    static timeDisplayConvertion td = (Timestamp tStamp, String sT, String eT) -> {
        LocalDateTime ldt = tStamp.toLocalDateTime();
        ZonedDateTime inputZDT = ldt.atZone(ZoneId.of(sT));
        ZonedDateTime outputZDT = inputZDT.withZoneSameInstant(ZoneId.of(eT));
        LocalDateTime ldtIn = outputZDT.toLocalDateTime();
        tStamp = Timestamp.valueOf(ldtIn);
        return tStamp;
    };
}
