/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Forms.Contacts;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author jean
 */
public class clsContactsDetails {
    private int pID;
    private int pIDModifications;
    private String pFirstName;
    private String pMiddleName;
    private String pLastName;
    private Date pBirthday;
    private String pGender;
    private String pStreet;
    private String pPostcode;
    private String pCity;
    private String pCountry;
    private String pAvailability;
    private String pComment;
    private Date pDateCreated;
    private Date pDateModified;
    private ArrayList pAvailabilityToDelete;
    
    public void setID(int aID) {
        this.pID = aID;
    }
    
    public void setIDModification(int aIDModifications) {
        this.pIDModifications = aIDModifications;
    }
    
    public void addAvailabilityDeletion(int aIDContactAvailabilities) {
        if (this.pAvailabilityToDelete == null) {
            this.pAvailabilityToDelete = new ArrayList();
        }
        this.pAvailabilityToDelete.add(aIDContactAvailabilities);
    }
    
    public void setFirstName(String aFirstName) {
        this.pFirstName = aFirstName;
    }
    
    public void setMiddleName(String aMiddleName) {
        this.pMiddleName = aMiddleName;
    }
    
    public void setLastName(String aLastName) {
        this.pLastName = aLastName;
    }
    
    public void setBirthday(Date aBirthday) {
        this.pBirthday = aBirthday;
    }
    
    public void setGender(String aGender) {
        this.pGender = aGender;
    }
    
    public void setStreet(String aStreet) {
        this.pStreet = aStreet;
    }
    
    public void setPostcode(String aPostcode) {
        this.pPostcode = aPostcode;
    }
    
    public void setCity(String aCity) {
        this.pCity = aCity;
    }
    
    public void setCountry(String aCountry) {
        this.pCountry = aCountry;
    }
    
    public void setAvailability(String aAvailability) {
        this.pAvailability = aAvailability;
    }
    
    public void setDateCreated(Date aDateCreated) {
        this.pDateCreated = aDateCreated;
    }
    
    public void setDateModified(Date aDateModified) {
        this.pDateModified = aDateModified;
    }
    
    public void setComment(String aComment) {
        this.pComment = aComment;
    }
    
    public int getID() {
        return this.pID;
    }
    
    public int getIDModifications() {
        return this.pIDModifications;
    }
    
    public String getFirstName() {
        return this.pFirstName;
    }
    
    public String getMiddleName() {
        return this.pMiddleName;
    }
    
    public String getLastName() {
        return this.pLastName;
    }
    
    public Date getBirthday() {
        return this.pBirthday;
    }
    
    public String getGender() {
        if (this.pGender != null) {
            return this.pGender;
        } else {
            return "";
        }
    }
    
    public String getStreet() {
        return this.pStreet;
    }
    
    public String getPostcode() {
        return this.pPostcode;
    }
    
    public String getCity() {
        return this.pCity;
    }
    
    public String getCountry() {
        return this.pCountry;
    }
    
    public String getAvailability() {
        return this.pAvailability;
    }
    
    public String getComment() {
        return this.pComment;
    }
    
    public Date getDateCreated() {
        return this.pDateCreated;
    }
    
    public Date getDateModified() {
        return this.pDateModified;
    }
}
