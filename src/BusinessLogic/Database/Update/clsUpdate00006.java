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
public class clsUpdate00006 implements iUpdate {
    private iDatabase pDatabase;
    
    public clsUpdate00006(iDatabase aDatabase) {
        this.pDatabase = aDatabase;
    }

    @Override
    public boolean performUpdate() {
        try {
            //Build SQL.
            clsSQL mSQL1 = new clsSQL(clsSQL.enmSQLType.Create_Table);
            mSQL1.addField(new clsSQLField("Categories", "ID", clsSQLField.enmFieldType.isInteger, "", true, true, true));
            mSQL1.addField(new clsSQLField("Categories", "ID_Modifications", clsSQLField.enmFieldType.isInteger, "", true, false, false));
            mSQL1.addField(new clsSQLField("Categories", "Deleted", clsSQLField.enmFieldType.isInteger, "0", true, false, false));
            mSQL1.addField(new clsSQLField("Categories", "CategoryName", clsSQLField.enmFieldType.isVarchar, "", true, false, false));
            mSQL1.addField(new clsSQLField("Categories", "ID_Categories_ParentCategory", clsSQLField.enmFieldType.isInteger, "", true, false, false));
            mSQL1.addField(new clsSQLField("Categories", "Area", clsSQLField.enmFieldType.isInteger, "", true, false, false));
            this.pDatabase.QueryExecuteAffectedRows(mSQL1);
            
            //Build SQL.
            clsSQL mSQLVersionUp = new clsSQL(clsSQL.enmSQLType.Update);
            mSQLVersionUp.addField(new clsSQLField("Configuration", "DatabaseVersion", "(DatabaseVersion + 1)"));
            this.pDatabase.QueryExecuteAffectedRows(mSQLVersionUp);
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public int getUpdateVersion() {
        return 6;
    }
    
}
