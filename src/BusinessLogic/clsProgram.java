/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic;

import BusinessLogic.Forms.clsUpdate;
import DataAccess.Database.clsMySQL;
import DataAccess.Database.clsMySQLConnection;
import DataAccess.Database.iDatabase;
import DataAccess.Database.iDatabaseConnection;
import DataAccess.SQL.clsSQL;
import DataAccess.SQL.clsSQLField;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jean
 */
public class clsProgram {
    public final String ProgramName = "Glasses V12";
    public final int ProgramVersion = 1;
    
    private iDatabase pDatabase;
    private final clsConfiguration pConfiguration;
    
    public clsProgram() {
        System.setProperty("program.name", ProgramName.replaceAll(" ", ""));
        this.pConfiguration = new clsConfiguration();
    }
    
    public boolean DatabaseAccessible() {
        try {
            return getDatabase().Connect(false);
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public int getProgramDatabaseVersion() {
        clsUpdate mUpdate = new clsUpdate(this);
        return mUpdate.getLatestUpdateVersion();
    }
    
    public int getCurrentDatabaseVersion() {
        int mDatabaseVersion = 0;
        
        try {
            //Retrieve database version.
            if (this.pDatabase.checkTableExists("Configuration") == true) {
                //Build SQL.
                clsSQL mSQL = new clsSQL(clsSQL.enmSQLType.Select);
                mSQL.addField(new clsSQLField("Configuration", "DatabaseVersion"));
                //Retrieve query.
                //ResultSet rs = this.pDatabase.QueryRetrieve("SELECT DatabaseVersion FROM Configuration;");
                ResultSet rs = this.pDatabase.QueryRetrieve(mSQL);
                
                //Fetch database version.
                try {
                    while (rs.next()) {
                        //Database version.
                        mDatabaseVersion = rs.getInt("DatabaseVersion");
                        rs.last();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(clsUpdate.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(clsUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mDatabaseVersion;
    }
    
    public iDatabase getDatabase() {
        if (this.pDatabase == null) {
            chooseDatabase();
        }
        return this.pDatabase;
    }
    
    public clsConfiguration getConfiguration() {
        return this.pConfiguration;
    }
    
    public boolean chooseDatabase() {
        boolean returnVal = false;
        
        //Access to database
        if (this.pDatabase == null) {
            switch (this.pConfiguration.getDatabaseType()) {
                case "MySQL":
                    try {
                        iDatabaseConnection mDatabaseConnection = new clsMySQLConnection(pConfiguration);
                        this.pDatabase = new clsMySQL(mDatabaseConnection, pConfiguration.getDatabasePrefix());
                    } catch (ClassNotFoundException ex) {
                        // TODO: Handle exception of MySQL-Class not found!
                        Logger.getLogger(clsProgram.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    returnVal = true;
                    break;
                default:
                    this.pDatabase = null;
                    break;
            }
        }
        
        return returnVal;
    }
}
