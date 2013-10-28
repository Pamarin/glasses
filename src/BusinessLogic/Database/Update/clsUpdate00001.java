/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Database.Update;

import DataAccess.Database.iDatabase;
import DataAccess.SQL.clsSQL;
import DataAccess.SQL.clsSQLField;

/**
 *
 * @author jean
 */
public class clsUpdate00001 implements iUpdate {
    private iDatabase pDatabase;
    
    public clsUpdate00001(iDatabase aDatabase) {
        this.pDatabase = aDatabase;
    }
    
    @Override
    public boolean performUpdate() {
        try {
            //Create Glasses database if not existent.
            if (this.pDatabase.checkDatabaseExists("Glasses", true) == false) {
                this.pDatabase.createDatabase("Glasses", true);
            }
            
            //Create configuration table.
            if (this.pDatabase.checkTableExists("Configuration") == false) {
                //Build SQL.
                clsSQL mSQL = new clsSQL(clsSQL.enmSQLType.Create_Table);
                mSQL.addField(new clsSQLField("Configuration", "ID", clsSQLField.enmFieldType.isInteger, "", true, true, true));
                mSQL.addField(new clsSQLField("Configuration", "DatabaseVersion", clsSQLField.enmFieldType.isInteger, "", true, false, false));
                //this.pDatabase.QueryExecuteAffectedRows("CREATE TABLE Configuration (ID INT NOT NULL AUTO_INCREMENT, DatabaseVersion INT NOT NULL, PRIMARY KEY (ID));");
                this.pDatabase.QueryExecuteAffectedRows(mSQL);
            }
            
            //Build SQL.
            clsSQL mSQL = new clsSQL(clsSQL.enmSQLType.Insert);
            mSQL.addField(new clsSQLField("Configuration", "ID", "1"));
            mSQL.addField(new clsSQLField("Configuration", "DatabaseVersion", "1"));
            //Create configuration entry.
            //this.pDatabase.QueryExecuteAffectedRows("INSERT INTO Configuration (ID, DatabaseVersion) VALUES (1, 1);");
            this.pDatabase.QueryExecuteAffectedRows(mSQL);
            
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public int getUpdateVersion() {
        return 1;
    }
}
