package DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jean
 */
public class clsQuery {
	private String sql;
	private clsField[][] table;
	
	/**
	 * Handles a database query.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @param sql The SQL query.
	 */
	public clsQuery(String sql) {
		this.sql = sql;
	}
	
	/**
	 * Reads the complete RecordSet and stores it in a 2D-array.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @param rs The ResultSet to be copied.
	 * @return Returns the state of success.
	 * @throws SQLException when the RecordSet fails.
	 */
	public boolean copyTable(ResultSet rs) throws SQLException {
		//Default variable for the return value.
		boolean returnval = false;
		
		//Count rows.
		rs.last();
		int numrows = rs.getRow();
		rs.beforeFirst();
		//Count columns.
		int numcols = rs.getMetaData().getColumnCount();
		//Create array for the ResultSet table.
		this.table = new clsField[numrows][numcols];

		//Go through all the rows of the ResultSet.
		while(rs.next()) {
			//Go through all the columns of the ResultSet.
			for(int i=1; i<(numcols+1); i++) {
				//Store column type.
				int fieldtype = rs.getMetaData().getColumnType(i);
				//Store column name.
				String fieldname = rs.getMetaData().getColumnLabel(i);
				//Store column value.
				Object val = rs.getObject(i);
				//Define clsField object.
				clsField field = new clsField(fieldtype, fieldname, val);
				//Store the field in the table.
				this.table[rs.getRow()-1][i-1] = field;
			}
		}

		return returnval;
	}
	
	/**
	 * Gets the SQL query.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @return Returns the SQL query.
	 */
	public String getSQL() {
		return this.sql;
	}
	
	/**
	 * Gets the table.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @return Returns the table.
	 */
	public clsField[][] getTable() {
		return this.table;
	}
}
