/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Forms.Special;

/**
 *
 * @author jean
 */
public class clsIDEntry {
    private int pID;
    private Object pValue;
    
    public int getID() {
        return this.pID;
    }
    
    public Object getValue() {
        return this.pValue;
    }
    
    public void setID(int aID) {
        this.pID = aID;
    }
    
    public void setValue(Object aValue) {
        this.pValue = aValue;
    }

    @Override
    public String toString() {
        return String.valueOf(this.pValue);
    }
}
