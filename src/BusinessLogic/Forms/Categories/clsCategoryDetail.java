/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Forms.Categories;

import BusinessLogic.Forms.clsCategories;
import java.util.Date;

/**
 *
 * @author jean
 */
public class clsCategoryDetail {
    private int pID;
    private int pID_Categories_ParentCategory;
    private int pID_Modifications_ID;
    private String pCategoryName;
    private Date pDateCreated;
    private Date pDateModified;
    private clsCategories.enmArea pArea;
    
    public void setID(int aID) {
        this.pID = aID;
    }
    
    public void setID_Categories_ParentCategory(int aID_Categories_ParentCategory) {
        this.pID_Categories_ParentCategory = aID_Categories_ParentCategory;
    }
    
    public void setID_Modifications_ID(int aID_Modifications_ID) {
        this.pID_Modifications_ID = aID_Modifications_ID;
    }
    
    public void setCategoryName(String aCategoryName) {
        this.pCategoryName = aCategoryName;
    }
    
    public void setDateCreated(Date aCreationDate) {
        this.pDateCreated = aCreationDate;
    }
    
    public void setDateModified(Date aModificationDate) {
        this.pDateModified = aModificationDate;
    }
    
    public void setArea(clsCategories.enmArea aArea) {
        this.pArea = aArea;
    }
    
    public int getID() {
        return this.pID;
    }
    
    public int getID_Categories_ParentCategory() {
        return this.pID_Categories_ParentCategory;
    }
    
    public int getID_Modifications_ID() {
        return this.pID_Modifications_ID;
    }
    
    public String getCategoryName() {
        return this.pCategoryName;
    }
    
    public Date getDateCreated() {
        return this.pDateCreated;
    }
    
    public Date getDateModified() {
        return this.pDateModified;
    }
    
    public clsCategories.enmArea getArea() {
        return this.pArea;
    }

    @Override
    public String toString() {
        return this.pCategoryName;
    }
}
