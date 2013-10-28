/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.Database;

import BusinessLogic.clsConfiguration;
import DataAccess.SQL.clsSQL;
import DataAccess.SQL.clsSQLField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jean
 */
public class clsDatabase implements iDatabase {
    protected iDatabaseConnection pDatabaseConnection;
    protected Connection pConnection;
    protected Statement pStatement;
    protected ResultSet pResultSet;
    protected clsConfiguration pConfiguration;
    protected String pTablePrefix;
    
    public clsDatabase(iDatabaseConnection aDatabaseConnection, String aTablePrefix) {
        this.pDatabaseConnection = aDatabaseConnection;
        this.pTablePrefix = aTablePrefix;
    }

    @Override
    public boolean Connect(boolean aRenewOldConnection) throws SQLException {
        //Close all connections first.
        Disconnect();
        //Setup the connection.
        this.pConnection = this.pDatabaseConnection.getConnection(aRenewOldConnection);
        
        return true;
    }

    @Override
    public boolean Disconnect() {
        try {
            if (this.pResultSet != null) {
                this.pResultSet.close();
            }
            
            if (this.pStatement != null) {
                this.pStatement.close();
            }
            
            if (this.pConnection != null) {
                this.pConnection.close();
            }
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean createDatabase(String aDatabaseName, boolean aCreateOwnConnection) throws SQLException {
        throw new UnsupportedOperationException("Super class not inteded for use.");
    }

    @Override
    public boolean renameDatabase(String aOldDatabaseName, String aNewDatabaseName, boolean aCreateOwnConnection) throws SQLException {
        throw new UnsupportedOperationException("Super class not inteded for use.");
    }

    @Override
    public boolean deleteDatabase(String aDatabaseName, boolean aCreateOwnConnection) throws SQLException {
        throw new UnsupportedOperationException("Super class not inteded for use.");
    }

    @Override
    public boolean checkTableExists(String aTableName) throws SQLException {
        throw new UnsupportedOperationException("Super class not inteded for use.");
    }

    @Override
    public boolean checkDatabaseExists(String aDatabaseName, boolean aCreateOwnConnection) throws SQLException {
        throw new UnsupportedOperationException("Super class not inteded for use.");
    }

    @Override
    public ResultSet QueryRetrieve(clsSQL aSQL) throws SQLException {
        throw new UnsupportedOperationException("Super class not inteded for use.");
    }

    @Override
    public int QueryExecuteAffectedRows(clsSQL aSQL) throws SQLException {
        throw new UnsupportedOperationException("Super class not inteded for use.");
    }

    @Override
    public ResultSet QueryExecuteCreatedID(clsSQL aSQL) throws SQLException {
        throw new UnsupportedOperationException("Super class not inteded for use.");
    }

    @Override
    public String getField(clsSQLField aSQLField) {
        throw new UnsupportedOperationException("Super class not inteded for use.");
    }
}
