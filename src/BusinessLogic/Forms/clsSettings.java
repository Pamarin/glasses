/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Forms;

import BusinessLogic.clsProgram;
import DataAccess.Database.iDatabase;
import DataAccess.SQL.clsSQL;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jean
 */
public class clsSettings {
    clsProgram pProgram;
    
    public clsSettings(clsProgram aProgram) {
        this.pProgram = aProgram;
    }
    
    public String getConfigurationFileLocation() {
        return this.pProgram.getConfiguration().getConfigurationFileLocation();
    }
    
    public String getDatabaseType() {
        return this.pProgram.getConfiguration().getDatabaseType(0);
    }
    
    public String getDatabaseLocation() {
        return this.pProgram.getConfiguration().getDatabaseLocation(0);
    }
    
    public String getDatabasePort() {
        return this.pProgram.getConfiguration().getDatabasePort(0);
    }
    
    public String getDatabaseName() {
        return this.pProgram.getConfiguration().getDatabaseName(0);
    }
    
    public String getDatabasePrefix() {
        return this.pProgram.getConfiguration().getDatabasePrefix(0);
    }
    
    public String getDatabaseUsername() {
        return this.pProgram.getConfiguration().getDatabaseUsername(0);
    }
    
    public String getDatabasePassword() {
        return this.pProgram.getConfiguration().getDatabasePassword(0);
    }
    
    public void setDatabaseType(String aDatabaseType) {
        this.pProgram.getConfiguration().setDatabaseType(0, aDatabaseType);
    }
    
    public void setDatabaseLocation(String aDatabaseLocation) {
        this.pProgram.getConfiguration().setDatabaseLocation(0, aDatabaseLocation);
    }
    
    public void setDatabasePort(String aDatabasePort) {
        this.pProgram.getConfiguration().setDatabasePort(0, aDatabasePort);
    }
    
    public void setDatabaseName(String aDatabaseName) {
        this.pProgram.getConfiguration().setDatabaseName(0, aDatabaseName);
    }
    
    public void setDatabasePrefix(String aDatabasePrefix) {
        this.pProgram.getConfiguration().setDatabasePrefix(0, aDatabasePrefix);
    }
    
    public void setDatabaseUsername(String aDatabaseUsernam) {
        this.pProgram.getConfiguration().setDatabaseUsername(0, aDatabaseUsernam);
    }
    
    public void setDatabasePassword(String aDatabasePassword) {
        this.pProgram.getConfiguration().setDatabasePassword(0, aDatabasePassword);
    }
    
    public boolean saveDatabaseSettings() {
        return this.pProgram.getConfiguration().saveDatabaseSettings(0);
    }
    
    public boolean testDatabaseSettings() throws SQLException {
        //Get the current database.
        iDatabase mDatabase = this.pProgram.getDatabase();
        //Check it for Null-state.
        if (mDatabase == null) {
            //No database to access to.
            return false;
        } else {
            //Try to connect.
            if (this.pProgram.getDatabase().Connect(true)) {
                //Build SQL.
                clsSQL mSQL = new clsSQL(clsSQL.enmSQLType.Show_Tables);
                //String mSQL = "SHOW TABLES;";
                ResultSet mTables = this.pProgram.getDatabase().QueryRetrieve(mSQL);
                
                return true;
            } else {
                return false;
            }
        }
    }
    
    public boolean loadConfiguration() {
        return this.pProgram.getConfiguration().loadDatabaseSettings();
    }
}
