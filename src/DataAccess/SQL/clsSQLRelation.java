/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.SQL;

/**
 *
 * @author jean
 */
public class clsSQLRelation {
    private final clsSQLField pField1;
    private clsSQLField pField2;
    private String pField2String;
    private final enmRelationType pRelationType;
    
    public enum enmRelationType {
        Where,
        Left_Join
    }
    
    public clsSQLRelation(enmRelationType aRelationType, clsSQLField aField1, clsSQLField aField2) {
        this.pRelationType = aRelationType;
        this.pField1 = aField1;
        this.pField2 = aField2;
    }
    
    public clsSQLRelation(enmRelationType aRelationType, clsSQLField aField1, String aField2String) {
        this.pRelationType = aRelationType;
        this.pField1 = aField1;
        this.pField2String = aField2String;
    }
    
    public enmRelationType getRelationType() {
        return this.pRelationType;
    }
    
    public clsSQLField getField1() {
        return this.pField1;
    }
    
    public clsSQLField getField2() {
        return this.pField2;
    }
    
    public String getField2String() {
        return this.pField2String;
    }
}
