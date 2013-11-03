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
public class clsUpdate00002 implements iUpdate {
    private final iDatabase pDatabase;
    
    public clsUpdate00002(iDatabase aDatabase) {
        this.pDatabase = aDatabase;
    }
    
    @Override
    public boolean performUpdate() {
        try {
            //Build SQL.
            clsSQL mSQL1 = new clsSQL(clsSQL.enmSQLType.Create_Table);
            mSQL1.addField(new clsSQLField("Modifications", "ID", clsSQLField.enmFieldType.isInteger, "", true, true, true));
            mSQL1.addField(new clsSQLField("Modifications", "CreationDate", clsSQLField.enmFieldType.isDateTime, "", true, false, false));
            mSQL1.addField(new clsSQLField("Modifications", "ModificationDate", clsSQLField.enmFieldType.isDateTime, "", true, false, false));
            //this.pDatabase.QueryExecuteAffectedRows("CREATE TABLE Modifications (ID INT NOT NULL AUTO_INCREMENT, CreationDate DATETIME NOT NULL, ModificationDate DATETIME NOT NULL, PRIMARY KEY (ID));");
            this.pDatabase.QueryExecuteAffectedRows(mSQL1);
            
            //Build SQL.
            clsSQL mSQL2 = new clsSQL(clsSQL.enmSQLType.Create_Table);
            mSQL2.addField(new clsSQLField("Contacts", "ID", clsSQLField.enmFieldType.isInteger, "", true, true, true));
            mSQL2.addField(new clsSQLField("Contacts", "ID_Modifications", clsSQLField.enmFieldType.isInteger, "", true, false, false));
            mSQL2.addField(new clsSQLField("Contacts", "Deleted", clsSQLField.enmFieldType.isInteger, "0", true, false, false));
            mSQL2.addField(new clsSQLField("Contacts", "FirstName", clsSQLField.enmFieldType.isVarchar, "", false, false, false));
            mSQL2.addField(new clsSQLField("Contacts", "MiddleName", clsSQLField.enmFieldType.isVarchar, "", false, false, false));
            mSQL2.addField(new clsSQLField("Contacts", "LastName", clsSQLField.enmFieldType.isVarchar, "", false, false, false));
            mSQL2.addField(new clsSQLField("Contacts", "Birthday", clsSQLField.enmFieldType.isDate, "", false, false, false));
            mSQL2.addField(new clsSQLField("Contacts", "Gender", clsSQLField.enmFieldType.isVarchar, "", false, false, false));
            mSQL2.addField(new clsSQLField("Contacts", "Street", clsSQLField.enmFieldType.isVarchar, "", false, false, false));
            mSQL2.addField(new clsSQLField("Contacts", "Postcode", clsSQLField.enmFieldType.isVarchar, "", false, false, false));
            mSQL2.addField(new clsSQLField("Contacts", "City", clsSQLField.enmFieldType.isVarchar, "", false, false, false));
            mSQL2.addField(new clsSQLField("Contacts", "Country", clsSQLField.enmFieldType.isVarchar, "", false, false, false));
            mSQL2.addField(new clsSQLField("Contacts", "Phone", clsSQLField.enmFieldType.isVarchar, "", false, false, false));
            mSQL2.addField(new clsSQLField("Contacts", "Fax", clsSQLField.enmFieldType.isVarchar, "", false, false, false));
            mSQL2.addField(new clsSQLField("Contacts", "Email", clsSQLField.enmFieldType.isVarchar, "", false, false, false));
            mSQL2.addField(new clsSQLField("Contacts", "Website", clsSQLField.enmFieldType.isVarchar, "", false, false, false));
            //this.pDatabase.QueryExecuteAffectedRows("CREATE TABLE Contacts (ID INT NOT NULL AUTO_INCREMENT, ID_Modifications INT NOT NULL, Deleted INT NOT NULL DEFAULT 0, FirstName VARCHAR(255), MiddleName VARCHAR(255), LastName VARCHAR(255), Birthday DATE, Gender VARCHAR(1), Street VARCHAR(255), Postcode VARCHAR(255), City VARCHAR(255), Country VARCHAR(255), Phone VARCHAR(255), Fax VARCHAR(255), Email VARCHAR(255), Website  VARCHAR(255), PRIMARY KEY (ID));");
            this.pDatabase.QueryExecuteAffectedRows(mSQL2);
            
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
        return 2;
    }
    
}
