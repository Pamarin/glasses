/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.SQL;

/**
 *
 * @author jean
 */
public class clsSQLField {
    private final String pTable;
    private final String pField;
    private enmFieldType pFieldType;
    private boolean pIsNotNull;
    private boolean pIsAutoIncrement;
    private boolean pIsPrimaryKey;
    private String pValue;
    private String pDefaultValue;
    
    public enum enmFieldType {
        isInteger,
        isDate,
        isDateTime,
        isVarchar,
        isText
    }
    
    public clsSQLField(String aTable, String aField) {
        this.pTable = aTable;
        this.pField = aField;
    }
    
    public clsSQLField(String aTable, String aField, enmFieldType aFieldType, String aDefaultValue, boolean aIsNotNull, boolean aIsAutoIncrement, boolean aIsPrimaryKey) {
        this.pTable = aTable;
        this.pField = aField;
        this.pFieldType = aFieldType;
        this.pIsNotNull = aIsNotNull;
        this.pIsAutoIncrement = aIsAutoIncrement;
        this.pIsPrimaryKey = aIsPrimaryKey;
        this.pDefaultValue = aDefaultValue;
    }
    
    public clsSQLField(String aTable, String aField, String aValue) {
        this.pTable = aTable;
        this.pField = aField;
        this.pValue = aValue;
    }
    
    public String getTable() {
        return this.pTable;
    }
    
    public String getField() {
        return this.pField;
    }
    
    public String getValue() {
        return this.pValue;
    }
    
    public boolean getIsNotNull() {
        return this.pIsNotNull;
    }
    
    public boolean getIsAutoIncrement() {
        return this.pIsAutoIncrement;
    }
    
    public boolean getIsPrimaryKey() {
        return this.pIsPrimaryKey;
    }
    
    public enmFieldType getFieldType() {
        return this.pFieldType;
    }
}
