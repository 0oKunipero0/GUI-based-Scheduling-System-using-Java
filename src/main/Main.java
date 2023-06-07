package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import DBConnection.DBConnection;

import java.util.Locale;

/**
 * @author: Kun Xie
 * login_activity.text
 */


/**
 * This class creates an appointment scheduling application for a business. 
 */
public class Main extends Application {

    /**
     * This method starts the main stage
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        String userLang = Locale.getDefault().getLanguage();
        if(userLang.equals("fr"))
            stage.setTitle("Prise de Rendez-vous");
        else
            stage.setTitle("Appointment Scheduling");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /** 
     *  This method launches the application and connects it to the database.It's the first method getting called upon launching the program.
     * @param args */
    public static void main(String[] args)
    {
        DBConnection.initiateConnection();
        launch(args);
        DBConnection.closeConnection();
    }

}
