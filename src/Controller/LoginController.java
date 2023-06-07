package Controller;

/**
 *
 * @author Kun Xie
 */

import static Controller.MainFormController.getAllAppointments;
import DBConnection.DBConnection;
import Model.Appointment;
import Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;


/**
 * This class creates the login form for users to access the application. 
 */
public class LoginController implements Initializable {
    /**
     * text field to enter username. 
     */
    @FXML
    private TextField usernameTxtF;
    /**
     * username text label.
     */
    @FXML
    private Label usernameLbl;
    /**
     * password text label. 
     */
    @FXML
    private Label passwordLbl;
    /**
     * location text label.  
     */
    @FXML
    private Label locationLbl;
    /**
     * user location text. 
     */
    @FXML
    private Label userLocLbl;
    /**
     * text field to enter password. 
     */
    @FXML
    private PasswordField passwordTxtF;
    /**
     * login form title. 
     */
    @FXML
    private Label loginTitleLbl;
    /**
     * login button. 
     */
    @FXML
    private Button loginBtn;
    /**
     * invalid login label.  
     */
    @FXML
    private Label invalidLoginLbl;
    /**
     * user system language. 
     */
    private String userLang;
    /**
     * user system timezone. 
     */
    private ZoneId userZoneID;
    /**
     * user object. 
     */
    private static User user;
    /**
     * list of appointments. 
     */
    private ObservableList<Appointment> aptmL;

    /**
     * This method initializes the login form (the UI will be in French if the system detects user language is French). 
     * @param url
     * @param resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {

        userLang = Locale.getDefault().getLanguage();
        userZoneID = ZoneId.systemDefault();

        if (userLang.equals("fr"))
        {
            locationLbl.setText("Emplacement:");
            usernameLbl.setText("Nom d'utilisateur:");
            loginTitleLbl.setText("Prise de Rendez-vous"); 
            passwordLbl.setText("Mot de passe:");
            loginBtn.setText("L'identifiant:");
        }userLocLbl.setText(userZoneID.getDisplayName(TextStyle.FULL, Locale.getDefault()));
    }
    
    /**
     *it gets the user in file.
     * @return user
     */
    public static User getUser(){return user;}
    
    /**
     * It allows the user to access the main application screen (MainForm) if both the username and passwords are verified. 
     * LAMBDA EXPRESSION is USED ==> a lambda expression is used here for the appointment alert. It is used to reduce lines of codes. 
     * @param actionEvent
     * @throws IOException
     */
    public void loginBtnPressed(ActionEvent actionEvent) throws IOException {
        ObservableList<User> userList = getAllUsers();
        boolean validLogin = false;
        invalidLoginLbl.setText("");
        for (User validU: userList)
        {
            if (validU.isValidLogin(usernameTxtF.getText(), passwordTxtF.getText()))
            {
                user = validU;
                logSucceededLogin(user.getUserName());
                System.out.println("valid login");
                validLogin = true;
                Parent addProductParent = FXMLLoader.load(getClass().getResource("../View/MainForm.fxml"));
                Scene addProductScene = new Scene(addProductParent);
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.setScene(addProductScene);
                window.show();
            }
            if (!validLogin)
            {
                logFailedLogin(usernameTxtF.getText());
                if (userLang.equals("fr"))
                { invalidLoginLbl.setText("le nom d'utilisateur et/ou le mot de passe sont invalides !");}
                else
                {invalidLoginLbl.setText("username and/or password are invalid!");}
            }
        }
        aptmL = getAllAppointments();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fifteenFromNow =LocalDateTime.now().plusMinutes(15);
        boolean aptmIn15Min = false;
        
        appointmentAlert aAlert = () -> "An appointment is coming up soon!\n";
       

        for (Appointment aptm: aptmL)
        {
            LocalDateTime aptLDT = aptm.getStart().toLocalDateTime();
            if(aptLDT.isAfter(now) && aptLDT.isBefore(fifteenFromNow))
            {aptmIn15Min = true;}
            if(aptmIn15Min)
            {
                Alert aptmUpcomingAlert = new Alert(Alert.AlertType.WARNING);
                aptmUpcomingAlert.setTitle("Appointment Alert!");
                aptmUpcomingAlert.setContentText(aAlert.alert() + "ID: " + aptm.getAppointmentID() + "    |      Start: " + aptm.getStart());
                aptmUpcomingAlert.showAndWait();
            }
        }
    }
    
    /**
     *this Observablelist gets all user records from the database.
     * @return all users
     */
    public static ObservableList<User> getAllUsers()
    {
        ObservableList<User> uL = FXCollections.observableArrayList();
        try
        {String sql = "SELECT * FROM users";
         PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(sql);
         ResultSet rSet = pStatement.executeQuery();
            while (rSet.next())
            {
                int userID = rSet.getInt("User_ID");
                String username = rSet.getString("User_Name");
                String password = rSet.getString("Password");
                User u = new User(userID, username, password);
                uL.add(u);
            }
        }
        catch (SQLException throwables){throwables.printStackTrace();}
        return uL;
    }
    
    /**
     * Writes aptm successful login to the 'login_activity.txt' file.
     * @param username
     * @throws IOException
     */
    public void logSucceededLogin(String username) throws IOException{
        String logString = "\n| Login Attempt --> Username: [ "+ username + " ]" +"\n| Date: [ "+ LocalDate.now() +" ] | Timestamp: [ "+ Timestamp.valueOf(LocalDateTime.now()) + " ]" +"\n| Login Status: [ SUCESSESSFUL! ]" + "\n___________________________________________________________________";
        FileWriter fWriter = new FileWriter("login_activity.txt",true);
        PrintWriter pWriter = new PrintWriter(fWriter);
        pWriter.println(logString);
        pWriter.close();
    }

    /**
     * Writes an unsuccessful login to the 'login_activity.txt' file.
     * @param username Username data from username_textfield
     * @throws IOException
     */
    public void logFailedLogin(String username) throws IOException{
        String logString = "\n| Login Attempt --> Username: [ "+ username + " ]" + "\n| Date: [ "+ LocalDate.now() +" ] | Timestamp: [ "+ Timestamp.valueOf(LocalDateTime.now()) + " ]" +"\n| Login Status: [ FAILED! ]" + "\n___________________________________________________________________";
        FileWriter fWriter = new FileWriter("login_activity.txt",true);
        PrintWriter pWriter = new PrintWriter(fWriter);
        pWriter.println(logString);
        pWriter.close();
    }
    
    /**
    *interface used for LAMBDA EXPRESSION  ==> this lambda expression is created to reduce lines of codes when creating appointment alert in this form. 
    */

    public interface appointmentAlert {String alert();}

}

