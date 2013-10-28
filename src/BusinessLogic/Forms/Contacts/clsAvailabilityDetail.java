/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Forms.Contacts;

/**
 *
 * @author jean
 */
public class clsAvailabilityDetail {
    private int pID;
    private String pType;
    private String pDial;
    private String pComment;
    
    public void setID(int aID) {
        this.pID = aID;
    }
    
    public void setType(String aType) {
        this.pType = aType;
    }
    
    public void setDial(String aDial) {
        this.pDial = aDial;
    }
    
    public void setComment(String aComment) {
        this.pComment = aComment;
    }
    
    public int getID() {
        return this.pID;
    }
    
    public String getType() {
        return this.pType;
    }
    
    public String getDial() {
        return this.pDial;
    }
    
    public String getComment() {
        return this.pComment;
    }
}
