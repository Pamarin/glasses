/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationPresentation;

import BusinessLogic.Forms.clsMain;
import java.awt.Color;

/**
 *
 * @author jean
 */
public class dlgConfiguration extends javax.swing.JDialog {
    private clsMain pMainClass;
    
    private enum enmStatus {
        Welcome,
        Database,
        Finish;
        
        public static Integer getIndex(enmStatus aStatus)
        {
            switch (aStatus)
            {
                case Welcome: return 0;
                case Database: return 1;
                case Finish: return 2;
                default: return -1;
            }
        }
    }
    
    /**
     * Creates new form dlgConfiguration
     */
    public dlgConfiguration(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public dlgConfiguration(java.awt.Frame parent, boolean modal, clsMain aMainClass) {
        super(parent, modal);
        initComponents();
        this.pMainClass = aMainClass;
    }
    
    private void setStatus(enmStatus aStatus) {
        switch (aStatus) {
            case Welcome:
                btnCancel.setEnabled(true);
                btnBack.setEnabled(false);
                btnNext.setEnabled(true);
                break;
            case Database:
                btnCancel.setEnabled(true);
                btnBack.setEnabled(true);
                btnNext.setEnabled(true);
                break;
            case Finish:
                btnCancel.setEnabled(false);
                btnBack.setEnabled(true);
                btnNext.setEnabled(true);
                break;
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

        lblIcon = new javax.swing.JLabel();
        tabMain = new javax.swing.JTabbedPane();
        pnlWelcome = new javax.swing.JPanel();
        scrWelcome = new javax.swing.JScrollPane();
        txtWelcome = new javax.swing.JTextArea();
        pnlDatabase = new javax.swing.JPanel();
        pnlAddressing = new javax.swing.JPanel();
        lblDatabaseAddressingType = new javax.swing.JLabel();
        lblDatabaseAddressingLocation = new javax.swing.JLabel();
        lblDatabaseAddressingPort = new javax.swing.JLabel();
        txtDatabaseAddressingLocation = new javax.swing.JTextField();
        txtDatabaseAddressingPort = new javax.swing.JTextField();
        cboDatabaseAddressingType = new javax.swing.JComboBox();
        pnlNaming = new javax.swing.JPanel();
        lblDatabaseNamingDatabaseName = new javax.swing.JLabel();
        lblDatabaseNamingTablePrefix = new javax.swing.JLabel();
        txtDatabaseNamingDatabaseName = new javax.swing.JTextField();
        txtDatabaseNamingTablePrefix = new javax.swing.JTextField();
        pnlAuthentication = new javax.swing.JPanel();
        lblDatabaseAuthenticationUsername = new javax.swing.JLabel();
        lblDatabaseAuthenticationPassword = new javax.swing.JLabel();
        txtDatabaseAuthenticationUsername = new javax.swing.JTextField();
        txtDatabaseAuthenticationPassword = new javax.swing.JPasswordField();
        pnlSetting = new javax.swing.JPanel();
        lblDatabaseSettingName = new javax.swing.JLabel();
        txtDatabaseSettingName = new javax.swing.JTextField();
        pnlFinish = new javax.swing.JPanel();
        scrFinish = new javax.swing.JScrollPane();
        txtFinish = new javax.swing.JTextArea();
        pnlControl = new javax.swing.JPanel();
        btnNext = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ApplicationPresentation/Icons/black/png/cog_icon&48.png"))); // NOI18N

        tabMain.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        tabMain.setEnabled(false);
        tabMain.setFocusable(false);

        pnlWelcome.setForeground(new java.awt.Color(242, 242, 242));
        pnlWelcome.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                pnlWelcomeComponentShown(evt);
            }
        });

        txtWelcome.setEditable(false);
        txtWelcome.setBackground(new java.awt.Color(242, 242, 242));
        txtWelcome.setColumns(20);
        txtWelcome.setLineWrap(true);
        txtWelcome.setRows(5);
        txtWelcome.setText("Welcome to Glasses!\n\nBefore you start you need to choose a database. The settings will be saved in a special configuration file and may be manipulated later if necessary.\n\nPlease click Next to proceed.");
        txtWelcome.setFocusable(false);
        scrWelcome.setViewportView(txtWelcome);

        javax.swing.GroupLayout pnlWelcomeLayout = new javax.swing.GroupLayout(pnlWelcome);
        pnlWelcome.setLayout(pnlWelcomeLayout);
        pnlWelcomeLayout.setHorizontalGroup(
            pnlWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWelcomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrWelcome, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlWelcomeLayout.setVerticalGroup(
            pnlWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWelcomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrWelcome, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabMain.addTab("Welcome", pnlWelcome);

        pnlDatabase.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                pnlDatabaseComponentShown(evt);
            }
        });

        pnlAddressing.setBorder(javax.swing.BorderFactory.createTitledBorder("Addressing"));

        lblDatabaseAddressingType.setText("Type:");

        lblDatabaseAddressingLocation.setText("Location:");

        lblDatabaseAddressingPort.setText("Port:");

        cboDatabaseAddressingType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MySQL" }));

        javax.swing.GroupLayout pnlAddressingLayout = new javax.swing.GroupLayout(pnlAddressing);
        pnlAddressing.setLayout(pnlAddressingLayout);
        pnlAddressingLayout.setHorizontalGroup(
            pnlAddressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddressingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAddressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDatabaseAddressingPort)
                    .addComponent(lblDatabaseAddressingLocation)
                    .addComponent(lblDatabaseAddressingType))
                .addGap(60, 60, 60)
                .addGroup(pnlAddressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDatabaseAddressingLocation, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtDatabaseAddressingPort, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboDatabaseAddressingType, 0, 353, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlAddressingLayout.setVerticalGroup(
            pnlAddressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddressingLayout.createSequentialGroup()
                .addGroup(pnlAddressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDatabaseAddressingType)
                    .addComponent(cboDatabaseAddressingType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAddressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDatabaseAddressingLocation)
                    .addComponent(txtDatabaseAddressingLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAddressingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDatabaseAddressingPort)
                    .addComponent(txtDatabaseAddressingPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlNaming.setBorder(javax.swing.BorderFactory.createTitledBorder("Naming"));

        lblDatabaseNamingDatabaseName.setText("Database name:");

        lblDatabaseNamingTablePrefix.setText("Table prefix:");

        javax.swing.GroupLayout pnlNamingLayout = new javax.swing.GroupLayout(pnlNaming);
        pnlNaming.setLayout(pnlNamingLayout);
        pnlNamingLayout.setHorizontalGroup(
            pnlNamingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNamingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNamingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDatabaseNamingDatabaseName)
                    .addComponent(lblDatabaseNamingTablePrefix))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlNamingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDatabaseNamingTablePrefix)
                    .addComponent(txtDatabaseNamingDatabaseName))
                .addContainerGap())
        );
        pnlNamingLayout.setVerticalGroup(
            pnlNamingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNamingLayout.createSequentialGroup()
                .addGroup(pnlNamingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDatabaseNamingDatabaseName)
                    .addComponent(txtDatabaseNamingDatabaseName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlNamingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDatabaseNamingTablePrefix)
                    .addComponent(txtDatabaseNamingTablePrefix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlAuthentication.setBorder(javax.swing.BorderFactory.createTitledBorder("Authentication"));

        lblDatabaseAuthenticationUsername.setText("Username:");

        lblDatabaseAuthenticationPassword.setText("Password:");

        txtDatabaseAuthenticationPassword.setEchoChar('\u2022');

        javax.swing.GroupLayout pnlAuthenticationLayout = new javax.swing.GroupLayout(pnlAuthentication);
        pnlAuthentication.setLayout(pnlAuthenticationLayout);
        pnlAuthenticationLayout.setHorizontalGroup(
            pnlAuthenticationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAuthenticationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAuthenticationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDatabaseAuthenticationUsername)
                    .addComponent(lblDatabaseAuthenticationPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlAuthenticationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDatabaseAuthenticationUsername)
                    .addComponent(txtDatabaseAuthenticationPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlAuthenticationLayout.setVerticalGroup(
            pnlAuthenticationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAuthenticationLayout.createSequentialGroup()
                .addGroup(pnlAuthenticationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDatabaseAuthenticationUsername)
                    .addComponent(txtDatabaseAuthenticationUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAuthenticationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDatabaseAuthenticationPassword)
                    .addComponent(txtDatabaseAuthenticationPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnlSetting.setBorder(javax.swing.BorderFactory.createTitledBorder("Setting"));

        lblDatabaseSettingName.setText("Name:");

        javax.swing.GroupLayout pnlSettingLayout = new javax.swing.GroupLayout(pnlSetting);
        pnlSetting.setLayout(pnlSettingLayout);
        pnlSettingLayout.setHorizontalGroup(
            pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSettingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDatabaseSettingName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addComponent(txtDatabaseSettingName, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlSettingLayout.setVerticalGroup(
            pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSettingLayout.createSequentialGroup()
                .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDatabaseSettingName)
                    .addComponent(txtDatabaseSettingName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlDatabaseLayout = new javax.swing.GroupLayout(pnlDatabase);
        pnlDatabase.setLayout(pnlDatabaseLayout);
        pnlDatabaseLayout.setHorizontalGroup(
            pnlDatabaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatabaseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatabaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlNaming, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAddressing, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAuthentication, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlDatabaseLayout.setVerticalGroup(
            pnlDatabaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatabaseLayout.createSequentialGroup()
                .addComponent(pnlSetting, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlAddressing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlNaming, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlAuthentication, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabMain.addTab("Database", pnlDatabase);

        pnlFinish.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                pnlFinishComponentShown(evt);
            }
        });

        txtFinish.setEditable(false);
        txtFinish.setBackground(new java.awt.Color(242, 242, 242));
        txtFinish.setColumns(20);
        txtFinish.setLineWrap(true);
        txtFinish.setRows(5);
        txtFinish.setText("Configuration done!\n\nYou can now proceed to the main program by clicking the Next button.");
        txtFinish.setFocusable(false);
        scrFinish.setViewportView(txtFinish);

        javax.swing.GroupLayout pnlFinishLayout = new javax.swing.GroupLayout(pnlFinish);
        pnlFinish.setLayout(pnlFinishLayout);
        pnlFinishLayout.setHorizontalGroup(
            pnlFinishLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFinishLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrFinish, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlFinishLayout.setVerticalGroup(
            pnlFinishLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFinishLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrFinish, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabMain.addTab("Finish", pnlFinish);

        pnlControl.setBorder(javax.swing.BorderFactory.createTitledBorder("Control"));
        pnlControl.setPreferredSize(new java.awt.Dimension(12, 65));

        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlControlLayout = new javax.swing.GroupLayout(pnlControl);
        pnlControl.setLayout(pnlControlLayout);
        pnlControlLayout.setHorizontalGroup(
            pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlControlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCancel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext)
                .addContainerGap())
        );
        pnlControlLayout.setVerticalGroup(
            pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlLayout.createSequentialGroup()
                .addGroup(pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNext)
                    .addComponent(btnCancel)
                    .addComponent(btnBack))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabMain)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon)
                .addContainerGap())
            .addComponent(pnlControl, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabMain)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if ((tabMain.getSelectedIndex() + 1) < tabMain.getComponentCount()) {
            int abc = enmStatus.getIndex(enmStatus.Database);
            if (tabMain.getSelectedIndex() == enmStatus.getIndex(enmStatus.Database)) {
                if (checkOfFieldsOk()) {
                    if (saveOfFieldsOk()) {
                        tabMain.setSelectedIndex(tabMain.getSelectedIndex() + 1);
                    }
                }
            } else {
                tabMain.setSelectedIndex(tabMain.getSelectedIndex() + 1);
            }
        } else {
            this.setVisible(false);
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        if (tabMain.getSelectedIndex() != 0) {
            tabMain.setSelectedIndex(tabMain.getSelectedIndex() - 1);
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void pnlWelcomeComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnlWelcomeComponentShown
        setStatus(enmStatus.Welcome);
    }//GEN-LAST:event_pnlWelcomeComponentShown

    private void pnlDatabaseComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnlDatabaseComponentShown
        setStatus(enmStatus.Database);
    }//GEN-LAST:event_pnlDatabaseComponentShown

    private void pnlFinishComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnlFinishComponentShown
        setStatus(enmStatus.Finish);
    }//GEN-LAST:event_pnlFinishComponentShown

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing
    
    private Boolean checkOfFieldsOk() {
        if ("".equals(txtDatabaseSettingName.getText())) {
            txtDatabaseSettingName.setForeground(Color.red);
            return false;
        } else {
            txtDatabaseSettingName.setBackground(Color.white);
        }
        
        if ("".equals(txtDatabaseAddressingLocation.getText())) {
            txtDatabaseAddressingLocation.setBackground(Color.red);
            return false;
        } else {
            txtDatabaseAddressingLocation.setBackground(Color.white);
        }
        
        if ("".equals(txtDatabaseAddressingPort.getText())) {
            txtDatabaseAddressingPort.setBackground(Color.red);
            return false;
        } else {
            txtDatabaseAddressingPort.setBackground(Color.white);
        }
        
        if ("".equals(txtDatabaseNamingDatabaseName.getText())) {
            txtDatabaseNamingDatabaseName.setBackground(Color.red);
            return false;
        } else {
            txtDatabaseNamingDatabaseName.setBackground(Color.white);
        }
        
        if ("".equals(txtDatabaseNamingTablePrefix.getText())) {
            txtDatabaseNamingTablePrefix.setBackground(Color.red);
            return false;
        } else {
            txtDatabaseNamingTablePrefix.setBackground(Color.white);
        }
        
        return true;
    }
    
    private Boolean saveOfFieldsOk() {
        try {
            this.pMainClass.getProgram().getConfiguration().setDatabaseSettingName(0, txtDatabaseSettingName.getText());
            this.pMainClass.getProgram().getConfiguration().setDatabaseType(0, String.valueOf(cboDatabaseAddressingType.getItemAt(cboDatabaseAddressingType.getSelectedIndex())));
            this.pMainClass.getProgram().getConfiguration().setDatabaseLocation(0, txtDatabaseAddressingLocation.getText());
            this.pMainClass.getProgram().getConfiguration().setDatabasePort(0, txtDatabaseAddressingPort.getText());
            this.pMainClass.getProgram().getConfiguration().setDatabaseName(0, txtDatabaseNamingDatabaseName.getText());
            this.pMainClass.getProgram().getConfiguration().setDatabasePrefix(0, txtDatabaseNamingTablePrefix.getText());
            if (!"".equals(txtDatabaseAuthenticationUsername.getText())) {
                this.pMainClass.getProgram().getConfiguration().setDatabaseUsername(0, txtDatabaseAuthenticationUsername.getText());
            }
            if (txtDatabaseAuthenticationPassword.getPassword().length > 0) {
                this.pMainClass.getProgram().getConfiguration().setDatabasePassword(0, convertFromCharArrayToString(txtDatabaseAuthenticationPassword.getPassword()));
            }

            this.pMainClass.getProgram().getConfiguration().saveDatabaseSettings(0);
            
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    private String convertFromCharArrayToString(char[] aCharArray) {
        String returnVal = "";
        for (int i=0; i<aCharArray.length; i++) {
            returnVal += String.valueOf(aCharArray[i]);
        }
        return returnVal;
    }
        
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(dlgConfiguration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dlgConfiguration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dlgConfiguration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dlgConfiguration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                dlgConfiguration dialog = new dlgConfiguration(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnNext;
    private javax.swing.JComboBox cboDatabaseAddressingType;
    private javax.swing.JLabel lblDatabaseAddressingLocation;
    private javax.swing.JLabel lblDatabaseAddressingPort;
    private javax.swing.JLabel lblDatabaseAddressingType;
    private javax.swing.JLabel lblDatabaseAuthenticationPassword;
    private javax.swing.JLabel lblDatabaseAuthenticationUsername;
    private javax.swing.JLabel lblDatabaseNamingDatabaseName;
    private javax.swing.JLabel lblDatabaseNamingTablePrefix;
    private javax.swing.JLabel lblDatabaseSettingName;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JPanel pnlAddressing;
    private javax.swing.JPanel pnlAuthentication;
    private javax.swing.JPanel pnlControl;
    private javax.swing.JPanel pnlDatabase;
    private javax.swing.JPanel pnlFinish;
    private javax.swing.JPanel pnlNaming;
    private javax.swing.JPanel pnlSetting;
    private javax.swing.JPanel pnlWelcome;
    private javax.swing.JScrollPane scrFinish;
    private javax.swing.JScrollPane scrWelcome;
    private javax.swing.JTabbedPane tabMain;
    private javax.swing.JTextField txtDatabaseAddressingLocation;
    private javax.swing.JTextField txtDatabaseAddressingPort;
    private javax.swing.JPasswordField txtDatabaseAuthenticationPassword;
    private javax.swing.JTextField txtDatabaseAuthenticationUsername;
    private javax.swing.JTextField txtDatabaseNamingDatabaseName;
    private javax.swing.JTextField txtDatabaseNamingTablePrefix;
    private javax.swing.JTextField txtDatabaseSettingName;
    private javax.swing.JTextArea txtFinish;
    private javax.swing.JTextArea txtWelcome;
    // End of variables declaration//GEN-END:variables
}
