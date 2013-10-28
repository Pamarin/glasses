/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.Database;

import BusinessLogic.clsConfiguration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jean
 */
public class clsMySQLConnection implements iDatabaseConnection {
    private Connection pConnection;
    private clsConfiguration pConfiguration;
    
    public clsMySQLConnection(clsConfiguration aConfiguration) {
        this.pConfiguration = aConfiguration;
        
        try {
            //Load the MySQL driver.
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(clsMySQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Connection getConnection(boolean aRenewOldConnection) throws SQLException {
        if (this.pConnection == null || aRenewOldConnection == true) {
            //Prepare connection variables.
            String mUser = "";
            if (!"".equals(this.pConfiguration.getDatabaseUsername())) {
                mUser = "?user=" + this.pConfiguration.getDatabaseUsername();
            }
            
            String mPassword = "";
            if (!"".equals(this.pConfiguration.getDatabasePassword())) {
                mPassword = "&password=" + this.pConfiguration.getDatabasePassword();
            }
            
            String mPort = "";
            if (!"".equals(this.pConfiguration.getDatabasePort())) {
                mPort = ":" + this.pConfiguration.getDatabasePort();
            }
            
            //Setup the connection.
            String mConnectionString = "jdbc:mysql://" + this.pConfiguration.getDatabaseLocation() + mPort + "/" + this.pConfiguration.getDatabaseName() + mUser + mPassword;
            this.pConnection = DriverManager.getConnection(mConnectionString);
        }
        
        return this.pConnection;
    }

    @Override
    public Connection getNewConnection() throws SQLException {
        //Setup the connection.
        String mConnectionString = "jdbc:mysql://" + this.pConfiguration.getDatabaseLocation() + "/mysql?user=" + this.pConfiguration.getDatabaseUsername() + "&password=" + this.pConfiguration.getDatabasePassword();
        return DriverManager.getConnection(mConnectionString);
    }
}
