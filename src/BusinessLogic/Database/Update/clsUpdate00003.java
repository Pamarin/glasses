/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Database.Update;

import DataAccess.Database.iDatabase;
import DataAccess.SQL.clsSQL;
import DataAccess.SQL.clsSQLField;
import java.sql.SQLException;

/**
 *
 * @author jean
 */
public class clsUpdate00003 implements iUpdate {
    private final iDatabase pDatabase;
    
    public clsUpdate00003(iDatabase aDatabase) {
        this.pDatabase = aDatabase;
    }
    
    @Override
    public boolean performUpdate() {
        try {
            //Build SQL.
            clsSQL mSQL1 = new clsSQL(clsSQL.enmSQLType.Alter_Table_Add_Column);
            mSQL1.addField(new clsSQLField("Contacts", "Comment", clsSQLField.enmFieldType.isText, "", false, false, false));
            //this.pDatabase.QueryExecuteAffectedRows("ALTER TABLE Contacts ADD COLUMN Comment TEXT;");
            this.pDatabase.QueryExecuteAffectedRows(mSQL1);
            
            //Build SQL.
            clsSQL mSQLVersionUp = new clsSQL(clsSQL.enmSQLType.Update);
            mSQLVersionUp.addField(new clsSQLField("Configuration", "DatabaseVersion", "(DatabaseVersion + 1)"));
            //this.pDatabase.QueryExecuteAffectedRows("UPDATE Configuration SET DatabaseVersion = (DatabaseVersion + 1);");
            this.pDatabase.QueryExecuteAffectedRows(mSQLVersionUp);
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public int getUpdateVersion() {
        return 3;
    }
    
}
