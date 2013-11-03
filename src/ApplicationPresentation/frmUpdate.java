/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationPresentation;

import BusinessLogic.Forms.clsMain;
import BusinessLogic.Forms.clsUpdate;
import BusinessLogic.clsProgram;

/**
 *
 * @author jean
 */
public class frmUpdate extends javax.swing.JInternalFrame {
    private final frmMain pMainForm;
    private final clsMain pMainClass;
    private final clsUpdate pUpdate;
    private final clsProgram pProgram;
    private int pCurrentDatabaseVersion;
    private int pUpdateDatabaseVersion;
    
    /**
     * Creates new form frmUpdate
     * @param aMainForm
     * @param aMainClass
     */
    public frmUpdate(frmMain aMainForm, clsMain aMainClass) {
        this.pMainForm = aMainForm;
        this.pMainClass = aMainClass;
        this.pUpdate = new clsUpdate(this.pMainClass.getProgram());
        this.pProgram = aMainClass.getProgram();
        initComponents();
        setComponents();
    }
    
    private void setComponents() {
        this.pCurrentDatabaseVersion = this.pProgram.getCurrentDatabaseVersion();
        this.pUpdateDatabaseVersion = this.pProgram.getProgramDatabaseVersion();
        
        //If there is no update needed, close this window.
        lblCurrentDatabaseVersion.setText(Integer.toString(this.pCurrentDatabaseVersion));
        lblUpdateDatabaseVersion.setText(Integer.toString(this.pUpdateDatabaseVersion));
        
        if (this.pCurrentDatabaseVersion == this.pUpdateDatabaseVersion) {
            btnUpdate.setEnabled(false);
            btnCancel.setText("Close");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCurrentDatabaseVersionLabel = new javax.swing.JLabel();
        lblUpdateDatabaseVersionLabel = new javax.swing.JLabel();
        lblCurrentDatabaseVersion = new javax.swing.JLabel();
        lblUpdateDatabaseVersion = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationPresentation/Icons/black/png/refresh_icon&16.png"))); // NOI18N
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

        lblCurrentDatabaseVersionLabel.setText("Current database version:");

        lblUpdateDatabaseVersionLabel.setText("Update database version:");

        lblCurrentDatabaseVersion.setText("0000");

        lblUpdateDatabaseVersion.setText("0000");

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationPresentation/Icons/black/png/refresh_icon&48.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUpdateDatabaseVersionLabel)
                            .addComponent(lblCurrentDatabaseVersionLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCurrentDatabaseVersion)
                            .addComponent(lblUpdateDatabaseVersion)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel))
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCurrentDatabaseVersionLabel)
                    .addComponent(lblCurrentDatabaseVersion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUpdateDatabaseVersionLabel)
                    .addComponent(lblUpdateDatabaseVersion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        this.pMainForm.closeWindowUpdate();
    }//GEN-LAST:event_formInternalFrameClosed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        if (this.pCurrentDatabaseVersion == this.pUpdateDatabaseVersion) {
            closeWindow();
        } else {
            System.exit(0);
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        this.pUpdate.performUpdate();
        setComponents();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void closeWindow() {
        this.pMainForm.setMenuVisible();
        this.setVisible(false);
        this.dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblCurrentDatabaseVersion;
    private javax.swing.JLabel lblCurrentDatabaseVersionLabel;
    private javax.swing.JLabel lblUpdateDatabaseVersion;
    private javax.swing.JLabel lblUpdateDatabaseVersionLabel;
    // End of variables declaration//GEN-END:variables
}
