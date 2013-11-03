/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Forms.Contacts;

import BusinessLogic.clsProgram;
import DataAccess.SQL.clsSQLField;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jean
 */
public class clsContactsTableModel extends AbstractTableModel {
    private Object[][] pDataArray;
    private ResultSet pDataResultSet;
    private final clsProgram pProgram;
    private final String[] columnNames = {"First name", "Last name", "Type"};
    
    public clsContactsTableModel(clsProgram aProgram) {
        this.pProgram = aProgram;
    }
    
    /**
     * Sets the data for the model.
     * @author Jean-Luc Burot
     * @param aData The RecordSet given from the database.
     */
    public void setData(ResultSet aData) {
        //Fills both, the ResultSet (pDatabaseData) and the Object-Array (pTableData).
        
        this.pDataResultSet = aData;
        
        try {
            //Count the desired table columns (Not the database columns).
            int mColumnCount = columnNames.length + 2;
            //Create the rows.
            List mRows = new ArrayList();
            //Create each rows.
            this.pDataResultSet.first();
            while (this.pDataResultSet.isAfterLast() == false) {
                Object[] mRow = new Object[mColumnCount];
                mRow[0] = this.pDataResultSet.getObject(this.pProgram.getDatabase().getField(new clsSQLField("Contacts", "FirstName")));
                mRow[1] = this.pDataResultSet.getObject(this.pProgram.getDatabase().getField(new clsSQLField("Contacts", "LastName")));
                mRow[2] = "-not set yet-";
                mRow[3] = this.pDataResultSet.getObject(this.pProgram.getDatabase().getField(new clsSQLField("Contacts", "ID")));
                mRow[4] = this.pDataResultSet.getObject(this.pProgram.getDatabase().getField(new clsSQLField("Contacts", "ID_Modifications")));
                mRows.add(mRow);
                this.pDataResultSet.next();
            }
            
            this.pDataArray = (Object[][]) mRows.toArray(new Object[mRows.size()][mColumnCount]);
        } catch (SQLException ex) {
            this.pDataResultSet = null;
            this.pDataArray = null;
            Logger.getLogger(clsContactsTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
 
    @Override
    public int getRowCount() {
        if (this.pDataArray == null) {
            return 0;
        } else {
            return this.pDataArray.length;
        }
    }
 
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
 
    @Override
    public Object getValueAt(int aRow, int aCol) {
        if (this.pDataArray[aRow][aCol] != null) {
            return this.pDataArray[aRow][aCol];
        } else {
            return "";
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int aRow, int aCol) {
        this.pDataArray[aRow][aCol] = aValue;
        fireTableCellUpdated(aRow, aCol);
    }
    
    public int getID(int aRow) {
        return (int) getValueAt(aRow, 3);
    }
    
    public int getIDModifications(int aRow) {
        return (int) getValueAt(aRow, 4);
    }
    
    public clsContactsDetails getContactDetails(int aRow) {
        clsContactsDetails mContactsDetails = new clsContactsDetails();
        int mID = getID(aRow);
        //Search all contacts until match.
        try {
            this.pDataResultSet.first();
            while(this.pDataResultSet.isAfterLast() == false) {
                if(this.pDataResultSet.getInt("ID") == mID) {
                    mContactsDetails.setID(mID);
                    mContactsDetails.setIDModification(this.pDataResultSet.getInt(this.pProgram.getDatabase().getField(new clsSQLField("Contacts", "ID_Modifications"))));
                    mContactsDetails.setFirstName(this.pDataResultSet.getString(this.pProgram.getDatabase().getField(new clsSQLField("Contacts", "FirstName"))));
                    mContactsDetails.setMiddleName(this.pDataResultSet.getString(this.pProgram.getDatabase().getField(new clsSQLField("Contacts", "MiddleName"))));
                    mContactsDetails.setLastName(this.pDataResultSet.getString(this.pProgram.getDatabase().getField(new clsSQLField("Contacts", "LastName"))));
                    try {
                        mContactsDetails.setBirthday(this.pDataResultSet.getDate(this.pProgram.getDatabase().getField(new clsSQLField("Contacts", "Birthday"))));
                    } catch (SQLException ex) {
                        // TODO: Hanlde this nicer.
                    }
                    mContactsDetails.setGender(this.pDataResultSet.getString(this.pProgram.getDatabase().getField(new clsSQLField("Contacts", "Gender"))));
                    mContactsDetails.setStreet(this.pDataResultSet.getString(this.pProgram.getDatabase().getField(new clsSQLField("Contacts", "Street"))));
                    mContactsDetails.setPostcode(this.pDataResultSet.getString(this.pProgram.getDatabase().getField(new clsSQLField("Contacts", "Postcode"))));
                    mContactsDetails.setCity(this.pDataResultSet.getString(this.pProgram.getDatabase().getField(new clsSQLField("Contacts", "City"))));
                    mContactsDetails.setCountry(this.pDataResultSet.getString(this.pProgram.getDatabase().getField(new clsSQLField("Contacts", "Country"))));
                    mContactsDetails.setAvailability(this.pDataResultSet.getString(this.pProgram.getDatabase().getField(new clsSQLField("Contacts", "Availability"))));
                    mContactsDetails.setComment(this.pDataResultSet.getString(this.pProgram.getDatabase().getField(new clsSQLField("Contacts", "Comment"))));
                    
                    long mDateCreatedTime1 = this.pDataResultSet.getDate(this.pProgram.getDatabase().getField(new clsSQLField("Modifications", "CreationDate"))).getTime();
                    long mDateCreatedTime2 = this.pDataResultSet.getTime(this.pProgram.getDatabase().getField(new clsSQLField("Modifications", "CreationDate"))).getTime();
                    long mDateCreatedTime3 = mDateCreatedTime1 + mDateCreatedTime2;
                    Date mDateCreated = new Date(mDateCreatedTime3);
                    mContactsDetails.setDateCreated(mDateCreated);
                    
                    long mDateModifiedTime1 = this.pDataResultSet.getDate(this.pProgram.getDatabase().getField(new clsSQLField("Modifications", "ModificationDate"))).getTime();
                    long mDateModifiedTime2 = this.pDataResultSet.getTime(this.pProgram.getDatabase().getField(new clsSQLField("Modifications", "ModificationDate"))).getTime();
                    long mDateModifiedTime3 = mDateModifiedTime1 + mDateModifiedTime2;
                    Date mDateModified = new Date(mDateModifiedTime3);
                    mContactsDetails.setDateModified(mDateModified);
                    
                    break;
                } else {
                    this.pDataResultSet.next();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(clsContactsTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mContactsDetails;
    }
    
    public int getRowPosition(int aID) {
        for (int i=0; i<this.pDataArray.length; i++) {
            if ((int) this.pDataArray[i][3] == aID) {
                return i;
            }
        }
        return -1;
    }
 
    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn’t implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
 
    /*
     * Don’t need to implement this method unless your table’s
     * editable.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        return false;
    }
}
