/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Forms.Contacts;

import java.util.ArrayList;

/**
 *
 * @author jean
 */
public class clsAvailabilityList {
    private ArrayList<clsAvailabilityDetail> pContactAvailabilities;
    
    public clsAvailabilityList() {
        this.pContactAvailabilities = new ArrayList();
    }
    
    public void addAvaiability(clsAvailabilityDetail aContactAvailability) {
        this.pContactAvailabilities.add(aContactAvailability);
    }
    
    public ArrayList<clsAvailabilityDetail> getAvailabilities() {
        return this.pContactAvailabilities;
    }
}
