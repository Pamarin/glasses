/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Forms;

import BusinessLogic.clsProgram;

/**
 *
 * @author jean
 */
public class clsMain {
    private final clsProgram pProgram;
    
    public clsMain() {
        this.pProgram = new clsProgram();
    }

    public boolean updateNecessary() {
        int mCurrentDatabaseVersion = this.pProgram.getCurrentDatabaseVersion();
        int mUpdateDatabaseVersion = this.pProgram.getProgramDatabaseVersion();
        return mCurrentDatabaseVersion != mUpdateDatabaseVersion;
    }
    
    public clsProgram getProgram() {
        return this.pProgram;
    }
}
