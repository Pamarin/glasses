/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.Database;

import DataAccess.SQL.clsSQL;
import DataAccess.SQL.clsSQLField;
import DataAccess.SQL.clsSQLRelation;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 *
 * @author jean
 */
public class clsMySQL extends clsDatabase {
    public clsMySQL(iDatabaseConnection aDatabaseConnection, String aTablePrefix) throws ClassNotFoundException {
        super(aDatabaseConnection, aTablePrefix);
    }

    @Override
    public boolean createDatabase(String aDatabaseName, boolean aCreateOwnConnection) throws SQLException {
        if (aDatabaseName.equals("")) {
            return false;
        }
        
        Connection mConnection = null;

        if (aCreateOwnConnection) {
                //Setup the own connection.
                mConnection = this.pDatabaseConnection.getNewConnection();
        } else {
            mConnection = this.pConnection;
        }

        //Statement for allowing to issue SQL queries.
        Statement mStatement = mConnection.createStatement();
        //Create the database.
        mStatement.executeUpdate("CREATE DATABASE " + aDatabaseName + ";");

        return true;
    }

    @Override
    public boolean renameDatabase(String aOldDatabaseName, String aNewDatabaseName, boolean aCreateOwnConnection) throws SQLException {
        if (aOldDatabaseName.equals("") || aNewDatabaseName.equals("")) {
            return false;
        }
        
        Connection mConnection = null;

        if (aCreateOwnConnection) {
                //Setup the own connection.
                mConnection = this.pDatabaseConnection.getNewConnection();
        } else {
            mConnection = this.pConnection;
        }

        //Statement for allowing to issue SQL queries.
        Statement mStatement = mConnection.createStatement();
        //Rename the database.
        mStatement.executeUpdate("RENAME DATABASE " + aOldDatabaseName + " TO " + aNewDatabaseName + ";");

        return true;
    }

    @Override
    public boolean deleteDatabase(String aDatabaseName, boolean aCreateOwnConnection) throws SQLException {
        if (aDatabaseName.equals("")) {
            return false;
        }
        
        Connection mConnection = null;

        if (aCreateOwnConnection) {
                //Setup the own connection.
                mConnection = this.pDatabaseConnection.getNewConnection();
        } else {
            mConnection = this.pConnection;
        }

        //Statement for allowing to issue SQL queries.
        Statement mStatement = mConnection.createStatement();
        //Delete the database.
        mStatement.executeUpdate("DROP DATABASE " + aDatabaseName + ";");

        return true;
    }

    @Override
    public boolean checkTableExists(String aTableName) throws SQLException {
        if (this.pConnection == null) {
            Connect(false);
        }

        //Statement for allowing to issue SQL queries.
        Statement mStatement = this.pConnection.createStatement();
        //Excecute the SQL.
        
        //Get the number of rows.
        String mSQL = "SHOW TABLES LIKE '" + this.pTablePrefix + aTableName + "';";
        ResultSet rs = mStatement.executeQuery(mSQL);
        rs.last();
        int rowsAffected = rs.getRow();
        return rowsAffected > 0;
    }

    @Override
    public boolean checkDatabaseExists(String aDatabaseName, boolean aCreateOwnConnection) throws SQLException {
        if (aDatabaseName.equals("")) {
            return false;
        }
        
        Connection mConnection = null;

        //Choose where to take the connection from.
        if (aCreateOwnConnection) {
                //Setup the own connection.
                mConnection = this.pDatabaseConnection.getNewConnection();
        } else {
            mConnection = this.pConnection;
        }

        //Statement for allowing to issue SQL queries.
        this.pStatement = mConnection.createStatement();

        //Excecute the SQL.
        String mSQL = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + aDatabaseName + "';";
        //Get the number of rows.
        ResultSet rs = this.pStatement.executeQuery(mSQL);
        rs.last();
        int rowsAffected = rs.getRow();
        return rowsAffected > 0;
    }

    @Override
    public int QueryExecuteAffectedRows(clsSQL aSQL) throws SQLException {
        if (this.pConnection == null) {
            Connect(false);
        }
        
        //Statement for allowing to issue SQL queries.
        this.pStatement = this.pConnection.createStatement();
        
        //Build the SQL.
        String mSQL = buildSQL(aSQL);
        //Excecute the SQL.
        return this.pStatement.executeUpdate(mSQL);
    }

    @Override
    public ResultSet QueryExecuteCreatedID(clsSQL aSQL) throws SQLException {
        if (this.pConnection == null) {
            Connect(false);
        }
        
        //Statement for allowing to issue SQL queries.
        this.pStatement = this.pConnection.createStatement();
        
        //Build the SQL.
        String mSQL = buildSQL(aSQL);
        //Excecute the SQL.
        this.pStatement.executeUpdate(mSQL, Statement.RETURN_GENERATED_KEYS);
        ResultSet mRS = this.pStatement.getGeneratedKeys();
        return mRS;
    }

    @Override
    public ResultSet QueryRetrieve(clsSQL aSQL) throws SQLException {
        if (this.pConnection == null) {
            Connect(false);
        }
        
        //Statement for allowing to issue SQL queries.
        this.pStatement = this.pConnection.createStatement();
        
        //Build the SQL.
        String mSQL = buildSQL(aSQL);
        //Excecute the SQL.
        return this.pStatement.executeQuery(mSQL);
    }
    
    private String buildSQL(clsSQL aSQL) {
        //TODO: Build the appropriate SQL.
        String mSQL = "";
        String mSQLJoin = "";
        ArrayList<String> mJoinTable = new ArrayList<>();
        ArrayList<clsSQLRelation> mRelation = null;
        ArrayList<clsSQLRelation> mRelationWhere = null;
        
        switch (aSQL.getSQLType()) {
            case Alter_Table_Add_Column:
                //ALTER TABLE Contacts ADD COLUMN Comment TEXT;
                
                //Generate start SQL syntax.
                mSQL = "ALTER TABLE ";
                //Generate table to alter.
                ArrayList<String> mAlterTableAddColumnTables = aSQL.getTables();
                mSQL += this.pTablePrefix + mAlterTableAddColumnTables.get(0);
                //Add columns.
                ArrayList<clsSQLField> mAlterTableAddColumnFields = aSQL.getFields();
                for (int i=0; i<mAlterTableAddColumnFields.size(); i++) {
                    if (i>0) {
                        mSQL += ", ";
                    } else {
                        mSQL += " ";
                    }
                    //Add column name and type.
                    mSQL += "ADD COLUMN " + mAlterTableAddColumnFields.get(i).getField();
                    //Add field type.
                    switch (mAlterTableAddColumnFields.get(i).getFieldType()) {
                        case isDate:
                            mSQL += " DATE";
                            break;
                        case isDateTime:
                            mSQL += " DATETIME";
                            break;
                        case isInteger:
                            mSQL += " INT";
                            break;
                        case isText:
                            mSQL += " TEXT";
                            break;
                        case isVarchar:
                            mSQL += " VARCHAR(255)";
                            break;
                    }
                    //Add null reaction.
                    if (mAlterTableAddColumnFields.get(i).getIsNotNull()) {
                        mSQL += " NOT NULL";
                    }
                    //Add auto increment.
                    if (mAlterTableAddColumnFields.get(i).getIsAutoIncrement()) {
                        mSQL += " AUTO_INCREMENT";
                    }
                }
                mSQL += ";";
                break;
            case Alter_Table_Drop_Column:
                //ALTER TABLE Contacts DROP COLUMN Phone, DROP COLUMN Fax, DROP COLUMN Email, DROP COLUMN Website;
                
                //Generate start SQL syntax.
                mSQL = "ALTER TABLE ";
                //Generate table to alter.
                ArrayList<String> mAlterTableDropColumnTables = aSQL.getTables();
                mSQL += this.pTablePrefix + mAlterTableDropColumnTables.get(0);
                //Add columns.
                ArrayList<clsSQLField> mAlterTableDropColumnFields = aSQL.getFields();
                for (int i=0; i<mAlterTableDropColumnFields.size(); i++) {
                    if (i>0) {
                        mSQL += ", ";
                    } else {
                        mSQL += " ";
                    }
                    //Add column name.
                    mSQL += "DROP COLUMN " + mAlterTableDropColumnFields.get(i).getField();
                }
                mSQL += ";";
                break;
            case Create_Table:
                //Generate start SQL syntax.
                mSQL = "CREATE TABLE ";
                
                //Generate table to create.
                ArrayList<String> mCreateTable = aSQL.getTables();
                mSQL += this.pTablePrefix + mCreateTable.get(0);
                
                //Generate fields to create.
                mSQL += " (";
                ArrayList<clsSQLField> mCreateFields = aSQL.getFields();
                ArrayList<String> mCreatePrimaryKeys = new ArrayList<>();
                for (int i=0; i<mCreateFields.size(); i++) {
                    if (i>0) {
                        mSQL += ", ";
                    }
                    //Add field name.
                    mSQL += mCreateFields.get(i).getField();
                    //Add field type.
                    switch (mCreateFields.get(i).getFieldType()) {
                        case isDate:
                            mSQL += " DATE";
                            break;
                        case isDateTime:
                            mSQL += " DATETIME";
                            break;
                        case isInteger:
                            mSQL += " INT";
                            break;
                        case isText:
                            mSQL += " TEXT";
                            break;
                        case isVarchar:
                            mSQL += " VARCHAR(255)";
                            break;
                    }
                    //Add null reaction.
                    if (mCreateFields.get(i).getIsNotNull()) {
                        mSQL += " NOT NULL";
                    }
                    //Add auto increment.
                    if (mCreateFields.get(i).getIsAutoIncrement()) {
                        mSQL += " AUTO_INCREMENT";
                    }
                    //Remember primary keys.
                    if (mCreateFields.get(i).getIsPrimaryKey()) {
                        mCreatePrimaryKeys.add(mCreateFields.get(i).getField());
                    }
                }
                //Add primary keys.
                if (mCreatePrimaryKeys.size()>0) {
                    mSQL += ", PRIMARY KEY (";
                    for (int i=0; i<mCreatePrimaryKeys.size(); i++) {
                        if (i>0) {
                            mSQL += ", ";
                        }
                        mSQL += mCreatePrimaryKeys.get(i);
                    }
                    mSQL += ")";
                }
                mSQL += ");";
                break;
            case Drop_Table:
                //Generate drop table SQL syntax.
                ArrayList<String> mDropTableTables = aSQL.getTables();
                mSQL = "DROP TABLE " + this.pTablePrefix + mDropTableTables.get(0) + ";";
                break;
            case Insert:
                //Generate start SQL syntax.
                mSQL = "INSERT INTO ";
                
                //Generate table SQL syntax.
                ArrayList<String> mInsertTable = aSQL.getTables();
                mSQL += this.pTablePrefix + mInsertTable.get(0);
                
                //Generate fields SQL sytnax.
                ArrayList<clsSQLField> mInsertFields = aSQL.getFields();
                String mSQLFields = "";
                String mSQLValues = "";
                for (int i=0; i<mInsertFields.size(); i++) {
                    if (i>0) {
                        mSQLFields += ", ";
                        mSQLValues += ", ";
                    }
                    mSQLFields += this.pTablePrefix + mInsertFields.get(i).getTable() + "." + mInsertFields.get(i).getField();
                    mSQLValues += mInsertFields.get(i).getValue();
                }
                mSQL += " (" + mSQLFields + ") VALUES (" + mSQLValues + ");";
                break;
            case Select:
                //Generate start SQL syntax.
                mSQL = "SELECT ";
                
                //Generate SQL fields.
                ArrayList<clsSQLField> mSelectFields = aSQL.getFields();
                for (int i=0; i<mSelectFields.size(); i++) {
                    if (i>0) {
                        //Add a comma between the fields.
                        mSQL += ", ";
                    }
                    //Add field to the return SQL.
                    mSQL += this.pTablePrefix + mSelectFields.get(i).getTable() + "." + mSelectFields.get(i).getField();
                }
                
                //Generate relations SQL syntax.
                mRelation = aSQL.getRelations();
                mRelationWhere = new ArrayList<>();
                for (int i=0; i<mRelation.size(); i++) {
                    if (mRelation.get(i).getRelationType() == clsSQLRelation.enmRelationType.Left_Join) {
                        //Generate the left join SQL syntax.
                        mJoinTable.add(mRelation.get(i).getField1().getTable());
                        mSQLJoin += " LEFT JOIN " + this.pTablePrefix + mRelation.get(i).getField1().getTable() + " ON " + this.pTablePrefix + mRelation.get(i).getField1().getTable() + "." + mRelation.get(i).getField1().getField() + " = ";
                        if (mRelation.get(i).getField2() == null) {
                            mSQLJoin += mRelation.get(i).getField2String();
                        } else {
                            mSQLJoin += this.pTablePrefix + mRelation.get(i).getField2().getTable() + "." + mRelation.get(i).getField2().getField();
                        }
                    } else if (mRelation.get(i).getRelationType() == clsSQLRelation.enmRelationType.Where) {
                        //Collect all the where clauses for later use.
                        mRelationWhere.add(mRelation.get(i));
                    }
                }
                
                //Generate split SQL syntax.
                mSQL += " FROM ";
                ArrayList<String> mTables = aSQL.getTables();
                mTables.removeAll(mJoinTable);
                for (int i=0; i<mTables.size(); i++) {
                    if (i>0) {
                        //Add a comma between the tables.
                        mSQL += ", ";
                    }
                    //Add table to the return SQL.
                    mSQL += this.pTablePrefix + mTables.get(i);
                }
                
                mSQL += mSQLJoin;
                
                //Generate the where SQL syntax.
                for (int i=0; i<mRelationWhere.size(); i++) {
                    if (i==0) {
                        mSQL += " WHERE";
                    } else {
                        mSQL += " AND";
                    }
                    mSQL += " " + this.pTablePrefix + mRelationWhere.get(i).getField1().getTable() + "." + mRelationWhere.get(i).getField1().getField() + " = ";
                    if (mRelationWhere.get(i).getField2() == null) {
                        mSQL += mRelationWhere.get(i).getField2String();
                    } else {
                        mSQL += this.pTablePrefix + mRelationWhere.get(i).getField2().getTable() + "." + mRelationWhere.get(i).getField2().getField();
                    }
                }
                //Generate the ordering SQL syntax.
                ArrayList<clsSQLField> mOrdering = aSQL.getOrderings();
                for (int i=0; i<mOrdering.size(); i++) {
                    if (i==0) {
                        mSQL += " ORDER BY";
                    } else {
                        mSQL += ",";
                    }
                    mSQL += " " + this.pTablePrefix + mOrdering.get(i).getTable() + "." + mOrdering.get(i).getField();
                }
                
                mSQL += ";";
                break;
            case Show_Tables:
                mSQL = "SHOW TABLES;";
                break;
            case Update:
                //Generate start SQL syntax.
                mSQL = "UPDATE ";
                //Generate table to create.
                ArrayList<String> mUpdateTables = aSQL.getTables();
                mSQL += this.pTablePrefix + mUpdateTables.get(0);
                //Split up update.
                mSQL += " SET ";
                //Generate fields.
                ArrayList<clsSQLField> mUpdateFields = aSQL.getFields();
                for (int i=0; i<mUpdateFields.size(); i++) {
                    if (i>0) {
                        mSQL += ", ";
                    }
                    mSQL += this.pTablePrefix + mUpdateTables.get(0) + "." + mUpdateFields.get(i).getField() + " = " + mUpdateFields.get(i).getValue();
                }
                
                //Generate relations SQL syntax.
                mRelation = aSQL.getRelations();
                mRelationWhere = new ArrayList<>();
                for (int i=0; i<mRelation.size(); i++) {
                    if (mRelation.get(i).getRelationType() == clsSQLRelation.enmRelationType.Left_Join) {
                        //Generate the left join SQL syntax.
                        mJoinTable.add(mRelation.get(i).getField1().getTable());
                        mSQLJoin += " LEFT JOIN " + this.pTablePrefix + mRelation.get(i).getField1().getTable() + " ON " + this.pTablePrefix + mRelation.get(i).getField1().getTable() + "." + mRelation.get(i).getField1().getField() + " = ";
                        if (mRelation.get(i).getField2() == null) {
                            mSQLJoin += mRelation.get(i).getField2String();
                        } else {
                            mSQLJoin += this.pTablePrefix + mRelation.get(i).getField2().getTable() + "." + mRelation.get(i).getField2().getField();
                        }
                    } else if (mRelation.get(i).getRelationType() == clsSQLRelation.enmRelationType.Where) {
                        //Collect all the where clauses for later use.
                        mRelationWhere.add(mRelation.get(i));
                    }
                }
                
                //Generate the where SQL syntax.
                for (int i=0; i<mRelationWhere.size(); i++) {
                    if (i==0) {
                        mSQL += " WHERE";
                    } else {
                        mSQL += " AND";
                    }
                    mSQL += " " + this.pTablePrefix + mRelationWhere.get(i).getField1().getTable() + "." + mRelationWhere.get(i).getField1().getField() + " = ";
                    if (mRelationWhere.get(i).getField2() == null) {
                        mSQL += mRelationWhere.get(i).getField2String();
                    } else {
                        mSQL += this.pTablePrefix + mRelationWhere.get(i).getField2().getTable() + "." + mRelationWhere.get(i).getField2().getField();
                    }
                }
                mSQL += ";";
                break;
        }
        
        return mSQL;
    }

    @Override
    public String getField(clsSQLField aSQLField) {
        return this.pTablePrefix + aSQLField.getTable() + "." + aSQLField.getField();
    }
}
