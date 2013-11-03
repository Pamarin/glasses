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
public class clsUpdate00004 implements iUpdate {
    private final iDatabase pDatabase;
    
    public clsUpdate00004(iDatabase aDatabase) {
        this.pDatabase = aDatabase;
    }
    
    @Override
    public boolean performUpdate() {
        try {
            //Build SQL.
            clsSQL mSQL1 = new clsSQL(clsSQL.enmSQLType.Create_Table);
            mSQL1.addField(new clsSQLField("ContactAvailabilities", "ID", clsSQLField.enmFieldType.isInteger, "", true, true, true));
            mSQL1.addField(new clsSQLField("ContactAvailabilities", "ID_Contacts", clsSQLField.enmFieldType.isInteger, "", true, false, false));
            mSQL1.addField(new clsSQLField("ContactAvailabilities", "ID_ContactAvailabilityType", clsSQLField.enmFieldType.isInteger, "", true, false, false));
            mSQL1.addField(new clsSQLField("ContactAvailabilities", "Dial", clsSQLField.enmFieldType.isVarchar, "", false, false, false));
            mSQL1.addField(new clsSQLField("ContactAvailabilities", "Comment", clsSQLField.enmFieldType.isVarchar, "", false, false, false));
            //this.pDatabase.QueryExecuteAffectedRows("CREATE TABLE ContactAvailabilities (ID INT NOT NULL AUTO_INCREMENT, ID_Contacts INT NOT NULL, ID_ContactAvailabilityType INT NOT NULL, Dial VARCHAR(255), Comment VARCHAR(255), PRIMARY KEY (ID));");
            this.pDatabase.QueryExecuteAffectedRows(mSQL1);
            
            //Build SQL.
            clsSQL mSQL2 = new clsSQL(clsSQL.enmSQLType.Create_Table);
            mSQL2.addField(new clsSQLField("ContactAvailabilityType", "ID", clsSQLField.enmFieldType.isInteger, "", true, true, true));
            mSQL2.addField(new clsSQLField("ContactAvailabilityType", "TypeName", clsSQLField.enmFieldType.isVarchar, "", false, false, false));
            //this.pDatabase.QueryExecuteAffectedRows("CREATE TABLE ContactAvailabilityType (ID INT NOT NULL AUTO_INCREMENT, TypeName VARCHAR(255), PRIMARY KEY (ID));");
            this.pDatabase.QueryExecuteAffectedRows(mSQL2);
            
            //Build SQL.
            clsSQL mSQL3 = new clsSQL(clsSQL.enmSQLType.Alter_Table_Drop_Column);
            mSQL3.addField(new clsSQLField("Contacts", "Phone"));
            mSQL3.addField(new clsSQLField("Contacts", "Fax"));
            mSQL3.addField(new clsSQLField("Contacts", "Email"));
            mSQL3.addField(new clsSQLField("Contacts", "Website"));
            //this.pDatabase.QueryExecuteAffectedRows("ALTER TABLE Contacts DROP COLUMN Phone, DROP COLUMN Fax, DROP COLUMN Email, DROP COLUMN Website;");
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
        return 4;
    }
    
}
