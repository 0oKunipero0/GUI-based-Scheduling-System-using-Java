package Model;

/**
 *
 * @author Kun Xie
 */

/**
* this class represents the country objects. 
*/
public class Country {
    /**
     * id of a country. 
     */
    private int countryID;
    /**
     * name of a country. 
     */
    private String countryName;

    /**
     * this method constructs the country objects from database. 
     * @param id
     * @param name
     */
    public Country(int id, String name)
    {
        this.countryID = id;
        this.countryName = name;
    }

    /**
     *it gets the country id.
     * @return country id
     */
    public int getCountryID() {return countryID;}

    /**
     *it sets the country id.
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     *it gets the country name.
     * @return country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     *it sets the country name. 
     * @param countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     *it converts the memory address of a country name into string.
     * @return country name in string
     */
    public String toString() {return countryName;}
}
