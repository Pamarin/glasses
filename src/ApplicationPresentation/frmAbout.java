/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationPresentation;

import BusinessLogic.Forms.clsAbout;
import BusinessLogic.Forms.clsMain;

/**
 *
 * @author jean
 */
public class frmAbout extends javax.swing.JInternalFrame {
    private frmMain pMainForm;
    private clsMain pMainClass;
    private clsAbout pAbout;
    
    /**
     * Creates new form frmAbout
     */
    public frmAbout(frmMain aMainForm, clsMain aMainClass) {
        this.pMainForm = aMainForm;
        this.pMainClass = aMainClass;
        this.pAbout = new clsAbout(this.pMainClass.getProgram());
        initComponents();
        setComponents();
    }
    
    private void setComponents() {
        lblProgramNameVal.setText(this.pAbout.getProgramName());
        lblProgramVersionVal.setText(Integer.toString(this.pAbout.getProgramVersion()));
        lblDatabaseTypeVal.setText(this.pAbout.getDatabaseType());
        lblDatabaseVersionVal.setText(Integer.toString(this.pAbout.getDatabaseVersion()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblProgramName = new javax.swing.JLabel();
        lblProgramVersion = new javax.swing.JLabel();
        lblDatabaseType = new javax.swing.JLabel();
        lblDatabaseVersion = new javax.swing.JLabel();
        lblProgramNameVal = new javax.swing.JLabel();
        lblProgramVersionVal = new javax.swing.JLabel();
        lblDatabaseTypeVal = new javax.swing.JLabel();
        lblDatabaseVersionVal = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationPresentation/Icons/black/png/info_icon&16.png"))); // NOI18N
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

        lblProgramName.setText("Program name:");

        lblProgramVersion.setText("Program version:");

        lblDatabaseType.setText("Database type:");

        lblDatabaseVersion.setText("Database version:");

        lblProgramNameVal.setText("lblProgramNameVal");

        lblProgramVersionVal.setText("lblProgramVersionVal");

        lblDatabaseTypeVal.setText("lblDatabaseTypeVal");

        lblDatabaseVersionVal.setText("lblDatabaseVersionVal");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationPresentation/Icons/black/png/info_icon&48.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProgramName)
                            .addComponent(lblDatabaseVersion)
                            .addComponent(lblDatabaseType)
                            .addComponent(lblProgramVersion))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblProgramNameVal)
                            .addComponent(lblDatabaseVersionVal)
                            .addComponent(lblDatabaseTypeVal)
                            .addComponent(lblProgramVersionVal)))
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProgramName)
                    .addComponent(lblProgramNameVal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProgramVersion)
                    .addComponent(lblProgramVersionVal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDatabaseType)
                    .addComponent(lblDatabaseTypeVal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDatabaseVersion)
                    .addComponent(lblDatabaseVersionVal))
                .addContainerGap(133, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        this.pMainForm.closeWindowAbout();
    }//GEN-LAST:event_formInternalFrameClosed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblDatabaseType;
    private javax.swing.JLabel lblDatabaseTypeVal;
    private javax.swing.JLabel lblDatabaseVersion;
    private javax.swing.JLabel lblDatabaseVersionVal;
    private javax.swing.JLabel lblProgramName;
    private javax.swing.JLabel lblProgramNameVal;
    private javax.swing.JLabel lblProgramVersion;
    private javax.swing.JLabel lblProgramVersionVal;
    // End of variables declaration//GEN-END:variables
}