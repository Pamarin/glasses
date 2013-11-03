/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationPresentation;

import BusinessLogic.Forms.clsMain;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author jean
 */
public class frmMain extends javax.swing.JFrame {
    // Manual variables declaration
    private final BusinessLogic.Forms.clsMain pMain;
    
    private ApplicationPresentation.frmAbout frmAbout1;
    private ApplicationPresentation.frmCategories frmCategories1;
    private ApplicationPresentation.frmContacts frmContacts1;
    private ApplicationPresentation.frmSettings frmSettings1;
    private ApplicationPresentation.frmUpdate frmUpdate1;
    // End of manual variables declaration
    
    /**
     * Creates new form frmMain
     */
    public frmMain() {
        //Load the forms' backend.
        this.pMain = new clsMain();
        //Initialize the components.
        initComponents();
        loadConfiguration();
    }
    
    private void loadConfiguration() {
        Boolean checkConfigurationFile = true;
        Boolean checkUpdateNecessarity = true;
        
        while (checkConfigurationFile) {
            //Check for configuration file
            if (this.pMain.getProgram().getConfiguration().ConfigurationFileExists()) {
                //Configuration file exists.
                //Load configuration.
                if (this.pMain.getProgram().getConfiguration().loadDatabaseSettings()) {
                    //Configuration file loaded.
                    //Check database.
                    while (checkUpdateNecessarity) {
                        //Check if database is accessible.
                        if (this.pMain.getProgram().DatabaseAccessible()) {
                            //Database is accessible.
                            //Check if database update necessary.
                            if (this.pMain.updateNecessary()) {
                                //Update necessary.
                                //Run update.
                                openWindowUpdate();
                            }
                            //Database check done.
                            checkConfigurationFile = false;
                            checkUpdateNecessarity = false;
                        } else {
                            //Database is not accessible.
                            Object[] databaseNotAccessibleDialogOptions = {"View Configuration", "No", "Yes"};
                            int databaseNotAccessibleDialogResponse = JOptionPane.showOptionDialog(this, "The database is not accessible. Retry?", "Database not accessible", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, databaseNotAccessibleDialogOptions, databaseNotAccessibleDialogOptions[2]);
                            if (databaseNotAccessibleDialogResponse == 2) {
                                //Retry check.
                            } else if (databaseNotAccessibleDialogResponse == 0) {
                                //Configure configuration file.
                                openWindowConfiguration();
                            } else {
                                //Database check abborted.
                                System.exit(0);
                            }
                        }
                    }
                } else {
                    //Configuration file not loaded.
                    //Check configuration settings.
                    openWindowConfiguration();
                }
            } else {
                //Configuration file does not exist.
                //Create configuration file.
                openWindowConfiguration();
            }
        }
    }
    
    public void openWindow(JInternalFrame aWindow, String aTitle, boolean aIsMaximizable, boolean aIsClosable, boolean aIsResizable, boolean aSetCentre, boolean aSetModal) {              
        if (aSetModal) {
            jDesktopPane1.add(aWindow, javax.swing.JLayeredPane.MODAL_LAYER);
        } else {
            jDesktopPane1.add(aWindow, javax.swing.JLayeredPane.DEFAULT_LAYER);
        }
        
        aWindow.setTitle(aTitle);
        aWindow.setVisible(true);
        if (aIsMaximizable) {
            aWindow.setMaximizable(true);
        }
        if (aIsResizable) {
            aWindow.setResizable(true);
        }
        if (aIsClosable) {
            aWindow.setClosable(true);
        }

        if (aSetCentre) {
            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension aWindowSize = aWindow.getSize();
            aWindow.setLocation((desktopSize.width - aWindowSize.width) / 2, (desktopSize.height - aWindowSize.height) / 2);
        }

        try {
            aWindow.setSelected(true);
        } catch (PropertyVetoException ex) {
            JOptionPane.showMessageDialog(this, "Could not open requested window. " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void openWindowConfiguration() {
        JDialog dlgConfiguration1 = new ApplicationPresentation.dlgConfiguration(this, true, this.pMain);
        
        //Set the window title.
        dlgConfiguration1.setTitle("Configuration");
        
        //Set window to the centre.
        Dimension desktopSize = Toolkit.getDefaultToolkit().getScreenSize();
        //Dimension desktopSize = jDesktopPane1.getSize();
        Dimension aWindowSize = dlgConfiguration1.getSize();
        dlgConfiguration1.setLocation((desktopSize.width - aWindowSize.width) / 2, (desktopSize.height - aWindowSize.height) / 2);
        
        //Open the window.
        dlgConfiguration1.setVisible(true);
    }
    
    private void openWindowAbout() {
        if (this.frmAbout1 == null) {
            this.frmAbout1 = new ApplicationPresentation.frmAbout(this, this.pMain);
        }
        
        this.mniAbout.setEnabled(false);
        openWindow(this.frmAbout1, "About", true, true, true, false, false);
    }
    
    private void openWindowCategories() {
        if (this.frmCategories1 == null) {
            this.frmCategories1 = new ApplicationPresentation.frmCategories(this, this.pMain.getProgram());
        }
        
        this.mniCategories.setEnabled(false);
        openWindow(this.frmCategories1, "Categories", true, true, true, false, false);
    }
    
    private void openWindowContacts() throws SQLException {
        if (this.frmContacts1 == null) {
            this.frmContacts1 = new ApplicationPresentation.frmContacts(this, this.pMain.getProgram());
        }
        
        this.mniContacts.setEnabled(false);
        openWindow(this.frmContacts1, "Contacts", true, true, true, false, false);
    }
    
    private void openWindowSettings(boolean aSolitary) {
        boolean mSetCentre = false;

        if (aSolitary) {
            this.mnuMain.setVisible(false);
            mSetCentre = true;
        }

        if (this.frmSettings1 == null) {
            this.frmSettings1 = new ApplicationPresentation.frmSettings(this, this.pMain);
        }

        this.mniSettings.setEnabled(false);
        openWindow(this.frmSettings1, "Settings", true, true, true, mSetCentre, true);
    }
    
    private void openWindowUpdate() {
        this.mnuMain.setVisible(false);

        if (this.frmUpdate1 == null) {
            this.frmUpdate1 = new ApplicationPresentation.frmUpdate(this, this.pMain);
        }

        this.mniUpdate.setEnabled(false);
        openWindow(frmUpdate1, "Update", false, false, false, true, true);
    }
    
    public void closeWindowAbout() {
        this.mniAbout.setEnabled(true);
        this.frmAbout1 = null;
    }
    
    public void closeWindowCategories() {
        this.mniCategories.setEnabled(true);
        this.frmCategories1 = null;
    }
    
    public void closeWindowContacts() {
        this.mniContacts.setEnabled(true);
        this.frmContacts1 = null;
    }
    
    public void closeWindowSettings() {
        this.mniSettings.setEnabled(true);
        this.frmSettings1 = null;
    }
    
    public void closeWindowUpdate() {
        mniUpdate.setEnabled(true);
        this.frmUpdate1 = null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu3 = new javax.swing.JMenu();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        mnuMain = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mniSettings = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mniClose = new javax.swing.JMenuItem();
        mnuMask = new javax.swing.JMenu();
        mniCategories = new javax.swing.JMenuItem();
        mniContacts = new javax.swing.JMenuItem();
        mnuView = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        mnuQuestion = new javax.swing.JMenu();
        mniAbout = new javax.swing.JMenuItem();
        mniUpdate = new javax.swing.JMenuItem();

        jMenu3.setText("jMenu3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Glasses V12");
        setPreferredSize(new java.awt.Dimension(900, 700));

        mnuFile.setText("File");

        mniSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationPresentation/Icons/black/png/cog_icon&16.png"))); // NOI18N
        mniSettings.setText("Settings");
        mniSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSettingsActionPerformed(evt);
            }
        });
        mnuFile.add(mniSettings);
        mnuFile.add(jSeparator1);

        mniClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationPresentation/Icons/black/png/on-off_icon&16.png"))); // NOI18N
        mniClose.setText("Close");
        mniClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCloseActionPerformed(evt);
            }
        });
        mnuFile.add(mniClose);

        mnuMain.add(mnuFile);

        mnuMask.setText("Mask");

        mniCategories.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationPresentation/Icons/black/png/3x3_grid_icon&16.png"))); // NOI18N
        mniCategories.setText("Categories");
        mniCategories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCategoriesActionPerformed(evt);
            }
        });
        mnuMask.add(mniCategories);

        mniContacts.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationPresentation/Icons/black/png/contact_card_icon&16.png"))); // NOI18N
        mniContacts.setText("Contacts");
        mniContacts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniContactsActionPerformed(evt);
            }
        });
        mnuMask.add(mniContacts);

        mnuMain.add(mnuMask);

        mnuView.setText("View");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("Window manager");
        mnuView.add(jCheckBoxMenuItem1);

        mnuMain.add(mnuView);

        mnuQuestion.setText("?");

        mniAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationPresentation/Icons/black/png/info_icon&16.png"))); // NOI18N
        mniAbout.setText("About");
        mniAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniAboutActionPerformed(evt);
            }
        });
        mnuQuestion.add(mniAbout);

        mniUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationPresentation/Icons/black/png/playback_reload_icon&16.png"))); // NOI18N
        mniUpdate.setText("Update");
        mniUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniUpdateActionPerformed(evt);
            }
        });
        mnuQuestion.add(mniUpdate);

        mnuMain.add(mnuQuestion);

        setJMenuBar(mnuMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1025, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mniContactsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniContactsActionPerformed
        try {
            openWindowContacts();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "The requested window could not be opened: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_mniContactsActionPerformed

    private void mniAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniAboutActionPerformed
        openWindowAbout();
    }//GEN-LAST:event_mniAboutActionPerformed

    private void mniUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniUpdateActionPerformed
        openWindowUpdate();
    }//GEN-LAST:event_mniUpdateActionPerformed

    private void mniSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSettingsActionPerformed
        openWindowSettings(false);
    }//GEN-LAST:event_mniSettingsActionPerformed

    private void mniCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCloseActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mniCloseActionPerformed

    private void mniCategoriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCategoriesActionPerformed
        openWindowCategories();
    }//GEN-LAST:event_mniCategoriesActionPerformed
       
    public void setMenuVisible() {
        this.mnuMain.setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                
                if ("Nimbus".equals(info.getName())) {
                    //javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
                    break;
                }
                
            }
            //javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new frmMain().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem mniAbout;
    private javax.swing.JMenuItem mniCategories;
    private javax.swing.JMenuItem mniClose;
    private javax.swing.JMenuItem mniContacts;
    private javax.swing.JMenuItem mniSettings;
    private javax.swing.JMenuItem mniUpdate;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenuBar mnuMain;
    private javax.swing.JMenu mnuMask;
    private javax.swing.JMenu mnuQuestion;
    private javax.swing.JMenu mnuView;
    // End of variables declaration//GEN-END:variables
}
