package Controller;

/**
 *
 * @author Kun Xie
 */

import DBConnection.DBConnection;
import Model.Appointment;
import Model.Customer;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ResourceBundle;

/**
 * It initializes the MainForm of the application.
 */
public class MainFormController implements Initializable {
    /**
     * customer table. 
     */
    @FXML
    private TableView<Customer> cusTableView;
    /**
     * customer table customer id column. 
     */
    @FXML
    private TableColumn<Customer, Integer> cusIDColm;
    /**
     * customer table customer name column. 
     */
    @FXML
    private TableColumn<Customer, String> cusNameColm;
    /**
     * customer table customer address column. 
     */
    @FXML
    private TableColumn<Customer, String> cusAddressColm;
    /**
     * customer table customer zip code column. 
     */
    @FXML
    private TableColumn<Customer, String> cusPostalCodeColm;
    /**
     * customer table phone number column. 
     */
    @FXML
    private TableColumn<Customer, String> cusPhoneNumberColm;
    /**
     * customer table division id column. 
     */
    @FXML
    private TableColumn<Customer, Integer> cusDivisionIDColm;
    /**
     * appointment table.  
     */
    @FXML
    private TableView<Appointment> aptmTableView;
    /**
     * appointment table appointment id column. 
     */
    @FXML
    private TableColumn<Appointment, Integer> aptmIDColm;
    /**
     * appointment table title column. 
     */
    @FXML
    private TableColumn<Appointment, String> aptmTitleColm;
    /**
     * appointment table description column. 
     */
    @FXML
    private TableColumn<Appointment, String> aptmDescriptionColm;
    /**
     * appointment table location column. 
     */
    @FXML
    private TableColumn<Appointment, String> aptmLocationColm;
    /**
     * appointment table contact id column. 
     */
    @FXML
    private TableColumn<Appointment, Integer> aptmContactIDColm;
    /**
     * appointment table type column. 
     */
    @FXML
    private TableColumn<Appointment, String> aptmTypeColm;
    /**
     * appointment table start date/time column. 
     */
    @FXML
    private TableColumn<Appointment, Timestamp> aptmStartColm;
    /**
     * appointment table end date/time column. 
     */
    @FXML
    private TableColumn<Appointment, Timestamp> aptmEndColm;
    /**
     * appointment table customer id column. 
     */
    @FXML
    private TableColumn<Appointment, Integer> aptmCustomerColm;
     /**
     * appointment table user id column. 
     */
    @FXML
    private TableColumn<Appointment, Integer> aptmUserColm;
    /**
     * appointment table view all option. 
     */
    @FXML
    private RadioButton aptmAllRadio;
    /**
     * appointment table view current week. 
     */
    @FXML
    private RadioButton aptmWeekRadio;
    /**
     * appointment table view current month. 
     */
    @FXML
    private RadioButton aptmMonthRadio;
    /**
     * label text for appointment within 15 minutes. 
     */
    @FXML
    private Label aptmIn15Label;
    /**
     * list of appointments.
     */
    private ObservableList<Appointment> aptmL;
    /**
     * boolean addCustomer false.
     */
    private static boolean cusAddition = false;
    /**
     * boolean addAppointment false. 
     */
    private static boolean aptmAddition = false;
    /**
     * selected customer for modification. 
     */
    private static Customer selectedCus;
    /**
     * selected appointment for modification. 
     */
    private static Appointment selectedAptm;

    /**
     * it populates both the appointment and customer tables and check if there's an appointment within the next 15 minutes or not. 
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {   cusAddition = false;

        ObservableList<Customer> cusL = getAllCustomers();
        aptmL = getAllAppointments();

        cusIDColm.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
        cusNameColm.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        cusAddressColm.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        cusPostalCodeColm.setCellValueFactory(new PropertyValueFactory<Customer, String>("zipcode"));
        cusPhoneNumberColm.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
        cusDivisionIDColm.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("divisionID"));
        cusTableView.setItems(cusL);
        
        aptmIDColm.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));
        aptmTitleColm.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        aptmDescriptionColm.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        aptmLocationColm.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        aptmTypeColm.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        aptmStartColm.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("start"));
        aptmEndColm.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("end"));
        aptmCustomerColm.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
        aptmContactIDColm.setCellValueFactory(new PropertyValueFactory<Appointment, Integer> ("contactID"));
        aptmUserColm.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userID"));
        aptmTableView.setItems(aptmL);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fifteenFromNow =LocalDateTime.now().plusMinutes(15);
        String in15MinNotification = "";
        boolean aptmIn15Min = false;
        for (Appointment aptm: aptmL)
        {
            LocalDateTime aptLDT = aptm.getStart().toLocalDateTime();
            if(aptLDT.isAfter(now) && aptLDT.isBefore(fifteenFromNow))
            {aptmIn15Min = true;
             in15MinNotification = in15MinNotification + "ID: " + aptm.getAppointmentID() + " Start: " + aptm.getStart();}
        }
        if(aptmIn15Min){aptmIn15Label.setText(in15MinNotification);}
        else{in15MinNotification = "No appointments within 15 minutes.";
             aptmIn15Label.setText(in15MinNotification);}
    }
    
    /**
     *this ObservableList gets all records of appointments from the database. 
     * @return all appointments
     */
    public static ObservableList<Appointment> getAllAppointments()
    {
        ObservableList<Appointment> aptmL = FXCollections.observableArrayList();
        try
        {
            String sql = "SELECT * FROM appointments";
            PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rSet = pStatement.executeQuery();
            while (rSet.next())
            {int aptmID = rSet.getInt("Appointment_ID");
             String aptmTitle = rSet.getString("Title");
             String aptmDes = rSet.getString("Description");
             String aptmLoc = rSet.getString("Location");
             String aptmType = rSet.getString("Type");
             Timestamp aptmStart = rSet.getTimestamp("Start");
             aptmStart = Appointment.userTime(aptmStart);
             Timestamp aptmEnd = rSet.getTimestamp("End");
             aptmEnd = Appointment.userTime(aptmEnd);
             int cusID = rSet.getInt("Customer_ID");
             int uID = rSet.getInt("User_ID");
             int conID = rSet.getInt("Contact_ID");
             Appointment appointment = new Appointment(aptmID, aptmTitle, aptmDes,
             aptmLoc, aptmType, aptmStart, aptmEnd, cusID, uID, conID);
             aptmL.add(appointment);}
        }
        catch (SQLException throwables){throwables.printStackTrace();}
        return aptmL;
    }
    
    /**
     * this Observablelist gets all records of customers from the database. 
     * @return all customers
     */
    public static ObservableList<Customer> getAllCustomers()
    {
        ObservableList<Customer> cusL = FXCollections.observableArrayList();

        try
        {
            String sql = "SELECT * FROM customers";
            PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rSet = pStatement.executeQuery();

            while (rSet.next())
            {
                Integer cusID = rSet.getInt("Customer_ID");
                String cusN = rSet.getString("Customer_Name");
                String cusA = rSet.getString("Address");
                String cusPC = rSet.getString("Postal_Code");
                String cusP = rSet.getString("Phone");
                Integer dID = rSet.getInt("Division_ID");
                Customer customer = new Customer(cusID, cusN, cusA, cusPC, cusP, dID);
                cusL.add(customer);
            }
        }
        catch (SQLException throwables){throwables.printStackTrace();}
        return cusL;
    }

    /**
     * this method populates the appointment table based on the radio button selected: all, month or week. 
     * @param actionEvent
     * @throws IOException
     */
    public void radioButtonPressed(ActionEvent actionEvent) throws IOException
    {

        LocalDateTime now = LocalDateTime.now();
        int weekNow = now.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        int monNow = now.getMonthValue();
        int yrNow = now.getYear();
        ObservableList<Appointment> weekNowAptm = FXCollections.observableArrayList();
        ObservableList<Appointment> monNowAptm = FXCollections.observableArrayList();
        if(aptmWeekRadio.isSelected()) {
            for (Appointment aptm : aptmL) {
                LocalDateTime ldt = aptm.getStart().toLocalDateTime();
                int week = ldt.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
                int year = ldt.getYear();
                if (week == weekNow && year == yrNow)
                    weekNowAptm.add(aptm);}
            aptmTableView.setItems(weekNowAptm);
        }

        if(aptmMonthRadio.isSelected()) {
            for (Appointment aptm : aptmL) {
                LocalDateTime ldt = aptm.getStart().toLocalDateTime();
                int month = ldt.getMonthValue();
                int year = ldt.getYear();
                if (month == monNow && year == yrNow)
                    monNowAptm.add(aptm);}
            aptmTableView.setItems(monNowAptm);
        }
        if(aptmAllRadio.isSelected()){aptmTableView.setItems(aptmL);}
    }

    /**
     * this button initiates the Add Customer form. 
     * @param e
     * @throws IOException
     */
    public void cusAddBtnPressed(ActionEvent e) throws IOException {cusAddition = true;
                                                                    fc.formChange("../View/CustomerAdd_or_Mod.FXML", e);}

    /**
     * this button initiates the Modify Customer form. 
     * @param e
     * @throws IOException
     */
    public void cusModifyBtnPressed(ActionEvent e) throws IOException
    {
        selectedCus = cusTableView.getSelectionModel().getSelectedItem();
        if (selectedCus == null){invalidEntry(1); }
        else{cusAddition = false;
             fc.formChange("../View/CustomerAdd_or_Mod.FXML", e);}
    }

    /**
     * it deletes a selected customer from record. 
     * @param e
     * @throws IOException
     */
    public void cusDeleteBtnPressed(ActionEvent e) throws IOException
    {selectedCus = cusTableView.getSelectionModel().getSelectedItem();
        if (selectedCus == null){invalidEntry(2);}
        else {
            boolean delValidation = true;
            for (Appointment aptm : aptmL) {
                if (aptm.getCustomerID() == selectedCus.getCustomerID()) {
                    delValidation = false;}}
            if (delValidation) {
                int input = JOptionPane.showConfirmDialog(null, "Are you SURE you want to delete the selected customer?");
                if (input == 0) {
                    try {
                        String sql = "DELETE FROM customers WHERE Customer_ID = " + selectedCus.getCustomerID();
                        PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(sql);
                        pStatement.executeUpdate(sql);

                        }
                    catch (SQLException throwables){throwables.printStackTrace();}
                    ObservableList<Customer> allCusL = getAllCustomers();
                    cusTableView.setItems(allCusL);
                }
            }
            else{invalidEntry(3);}
        }
    }

    /**
     * it initiates the Add Appointment form.
     * @param e
     * @throws IOException
     */
    public void aptmAddBtnPressed(ActionEvent e) throws IOException{aptmAddition = true;
                                                                    fc.formChange("../View/AppointmentAdd_or_Mod.FXML", e);}

    /**
     * it initiates the Modify Appointment form.
     * @param e
     * @throws IOException
     */
    public void aptmModifyBtnPressed(ActionEvent e) throws IOException
    {selectedAptm = aptmTableView.getSelectionModel().getSelectedItem();
     if (selectedAptm == null){JOptionPane.showMessageDialog(null, "You DIDN'T select an appointment to modify!");}
     else{aptmAddition = false;
         fc.formChange("../View/AppointmentAdd_or_Mod.FXML", e);}
    }

    /**
     * it deletes the selected appointment from record. 
     * @param e
     * @throws IOException
     */
    public void aptmDeleteBtnPressed(ActionEvent e) throws IOException {
        selectedAptm = aptmTableView.getSelectionModel().getSelectedItem();
        if (selectedAptm == null){JOptionPane.showMessageDialog(null, "You DIDN'T select an appointment to delete!");}
        else {
            int input = JOptionPane.showConfirmDialog(null, "Do you want to remove Appointment ID: " + selectedAptm.getAppointmentID() + " Name: " + selectedAptm.getTitle() + "?");
            if (input == 0) {
                 try {String sql = "DELETE FROM appointments WHERE Appointment_ID = " + selectedAptm.getAppointmentID();
                        PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(sql);
                        pStatement.executeUpdate(sql);}
        catch (SQLException throwables){throwables.printStackTrace();}
                aptmL = getAllAppointments();
                aptmTableView.setItems(aptmL);
            }
        }
    }
    
    /**
    * LAMBDA EXPRESSION appointmentAlert is USED ==> used to reduce lines of codes while create error messages (make lines easier to read).
     * @param inValid
    */
     public void invalidEntry (int inValid) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        LoginController.appointmentAlert aAlert = () -> "INVALID ENTRY: ";
        LoginController.appointmentAlert bAlert = () -> "CONFIRM ENTRY: ";
        switch (inValid) {
            case 1:
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Modification Selection ERROR");
                errorAlert.setContentText(aAlert.alert() + "You DIDN'T select a customer to modify!");
                errorAlert.showAndWait();
                break;
            case 2:
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Deletion Selection ERROR");
                errorAlert.setContentText(aAlert.alert() + "You DIDN'T select a customer to delete!");
                errorAlert.showAndWait();
                break;
            case 3:
                confirmationAlert.setTitle("Confirmation");
                confirmationAlert.setHeaderText("Attention");
                confirmationAlert.setContentText(bAlert.alert() + "Delete the appointments associated with this customer first!");
                confirmationAlert.showAndWait();
                break;}}
    
    /**
     *It returns true if add customer button is selected. 
     * @return
     */
    public static boolean getAddCustomer() { return cusAddition; }

    /**
     *It returns true if add appointment button is selected. 
     * @return 
     */
    public static boolean getAddAppointment() {return aptmAddition; }

    /**
     * display the general report form
     * @param aEvent
     * @throws IOException
     */
    public void rptGenerateBtnPressed(ActionEvent aEvent) throws IOException {fc.formChange("../View/Reports.FXML", aEvent);}

     /**
     * LAMBDA EXPRESSION (from MainFormController) is USED ==> here a lambda expression is applied to reduces the amount of codes when converting timezones in different places. 
     * It provides a more clean code. 
     */
     formFunctionChange fc = (String form, ActionEvent aEvent) ->
    {
        Parent addProductParent = FXMLLoader.load(getClass().getResource(form));
        Scene addProductScene = new Scene(addProductParent);
        Stage window = (Stage) ((Node) aEvent.getSource()).getScene().getWindow();
        window.setScene(addProductScene);
        window.show();
    };
    
    /**
     * it returns the user back to the login screen. 
     * @param e
     * @throws IOException
     */
    public void logoutBtnPressed(ActionEvent e) throws IOException{fc.formChange("../View/Login.FXML", e);}
    
    /**
     * interface used for LAMBDA EXPRESSION  ==>  this lambda expression is created to reduce lines of codes where form change occurs in multiple places in this application. 
     */
    public interface formFunctionChange {
        /**
         *  interface used for LAMBDA EXPRESSION  ==>  this lambda expression is created to reduce lines of codes where form change occurs in multiple places in this application. 
         * @param form
         * @param aEvent
         * @throws IOException
         */
        void formChange (String form, ActionEvent aEvent) throws IOException;
    }
   
    /**
     * interface used for LAMBDA EXPRESSION  ==>  this lambda expression is created to reduce lines of codes when converting timezones in multiple places in this application.
     */
    public static interface timeDisplayConvertion {
        /**
         *  interface used for LAMBDA EXPRESSION  ==> this lambda expression is created to reduce lines of codes when converting timezones in multiple places in this application.
         * @param tStamp
         * @param sT
         * @param eT
         * @return
         */
        Timestamp timeDisplay(Timestamp tStamp, String sT, String eT);
    }

    /**
     *It selects a customer from the customer table. 
     * @return a selected customer
     */
    public static Customer getSelectedCustomer() {return selectedCus;}

    /**
     *It selects an appointment from the appointment table. 
     * @return a selected appointment
     */
    public static Appointment getSelectedAppointment() {return selectedAptm;}

}
