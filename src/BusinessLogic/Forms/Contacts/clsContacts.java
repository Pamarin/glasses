/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Forms.Contacts;

import BusinessLogic.clsProgram;
import DataAccess.SQL.clsSQL;
import DataAccess.SQL.clsSQLField;
import DataAccess.SQL.clsSQLRelation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jean
 */
public class clsContacts {
    private clsProgram pProgram;
    
    public enum enmSavingMode {
        New,
        Modify
    }
    
    public clsContacts(clsProgram aProgram) {
        this.pProgram = aProgram;
    }
    
    public AbstractTableModel getContacts() throws SQLException {
        //Create a new table model and directly override the isCellEditable to make the cells not editable.
        clsContactsTableModel mModel = new clsContactsTableModel(this.pProgram);
        
        //Build SQL.
        clsSQL mSQL = new clsSQL(clsSQL.enmSQLType.Select);
        mSQL.addField(new clsSQLField("Contacts", "ID"));
        mSQL.addField(new clsSQLField("Contacts", "ID_Modifications"));
        mSQL.addField(new clsSQLField("Contacts", "FirstName"));
        mSQL.addField(new clsSQLField("Contacts", "MiddleName"));
        mSQL.addField(new clsSQLField("Contacts", "LastName"));
        mSQL.addField(new clsSQLField("Contacts", "Birthday"));
        mSQL.addField(new clsSQLField("Contacts", "Gender"));
        mSQL.addField(new clsSQLField("Contacts", "Street"));
        mSQL.addField(new clsSQLField("Contacts", "Postcode"));
        mSQL.addField(new clsSQLField("Contacts", "City"));
        mSQL.addField(new clsSQLField("Contacts", "Country"));
        mSQL.addField(new clsSQLField("Contacts", "Availability"));
        mSQL.addField(new clsSQLField("Contacts", "Comment"));
        mSQL.addField(new clsSQLField("Modifications", "CreationDate"));
        mSQL.addField(new clsSQLField("Modifications", "ModificationDate"));
        mSQL.addRelation(new clsSQLRelation(clsSQLRelation.enmRelationType.Where, new clsSQLField("Contacts", "Deleted"), "0"));
        mSQL.addRelation(new clsSQLRelation(clsSQLRelation.enmRelationType.Left_Join, new clsSQLField("Modifications", "ID"), new clsSQLField("Contacts", "ID_Modifications")));
        mSQL.addOrdering(new clsSQLField("Contacts", "FirstName"));
        mSQL.addOrdering(new clsSQLField("Contacts", "LastName"));
        //String mSQL = "SELECT * FROM Contacts LEFT JOIN Modifications ON Contacts.ID_Modifications = Modifications.ID WHERE Contacts.Deleted = 0 ORDER BY Contacts.FirstName, Contacts.LastName;";
        //Get the contacts from database.
        mModel.setData(this.pProgram.getDatabase().QueryRetrieve(mSQL));
        
        return mModel;
    }
    
    public boolean deleteContact(int aID) throws SQLException {
        //Build SQL.
        clsSQL mSQL = new clsSQL(clsSQL.enmSQLType.Update);
        mSQL.addField(new clsSQLField("Contacts", "Deleted", "1"));
        mSQL.addRelation(new clsSQLRelation(clsSQLRelation.enmRelationType.Where, new clsSQLField("Contacts", "ID"), "" + aID + ""));
        //String mSQL = "UPDATE Contacts SET Deleted = 1 WHERE ID = " + aID + ";";
        //Update the contact.
        this.pProgram.getDatabase().QueryExecuteAffectedRows(mSQL);
        return true;
    }
    
    public int saveContact(enmSavingMode aSavingMode, clsContactsDetails aContactDetails) throws SQLException {
        //The creation id in the database.
        int mIDContacts = 0;
        //Create the entry in the Contacts table.
        String mFormattedBirthdayDate = "";
        if (aContactDetails.getBirthday() != null) {
            SimpleDateFormat mSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mFormattedBirthdayDate = mSDF.format(aContactDetails.getBirthday());
        } else {
            mFormattedBirthdayDate = "0000-00-00 00:00:00";
        }

        switch (aSavingMode) {
            case New:
                //Build SQL.
                clsSQL mSQLCreateModifiedEntry = new clsSQL(clsSQL.enmSQLType.Insert);
                mSQLCreateModifiedEntry.addField(new clsSQLField("Modifications", "CreationDate", "NOW()"));
                mSQLCreateModifiedEntry.addField(new clsSQLField("Modifications", "ModificationDate", "NOW()"));
                //Create the entry in the Modifications table.
                //String mSQLCreateModifiedEntry = "INSERT INTO Modifications (CreationDate, ModificationDate) VALUES (NOW(), NOW());";
                ResultSet mCreatedIDsInModified = this.pProgram.getDatabase().QueryExecuteCreatedID(mSQLCreateModifiedEntry);
                //Get the newly inserted IDs.
                int mIDModifications = 0;
                while (mCreatedIDsInModified.next()) {
                    mIDModifications = mCreatedIDsInModified.getInt(1);
                    break;
                }
                
                //Build SQL.
                clsSQL mSQLContactNew = new clsSQL(clsSQL.enmSQLType.Insert);
                mSQLContactNew.addField(new clsSQLField("Contacts", "ID_Modifications", "" + mIDModifications + ""));
                mSQLContactNew.addField(new clsSQLField("Contacts", "FirstName", "'" + aContactDetails.getFirstName() + "'"));
                mSQLContactNew.addField(new clsSQLField("Contacts", "MiddleName", "'" + aContactDetails.getMiddleName() + "'"));
                mSQLContactNew.addField(new clsSQLField("Contacts", "LastName", "'" + aContactDetails.getLastName() + "'"));
                mSQLContactNew.addField(new clsSQLField("Contacts", "Birthday", "'" + mFormattedBirthdayDate + "'"));
                mSQLContactNew.addField(new clsSQLField("Contacts", "Gender", "'" + aContactDetails.getGender() + "'"));
                mSQLContactNew.addField(new clsSQLField("Contacts", "Street", "'" + aContactDetails.getStreet() + "'"));
                mSQLContactNew.addField(new clsSQLField("Contacts", "Postcode", "'" + aContactDetails.getPostcode() + "'"));
                mSQLContactNew.addField(new clsSQLField("Contacts", "City", "'" + aContactDetails.getCity() + "'"));
                mSQLContactNew.addField(new clsSQLField("Contacts", "Country", "'" + aContactDetails.getCountry() + "'"));
                mSQLContactNew.addField(new clsSQLField("Contacts", "Comment", "'" + aContactDetails.getComment() + "'"));
                mSQLContactNew.addField(new clsSQLField("Contacts", "Availability", "'" + aContactDetails.getAvailability() + "'"));
                //mSQLContact = "INSERT INTO Contacts (ID_Modifications, FirstName, MiddleName, LastName, Birthday, Gender, Street, Postcode, City, Country, Comment, Availability) VALUES (" + mIDModifications + ", '" + aContactDetails.getFirstName() + "', '" + aContactDetails.getMiddleName() + "', '" + aContactDetails.getLastName() + "', '" + mFormattedBirthdayDate + "', '" + aContactDetails.getGender() + "', '" + aContactDetails.getStreet() + "', '" + aContactDetails.getPostcode() + "', '" + aContactDetails.getCity() + "', '" + aContactDetails.getCountry() + "', '" + aContactDetails.getComment() + "', '" + aContactDetails.getAvailability() + "');";
                //Insert the contact.
                ResultSet mRSCreatedIDs = this.pProgram.getDatabase().QueryExecuteCreatedID(mSQLContactNew);
                mRSCreatedIDs.first();
                while (mRSCreatedIDs.isAfterLast() == false) {
                    mIDContacts = mRSCreatedIDs.getInt(1);
                    break;
                }
                break;
            case Modify:
                //Build SQL.
                clsSQL mSQLContactModify = new clsSQL(clsSQL.enmSQLType.Update);
                mSQLContactModify.addField(new clsSQLField("Contacts", "FirstName", aContactDetails.getFirstName()));
                mSQLContactModify.addField(new clsSQLField("Contacts", "MiddleName", aContactDetails.getMiddleName()));
                mSQLContactModify.addField(new clsSQLField("Contacts", "LastName", aContactDetails.getLastName()));
                mSQLContactModify.addField(new clsSQLField("Contacts", "Birthday", mFormattedBirthdayDate));
                mSQLContactModify.addField(new clsSQLField("Contacts", "Gender", aContactDetails.getGender()));
                mSQLContactModify.addField(new clsSQLField("Contacts", "Street", aContactDetails.getFirstName()));
                mSQLContactModify.addField(new clsSQLField("Contacts", "Postcode", aContactDetails.getFirstName()));
                mSQLContactModify.addField(new clsSQLField("Contacts", "City", aContactDetails.getFirstName()));
                mSQLContactModify.addField(new clsSQLField("Contacts", "Country", aContactDetails.getFirstName()));
                mSQLContactModify.addField(new clsSQLField("Contacts", "Comment", aContactDetails.getFirstName()));
                mSQLContactModify.addField(new clsSQLField("Contacts", "Availability", aContactDetails.getFirstName()));
                mSQLContactModify.addRelation(new clsSQLRelation(clsSQLRelation.enmRelationType.Where, new clsSQLField("Contacts", "ID"), "" + aContactDetails.getID() + ""));
                //mSQLContact = "UPDATE Contacts SET FirstName = '" + aContactDetails.getFirstName() + "', MiddleName = '" + aContactDetails.getMiddleName() + "', LastName = '" + aContactDetails.getLastName() + "', Birthday = '" + mFormattedBirthdayDate + "', Gender = '" + aContactDetails.getGender() + "', Street = '" + aContactDetails.getStreet() + "', Postcode = '" + aContactDetails.getPostcode() + "', City = '" + aContactDetails.getCity() + "', Country = '" + aContactDetails.getCountry() + "', Comment = '" + aContactDetails.getComment() + "', Availability = '" + aContactDetails.getAvailability() + "' WHERE ID = " + aContactDetails.getID() + ";";
                //Update the contact.
                this.pProgram.getDatabase().QueryExecuteAffectedRows(mSQLContactModify);
                
                //Build SQL.
                clsSQL mSQLModification = new clsSQL(clsSQL.enmSQLType.Update);
                mSQLModification.addField(new clsSQLField("Modifications", "ModificationDate", "NOW()"));
                mSQLModification.addRelation(new clsSQLRelation(clsSQLRelation.enmRelationType.Where, new clsSQLField("Modifications", "ID"), "" + aContactDetails.getIDModifications() + ""));
                //mSQLModification = "UPDATE Modifications SET ModificationDate = NOW() WHERE ID = '" + aContactDetails.getIDModifications() + "';";
                //Update the modification date.
                this.pProgram.getDatabase().QueryExecuteAffectedRows(mSQLModification);
                mIDContacts = aContactDetails.getID();
                break;
        }

        return mIDContacts;
    }
}
