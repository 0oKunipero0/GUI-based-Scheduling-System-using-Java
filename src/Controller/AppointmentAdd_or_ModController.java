package Controller;

/**
 *
 * @author Kun Xie
 */

import Controller.MainFormController.formFunctionChange;
import DBConnection.DBConnection;
import Model.Appointment;
import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * This class creates a form you can add or modify appointments. 
 */
public class AppointmentAdd_or_ModController implements Initializable {
    

    /**
     * appointment add/modify form appointment id text field.
     */
    @FXML
    private TextField aptmIDField;
    /**
     * appointment add/modify form customer id text field.
     */
    @FXML
    private TextField cusIDField;
    /**
     * appointment add/modify form user id text field.
     */
    @FXML
    private TextField userIDField;
    /**
     * appointment add/modify form title text field.
     */
    @FXML
    private TextField titleField;
    /**
     * appointment add/modify form description text field.
     */
    @FXML
    private TextField descriptionField;
    /**
     * appointment add/modify form location text field.
     */
    @FXML
    private TextField locationField;
    /**
     * appointment add/modify form type text field.
     */
    @FXML
    private TextField typeField;
    /**
     * appointment add/modify form start date calendar.
     */
    @FXML
    private DatePicker startDateCal;
    /**
     * appointment add/modify form start time combobox.
     */
    @FXML
    private ComboBox<LocalTime> startTimeComboBox;
    /**
     * appointment add/modify form end date calendar.
     */
    @FXML
    private DatePicker endDateCal;
    /**
     * appointment add/modify form end time combobox.
     */
    @FXML
    private ComboBox<LocalTime> endTimeComboBox;
    /**
     * appointment add/modify form contact combobox. 
     */
    @FXML
    private ComboBox<Contact> contactComboBox;
    /**
     * title of the appointment add/modify form.
     */
    @FXML
    private Label titleLabel;
    /**
     * boolean true/false for add appointment. 
     */
    private boolean addAppointment;
    /**
     * selected appointment used for modification.
     */
    private Appointment selectedAppointment;

    /**
     * start TimeStamp.
     */
    private Timestamp startTS;
    /**
     * end TimeStamp.
     */
    private Timestamp endTS;
    /**
     * start date.
     */
    private LocalDate startDate;
    /**
     * end date.
     */
    private LocalDate endDate;
    /**
     * start time.
     */
    private LocalTime startTime;
    /**
     * end time. 
     */
    private LocalTime endTime;
    /**
     * customer id. 
     */
    private int customerID;
    /**
     * user id. 
     */
    private int userID;


    /**
     * Allows the user to add customer, or populates fields of customer records if the user chooses to modify.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int appointmentID = 0;

        ObservableList<Contact> contacts = getAllContacts();
        contactComboBox.setItems(contacts);
        ObservableList<LocalTime> times = populateTimeComboBox();
        startTimeComboBox.setItems(times);
        endTimeComboBox.setItems(times);

        addAppointment = MainFormController.getAddAppointment();
        if (addAppointment) {titleLabel.setText("Appointment Addition");
            appointmentID = Appointment.generateAppointmentID();
            aptmIDField.setText(String.valueOf(appointmentID));
        } else {selectedAppointment = MainFormController.getSelectedAppointment();
            startTS = selectedAppointment.getStart();
            endTS = selectedAppointment.getEnd();
            startDate = startTS.toLocalDateTime().toLocalDate();
            endDate = endTS.toLocalDateTime().toLocalDate();
            startTime = startTS.toLocalDateTime().toLocalTime();
            startTimeComboBox.getSelectionModel().select(startTime);
            endTime = endTS.toLocalDateTime().toLocalTime();
            endTimeComboBox.getSelectionModel().select(endTime);
            Contact c = getContact(selectedAppointment.getContactID());

            titleLabel.setText("Appointment Update");
            aptmIDField.setText(String.valueOf(selectedAppointment.getAppointmentID()));
            cusIDField.setText(String.valueOf(selectedAppointment.getCustomerID()));
            userIDField.setText(String.valueOf(selectedAppointment.getUserID()));
            titleField.setText(selectedAppointment.getTitle());
            descriptionField.setText(selectedAppointment.getDescription());
            locationField.setText(selectedAppointment.getLocation());
            typeField.setText(selectedAppointment.getType());
            startDateCal.setValue(startDate);
            endDateCal.setValue(endDate);
            contactComboBox.getSelectionModel().select(c);
        }
    }
    
    /**
     *it gets a contact id from the database.
     * @param contactID
     * @return contact id
     */
    public static Contact getContact(int contactID)
    {
        Contact contact = new Contact(0, "", "");
        try{String sql ="SELECT * FROM contacts WHERE Contact_ID = " + contactID;
            PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rSet = pStatement.executeQuery();
            while (rSet.next())
            {String contactName = rSet.getString("Contact_Name");
             String contactEmail = rSet.getString("Email");
             contact.setContactID(contactID);
             contact.setContactName(contactName);
             contact.setContactEmail(contactEmail);}}
        catch (SQLException throwables){throwables.printStackTrace();}
        return contact;
    }
    
    /**
     *it gets all contact records from the database.
     * @return all contacts
     */
    public static ObservableList<Contact> getAllContacts()
    {
        ObservableList<Contact> listOfAllContacts = FXCollections.observableArrayList();
        {try {String sql = "SELECT * FROM contacts";
              PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(sql);
              ResultSet rSet = pStatement.executeQuery();
              while (rSet.next()) {int contactID = rSet.getInt("Contact_ID");
                                   String contactName = rSet.getString("Contact_Name");
                                   String contactEmail = rSet.getString("Email");
                                   Contact country = new Contact(contactID, contactName, contactEmail);
                                   listOfAllContacts.add(country);}
            } catch (SQLException throwables) {throwables.printStackTrace();}
            return listOfAllContacts;
        }
    }
    /**
     * It returns the user to the MainForm.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void exitBtnPressed (ActionEvent actionEvent) throws IOException {fc.formChange("../View/MainForm.fxml", actionEvent);}
    
    /**
     *LAMBDA EXPRESSION (from MainFormController) is USED ==> here a lambda expression is applied to reduces the amount of codes when changing forms in multiple locations. 
     *It provides a more clean code.
     * @param s
     * @param actionEvent
     * @throws IOException
     */

    formFunctionChange fc= (String form, ActionEvent aEvent) ->
    {Parent addProductParent = FXMLLoader.load(getClass().getResource(form));
        Scene addProductScene = new Scene(addProductParent);
        Stage window = (Stage) ((Node) aEvent.getSource()).getScene().getWindow();
        window.setScene(addProductScene);};

    /**
     * this method is used to save newly added appointment to the database, or update existing appointments in record.
     * @param event
     * @throws IOException
     */
    @FXML
    void saveBtnPressed(ActionEvent event) throws IOException {
        try
        {
            customerID = Integer.parseInt(cusIDField.getText());
            userID = Integer.parseInt(userIDField.getText());
        }
        catch (NumberFormatException numberFormatException)
        {invalidEntry(2);}
        if (contactComboBox.getSelectionModel().getSelectedItem() != null && customerID >= 0 && userID >= 0)
        {
            try {
            startDate = startDateCal.getValue();
            endDate = endDateCal.getValue();
            startTime = startTimeComboBox.getSelectionModel().getSelectedItem();
            endTime = endTimeComboBox.getSelectionModel().getSelectedItem();
            LocalDateTime startLDT = LocalDateTime.of(startDate, startTime);
            startTS = Timestamp.valueOf(startLDT);
            LocalDateTime endLDT = LocalDateTime.of(endDate, endTime);
            endTS = Timestamp.valueOf(endLDT);
            }
            catch ( NullPointerException nullPointerException)
            {invalidEntry(1);}

            Appointment aptm = new Appointment(Integer.parseInt(aptmIDField.getText()), titleField.getText(),descriptionField.getText(), locationField.getText(), typeField.getText(), startTS,endTS, customerID, userID,contactComboBox.getSelectionModel().getSelectedItem().getContactID());
            if (aptm.isValid())
            {
                if (addAppointment)
                {
                    Timestamp tStamp = new Timestamp(System.currentTimeMillis());
                    tStamp = Appointment.coordinatedUT(tStamp);
                    Timestamp start = aptm.getStart();
                    start = Appointment.coordinatedUT(start);
                    aptm.setStart(start);
                    Timestamp end = aptm.getEnd();
                    end = Appointment.coordinatedUT(end);
                    aptm.setEnd(end);
                try
                   {
                    String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By" +
                    ", Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES (" + aptm.getAppointmentID() + ", '" + aptm.getTitle() + "', '" + aptm.getDescription() + "', '" + aptm.getLocation() + "', '" + aptm.getType() + "', '" + start + "', '" + end + "', '" + tStamp + "', '" + LoginController.getUser().getUserName() + "', '" + tStamp + "', '" + LoginController.getUser().getUserName() + "', " + aptm.getCustomerID() + ", " + aptm.getUserID() + ", " + aptm.getContactID() + ")";
                    PreparedStatement pStatement= DBConnection.getConnection().prepareStatement(sql);
                    pStatement.executeUpdate(sql);
                    }
                catch (SQLException throwables){throwables.printStackTrace();}
                }
                else
                {
                    Timestamp tStamp = new Timestamp(System.currentTimeMillis());
                    tStamp = Appointment.coordinatedUT(tStamp);
                    Timestamp start = aptm.getStart();
                    start = Appointment.coordinatedUT(start);
                    aptm.setStart(start);
                    Timestamp end = aptm.getEnd();
                    end = Appointment.coordinatedUT(end);
                    aptm.setEnd(end);

                    try {
                        String sql = "UPDATE appointments " +"SET Title = '" + aptm.getTitle() + "', " +"Description = '" + aptm.getDescription() + "', " +"Location = '" + aptm.getLocation() + "', " +"Type = '" + aptm.getType() + "', " +"Start = '" + aptm.getStart() + "', " +"End = '" + aptm.getEnd() + "', " +"Last_Update = '" + tStamp + "', " +"Last_Updated_By = '" + LoginController.getUser().getUserName() + "', " +"Customer_ID = " + aptm.getCustomerID() + ", " +"User_ID = " + aptm.getUserID() + ", " +"Contact_ID =" + aptm.getContactID() + " " +"WHERE Appointment_ID = " + aptm.getAppointmentID();
                        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                        ps.executeUpdate(sql);
                    }
                    catch (SQLException throwables)
                    {throwables.printStackTrace();}
                }
                fc.formChange("../View/MainForm.fxml", event);
            }
        }
    }
    
    /**
     * generates time combobox in 5 minutes increments. 
     * @return list of all LocalTimes
     */
    private ObservableList<LocalTime> populateTimeComboBox()
    {
        ObservableList<LocalTime> timeList = FXCollections.observableArrayList();
        for (int i = 0; i < 24; i++)
        {for ( int j = 0; j < 12; j++)
         {LocalTime local_t = LocalTime.of(i, j * 5);
          timeList.add(local_t );}}
        return timeList;
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
                alert.setHeaderText("Empty TimeStamp ERROR");
                alert.setContentText(aAlert.alert() + "please make sure you have entered a valid Start/End date and Start/End time!");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Empty ID ERROR");
                alert.setContentText(aAlert.alert() + "Please enter customer and user ID!");
                alert.showAndWait();
                break;}}
}
