package Model;

/**
 *
 * @author Kun Xie
 */

import DBConnection.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;

/**
 * this class represents the customer objects. 
 */
public class Customer {
    /**
     * id of a customer. 
     */
    private int customerID;
    /**
     * name of a customer. 
     */
    private String customerName;
    /**
     * address of a customer. 
     */
    private String address;
    /**
     * postal code of a customer. 
     */
    private String zipcode;
    /**
     * phone number of a customer. 
     */
    private String phoneNumber;
    /**
     * division id of a customer. 
     */
    private int divisionID;

    /**
     * this method constructs the customer objects from database. 
     * @param customerID
     * @param customerName
     * @param address
     * @param zipcode
     * @param phoneNumber
     * @param divisionID
     */
    public Customer(Integer customerID, String customerName, String address, String zipcode, String phoneNumber, Integer divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.zipcode = zipcode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
    }

    /**
     *it gets the customer id. 
     * @return customer id
     */
    public Integer getCustomerID() {
        return customerID;
    }

    /**
     *it sets the customer id. 
     * @param customerID
     */
    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    /**
     *it gets the customer name. 
     * @return customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     *it sets the customer name. 
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     *it gets the customer address.
     * @return customer address
     */
    public String getAddress() {
        return address;
    }

    /**
     *it sets the customer address.
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *it gets the customer postal code.
     * @return postal code
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     *it sets the customer postal code. 
     * @param zipcode
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     *it gets the customer phone number. 
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *it sets the customer phone number. 
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *it gets the division id. 
     * @return division id
     */
    public Integer getDivisionID() {
        return divisionID;
    }

    /**
     *it sets the division id.
     * @param divisionID
     */
    public void setDivisionID(Integer divisionID) {
        this.divisionID = divisionID;
    }
    
    // below overrides object memory address to object name

    /**
     * it converts the memory address of customer name into string.
     * @return customer name in string
     */
    public String toString()
    {
        return customerName;
    }
    
    /**
     *this Observablelist gets all customer records from the database.
     * @return all records of customer
     */
    public static ObservableList<Customer> getAllCustomers()
    {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try
        {
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                Integer customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                Integer divisionID = rs.getInt("Division_ID");
                Customer customer = new Customer(customerID, customerName, customerAddress, customerPostalCode, customerPhone, divisionID);
                customerList.add(customer);
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return customerList;
    }

    /**
     * it automates a customer id.
     * @return customer id
     */
    public static int generateCustomerID()
    {
        int max = 0;
        ObservableList<Customer> customers = getAllCustomers();
        for (Customer c: customers)
        {
            if (c.getCustomerID() > max)
                max = c.getCustomerID();
        }
        return max + 1;
    }

    /**
     * it validates inputs in the customer add/modification form. 
     * @return
     */
    public boolean isValid()
    {
        boolean valid = true;
        if (customerName.equals(""))
        {
            valid = false;
            JOptionPane.showMessageDialog(null, "Customer name CANNOT be empty!");
        }
        else if (address.equals(""))
        {
            valid = false;
            JOptionPane.showMessageDialog(null, "Customer address CANNOT be empty!");
        }
        else if(zipcode.equals(""))
        {
            valid= false;
            JOptionPane.showMessageDialog(null, "Customer zipcode CANNOT be empty!");
        }
        else if(phoneNumber.equals(""))
        {
            valid= false;
            JOptionPane.showMessageDialog(null, "Customer phone number CANNOT be empty!");
        }
        return valid;
    }

}
