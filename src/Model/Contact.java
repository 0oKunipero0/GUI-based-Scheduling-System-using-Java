package Model;

/**
 *
 * @author Kun Xie
 */

/**
* this class represents the contact objects. 
*/
public class Contact {
    /**
     * id of a contact. 
     */
    private int contactID;
    /**
     * name of a contact. 
     */
    private String contactName;
    /**
     * email of a contact. 
     */
    private String contactEmail;

    /**
     * this method constructs the contact objects from database.
     * @param contactID
     * @param contactName
     * @param contactEmail
     */
    public Contact(int contactID, String contactName, String contactEmail)
    {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     *it gets the contact id.
     * @return contact id
     */
    public int getContactID() {
        return contactID;
    }

    /**
     *it sets the contact id.
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     *it gets the contact name. 
     * @return contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     *it sets the contact name.
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     *it gets the contact email.
     * @return contact email
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     *it sets the contact email.
     * @param contactEmail
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     *it converts the memory address of a contact name into string.
     * @return contact name in string
     */
    public String toString()
    {
        return contactName;
    }
}
