package Controller;

import Model.Appointment;
import Model.Contact;
import Model.Customer;
import DBConnection.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * This class creates the report form that displays detailed appointment reports. 
 */
public class ReportsController implements Initializable {
    /**
     * appointment report table. 
     */
    @FXML
    private TableView<Appointment> appointmentReportTbl;
    /**
     * appointment report table appointment id column. 
     */
    @FXML
    private TableColumn<Appointment, String> ApptIDClm;
    /**
     * appointment report table title column. 
     */
    @FXML
    private TableColumn<Appointment, String> titleClm;
    /**
     * appointment report table type column. 
     */
    @FXML
    private TableColumn<Appointment, String> typeClm;
    /**
     * appointment report table description column. 
     */
    @FXML
    private TableColumn<Appointment, String> DescClm;
    /**
     * appointment report table start date/time column. 
     */
    @FXML
    private TableColumn<Appointment, String> startClm;
    /**
     * appointment report table end date/time column. 
     */
    @FXML
    private TableColumn<Appointment, String> endClm;
    /**
     * appointment report table customer id column. 
     */
    @FXML
    private TableColumn<Appointment, String> customerIDClm;
    /**
     * total number of appointments by month and type. 
     */
    @FXML
    public Label totalNum;
    /**
     * total number of appointment by customer. 
     */
    @FXML
    private Label totalNumbyCus;
    /**
     * combobox to select a customer. 
     */
    @FXML
    private ComboBox<Customer> customerComboBox;
    /**
     * combobox to select a month.
     */
    @FXML
    private ComboBox<Month> monthComboBox;
    /**
     * combobox to select a type. 
     */
    @FXML
    private ComboBox<Appointment> typeComboBox;
    /**
     * combobox to select a contact. 
     */
    @FXML
    private ComboBox<Contact> contactComboBox;
    
    /**
     * list of months. 
     */
    @FXML
    
    private ObservableList<Month> monL;
    /**
     * count list of appointments by month and type. 
     */
    public ObservableList<String> countByMonTypeL;
    /**
     * count list of appointments by customer.
     */
    public ObservableList<String> countCusL;
    /**
     * list of appointments.
     */
    private ObservableList<Appointment> aptmL;
    /**
     * an appointment object.
     */
    private Appointment aptm;

    
    
    /**
     * this method initiate the report form. 
     * @param url
     * @param resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        try {typeComboBox.setItems(getAllAptm());
            contactComboBox.setItems(getAllCon());
            monthComboBox.setItems(listOfMonths());
            customerComboBox.setItems(getAllCustomers());} 
        catch (SQLException ex) {Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, ex);}
    }
    
    /**
     *this Observablelist gets a list of months.
     * @return months
     */
    public ObservableList<Month> listOfMonths(){
        monL = FXCollections.observableArrayList();
        for(Month month : Month.values()){monL.add(month);}
        return monL;
    }
    
    /**
     * list of contacts. 
     */
    private ObservableList<Contact> conL = FXCollections.observableArrayList();

    /**
     * this Observablelists gets all records of contacts from database. 
     * @return all contacts
     */
    public ObservableList<Contact> getAllCon() {
        
        try {
            conL.clear();
            String contactQuery = "SELECT * FROM contacts";
            PreparedStatement contactSQL = DBConnection.myConn.prepareStatement(contactQuery);
            ResultSet result = contactSQL.executeQuery();

            while (result.next()) {int contactID = result.getInt("Contact_ID");
                                    String contactName = result.getString("Contact_Name");
                                    String contactEmail = result.getString("Email");
                                    conL.add(new Contact(contactID, contactName, contactEmail));}
        } catch (SQLException throwables) {throwables.printStackTrace();}
        return conL;
    }
    
    /**
     * list of customers. 
     */
    private ObservableList<Customer> listOfCustomers = FXCollections.observableArrayList();
    
    /**
     *this Observablelist gets all records of customers from database. 
     * @return all customers
     * @throws SQLException
     */
    public ObservableList<Customer> getAllCustomers() throws SQLException {
        
        try{
            listOfCustomers.clear();
            String customerQuery = "SELECT * FROM customers";
            PreparedStatement customerSQL = DBConnection.myConn.prepareStatement(customerQuery);
            ResultSet result = customerSQL.executeQuery();
            
             while (result.next()) {
                Integer customerID = result.getInt("Customer_ID");
                String customerName = result.getString("Customer_Name");
                String address = result.getString("Address");
                String zipcode = result.getString("Postal_Code");
                String phoneNumber = result.getString("Phone");
                Integer divisionID = result.getInt("Division_ID");

                listOfCustomers.add(new Customer(customerID, customerName, address, zipcode, phoneNumber, divisionID));
            }
        }
        catch(SQLException throwables) {throwables.printStackTrace();}
        return listOfCustomers;
    }
    
    /**
     *this Observablelist creates report of appointments based on month and type. 
     * @return appointments based on month and type
     * @throws SQLException
     */
    public ObservableList<String> countAptmByTypeAndMon() throws SQLException {
        countByMonTypeL = FXCollections.observableArrayList();
        String countByType = "SELECT Count(*) FROM appointments WHERE EXTRACT(MONTH FROM Start) = ? AND Type = ?";
        PreparedStatement countByTypeSQL = DBConnection.myConn.prepareStatement(countByType);
        countByTypeSQL.setInt(1, monthComboBox.getValue().getValue());
        countByTypeSQL.setString(2, typeComboBox.getValue().getType());
        ResultSet result = countByTypeSQL.executeQuery();
        while (result.next()){countByMonTypeL.add(result.getString("Count(*)"));}
        countByTypeSQL.close();
        return countByMonTypeL;

    }
    
    /**
     *this Observablelist creates report of appointments based on a selected customer. 
     * @return appointments of a selected customer
     * @throws SQLException
     */
    public ObservableList<String> countByCus() throws SQLException {
        countCusL = FXCollections.observableArrayList();
        String countByCustomer = "SELECT Count(*) FROM appointments WHERE Customer_ID = ?";
        PreparedStatement countByCustomerSQL = DBConnection.myConn.prepareStatement(countByCustomer);
        countByCustomerSQL.setInt(1,customerComboBox.getValue().getCustomerID());
        ResultSet result = countByCustomerSQL.executeQuery();
        while (result.next()){countCusL.add(result.getString("Count(*)"));}
        countByCustomerSQL.close();
        return countCusL;
        
    }
    
    /**
     *this Observablelist gets all records of appointments from the database. 
     * @return all appointments 
     * @throws SQLException
     */
    public ObservableList<Appointment> getAllAptm() throws SQLException {
        ObservableList<Appointment> aptmL = FXCollections.observableArrayList();
        String apptQuery = "SELECT * FROM appointments";
        PreparedStatement allApptSQL = DBConnection.myConn .prepareStatement(apptQuery);
        ResultSet result = allApptSQL.executeQuery();
        while (result.next()) {
            Appointment appointment = new Appointment(
                    result.getInt("Appointment_ID"),
                    result.getString("Title"),
                    result.getString("Description"),
                    result.getString("Location"),result.getString("Type"),
                    result.getTimestamp("Start"),result.getTimestamp("End"),
                    result.getInt("Customer_ID"),result.getInt("User_ID"),result.getInt("Contact_ID"));
            aptmL.add(appointment);
        }
        return aptmL;
    }
    
    /**
     *this Observablelist creates report based on a selected contact. 
     * @param contact
     * @return appointments of a selected contact 
     * @throws SQLException
     */
    public ObservableList<Appointment> displayByCon(Contact contact) throws SQLException {
        aptmL = FXCollections.observableArrayList();
        String reportsByContact = "SELECT * from appointments join contacts on contacts.Contact_ID = appointments.Contact_ID WHERE contacts.Contact_ID = ?";
        PreparedStatement reportsByContactSQL = DBConnection.myConn.prepareStatement(reportsByContact);
        reportsByContactSQL.setInt(1, contact.getContactID());
        ResultSet result = reportsByContactSQL.executeQuery();
        while (result.next()) {
            aptm = new Appointment(
                    result.getInt("Appointment_ID"),
                    result.getString("Title"),
                    result.getString("Description"),
                    result.getString("Location"),
                    result.getString("Type"),
                    result.getTimestamp("Start"),
                    result.getTimestamp("End"),result.getInt("Customer_ID"),
                    result.getInt("User_ID"),result.getInt("Contact_ID"));
            aptmL.add(aptm);;
        }
        reportsByContactSQL.close();
        return aptmL;
    }
    
    /**
     *this method counts the total amount of appointments based on selected month and type when you press the generate button. 
     * @throws SQLException
     */
    public void onCountNum() throws SQLException {
        
        if(monthComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Required: select a month");
            Optional<ButtonType> result = alert.showAndWait();
        }
        if(typeComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Required: select a type");
            Optional<ButtonType> result = alert.showAndWait();
        }
        totalNum.setText(countAptmByTypeAndMon().get(0));
    }
    
    /**
     *this method counts the total amount of appointments based on a selected customer. 
     * @throws SQLException
     */
    public void onCustomerSelect() throws SQLException {totalNumbyCus.setText(countByCus().get(0));}
        
    /**
     *this method populate appointment details in the appointment report table based on a selected contact. 
     * @throws SQLException
     */
    public void onContactSelect() throws SQLException {

        ApptIDClm.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleClm.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeClm.setCellValueFactory(new PropertyValueFactory<>("type"));
        DescClm.setCellValueFactory(new PropertyValueFactory<>("description"));
        startClm.setCellValueFactory(new PropertyValueFactory<>("start"));
        endClm.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDClm.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentReportTbl.setItems(displayByCon(contactComboBox.getValue()));

    }

    /**
     * LAMBDA EXPRESSION (from MainFormController) is USED ==> here a lambda expression is applied to reduces the amount of codes when converting timezones in different places. 
     * It provides a more clean code. It returns the user back to the MainForm. 
     * @param event
     * @throws IOException
     */
    @FXML
    void exitButtonPushed(ActionEvent aEvent) throws IOException {

        fc.formChange("../View/MainForm.FXML", aEvent);
    }
     /**
     * LAMBDA EXPRESSION (from MainFormController) is USED ==> here a lambda expression is applied to reduces the amount of codes when converting timezones in different places. 
     * It provides a more clean code. 
     */
     MainFormController.formFunctionChange fc = (String form, ActionEvent aEvent) ->
    {
        Parent addProductParent = FXMLLoader.load(getClass().getResource(form));
        Scene addProductScene = new Scene(addProductParent);
        Stage window = (Stage) ((Node) aEvent.getSource()).getScene().getWindow();
        window.setScene(addProductScene);
        window.show();
    };
}

