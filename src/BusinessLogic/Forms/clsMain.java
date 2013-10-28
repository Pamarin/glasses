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
    private clsProgram pProgram;
    
    public clsMain() {
        this.pProgram = new clsProgram();
    }

    public boolean updateNecessary() {
        int mCurrentDatabaseVersion = this.pProgram.getCurrentDatabaseVersion();
        int mUpdateDatabaseVersion = this.pProgram.getProgramDatabaseVersion();

        if (mCurrentDatabaseVersion != mUpdateDatabaseVersion) {
            return true;
        } else {
            return false;
        }
    }
    
    public clsProgram getProgram() {
        return this.pProgram;
    }
}
