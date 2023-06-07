package DBConnection;

/**
 *
 * @author Kun Xie
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * this class connects the application to the database. 
 */
public class DBConnection {
    
    /**
     * protocol of the database address. 
     */
    private static final String protocol = "jdbc";
    /**
     * vendor of the database address. 
     */
    private static final String vendor = ":mysql:";
    /**
     * ip of the database address. 
     */
    private static final String DBIp = "//localhost:3306/";
    /**
     * database name of the database address. 
     */
    private static final String dbName = "client_schedule";
    /**
     * database address. 
     */
    private static final String jdbcURL = protocol + vendor + DBIp + dbName + "?connectionTimeZone=SERVER";
    /**
     * driver of the database. 
     */
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    /**
     * database connection name. 
     */
    public static Connection myConn;
    /**
     * database access username.
     */
    private static final String DBLoginUsername = "sqlUser";
    /**
     * database access password. 
     */
    private static final String DBLoginPassword = "Passw0rd!";

    /**
     * it gets the connection with the database. 
     * @return connection
     */
    public static Connection getConnection(){return myConn;}
    
    /**
     *it initiates a connection attempt with the database. 
     * @return
     */
    public static Connection initiateConnection()
    {
        try
        {
            Class.forName(driver);
            myConn = DriverManager.getConnection(jdbcURL, DBLoginUsername, DBLoginPassword);
            System.out.println("Connection successfully made!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
        return myConn;
    }

    /**
     * it ends the connection with the database. 
     */
    public static void closeConnection() {
        try {
            myConn.close();
            System.out.println("Connection is closed!");
        }
        catch(Exception e){System.out.println("Error:" + e.getMessage());}
    }

}
