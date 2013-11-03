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
public class clsUpdate00005 implements iUpdate {
    private final iDatabase pDatabase;
    
    public clsUpdate00005(iDatabase aDatabase) {
        this.pDatabase = aDatabase;
    }

    @Override
    public boolean performUpdate() {
        try {
            //Build SQL.
            clsSQL mSQL1 = new clsSQL(clsSQL.enmSQLType.Drop_Table);
            mSQL1.addField(new clsSQLField("ContactAvailabilities", ""));
            //this.pDatabase.QueryExecuteAffectedRows("DROP TABLE ContactAvailabilities;");
            this.pDatabase.QueryExecuteAffectedRows(mSQL1);
            
            //Build SQL.
            clsSQL mSQL2 = new clsSQL(clsSQL.enmSQLType.Drop_Table);
            mSQL2.addField(new clsSQLField("ContactAvailabilityType", ""));
            //this.pDatabase.QueryExecuteAffectedRows("DROP TABLE ContactAvailabilityType;");
            this.pDatabase.QueryExecuteAffectedRows(mSQL2);
            
            //Build SQL.
            clsSQL mSQL3 = new clsSQL(clsSQL.enmSQLType.Alter_Table_Add_Column);
            mSQL3.addField(new clsSQLField("Contacts", "Availability", clsSQLField.enmFieldType.isText, "", false, false, false));
            //this.pDatabase.QueryExecuteAffectedRows("ALTER TABLE Contacts ADD COLUMN Availability TEXT");
            this.pDatabase.QueryExecuteAffectedRows(mSQL3);
            
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
        return 5;
    }
    
}
