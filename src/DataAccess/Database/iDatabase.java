/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.Database;

import DataAccess.SQL.clsSQL;
import DataAccess.SQL.clsSQLField;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jean
 */
public interface iDatabase {
    public boolean Connect(boolean aRenewOldConnection) throws SQLException;
    public boolean Disconnect();
    public boolean createDatabase(String aDatabaseName, boolean aCreateOwnConnection) throws SQLException;
    public boolean renameDatabase(String aOldDatabaseName, String aNewDatabaseName, boolean aCreateOwnConnection) throws SQLException;
    public boolean deleteDatabase(String aDatabaseName, boolean aCreateOwnConnection) throws SQLException;
    public boolean checkTableExists(String aTableName) throws SQLException;
    public boolean checkDatabaseExists(String aDatabaseName, boolean aCreateOwnConnection) throws SQLException;
    public ResultSet QueryRetrieve(clsSQL aSQL) throws SQLException;
    public int QueryExecuteAffectedRows(clsSQL aSQL) throws SQLException;
    public ResultSet QueryExecuteCreatedID(clsSQL aSQL) throws SQLException;
    public String getField(clsSQLField aSQLField);
}
