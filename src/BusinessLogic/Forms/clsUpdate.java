/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Forms;

import BusinessLogic.Database.Update.*;
import BusinessLogic.clsProgram;
import DataAccess.Database.iDatabase;
import java.util.ArrayList;

/**
 *
 * @author jean
 */
public class clsUpdate {
    private final clsProgram pProgram;
    private final iDatabase pDatabase;
    private final ArrayList<iUpdate> pUpdates;
    
    public clsUpdate(clsProgram aProgram) {
        this.pProgram = aProgram;
        this.pDatabase = this.pProgram.getDatabase();
        
        this.pUpdates = new ArrayList<>();
        this.pUpdates.add(new clsUpdate00001(this.pProgram.getDatabase()));
        this.pUpdates.add(new clsUpdate00002(this.pProgram.getDatabase()));
        this.pUpdates.add(new clsUpdate00003(this.pProgram.getDatabase()));
        this.pUpdates.add(new clsUpdate00004(this.pProgram.getDatabase()));
        this.pUpdates.add(new clsUpdate00005(this.pProgram.getDatabase()));
        this.pUpdates.add(new clsUpdate00006(this.pProgram.getDatabase()));
    }
    
    public int getLatestUpdateVersion() {
        int mUpdatesArraySize = this.pUpdates.size();
        iUpdate mLastUpdate = this.pUpdates.get(mUpdatesArraySize - 1);
        return mLastUpdate.getUpdateVersion();
    }
    
    public void performUpdate() {
        for (int i=0; i<this.pUpdates.size(); i++) {
            iUpdate mUpdate = this.pUpdates.get(i);
            mUpdate.performUpdate();
        }
    }
}
