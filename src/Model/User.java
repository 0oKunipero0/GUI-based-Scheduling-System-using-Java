package Model;

/**
 *
 * @author Kun Xie
 */

/**
* this class represents the user objects. 
*/
public class User {
    /**
     * id of a user. 
     */
    private int userID;
    /**
     * username of this user. 
     */
    private String userName;
    /**
     * password of this user. 
     */
    private String userPassword;

    /**
     * this method constructs the user objects from database. 
     * @param userID
     * @param userName
     * @param userPassword
     */
    
    public User(int userID, String userName, String userPassword)
    {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     *This method returns the user ID. 
     * @return user id
     */
    public int getUserID() {
        return userID;
    }

    /**
     *this method sets the user ID. 
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     *this method returns the user name. 
     * @return user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     *this method sets the user name. 
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *this method returns the user password.
     * @return user password
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     *this method sets user password.
     * @param userPassword
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * this method is to validate the username and password records from the database. 
     * @param username
     * @param password
     * @return if both items match, return true; if not, return false. 
     */
    public boolean isValidLogin(String username, String password)
    {
        return username.equals(userName) && password.equals(userPassword);
    }


}
