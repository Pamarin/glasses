/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.Database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author jean
 */
public interface iDatabaseConnection {
    public Connection getConnection(boolean aRenewOldConnection) throws SQLException;
    public Connection getNewConnection() throws SQLException;
}
