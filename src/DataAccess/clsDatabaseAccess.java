package DataAccess;

import java.sql.*;

/**
 *
 * @author jean
 */
public class clsDatabaseAccess {
	private final String usr;
	private final String pwd;
	private String dbname;
	private final String dblocation;
	private Connection con;
	private final enmDatabaseType databasetype;
	
	public enum enmDatabaseType {
		MySQL
	}
	
	public clsDatabaseAccess(enmDatabaseType databasetype, String dblocation, String dbname, String usr, String pwd) {
		this.databasetype = databasetype;
		this.usr = usr;
		this.pwd = pwd;
		this.dbname = dbname;
		this.dblocation = dblocation;
	}
	
	private boolean setConnection() throws SQLException {
		boolean returnval = false;
		
		try {
			//Choose between the supported databases.
			switch (this.databasetype) {
				case MySQL:
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					con = DriverManager.getConnection("jdbc:mysql://" + this.dblocation + "/" + this.dbname, this.usr, this.pwd);
					break;
				default:
					returnval = true;
					break;
			}
                } catch (    InstantiationException | ClassNotFoundException | IllegalAccessException e) {
                    //TODO: Handle this exception properly.
		}
		
		return returnval;
	}
	
	public clsQuery getQuery(String SQL) throws SQLException {
		//Instantiate the query handler.
		clsQuery q = new clsQuery(SQL);
		//Define the ResultSet.
		ResultSet rs = null;
		
		setConnection();
		
		//Access to the database.
		Statement stmt = con.createStatement();
		rs = stmt.executeQuery(SQL);
		
		q.copyTable(rs);
		
		//Close the Statement, and Connection.
		stmt.close();
		con.close();
		
		return q;
	}
	
	public int executeQuery(String SQL) {
		int affectedRows = 0;
		
		try {
			setConnection();
			
			Statement stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			
			affectedRows = stmt.executeUpdate(SQL);
			
			stmt.close();
			con.close();
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return affectedRows;
	}
	
	public void setDatabase(String dbname) {
		this.dbname = dbname;
	}
	
	public void createDatabase(String dbname, boolean setdatabase) {
		try {
			if (dbname.equals(this.dbname)) {
				throw new Exception("Database already in use.");
			}
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/", this.usr, this.pwd);
			
			Statement stmt = con.createStatement();
			stmt.executeUpdate("CREATE DATABASE " + dbname + ";");
			
			if(setdatabase) {
				this.dbname = dbname;
			}
			
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void deleteDatabase(String dbname, boolean setdatabase) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/", this.usr, this.pwd);
			
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DROP DATABASE " + dbname + ";");
			
			if(this.dbname.equals(dbname) && setdatabase == true) {
				this.dbname = "";
			}
			
			stmt.close();
			con.close();
		} catch (    ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
			//TODO: Handle this exception properly.
		}
	}
}
