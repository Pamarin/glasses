/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationPresentation;

import BusinessLogic.Forms.Categories.clsCategoryDetail;
import BusinessLogic.Forms.Special.clsIDEntry;
import BusinessLogic.Forms.clsCategories;
import BusinessLogic.clsProgram;
import java.awt.Color;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;

/**
 *
 * @author jean
 */
public class frmCategories extends javax.swing.JInternalFrame {
    private final frmMain pMainForm;
    private final clsProgram pProgram;
    private final clsCategories pModel;
    private boolean pStartUp;
    private enmStatus pStatus;
    private clsCategoryDetail pCategoryDetail;
    private enum enmStatus {
        New,
        Edit,
        View
    }

    /**
     * Creates new form frmCategories
     * @param aMainForm
     * @param aProgram
     */
    public frmCategories(frmMain aMainForm, clsProgram aProgram) {
        this.pStartUp = true;
        this.pMainForm = aMainForm;
        this.pProgram = aProgram;
        this.pModel = new clsCategories(this.pProgram);
        initComponents();
        initControls();
        setControls();
        loadAreas();
        loadCategories();
        this.pStartUp = false;
    }
    
    private void initControls() {
        //Limit the selection of one node at a time in the TreeView.
        trvCategories.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        trvParentCategory.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        
        trvCategories.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                //Retrieve the node.
                DefaultMutableTreeNode mNode = (DefaultMutableTreeNode) trvCategories.getLastSelectedPathComponent();
                
                //If nothing is selected.
                if (mNode == null) {
                    return;
                } else {
                    if (mNode.isRoot()) {
                        //Fill fields.
                        clsCategoryDetail mCategoryDetail = new clsCategoryDetail();
                        mCategoryDetail.setID(0);
                        mCategoryDetail.setID_Categories_ParentCategory(0);
                        mCategoryDetail.setCategoryName((String) mNode.getUserObject());
                        fillDetails(mCategoryDetail);
                    } else {
                        fillDetails((clsCategoryDetail) mNode.getUserObject());
                    }
                }
            }
        });
    }
    
    private void fillDetails(clsCategoryDetail aCategoryDetail) {
        if (aCategoryDetail == null) {
            //Fill fields.
            txtCategoryName.setText("");
            setParentCategory(null);
            lblDateCreatedVal.setText("");
            lblDateModifiedVal.setText("");
        } else {
            //Save the current category details.
            this.pCategoryDetail = aCategoryDetail;
            
            //Fill fields.
            txtCategoryName.setText(this.pCategoryDetail.getCategoryName());
            
            //Convert Date to readable format.
            SimpleDateFormat mDateConversion = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (this.pCategoryDetail.getDateCreated() == null) {
                lblDateCreatedVal.setText("");
            } else {
                lblDateCreatedVal.setText(mDateConversion.format(this.pCategoryDetail.getDateCreated()));
            }
            if (this.pCategoryDetail.getDateModified() == null) {
                lblDateModifiedVal.setText("");
            } else {
                lblDateModifiedVal.setText(mDateConversion.format(this.pCategoryDetail.getDateModified()));
            }
            
            //Select the parent node.
            setParentCategory(trvCategories.getSelectionPath().getParentPath());
        }
    }
    
    private void setParentCategory(TreePath aPath) {
        trvParentCategory.setSelectionPath(aPath);
    }
    
    private void setControls() {
        if (this.pStatus == null) {
            this.pStatus = enmStatus.View;
        }
        
        switch (this.pStatus) {
            case New:
                cboArea.setEnabled(false);
                trvCategories.setEnabled(false);
                trvParentCategory.setEnabled(true);
                txtCategoryName.setEnabled(true);
                btnNew.setEnabled(false);
                btnEdit.setEnabled(false);
                btnSave.setEnabled(true);
                btnDelete.setEnabled(false);
                btnCancel.setEnabled(true);
                setParentCategory(null);
                break;
            case Edit:
                cboArea.setEnabled(false);
                trvCategories.setEnabled(false);
                trvParentCategory.setEnabled(true);
                txtCategoryName.setEnabled(true);
                btnNew.setEnabled(false);
                btnEdit.setEnabled(false);
                btnSave.setEnabled(true);
                btnDelete.setEnabled(false);
                btnCancel.setEnabled(true);
                break;
            case View:
                cboArea.setEnabled(true);
                trvCategories.setEnabled(true);
                trvParentCategory.setEnabled(false);
                txtCategoryName.setEnabled(false);
                btnNew.setEnabled(true);
                btnEdit.setEnabled(true);
                btnSave.setEnabled(false);
                btnDelete.setEnabled(true);
                btnCancel.setEnabled(false);
                
                break;
        }
    }
    
    private void loadCategories() {
        try {
            //Retrieve the categories from the model and put them into the TreeView.
            TreeModel mCategories = this.pModel.getCategories(getArea());
            trvCategories.setModel(mCategories);
            trvParentCategory.setModel(mCategories);
            selectCategory(0);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "The categories could not be retrieved. " + ex.getMessage());
        }
    }
    
    private DefaultMutableTreeNode findCategory(int aID) {
        //The node to return.
        DefaultMutableTreeNode mReturnNode = null;
        //The searched node.
        DefaultMutableTreeNode mSearchedNode = null;
        //Retrieve the root node of the category tree.
        DefaultMutableTreeNode mRootNode = (DefaultMutableTreeNode) trvCategories.getModel().getRoot();
        //Get the enumeration.
        Enumeration mEnum = mRootNode.breadthFirstEnumeration();
        
        //Iterate through the enumeration.
        while (mEnum.hasMoreElements()) {
            //Get the node.
            mSearchedNode = (DefaultMutableTreeNode) mEnum.nextElement();
            //Omit root node.
            if (mSearchedNode.isRoot() == false) {
                //Get the category detail.
                clsCategoryDetail mCategoryDetail = (clsCategoryDetail) mSearchedNode.getUserObject();
                //Compare the searched ID with the found one.
                if (aID == mCategoryDetail.getID()) {
                    mReturnNode = mSearchedNode;
                    break;
                }
            }
        }
        
        //Return the searched node.
        return mReturnNode;
    }
    
    private TreePath getCategoryPath(int aID) {
        //Find the category.
        TreeNode mSearchedCategory = findCategory(aID);
        
        //Collect all nodes till root node.
        ArrayList<TreeNode> mNodes = new ArrayList<>();
        //Walk through the path.
        while (mSearchedCategory != null) {
            //Add current node.
            mNodes.add(mSearchedCategory);
            //Move to parent node.
            mSearchedCategory = mSearchedCategory.getParent();
        }
        //Reverse the node collection.
        Collections.reverse(mNodes);
        //Convert array of nodes to TreePath.
        return new TreePath(mNodes.toArray());
    }
    
    private void selectCategory(int aID) {
        if (aID == 0) {
            trvCategories.setSelectionRow(0);
        } else {
            trvCategories.setSelectionPath(getCategoryPath(aID));
        }
    }
    
    private void loadAreas() {
        //Define entry Goods.
        clsIDEntry mGoods = new clsIDEntry();
        mGoods.setID(this.pModel.getAreaID(clsCategories.enmArea.Goods));
        mGoods.setValue("Goods");
        
        //Define entry Shops.
        clsIDEntry mShops = new clsIDEntry();
        mShops.setID(this.pModel.getAreaID(clsCategories.enmArea.Shops));
        mShops.setValue("Shops");
        
        //Add the entries to the ComboBox.
        cboArea.addItem(mGoods);
        cboArea.addItem(mShops);
    }
    
    private clsCategories.enmArea getArea() {
        //Area to return.
        clsCategories.enmArea mArea = null;
        //Get the current selection.
        clsIDEntry mEntry = (clsIDEntry) cboArea.getItemAt(cboArea.getSelectedIndex());
        
        //Convert the selection to readable enum.
        switch (mEntry.getID()) {
            case 1:
                mArea = clsCategories.enmArea.Goods;
                break;
            case 2:
                mArea = clsCategories.enmArea.Shops;
                break;
        }
        
        //Give back selection as enum.
        return mArea;
    }
    
    private void newCategory() {
        //Set status to new.
        this.pStatus = enmStatus.New;
        //Set the enabled status of the controls.
        setControls();
        //Empty all the fields.
        fillDetails(null);
    }
    
    private void editCategory() {
        DefaultMutableTreeNode mNode = (DefaultMutableTreeNode) trvCategories.getLastSelectedPathComponent();
        if (mNode.isRoot()) {
            JOptionPane.showMessageDialog(this, "The root node can not be edited.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            //Set the status to edit.
            this.pStatus = enmStatus.Edit;
            //Set the enabled status of the controls.
            setControls();
        }
    }
    
    private void saveCategory() throws SQLException {
        //Remember the category ID.
        clsCategoryDetail mCategoryDetail = this.pCategoryDetail;
        //Check whether fields are filled correctly.
        if (checkFieldsForSaving(false)) {
            switch (this.pStatus) {
                case New:
                    //Check whether category already exists.
                    if (this.pModel.checkCategoryExists(txtCategoryName.getText(), getArea())) {
                        JOptionPane.showMessageDialog(this, "The category already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        //Create the new category.
                        clsCategoryDetail mNewCategory = new clsCategoryDetail();
                        mNewCategory.setID_Categories_ParentCategory(getParentID());
                        mNewCategory.setCategoryName(txtCategoryName.getText());
                        mNewCategory.setArea(getArea());
                        //Save the new category.
                        int mIDCategory = this.pModel.saveNewCategory(mNewCategory);
                        //Check saving status.
                        if (mIDCategory > 0) {
                            //Set the status to view.
                            this.pStatus = enmStatus.View;
                            //Set the enabled status of the controls.
                            setControls();
                            //Reload the categories.
                            loadCategories();
                            //Select the newly created category.
                            selectCategory(mIDCategory);
                        } else {
                            JOptionPane.showMessageDialog(this, "Error saving the new category.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;
                case Edit:
                    //Create the modified category.
                    clsCategoryDetail mModifiedCategory = new clsCategoryDetail();
                    mModifiedCategory.setID(this.pCategoryDetail.getID());
                    
                    //If category was changed.
                    String mCategoryNameOld = this.pCategoryDetail.getCategoryName();
                    String mCategoryNameNew = txtCategoryName.getText();
                    
                    //Check whether category already exists.
                    if (!mCategoryNameOld.equals(mCategoryNameNew)) {
                        if (this.pModel.checkCategoryExists(txtCategoryName.getText(), getArea())) {
                            JOptionPane.showMessageDialog(this, "The category already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                            //Leave switch.
                            break;
                        } else {
                            //Add changed category name to newly created modified category.
                            mModifiedCategory.setCategoryName(txtCategoryName.getText());
                        }
                    }
                    
                    //Add changed parent category to newly created modified category.
                    mModifiedCategory.setID_Categories_ParentCategory(getParentID());
                    
                    //Save the modified category.
                    if (this.pModel.saveModifiedCategory(mModifiedCategory)) {
                        //Set the status to view.
                        this.pStatus = enmStatus.View;
                        //Set the enabled status of the controls.
                        setControls();
                        //Reload the categories.
                        loadCategories();
                        //Select the modified category.
                        selectCategory(mCategoryDetail.getID());
                    } else {
                        JOptionPane.showMessageDialog(this, "Error saving the modified category.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
            }
        }
    }
    
    private int getParentID() {
        //Retrieve the node.
        DefaultMutableTreeNode mNode = (DefaultMutableTreeNode) trvParentCategory.getLastSelectedPathComponent();
        //Check whether node is the root.
        if (mNode.isRoot()) {
            return 0;
        } else {
            //Cast the user object to category detail.
            clsCategoryDetail mCategoryDetail = (clsCategoryDetail) mNode.getUserObject();
            //Return the ID of the parent node.
            return mCategoryDetail.getID();
        }
    }
    
    private boolean checkFieldsForSaving(boolean aReturnTrue) {
        boolean mReturnValue = true;
        
        //Check whether there has been a node selected in the parent categories.
        if (aReturnTrue == false && (trvParentCategory.getSelectionCount() == 0 || getParentID() == this.pCategoryDetail.getID())) {
            mReturnValue = false;
            trvParentCategory.setBackground(new Color(255, 200, 200));
        } else {
            trvParentCategory.setBackground(new Color(255, 255, 255));
        }
        
        //Check whether there is some text in the category name field.
        if (aReturnTrue == false && (txtCategoryName.getText().length() == 0)) {
            mReturnValue = false;
            txtCategoryName.setBackground(new Color(255, 200, 200));
        } else {
            txtCategoryName.setBackground(new Color(255, 255, 255));
        }
        
        return mReturnValue;
    }
    
    private void deleteCategory() throws SQLException {
        if (this.pCategoryDetail.getID() == 0) {
            JOptionPane.showMessageDialog(this, "The root node can not be deleted.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            DefaultMutableTreeNode mNode = (DefaultMutableTreeNode) trvCategories.getLastSelectedPathComponent();
            if (mNode.getChildCount() > 0) {
                JOptionPane.showMessageDialog(this, "Deletion not possible. The category '" + this.pCategoryDetail.getCategoryName() + "' has " + mNode.getChildCount() + " subcategories.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (JOptionPane.showConfirmDialog(this, "Do you really want to delete the category '" + this.pCategoryDetail.getCategoryName() + "'?", "Delete category", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    //Delete the category.
                    this.pModel.deleteCategory(this.pCategoryDetail.getID());
                    //Reload the categories.
                    loadCategories();
                    //Select the modified category.
                    selectCategory(0);
                }
            }
        }
    }
    
    private void cancelCategory() {
        this.pStatus = enmStatus.View;
        setControls();
        fillDetails(this.pCategoryDetail);
        checkFieldsForSaving(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIcon = new javax.swing.JLabel();
        splVertical = new javax.swing.JSplitPane();
        grpLeft = new javax.swing.JPanel();
        grpList = new javax.swing.JPanel();
        srcCategories = new javax.swing.JScrollPane();
        trvCategories = new javax.swing.JTree();
        grpFilter = new javax.swing.JPanel();
        lblArea = new javax.swing.JLabel();
        cboArea = new javax.swing.JComboBox();
        grpRight = new javax.swing.JPanel();
        grpDetails = new javax.swing.JPanel();
        lblParentCategory = new javax.swing.JLabel();
        srcParentCategory = new javax.swing.JScrollPane();
        trvParentCategory = new javax.swing.JTree();
        lblCategoryName = new javax.swing.JLabel();
        txtCategoryName = new javax.swing.JTextField();
        grpModifications = new javax.swing.JPanel();
        lblDateCreated = new javax.swing.JLabel();
        lblDateCreatedVal = new javax.swing.JLabel();
        lblDateModified = new javax.swing.JLabel();
        lblDateModifiedVal = new javax.swing.JLabel();
        grpControl = new javax.swing.JPanel();
        btnNew = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationPresentation/Icons/black/png/3x3_grid_icon&16.png"))); // NOI18N
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationPresentation/Icons/black/png/3x3_grid_icon&48.png"))); // NOI18N

        grpLeft.setMinimumSize(new java.awt.Dimension(200, 200));

        grpList.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("View")));

        srcCategories.setViewportView(trvCategories);

        javax.swing.GroupLayout grpListLayout = new javax.swing.GroupLayout(grpList);
        grpList.setLayout(grpListLayout);
        grpListLayout.setHorizontalGroup(
            grpListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(srcCategories)
                .addContainerGap())
        );
        grpListLayout.setVerticalGroup(
            grpListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpListLayout.createSequentialGroup()
                .addComponent(srcCategories, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addContainerGap())
        );

        grpFilter.setBorder(javax.swing.BorderFactory.createTitledBorder("Filter"));

        lblArea.setText("Area:");

        cboArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboAreaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout grpFilterLayout = new javax.swing.GroupLayout(grpFilter);
        grpFilter.setLayout(grpFilterLayout);
        grpFilterLayout.setHorizontalGroup(
            grpFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpFilterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblArea)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboArea, 0, 212, Short.MAX_VALUE)
                .addContainerGap())
        );
        grpFilterLayout.setVerticalGroup(
            grpFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblArea)
                .addComponent(cboArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout grpLeftLayout = new javax.swing.GroupLayout(grpLeft);
        grpLeft.setLayout(grpLeftLayout);
        grpLeftLayout.setHorizontalGroup(
            grpLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(grpLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(grpFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(grpList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        grpLeftLayout.setVerticalGroup(
            grpLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(grpFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(grpList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        splVertical.setLeftComponent(grpLeft);

        grpRight.setMinimumSize(new java.awt.Dimension(200, 200));

        grpDetails.setBorder(javax.swing.BorderFactory.createTitledBorder("Details"));

        lblParentCategory.setText("Parent category:");

        srcParentCategory.setViewportView(trvParentCategory);

        lblCategoryName.setText("Category name:");

        javax.swing.GroupLayout grpDetailsLayout = new javax.swing.GroupLayout(grpDetails);
        grpDetails.setLayout(grpDetailsLayout);
        grpDetailsLayout.setHorizontalGroup(
            grpDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(grpDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblParentCategory)
                    .addComponent(lblCategoryName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(grpDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCategoryName)
                    .addComponent(srcParentCategory))
                .addContainerGap())
        );
        grpDetailsLayout.setVerticalGroup(
            grpDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpDetailsLayout.createSequentialGroup()
                .addGroup(grpDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(grpDetailsLayout.createSequentialGroup()
                        .addComponent(lblParentCategory)
                        .addGap(0, 209, Short.MAX_VALUE))
                    .addComponent(srcParentCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(grpDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCategoryName))
                .addContainerGap())
        );

        grpModifications.setBorder(javax.swing.BorderFactory.createTitledBorder("Modifications"));

        lblDateCreated.setText("Date created:");

        lblDateCreatedVal.setText("lblDateCreatedVal");

        lblDateModified.setText("Date modified:");

        lblDateModifiedVal.setText("lblDateModifiedVal");

        javax.swing.GroupLayout grpModificationsLayout = new javax.swing.GroupLayout(grpModifications);
        grpModifications.setLayout(grpModificationsLayout);
        grpModificationsLayout.setHorizontalGroup(
            grpModificationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpModificationsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(grpModificationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDateModified)
                    .addComponent(lblDateCreated))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(grpModificationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDateCreatedVal)
                    .addComponent(lblDateModifiedVal))
                .addContainerGap(143, Short.MAX_VALUE))
        );
        grpModificationsLayout.setVerticalGroup(
            grpModificationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpModificationsLayout.createSequentialGroup()
                .addGroup(grpModificationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDateCreated)
                    .addComponent(lblDateCreatedVal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(grpModificationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDateModified)
                    .addComponent(lblDateModifiedVal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout grpRightLayout = new javax.swing.GroupLayout(grpRight);
        grpRight.setLayout(grpRightLayout);
        grpRightLayout.setHorizontalGroup(
            grpRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpRightLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(grpRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(grpModifications, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(grpDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        grpRightLayout.setVerticalGroup(
            grpRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, grpRightLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(grpModifications, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(grpDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        splVertical.setRightComponent(grpRight);

        grpControl.setBorder(javax.swing.BorderFactory.createTitledBorder("Control"));
        grpControl.setPreferredSize(new java.awt.Dimension(631, 65));

        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout grpControlLayout = new javax.swing.GroupLayout(grpControl);
        grpControl.setLayout(grpControlLayout);
        grpControlLayout.setHorizontalGroup(
            grpControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpControlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNew)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEdit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        grpControlLayout.setVerticalGroup(
            grpControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpControlLayout.createSequentialGroup()
                .addGroup(grpControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNew)
                    .addComponent(btnEdit)
                    .addComponent(btnSave)
                    .addComponent(btnDelete)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(grpControl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(splVertical)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblIcon)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(splVertical)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(grpControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        this.pMainForm.closeWindowCategories();
    }//GEN-LAST:event_formInternalFrameClosed

    private void cboAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboAreaActionPerformed
        if (this.pStartUp == false) {
            loadCategories();
        }
    }//GEN-LAST:event_cboAreaActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        newCategory();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editCategory();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        try {
            saveCategory();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving category. " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            deleteCategory();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting category. " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        cancelCategory();
    }//GEN-LAST:event_btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cboArea;
    private javax.swing.JPanel grpControl;
    private javax.swing.JPanel grpDetails;
    private javax.swing.JPanel grpFilter;
    private javax.swing.JPanel grpLeft;
    private javax.swing.JPanel grpList;
    private javax.swing.JPanel grpModifications;
    private javax.swing.JPanel grpRight;
    private javax.swing.JLabel lblArea;
    private javax.swing.JLabel lblCategoryName;
    private javax.swing.JLabel lblDateCreated;
    private javax.swing.JLabel lblDateCreatedVal;
    private javax.swing.JLabel lblDateModified;
    private javax.swing.JLabel lblDateModifiedVal;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblParentCategory;
    private javax.swing.JSplitPane splVertical;
    private javax.swing.JScrollPane srcCategories;
    private javax.swing.JScrollPane srcParentCategory;
    private javax.swing.JTree trvCategories;
    private javax.swing.JTree trvParentCategory;
    private javax.swing.JTextField txtCategoryName;
    // End of variables declaration//GEN-END:variables
}
