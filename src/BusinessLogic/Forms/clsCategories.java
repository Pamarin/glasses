/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Forms;

import BusinessLogic.Forms.Categories.clsCategoryDetail;
import BusinessLogic.clsProgram;
import DataAccess.SQL.clsSQL;
import DataAccess.SQL.clsSQLField;
import DataAccess.SQL.clsSQLRelation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

/**
 *
 * @author jean
 */
public class clsCategories {
    private final clsProgram pProgram;
    public enum enmArea {
        Goods,
        Shops
    }
    
    public clsCategories(clsProgram aProgram) {
        this.pProgram = aProgram;
    }
    
    public int getAreaID(enmArea aArea) {
        int mID = 0;
        
        switch (aArea) {
            case Goods:
                mID = 1;
                break;
            case Shops:
                mID = 2;
                break;
        }
        
        return mID;
    }
    
    public TreeModel getCategories(enmArea aArea) throws SQLException {
        //Define first node as the area.
        DefaultMutableTreeNode mRoot = null;
        switch (aArea) {
            case Goods:
                mRoot = new DefaultMutableTreeNode("Goods");
                break;
            case Shops:
                mRoot = new DefaultMutableTreeNode("Shops");
                break;
        }
        
        //Build SQL.
        clsSQL mSQL = new clsSQL(clsSQL.enmSQLType.Select);
        mSQL.addField(new clsSQLField("Categories", "ID"));
        mSQL.addField(new clsSQLField("Categories", "ID_Categories_ParentCategory"));
        mSQL.addField(new clsSQLField("Categories", "CategoryName"));
        mSQL.addField(new clsSQLField("Modifications", "CreationDate"));
        mSQL.addField(new clsSQLField("Modifications", "ModificationDate"));
        mSQL.addField(new clsSQLField("Modifications", "ID"));
        mSQL.addRelation(new clsSQLRelation(clsSQLRelation.enmRelationType.Where, new clsSQLField("Categories", "ID_Modifications"), new clsSQLField("Modifications", "ID")));
        mSQL.addRelation(new clsSQLRelation(clsSQLRelation.enmRelationType.Where, new clsSQLField("Categories", "Deleted"), "0"));
        mSQL.addRelation(new clsSQLRelation(clsSQLRelation.enmRelationType.Where, new clsSQLField("Categories", "Area"), String.valueOf(getAreaID(aArea))));
        mSQL.addOrdering(new clsSQLField("Categories", "CategoryName"));
        
        //Retrieve Categories from database.
        ResultSet mCategoriesRS = this.pProgram.getDatabase().QueryRetrieve(mSQL);
        
        //Put table into array.
        ArrayList<ArrayList> mCategoriesArray = new ArrayList<>();
        while (mCategoriesRS.next()) {
            //Store table data into array columns.
            ArrayList mCategory = new ArrayList();
            mCategory.add(mCategoriesRS.getInt(this.pProgram.getDatabase().getField(new clsSQLField("Categories", "ID"))));
            mCategory.add(mCategoriesRS.getInt(this.pProgram.getDatabase().getField(new clsSQLField("Categories", "ID_Categories_ParentCategory"))));
            mCategory.add(mCategoriesRS.getString(this.pProgram.getDatabase().getField(new clsSQLField("Categories", "CategoryName"))));
            mCategory.add(mCategoriesRS.getDate(this.pProgram.getDatabase().getField(new clsSQLField("Modifications", "CreationDate"))));
            mCategory.add(mCategoriesRS.getDate(this.pProgram.getDatabase().getField(new clsSQLField("Modifications", "ModificationDate"))));
            mCategory.add(mCategoriesRS.getInt(this.pProgram.getDatabase().getField(new clsSQLField("Modifications", "ID"))));
            mCategoriesArray.add(mCategory);
        }
        
        //Add child nodes if existent.
        addCategory(mRoot, 0, mCategoriesArray);
        
        //Return the TreeModel.
        return new DefaultTreeModel(mRoot);
    }
    
    private void addCategory(DefaultMutableTreeNode aNode, int aParentID, ArrayList<ArrayList> aCategories) {
        for (int i=0; i<aCategories.size(); i++) {
            
            //Get the single category.
            ArrayList mRow = aCategories.get(i);
            int mParentID = (int)mRow.get(1);
            
            //Check if wanted parent ID.
            if (mParentID == aParentID) {
                
                //Create the entry from one categories row.
                clsCategoryDetail mCategoryEntry = new clsCategoryDetail();
                mCategoryEntry.setID((int) mRow.get(0));
                mCategoryEntry.setID_Categories_ParentCategory((int) mRow.get(1));
                mCategoryEntry.setCategoryName((String) mRow.get(2));
                mCategoryEntry.setDateCreated((Date) mRow.get(3));
                mCategoryEntry.setDateModified((Date) mRow.get(4));
                mCategoryEntry.setID_Modifications_ID((int) mRow.get(5));
                
                //Create the node with entry.
                DefaultMutableTreeNode mNode = new DefaultMutableTreeNode(mCategoryEntry);
                
                //Add child nodes if existent.
                addCategory(mNode, (int)mRow.get(0), aCategories);
                
                //Add entry entry to node.
                aNode.add(mNode);
            }
        }
    }
    
    private int saveNewModificationTime() throws SQLException {
        int mModificationsID = 0;
        
        //Create SQL for modifications entry.
        clsSQL mSQLCreateModifiedEntry = new clsSQL(clsSQL.enmSQLType.Insert);
        mSQLCreateModifiedEntry.addField(new clsSQLField("Modifications", "CreationDate", "NOW()"));
        mSQLCreateModifiedEntry.addField(new clsSQLField("Modifications", "ModificationDate", "NOW()"));
        
        ResultSet mCreatedIDInModified = this.pProgram.getDatabase().QueryExecuteCreatedID(mSQLCreateModifiedEntry);
        //Get the newly inserted ID.
        while (mCreatedIDInModified.next()) {
            mModificationsID = mCreatedIDInModified.getInt(1);
            break;
        }
        
        //Return newly created ID.
        return mModificationsID;
    }
    
    private boolean updateModificationTime(int aID) throws SQLException {
        //Update modification time.
        clsSQL mSQL = new clsSQL(clsSQL.enmSQLType.Update);
        mSQL.addField(new clsSQLField("Modifications", "ModificationDate", "NOW()"));
        mSQL.addRelation(new clsSQLRelation(clsSQLRelation.enmRelationType.Where, new clsSQLField("Modifications", "ID"), "" + aID + ""));
        //Update the database.
        this.pProgram.getDatabase().QueryExecuteAffectedRows(mSQL);
        return true;
    }
    
    public boolean deleteCategory(int aID) throws SQLException {
        //Update deletion status.
        clsSQL mSQL = new clsSQL(clsSQL.enmSQLType.Update);
        mSQL.addField(new clsSQLField("Categories", "Deleted", "1"));
        mSQL.addRelation(new clsSQLRelation(clsSQLRelation.enmRelationType.Where, new clsSQLField("Categories", "ID"), "" + aID + ""));
        //Update the database.
        this.pProgram.getDatabase().QueryExecuteAffectedRows(mSQL);
        return true;
    }
    
    public int saveNewCategory(clsCategoryDetail aCategoryDetail) throws SQLException {
        //ID of the created category.
        int mIDCategory = 0;
        //Save new categorys' modification time.
        int mIDModifications = saveNewModificationTime();
        
        //Save new category and give back saved status.
        clsSQL mSQL = new clsSQL(clsSQL.enmSQLType.Insert);
        mSQL.addField(new clsSQLField("Categories", "ID_Categories_ParentCategory", "" + aCategoryDetail.getID_Categories_ParentCategory() + ""));
        mSQL.addField(new clsSQLField("Categories", "CategoryName", "'" + aCategoryDetail.getCategoryName() + "'"));
        mSQL.addField(new clsSQLField("Categories", "ID_Modifications", "" + mIDModifications + ""));
        mSQL.addField(new clsSQLField("Categories", "Deleted", "0"));
        mSQL.addField(new clsSQLField("Categories", "Area", "" + getAreaID(aCategoryDetail.getArea()) + ""));
        //Insert the category.
        ResultSet mRSCreatedIDs = this.pProgram.getDatabase().QueryExecuteCreatedID(mSQL);
        
        //Retrieve the ID of the newly saved category.
        while (mRSCreatedIDs.next()) {
            mIDCategory = mRSCreatedIDs.getInt(1);
            break;
        }
        
        //Return the ID of the newly saved category.
        return mIDCategory;
    }
    
    public boolean saveModifiedCategory(clsCategoryDetail aCategoryDetail) throws SQLException {
        //Save modified category and give back saved status.
        clsSQL mSQL = new clsSQL(clsSQL.enmSQLType.Update);
        //Add it to the update SQL.
        mSQL.addField(new clsSQLField("Categories", "ID_Categories_ParentCategory", "" + aCategoryDetail.getID_Categories_ParentCategory() + ""));
        //If CategoryName has changed, add it to the update SQL.
        if (aCategoryDetail.getCategoryName() != null) {
            mSQL.addField(new clsSQLField("Categories", "CategoryName", "'" + aCategoryDetail.getCategoryName() + "'"));
        }
        //Add relation to SQL.
        mSQL.addRelation(new clsSQLRelation(clsSQLRelation.enmRelationType.Where, new clsSQLField("Categories", "ID"), "" + aCategoryDetail.getID() + ""));
        //Update the category.
        this.pProgram.getDatabase().QueryExecuteAffectedRows(mSQL);
        //Update modification time.
        updateModificationTime(aCategoryDetail.getID_Modifications_ID());
        return true;
    }
    
    public boolean checkCategoryExists(String aCategoryName, enmArea aArea) throws SQLException {
        boolean mIsCategoryExistent = false;
        
        //Compare the category name with the root name.
        if (aCategoryName.equals(getAreaName(aArea))) {
            mIsCategoryExistent = true;
        } else {
            //Check if category exists.
            clsSQL mSQL = new clsSQL(clsSQL.enmSQLType.Select);
            mSQL.addField(new clsSQLField("Categories", "ID"));
            mSQL.addRelation(new clsSQLRelation(clsSQLRelation.enmRelationType.Where, new clsSQLField("Categories", "CategoryName"), "'" + aCategoryName + "'"));
            mSQL.addRelation(new clsSQLRelation(clsSQLRelation.enmRelationType.Where, new clsSQLField("Categories", "Area"), "" + getAreaID(aArea) + ""));
            mSQL.addRelation(new clsSQLRelation(clsSQLRelation.enmRelationType.Where, new clsSQLField("Categories", "Deleted"), "0"));
            //Retrieve the category.
            ResultSet mCategory = this.pProgram.getDatabase().QueryRetrieve(mSQL);
            while (mCategory.next()) {
                mIsCategoryExistent = true;
                break;
            }
        }
        
        //Return the result.
        return mIsCategoryExistent;
    }
    
    private String getAreaName(enmArea aArea) {
        String mReturnName = "";
        
        switch (aArea) {
            case Goods:
                mReturnName = "Goods";
                break;
            case Shops:
                mReturnName = "Shops";
                break;
        }
        
        return mReturnName;
    }
}
