package Model;

/**
 *
 * @author Kun Xie
 */

/**
* this class represents the division objects. 
*/
public class Division {
    /**
     * id of a division. 
     */
    private int divisionID;
    /**
     * name of a division. 
     */
    private String divisionName;
    /**
     * id of a country. 
     */
    private int countryID;

    /**
     * this method constructs the division objects from database. 
     * @param divisionID
     * @param divisionName
     * @param countryID
     */
    public Division(int divisionID, String divisionName, int countryID)
    {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;

    }

    /**
     *it returns the division id.
     * @return division id
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     *it sets the division id. 
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     *it converts the address in memory of the division name into string. 
     * @return division name in string
     */
    public String toString() {
        return divisionName;
    }

    /**
     *it sets the division name. 
     * @param divisionName
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     *it gets the country id.
     * @return country id
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     *it sets the country id. 
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}
