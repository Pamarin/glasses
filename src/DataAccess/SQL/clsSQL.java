/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.SQL;

import java.util.ArrayList;

/**
 *
 * @author jean
 */
public class clsSQL {
    private ArrayList<clsSQLField> pSQLFields;
    private ArrayList<clsSQLRelation> pSQLRelation;
    private ArrayList<clsSQLField> pSQLOrdering;
    private ArrayList<String> pTable;
    private enmSQLType pSQLType;
    
    public enum enmSQLType {
        Select,
        Update,
        Insert,
        Alter_Table_Add_Column,
        Alter_Table_Drop_Column,
        Create_Table,
        Drop_Table,
        Show_Tables
    }
    
    public clsSQL(enmSQLType aSQLType) {
        this.pSQLType = aSQLType;
    }
    
    public enmSQLType getSQLType() {
        return this.pSQLType;
    }
    
    public void addField(clsSQLField aSQLField) {
        if (this.pSQLFields == null) {
            this.pSQLFields = new ArrayList<>();
        }
        
        this.pSQLFields.add(aSQLField);
        addTable(aSQLField);
    }
    
    public void addRelation(clsSQLRelation aSQLRelation) {
        if (this.pSQLRelation == null) {
            this.pSQLRelation = new ArrayList<>();
        }
        
        this.pSQLRelation.add(aSQLRelation);
        addTable(aSQLRelation.getField1());
        addTable(aSQLRelation.getField2());
    }
    
    public void addOrdering(clsSQLField aSQLOrdering) {
        if (this.pSQLOrdering == null) {
            this.pSQLOrdering = new ArrayList<>();
        }
        
        this.pSQLOrdering.add(aSQLOrdering);
        addTable(aSQLOrdering);
    }
    
    public ArrayList<clsSQLRelation> getRelations() {
        if (this.pSQLRelation == null) {
            this.pSQLRelation = new ArrayList<>();
        }
        return this.pSQLRelation;
    }
    
    public ArrayList<clsSQLField> getFields() {
        if (this.pSQLFields == null) {
            this.pSQLFields = new ArrayList<>();
        }
        return this.pSQLFields;
    }
    
    public ArrayList<String> getTables() {
        if (this.pTable == null) {
            this.pTable = new ArrayList<>();
        }
        return this.pTable;
    }
    
    public ArrayList<clsSQLField> getOrderings() {
        if (this.pSQLOrdering == null) {
            this.pSQLOrdering = new ArrayList<>();
        }
        return this.pSQLOrdering;
    }
    
    private void addTable(clsSQLField aSQLField) {
        if (this.pTable == null) {
            this.pTable = new ArrayList<>();
        }
        
        //Avoid doubles.
        if (aSQLField != null) {
            if (checkIfTableAlreadyAdded(aSQLField.getTable()) == false) {
                this.pTable.add(aSQLField.getTable());
            }
        }
    }
    
    private boolean checkIfTableAlreadyAdded(String aTable) {
        boolean returnVal = false;
        
        for (int i=0; i<this.pTable.size(); i++) {
            if (this.pTable.get(i).equals(aTable)) {
                returnVal = true;
            }
        }
        
        return returnVal;
    }
}
