package Controller;

/**
 *
 * @author Kun Xie
 */

import Controller.MainFormController.formFunctionChange;
import DBConnection.DBConnection;
import Model.Country;
import Model.Customer;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;

/**
 * This class creates a form you can add or modify customer. 
 */
public class CustomerAdd_or_ModController implements Initializable {
    /**
     * title of the customer add/modify form. 
     */
    @FXML
    private Label titleLabel;
    /**
     * id of a customer object. 
     */
    @FXML
    private TextField cusIDField;
    /**
     * name of a customer object. 
     */
    @FXML
    private TextField cusNameField;
    /**
     * address of a customer object. 
     */
    @FXML
    private TextField cusAddressField;
    /**
     * postal code of a customer object. 
     */
    @FXML
    private TextField cusPostalCodeField;
    /**
     * phone number of a customer object. 
     */
    @FXML
    private TextField cusPhoneField;
    /**
     * country of a customer object. 
     */
    @FXML
    private ComboBox<Country> countryComboBox;
    /**
     * state/province of a customer object. 
     */
    @FXML
    private ComboBox<Division> stateComboBox;
    /**
     * selected customer for modification. 
     */
    private Customer selectedCustomer;
    /**
     * boolean true/false for add customer. 
     */
    boolean addCustomer = MainFormController.getAddCustomer();

    /**
     * Allows the user to add customer, or populates fields of customer records if the user chooses to modify. 
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        int customerID = 0;
        ObservableList<Country> countries = getAllCountries();
        countryComboBox.setItems(countries);
        if(addCustomer)
        {titleLabel.setText("Customer Addition");
            customerID = Customer.generateCustomerID();
            cusIDField.setText(String.valueOf(customerID));}

        else
        {selectedCustomer = MainFormController.getSelectedCustomer();
         Division d = getDivision(selectedCustomer.getDivisionID());
            Country c = getCountry(d.getCountryID());

            titleLabel.setText("Customer Update");
            cusIDField.setText(selectedCustomer.getCustomerID().toString());
            cusNameField.setText(selectedCustomer.getCustomerName());
            cusAddressField.setText(selectedCustomer.getAddress());
            cusPostalCodeField.setText(selectedCustomer.getZipcode());
            cusPhoneField.setText(selectedCustomer.getPhoneNumber());
            stateComboBox.getSelectionModel().select(d);
            countryComboBox.getSelectionModel().select(c);
        }

    }
    
    /**
     *this method get a division id from the database. 
     * @param divisionID
     * @return division id
     */
    public static Division getDivision(int divisionID) {

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = " + divisionID;
            String divisionName = "";
            int countryID = 0;
            PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rSet = pStatement .executeQuery();

            if (rSet.next()) {divisionName = rSet.getString("Division");
                                countryID = rSet.getInt("Country_ID");}
            Division division = new Division(divisionID, divisionName, countryID);
            return division;
        }
        catch (SQLException throwables){throwables.printStackTrace();}
        return null;
    }
    
    /**
     *this method get all division ids from the database. 
     * @return all division ids
     */
    public static ObservableList<Division> getAllDivisions()
    {
        ObservableList<Division> dList = FXCollections.observableArrayList();
        try
        {String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rSet = pStatement.executeQuery();
        while (rSet.next())
            {int divisionID = rSet.getInt("Division_ID");
            String divisionName = rSet.getString("Division");
            int countryID = rSet.getInt("Country_ID");
            Division d = new Division(divisionID, divisionName, countryID);
            dList.add(d);}
        }
        catch (SQLException throwables){throwables.printStackTrace();}
        return dList;
    }
    
    /**
     * this method get a country id from the database. 
     * @param countryID
     * @return a country id
     */
    public static Country getCountry(int countryID) {

        try {
            String sql = "SELECT * FROM countries WHERE Country_ID = " + countryID;
            String countryName = "";
            PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rSet = pStatement.executeQuery();

            if (rSet.next()) {countryName = rSet.getString("Country");}
            Country country = new Country(countryID, countryName);
            return country;
        }
        catch (SQLException throwables){throwables.printStackTrace();}
        return null;
    }
    
    /**
     *this method get all country ids from the database. 
     * @return all country ids
     */
    public static ObservableList<Country> getAllCountries()
    {
        ObservableList<Country> clist = FXCollections.observableArrayList();
        try
        {String sql = "SELECT * FROM countries";
        PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rSet = pStatement.executeQuery();
        while (rSet.next())
        {int countryID = rSet.getInt("Country_ID");
        String countryName = rSet.getString("Country");
        Country country = new Country(countryID, countryName);
        clist.add(country);}}
        catch (SQLException throwables){throwables.printStackTrace();}
        return clist;
    }

    /**
     * this method populates the state/provision combobox depends on the country selected. 
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void countrySelected(ActionEvent actionEvent) throws IOException, SQLException {
        stateComboBox.setItems(null);
        ObservableList<Division> divisions = getAllDivisions();
        ObservableList<Division> matchingDivisions = FXCollections.observableArrayList();
        Country country = countryComboBox.getSelectionModel().getSelectedItem();
        for (Division division: divisions)
        {if (country.getCountryID() == division.getCountryID()){matchingDivisions.add(division);}}
        stateComboBox.setItems(matchingDivisions);
    }
    
    /**
     * It returns the user to the MainForm.
     * @param actionEvent
     * @throws IOException
     */
    public void exitBtnPressed(ActionEvent actionEvent) throws IOException {fc.formChange("../View/MainForm.fxml", actionEvent);}

     /**
     *LAMBDA EXPRESSION (from MainFormController) is USED ==> here a lambda expression is applied to reduces the amount of codes when changing forms in multiple locations. 
     *It provides a more clean code.
     * @param s
     * @param actionEvent
     * @throws IOException
     */
    formFunctionChange fc = (String form, ActionEvent aEvent) ->
    {Parent addProductParent = FXMLLoader.load(getClass().getResource(form));
     Scene addProductScene = new Scene(addProductParent);
     Stage window = (Stage) ((Node) aEvent.getSource()).getScene().getWindow();
     window.setScene(addProductScene);
     window.show();};

    /**
     * add new customer to record or modify existing customer records. 
     * @param actionEvent
     * @throws IOException
     */
    public void saveBtnPressed(ActionEvent actionEvent) throws IOException {
        if(countryComboBox.getSelectionModel().getSelectedItem() != null && stateComboBox.getSelectionModel().getSelectedItem() != null)
        {Customer cus = new Customer(Integer.parseInt(cusIDField.getText()), cusNameField.getText(),cusAddressField.getText(), cusPostalCodeField.getText(), cusPhoneField.getText(),stateComboBox.getSelectionModel().getSelectedItem().getDivisionID());
            if (cus.isValid())
            {if (addCustomer)
                {try {
                String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) " +"VALUES (" + cus.getCustomerID() + ", '" +cus.getCustomerName() + "', '" +cus.getAddress() + "', '" +cus.getZipcode() + "', '" +cus.getPhoneNumber() + "', '" +LoginController.getUser().getUserName() + "', '" +LoginController.getUser().getUserName() + "', " +cus.getDivisionID() + ")";
                PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(sql);
                pStatement.executeUpdate(sql);}
                catch (SQLException throwables){throwables.printStackTrace();}}
             else
                {Timestamp tStamp = new Timestamp(System.currentTimeMillis());
                try{String sql = "UPDATE customers " + "SET Customer_Name = '" + cus.getCustomerName() + "', " +"Address = '" + cus.getAddress() + "', " +"Postal_Code = '" + cus.getZipcode() + "', " +"Phone = '" + cus.getPhoneNumber() + "', " +"Last_Update = '" + tStamp + "', " +"Last_Updated_By = '" + LoginController.getUser().getUserName() + "', " +"Division_ID = " + cus.getDivisionID() + " " +"WHERE Customer_ID = " + cus.getCustomerID();
                    PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(sql);
                    pStatement.executeUpdate(sql);} 
                catch (SQLException throwables) {throwables.printStackTrace();}
                }
                fc.formChange("../View/MainForm.fxml", actionEvent);
            }
        }
        else{
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle("Empty Selection");
            alert.setContentText("You DIDN'T select a country and/or state/province!");
            alert.showAndWait();}
    }
}