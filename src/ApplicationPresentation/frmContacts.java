/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationPresentation;

import ApplicationPresentation.Specials.clsTextFieldLimit;
import BusinessLogic.Forms.Contacts.clsContacts;
import BusinessLogic.Forms.Contacts.clsContactsDetails;
import BusinessLogic.Forms.Contacts.clsContactsTableModel;
import BusinessLogic.clsProgram;
import java.awt.Color;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// TODO: Make the modifications visible through a new GroupField having the creation and modification date.
// TODO: Make it possible to enter more than one address, phone, fax, email, and website. Possibly through a new mask where own definitions can be set aside given standard ones, like phone, fax, and so on.

/**
 *
 * @author jean
 */
public class frmContacts extends javax.swing.JInternalFrame {
    private frmMain pMainForm;
    private clsProgram pProgram;
    private clsContacts pModel;
    private enmStatus pStatus;
    private int pSelectedRow;
    ListSelectionModel pContactSelectionModel;
    
    private enum enmStatus {
        View,
        New,
        Modify
    }
    
    /**
     * Creates new form frmContacts
     */
    public frmContacts(frmMain aMainForm, clsProgram aProgram) throws SQLException {
        this.pMainForm = aMainForm;
        this.pProgram = aProgram;
        this.pModel = new clsContacts(this.pProgram);
        this.pStatus = enmStatus.View;
        this.pSelectedRow = -1;
        
        initComponents();
        initControls();
        setComponents();
        selectContact();
    }
    
    private void initControls() {
        //Limit the maximum characters of JTextField.
        txtFirstName.setDocument(new clsTextFieldLimit(255));
        txtMiddleName.setDocument(new clsTextFieldLimit(255));
        txtLastName.setDocument(new clsTextFieldLimit(255));
        txtStreet.setDocument(new clsTextFieldLimit(255));
        txtPostcode.setDocument(new clsTextFieldLimit(255));
        txtCity.setDocument(new clsTextFieldLimit(255));
        txtCountry.setDocument(new clsTextFieldLimit(255));
        
        //Only allow JTable the selection of one single row.
        tblContacts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Make next column head auto-resizable.
        tblContacts.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        //Define a contact listener for handling row changes.
        this.pContactSelectionModel = tblContacts.getSelectionModel();
        this.pContactSelectionModel.addListSelectionListener(new tblContactsListSelectionHandler());
        tblContacts.setSelectionModel(pContactSelectionModel);
        //Set the mouse scroll speed of the JScrollPane.
        scrRight.getVerticalScrollBar().setUnitIncrement(10);
    }
    
    private void fillContactsTable() throws SQLException {
        tblContacts.setModel(this.pModel.getContacts());
    }
    
    private void selectContact() {
        int mPositionToSelect = 0;
        
        //Select the first or the former row in the contacts table.
        if (this.pSelectedRow > -1) {
            mPositionToSelect = this.pSelectedRow;
        }
        
        //Select the row if there are some.
        if (getRowCount() > 0) {
            try {
                this.pContactSelectionModel.setSelectionInterval(mPositionToSelect, mPositionToSelect);
            } catch (Exception ex) {
                Logger.getLogger(frmContacts.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void fillContactDetails(int aSelectedRow) throws SQLException {
        // TODO: Make the table sortable.
        
        switch (this.pStatus) {
            case New:
                //Empty all the fields for the new entry.
                txtFirstName.setText("");
                txtMiddleName.setText("");
                txtLastName.setText("");
                dtpBirthday.setDate(null);
                rdoUndefined.setSelected(true);
                txtStreet.setText("");
                txtPostcode.setText("");
                txtCity.setText("");
                txtCountry.setText("");
                txtAvailability.setText("");
                txtComment.setText("");
                lblDateCreatedVal.setText("");
                lblDateModifiedVal.setText("");
                // TODO: Empty the availabilities.
                break;
                
            case View:
                //Get access to the table data.
                clsContactsTableModel mModel = (clsContactsTableModel) tblContacts.getModel();
                //Get the entire contact details.
                clsContactsDetails mContactsDetails = mModel.getContactDetails(aSelectedRow);
                //Set the values into the fields.
                txtFirstName.setText(mContactsDetails.getFirstName());
                txtMiddleName.setText(mContactsDetails.getMiddleName());
                txtLastName.setText(mContactsDetails.getLastName());
                dtpBirthday.setDate(mContactsDetails.getBirthday());
                switch (mContactsDetails.getGender()) {
                    case "m":
                        rdoMale.setSelected(true);
                        break;
                    case "f":
                        rdoFemale.setSelected(true);
                        break;
                    default:
                        rdoUndefined.setSelected(true);
                        break;
                }
                txtStreet.setText(mContactsDetails.getStreet());
                txtPostcode.setText(mContactsDetails.getPostcode());
                txtCity.setText(mContactsDetails.getCity());
                txtCountry.setText(mContactsDetails.getCountry());
                txtAvailability.setText(mContactsDetails.getAvailability());
                txtComment.setText(mContactsDetails.getComment());
                //Convert Date to readable format.
                SimpleDateFormat mDateConversion = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                lblDateCreatedVal.setText(mDateConversion.format(mContactsDetails.getDateCreated()));
                lblDateModifiedVal.setText(mDateConversion.format(mContactsDetails.getDateModified()));
                break;
                
        }
    }
    
    private int getRowCount() {
        clsContactsTableModel mTableModel = (clsContactsTableModel) tblContacts.getModel();
        int mRowsCount = mTableModel.getRowCount();
        return mRowsCount;
    }
    
    private int getRowOfID(int aID) {
        int mRow = 0;
        clsContactsTableModel mTableModel = (clsContactsTableModel) tblContacts.getModel();
        
        for (int i=0; i<mTableModel.getRowCount(); i++) {
            if (mTableModel.getID(i) == aID) {
                mRow = i;
            }
        }
        
        return mRow;
    }
    
    private void setComponents() throws SQLException {
        //Set the enabled state of the controls.
        switch (this.pStatus) {
            case View:
                tblContacts.setEnabled(true);
                fillContactsTable();
                txtFirstName.setEnabled(false);
                txtMiddleName.setEnabled(false);
                txtLastName.setEnabled(false);
                dtpBirthday.setEnabled(false);
                rdoMale.setEnabled(false);
                rdoFemale.setEnabled(false);
                rdoUndefined.setEnabled(false);
                txtStreet.setEnabled(false);
                txtPostcode.setEnabled(false);
                txtCity.setEnabled(false);
                txtCountry.setEnabled(false);
                txtAvailability.setEnabled(false);
                txtComment.setEnabled(false);
                btnSave.setEnabled(false);
                btnNew.setEnabled(true);
                if (getRowCount() > 0) {
                    btnEdit.setEnabled(true);
                    btnDelete.setEnabled(true);
                } else {
                    btnEdit.setEnabled(false);
                    btnDelete.setEnabled(false);
                }
                btnCancel.setEnabled(false);
                break;
            case New:
            case Modify:
                tblContacts.setEnabled(false);
                txtFirstName.setEnabled(true);
                txtMiddleName.setEnabled(true);
                txtLastName.setEnabled(true);
                dtpBirthday.setEnabled(true);
                rdoMale.setEnabled(true);
                rdoFemale.setEnabled(true);
                rdoUndefined.setEnabled(true);
                txtStreet.setEnabled(true);
                txtPostcode.setEnabled(true);
                txtCity.setEnabled(true);
                txtCountry.setEnabled(true);
                txtAvailability.setEnabled(true);
                txtComment.setEnabled(true);
                btnSave.setEnabled(true);
                btnNew.setEnabled(false);
                btnEdit.setEnabled(false);
                btnDelete.setEnabled(false);
                btnCancel.setEnabled(true);
                break;
        }
    }
    
    private boolean checkFieldsForSaving() {
        boolean returnVal = true;
        
        if("".equals(txtLastName.getText())) {
            returnVal = false;
            txtLastName.setBackground(new Color(255, 200, 200));
        } else {
            txtLastName.setBackground(new Color(255, 255, 255));
        }
        
        return returnVal;
    }
    
    private String getGender() {
        //Get the gender.
        String mGender = "";
        if (rdoMale.getModel() == bgrpGender.getSelection()) {
            mGender = "m";
        } else if (rdoFemale.getModel() == bgrpGender.getSelection()) {
            mGender = "f";
        }
        
        return mGender;
    }
    
    private void saveContact() throws SQLException {
        // TODO: When adding new and editing given contacts, check for doubles in the database. Give them visible IDs to make the difference when contacts really have the same names.
        
        if (checkFieldsForSaving()) {

            clsContactsDetails mContactDetails = new clsContactsDetails();

            //Differentiate between the two saving modes new and modify.
            clsContacts.enmSavingMode mSavingMode = null;
            if (this.pStatus == enmStatus.New) {
                mSavingMode = clsContacts.enmSavingMode.New;
            } else if (this.pStatus == enmStatus.Modify) {
                mSavingMode = clsContacts.enmSavingMode.Modify;

                int mID = ((clsContactsTableModel) tblContacts.getModel()).getID(tblContacts.getSelectedRow());
                int mIDModifications = ((clsContactsTableModel) tblContacts.getModel()).getIDModifications(tblContacts.getSelectedRow());
                mContactDetails.setID(mID);
                mContactDetails.setIDModification(mIDModifications);
            }

            //Create the contact details.
            mContactDetails.setFirstName(txtFirstName.getText());
            mContactDetails.setMiddleName(txtMiddleName.getText());
            mContactDetails.setLastName(txtLastName.getText());
            mContactDetails.setBirthday(dtpBirthday.getDate());
            mContactDetails.setGender(getGender());
            mContactDetails.setStreet(txtStreet.getText());
            mContactDetails.setPostcode(txtPostcode.getText());
            mContactDetails.setCity(txtCity.getText());
            mContactDetails.setCountry(txtCountry.getText());
            mContactDetails.setAvailability(txtAvailability.getText());
            mContactDetails.setComment(txtComment.getText());
            
            int mIDContacts = this.pModel.saveContact(mSavingMode, mContactDetails);
            if (mIDContacts > 0) {
                this.pStatus = enmStatus.View;
                setComponents();
                
                //Select the created or modified row.
                if (mSavingMode == clsContacts.enmSavingMode.New) {
                    this.pSelectedRow = getRowOfID(mIDContacts);
                }
                selectContact();
            }
        }
    }
    
    private void deleteContact() throws SQLException {
        //Get access to the table data.
        clsContactsTableModel mModel = (clsContactsTableModel) tblContacts.getModel();
        //Get the entire contact details.
        clsContactsDetails mContactsDetails = mModel.getContactDetails(this.pSelectedRow);
        
        switch (JOptionPane.showInternalConfirmDialog(this, "Do you really want to delete the contact '" + mContactsDetails.getFirstName() + " " + mContactsDetails.getLastName() + "'?", "Delete contact?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
            case JOptionPane.YES_OPTION:
                int mID = ((clsContactsTableModel) tblContacts.getModel()).getID(tblContacts.getSelectedRow());
                if (this.pModel.deleteContact(mID)) {
                    this.pSelectedRow = -1;
                    fillContactsTable();
                    selectContact();
                }
                break;
        }
    }
    
    private void editContact() throws SQLException {
        this.pStatus = enmStatus.Modify;
        setComponents();
    }
    
    private void cancelContactModification() throws SQLException {
        this.pStatus = enmStatus.View;
        setComponents();
        if (this.pSelectedRow != (-1)) {
            selectContact();
        }
    }
    
    private void newContact() throws SQLException {
        this.pStatus = enmStatus.New;
        setComponents();
        fillContactDetails(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrpGender = new javax.swing.ButtonGroup();
        lblIcon = new javax.swing.JLabel();
        splVertical = new javax.swing.JSplitPane();
        scrLeft = new javax.swing.JScrollPane();
        tblContacts = new javax.swing.JTable();
        scrRight = new javax.swing.JScrollPane();
        grpRight = new javax.swing.JPanel();
        grpAddress = new javax.swing.JPanel();
        txtStreet = new javax.swing.JTextField();
        txtPostcode = new javax.swing.JTextField();
        lblStreet = new javax.swing.JLabel();
        lblCityCodeCity = new javax.swing.JLabel();
        lblCountry = new javax.swing.JLabel();
        txtCity = new javax.swing.JTextField();
        txtCountry = new javax.swing.JTextField();
        grpPersonal = new javax.swing.JPanel();
        txtLastName = new javax.swing.JTextField();
        txtMiddleName = new javax.swing.JTextField();
        dtpBirthday = new com.toedter.calendar.JDateChooser();
        txtFirstName = new javax.swing.JTextField();
        lblFirstName = new javax.swing.JLabel();
        lblMiddleName = new javax.swing.JLabel();
        lblLastName = new javax.swing.JLabel();
        lblBirthday = new javax.swing.JLabel();
        rdoMale = new javax.swing.JRadioButton();
        rdoFemale = new javax.swing.JRadioButton();
        lblGender = new javax.swing.JLabel();
        rdoUndefined = new javax.swing.JRadioButton();
        grpAvailability = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAvailability = new javax.swing.JTextArea();
        grpModifications = new javax.swing.JPanel();
        lblDateCreated = new javax.swing.JLabel();
        lblDateModified = new javax.swing.JLabel();
        lblDateCreatedVal = new javax.swing.JLabel();
        lblDateModifiedVal = new javax.swing.JLabel();
        grpComment = new javax.swing.JPanel();
        srcComment = new javax.swing.JScrollPane();
        txtComment = new javax.swing.JTextArea();
        grpControl = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationPresentation/Icons/black/png/contact_card_icon&16.png"))); // NOI18N
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));
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

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationPresentation/Icons/black/png/contact_card_icon&48.png"))); // NOI18N

        splVertical.setDividerLocation(250);
        splVertical.setMaximumSize(new java.awt.Dimension(400, 400));
        splVertical.setMinimumSize(new java.awt.Dimension(200, 100));

        scrLeft.setMinimumSize(new java.awt.Dimension(200, 25));

        tblContacts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Type"
            }
        ));
        scrLeft.setViewportView(tblContacts);

        splVertical.setLeftComponent(scrLeft);

        scrRight.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrRight.setMinimumSize(new java.awt.Dimension(440, 400));
        scrRight.setPreferredSize(new java.awt.Dimension(440, 400));

        grpRight.setMinimumSize(new java.awt.Dimension(350, 350));
        grpRight.setRequestFocusEnabled(false);

        grpAddress.setBorder(javax.swing.BorderFactory.createTitledBorder("Address"));

        lblStreet.setText("Street:");

        lblCityCodeCity.setText("Code / City:");

        lblCountry.setText("Country:");

        javax.swing.GroupLayout grpAddressLayout = new javax.swing.GroupLayout(grpAddress);
        grpAddress.setLayout(grpAddressLayout);
        grpAddressLayout.setHorizontalGroup(
            grpAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpAddressLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(grpAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStreet, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCityCodeCity)
                    .addComponent(lblCountry))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(grpAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(grpAddressLayout.createSequentialGroup()
                        .addComponent(txtPostcode, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCity))
                    .addComponent(txtStreet)
                    .addComponent(txtCountry))
                .addContainerGap())
        );
        grpAddressLayout.setVerticalGroup(
            grpAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpAddressLayout.createSequentialGroup()
                .addGroup(grpAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStreet))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(grpAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPostcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCityCodeCity)
                    .addComponent(txtCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(grpAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCountry)
                    .addComponent(txtCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        grpPersonal.setBorder(javax.swing.BorderFactory.createTitledBorder("Personal"));
        grpPersonal.setPreferredSize(new java.awt.Dimension(0, 0));

        lblFirstName.setText("First name:");

        lblMiddleName.setText("Middle name:");

        lblLastName.setText("Last name:");

        lblBirthday.setText("Birthday:");

        bgrpGender.add(rdoMale);
        rdoMale.setText("Male");

        bgrpGender.add(rdoFemale);
        rdoFemale.setText("Female");

        lblGender.setText("Gender:");

        bgrpGender.add(rdoUndefined);
        rdoUndefined.setText("Undefined");

        javax.swing.GroupLayout grpPersonalLayout = new javax.swing.GroupLayout(grpPersonal);
        grpPersonal.setLayout(grpPersonalLayout);
        grpPersonalLayout.setHorizontalGroup(
            grpPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpPersonalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(grpPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(grpPersonalLayout.createSequentialGroup()
                        .addGroup(grpPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMiddleName)
                            .addComponent(lblBirthday)
                            .addComponent(lblLastName)
                            .addComponent(lblFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(grpPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dtpBirthday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMiddleName)
                            .addComponent(txtLastName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtFirstName, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(grpPersonalLayout.createSequentialGroup()
                        .addComponent(lblGender)
                        .addGap(49, 49, 49)
                        .addComponent(rdoMale)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoFemale)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoUndefined)
                        .addGap(0, 130, Short.MAX_VALUE)))
                .addContainerGap())
        );
        grpPersonalLayout.setVerticalGroup(
            grpPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpPersonalLayout.createSequentialGroup()
                .addGroup(grpPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFirstName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(grpPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMiddleName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMiddleName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(grpPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLastName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(grpPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dtpBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(grpPersonalLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(lblBirthday)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(grpPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoMale)
                    .addComponent(rdoFemale)
                    .addComponent(lblGender)
                    .addComponent(rdoUndefined)))
        );

        grpAvailability.setBorder(javax.swing.BorderFactory.createTitledBorder("Availability"));

        txtAvailability.setColumns(20);
        txtAvailability.setRows(5);
        jScrollPane1.setViewportView(txtAvailability);

        javax.swing.GroupLayout grpAvailabilityLayout = new javax.swing.GroupLayout(grpAvailability);
        grpAvailability.setLayout(grpAvailabilityLayout);
        grpAvailabilityLayout.setHorizontalGroup(
            grpAvailabilityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpAvailabilityLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        grpAvailabilityLayout.setVerticalGroup(
            grpAvailabilityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpAvailabilityLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                .addContainerGap())
        );

        grpModifications.setBorder(javax.swing.BorderFactory.createTitledBorder("Modifications"));

        lblDateCreated.setText("Date created:");

        lblDateModified.setText("Date modified:");

        lblDateCreatedVal.setText("lblDateCreatedVal");

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        grpComment.setBorder(javax.swing.BorderFactory.createTitledBorder("Comment"));

        txtComment.setColumns(20);
        txtComment.setRows(5);
        srcComment.setViewportView(txtComment);

        javax.swing.GroupLayout grpCommentLayout = new javax.swing.GroupLayout(grpComment);
        grpComment.setLayout(grpCommentLayout);
        grpCommentLayout.setHorizontalGroup(
            grpCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, grpCommentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(srcComment)
                .addContainerGap())
        );
        grpCommentLayout.setVerticalGroup(
            grpCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(srcComment, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout grpRightLayout = new javax.swing.GroupLayout(grpRight);
        grpRight.setLayout(grpRightLayout);
        grpRightLayout.setHorizontalGroup(
            grpRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(grpPersonal, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
            .addComponent(grpModifications, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(grpAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(grpAvailability, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(grpComment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        grpRightLayout.setVerticalGroup(
            grpRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, grpRightLayout.createSequentialGroup()
                .addComponent(grpModifications, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(grpPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(grpAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(grpAvailability, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(grpComment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        scrRight.setViewportView(grpRight);

        splVertical.setRightComponent(scrRight);

        grpControl.setBorder(javax.swing.BorderFactory.createTitledBorder("Control"));

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
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
                    .addComponent(btnSave)
                    .addComponent(btnEdit)
                    .addComponent(btnCancel)
                    .addComponent(btnNew)
                    .addComponent(btnDelete))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(grpControl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(splVertical, javax.swing.GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(splVertical, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(grpControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        this.pMainForm.closeWindowContacts();
    }//GEN-LAST:event_formInternalFrameClosed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        try {
            newContact();
        } catch (SQLException ex) {
            Logger.getLogger(frmContacts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        try {
            editContact();
        } catch (SQLException ex) {
            Logger.getLogger(frmContacts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        try {
            saveContact();
        } catch (SQLException ex) {
            Logger.getLogger(frmContacts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            deleteContact();
        } catch (SQLException ex) {
            Logger.getLogger(frmContacts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        try {
            cancelContactModification();
        } catch (SQLException ex) {
            Logger.getLogger(frmContacts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrpGender;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private com.toedter.calendar.JDateChooser dtpBirthday;
    private javax.swing.JPanel grpAddress;
    private javax.swing.JPanel grpAvailability;
    private javax.swing.JPanel grpComment;
    private javax.swing.JPanel grpControl;
    private javax.swing.JPanel grpModifications;
    private javax.swing.JPanel grpPersonal;
    private javax.swing.JPanel grpRight;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBirthday;
    private javax.swing.JLabel lblCityCodeCity;
    private javax.swing.JLabel lblCountry;
    private javax.swing.JLabel lblDateCreated;
    private javax.swing.JLabel lblDateCreatedVal;
    private javax.swing.JLabel lblDateModified;
    private javax.swing.JLabel lblDateModifiedVal;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblGender;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblMiddleName;
    private javax.swing.JLabel lblStreet;
    private javax.swing.JRadioButton rdoFemale;
    private javax.swing.JRadioButton rdoMale;
    private javax.swing.JRadioButton rdoUndefined;
    private javax.swing.JScrollPane scrLeft;
    private javax.swing.JScrollPane scrRight;
    private javax.swing.JSplitPane splVertical;
    private javax.swing.JScrollPane srcComment;
    private javax.swing.JTable tblContacts;
    private javax.swing.JTextArea txtAvailability;
    private javax.swing.JTextField txtCity;
    private javax.swing.JTextArea txtComment;
    private javax.swing.JTextField txtCountry;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtMiddleName;
    private javax.swing.JTextField txtPostcode;
    private javax.swing.JTextField txtStreet;
    // End of variables declaration//GEN-END:variables
    
    class tblContactsListSelectionHandler implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            // TODO: When clicking into tblContacts with the mouse this event is triggered twice. Find out why.
            ListSelectionModel mSelectionModel = (ListSelectionModel)e.getSource();
            
            if (mSelectionModel.isSelectionEmpty() == false) {
                // Find out which indexes are selected.
                int mMinIndex = mSelectionModel.getMinSelectionIndex();
                int mMaxIndex = mSelectionModel.getMaxSelectionIndex();
                
                if (mMinIndex == mMaxIndex) {
                    if (mSelectionModel.isSelectedIndex(mMinIndex)) {
                        try {
                            fillContactDetails(mMinIndex);
                        } catch (SQLException ex) {
                            Logger.getLogger(frmContacts.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        //Save the selected row for the case that the next action after this is cancel.
                        pSelectedRow = mMinIndex;
                    }
                }
            }
        }
    }
}
