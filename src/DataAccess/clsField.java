package DataAccess;

/**
 *
 * @author jean
 */
public class clsField {

	private final int fieldtype;
	private final String fieldname;
	private final Object val;
	
	/**
	 * Handles a database field.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @param fieldtype The field type as java.sql.Types.
	 * @param fieldname The column name of the field.
	 * @param val The value of the field.
	 */
	public clsField(int fieldtype, String fieldname, Object val) {
		this.fieldtype = fieldtype;
		this.fieldname = fieldname;
		this.val = val;
	}
	
	/**
	 * Gets the field type.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @return Returns the field type as java.sql.Types.
	 */
	public int getFieldType() {
		return this.fieldtype;
	}
	
	/**
	 * Gets the field name.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @return Returns the field name.
	 */
	public String getFieldName() {
		return this.fieldname;
	}
	
	/**
	 * Gets the field value.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @return Returns the field value.
	 */
	public Object getValue() {
		return this.val;
	}
}
