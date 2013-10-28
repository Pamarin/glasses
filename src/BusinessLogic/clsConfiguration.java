/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic;

import BusinessLogic.Forms.Special.clsIDEntry;
import BusinessLogic.Forms.clsSettings;
import DataAccess.clsConfigurationFileAccess;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jean
 */
public class clsConfiguration {
    private clsConfigurationFileAccess pConfigurationFile;
    private Integer pActiveId;
    private ArrayList<clsIDEntry> pDatabaseSettingName;
    private ArrayList<clsIDEntry> pDatabaseType;
    private ArrayList<clsIDEntry> pDatabaseLocation;
    private ArrayList<clsIDEntry> pDatabasePort;
    private ArrayList<clsIDEntry> pDatabaseName;
    private ArrayList<clsIDEntry> pDatabasePrefix;
    private ArrayList<clsIDEntry> pDatabaseUsername;
    private ArrayList<clsIDEntry> pDatabasePassword;
    
    public clsConfiguration() {
        this.pConfigurationFile = new clsConfigurationFileAccess();
        this.pActiveId = 0;
    }
    
    public boolean ConfigurationFileExists() {
        return this.pConfigurationFile.ConfigurationFileExists();
    }
    
    public boolean loadDatabaseSettings() {
        try {
            Integer databaseEntryId = 0;
            Boolean loadComplete = false;
            while (loadComplete == false) {
                if (this.pConfigurationFile.getConfiguration(databaseEntryId + "#DatabaseType") == null) {
                    loadComplete = true;
                } else {
                    clsIDEntry entryDatabaseType = new clsIDEntry();
                    entryDatabaseType.setID(databaseEntryId);
                    entryDatabaseType.setValue(this.pConfigurationFile.getConfiguration(databaseEntryId + "#DatabaseType"));
                    if (this.pDatabaseType == null) {
                        this.pDatabaseType = new ArrayList<>();
                    }
                    this.pDatabaseType.add(entryDatabaseType);
                    
                    clsIDEntry entryDatabaseLocation = new clsIDEntry();
                    entryDatabaseLocation.setID(databaseEntryId);
                    entryDatabaseLocation.setValue(this.pConfigurationFile.getConfiguration(databaseEntryId + "#DatabaseLocation"));
                    if (this.pDatabaseLocation == null) {
                        this.pDatabaseLocation = new ArrayList<>();
                    }
                    this.pDatabaseLocation.add(entryDatabaseLocation);

                    clsIDEntry entryDatabasePort = new clsIDEntry();
                    entryDatabasePort.setID(databaseEntryId);
                    entryDatabasePort.setValue(this.pConfigurationFile.getConfiguration(databaseEntryId + "#DatabasePort"));
                    if (this.pDatabasePort == null) {
                        this.pDatabasePort = new ArrayList<>();
                    }
                    this.pDatabasePort.add(entryDatabasePort);

                    clsIDEntry entryDatabaseName = new clsIDEntry();
                    entryDatabaseName.setID(databaseEntryId);
                    entryDatabaseName.setValue(this.pConfigurationFile.getConfiguration(databaseEntryId + "#DatabaseName"));
                    if (this.pDatabaseName == null) {
                        this.pDatabaseName = new ArrayList<>();
                    }
                    this.pDatabaseName.add(entryDatabaseName);

                    clsIDEntry entryDatabasePrefix = new clsIDEntry();
                    entryDatabasePrefix.setID(databaseEntryId);
                    entryDatabasePrefix.setValue(this.pConfigurationFile.getConfiguration(databaseEntryId + "#DatabasePrefix"));
                    if (this.pDatabasePrefix == null) {
                        this.pDatabasePrefix = new ArrayList<>();
                    }
                    this.pDatabasePrefix.add(entryDatabasePrefix);

                    clsIDEntry entryDatabaseUsername = new clsIDEntry();
                    entryDatabaseUsername.setID(databaseEntryId);
                    entryDatabaseUsername.setValue(this.pConfigurationFile.getConfiguration(databaseEntryId + "#DatabaseUsername"));
                    if (this.pDatabaseUsername == null) {
                        this.pDatabaseUsername = new ArrayList<>();
                    }
                    this.pDatabaseUsername.add(entryDatabaseUsername);

                    clsIDEntry entryDatabasePassword = new clsIDEntry();
                    entryDatabasePassword.setID(databaseEntryId);
                    entryDatabasePassword.setValue(this.pConfigurationFile.getConfiguration(databaseEntryId + "#DatabasePassword"));
                    if (this.pDatabasePassword == null) {
                        this.pDatabasePassword = new ArrayList<>();
                    }
                    this.pDatabasePassword.add(entryDatabasePassword);

                    databaseEntryId++;
                }
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    public boolean saveDatabaseSettings(int aID) {
        try {
            this.pConfigurationFile.setConfiguration(aID + "#DatabaseType", getDatabaseType(aID), true);
            this.pConfigurationFile.setConfiguration(aID + "#DatabaseLocation", getDatabaseLocation(aID), true);
            this.pConfigurationFile.setConfiguration(aID + "#DatabasePort", getDatabasePort(aID), true);
            this.pConfigurationFile.setConfiguration(aID + "#DatabaseName", getDatabaseName(aID), true);
            this.pConfigurationFile.setConfiguration(aID + "#DatabasePrefix", getDatabasePrefix(aID), true);
            this.pConfigurationFile.setConfiguration(aID + "#DatabaseUsername", getDatabaseUsername(aID), true);
            this.pConfigurationFile.setConfiguration(aID + "#DatabasePassword", getDatabasePassword(aID), true);
            
            return true;
        } catch (IOException ex) {
            Logger.getLogger(clsSettings.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public ArrayList<Integer> getDatabaseIdList() {
        ArrayList<Integer> idList = new ArrayList<>();
        for (int i=0; i<this.pDatabaseName.size(); i++) {
            clsIDEntry entry = this.pDatabaseName.get(i);
            idList.add(entry.getID());
        }
        return idList;
    }
    
    public String getDatabaseSettingName(int aID) {
        if (this.pDatabaseSettingName != null) {
            for (int i=0; i<this.pDatabaseSettingName.size(); i++) {
                clsIDEntry entry = this.pDatabaseSettingName.get(i);
                if (entry.getID() == aID) {
                    return entry.getValue().toString();
                }
            }
            
            return "";
        } else {
            return "";
        }
    }
    
    public String getDatabaseSettingName() {
        return getDatabaseSettingName(this.pActiveId);
    }
    
    public String getDatabaseType(int aID) {
        if (this.pDatabaseType != null) {
            for (int i=0; i<this.pDatabaseType.size(); i++) {
                clsIDEntry entry = this.pDatabaseType.get(i);
                if (entry.getID() == aID) {
                    return entry.getValue().toString();
                }
            }
            
            return "";
        } else {
            return "";
        }
    }
    
    public String getDatabaseType() {
        return getDatabaseType(this.pActiveId);
    }
    
    public String getDatabaseLocation(int aID) {
        if (this.pDatabaseLocation != null) {
            for (int i=0; i<this.pDatabaseLocation.size(); i++) {
                clsIDEntry entry = this.pDatabaseLocation.get(i);
                if (entry.getID() == aID) {
                    return entry.getValue().toString();
                }
            }
            
            return "";
        } else {
            return "";
        }
    }
    
    public String getDatabaseLocation() {
        return getDatabaseLocation(this.pActiveId);
    }
    
    public String getDatabasePort(int aID) {
        if (this.pDatabasePort != null) {
            for (int i=0; i<this.pDatabasePort.size(); i++) {
                clsIDEntry entry = this.pDatabasePort.get(i);
                if (entry.getID() == aID) {
                    return entry.getValue().toString();
                }
            }
            
            return "";
        } else {
            return "";
        }
    }
    
    public String getDatabasePort() {
        return getDatabasePort(this.pActiveId);
    }
    
    public String getDatabaseName(int aID) {
        if (this.pDatabaseName != null) {
            for (int i=0; i<this.pDatabaseName.size(); i++) {
                clsIDEntry entry = this.pDatabaseName.get(i);
                if (entry.getID() == aID) {
                    return entry.getValue().toString();
                }
            }
            
            return "";
        } else {
            return "";
        }
    }
    
    public String getDatabaseName() {
        return getDatabaseName(this.pActiveId);
    }
    
    public String getDatabasePrefix(int aID) {
        if (this.pDatabasePrefix != null) {
            for (int i=0; i<this.pDatabasePrefix.size(); i++) {
                clsIDEntry entry = this.pDatabasePrefix.get(i);
                if (entry.getID() == aID) {
                    return entry.getValue().toString();
                }
            }
            
            return "";
        } else {
            return "";
        }
    }
    
    public String getDatabasePrefix() {
        return getDatabasePrefix(this.pActiveId);
    }
    
    public String getDatabaseUsername(int aID) {
        if (this.pDatabaseUsername != null) {
            for (int i=0; i<this.pDatabaseUsername.size(); i++) {
                clsIDEntry entry = this.pDatabaseUsername.get(i);
                if (entry.getID() == aID) {
                    return entry.getValue().toString();
                }
            }
            
            return "";
        } else {
            return "";
        }
    }
    
    public String getDatabaseUsername() {
        return getDatabaseUsername(this.pActiveId);
    }
    
    public String getDatabasePassword(int aID) {
        if (this.pDatabasePassword != null) {
            for (int i=0; i<this.pDatabasePassword.size(); i++) {
                clsIDEntry entry = this.pDatabasePassword.get(i);
                if (entry.getID() == aID) {
                    return entry.getValue().toString();
                }
            }
            
            return "";
        } else {
            return "";
        }
    }
    
    public String getDatabasePassword() {
        return getDatabasePassword(this.pActiveId);
    }
    
    public String getConfigurationFileLocation() {
        return System.getProperty("user.dir") + File.separator + this.pConfigurationFile.getConfigurationFileName();
    }
    
    private Integer idInList(Integer aID, ArrayList<clsIDEntry> aList) {
        for (int i=0; i<aList.size(); i++) {
            clsIDEntry entry = aList.get(i);
            if (entry.getID() == aID) {
                return i;
            }
        }
        
        return -1;
    }
    
    public void setActiveId(int aID) {
        this.pActiveId = aID;
    }
    
    public void setDatabaseSettingName(int aID, String aDatabaseSettingName) {
        //Create entry.
        clsIDEntry entry = new clsIDEntry();
        entry.setID(aID);
        entry.setValue(aDatabaseSettingName);
        
        //Get index of ID if exists.
        Integer indexOfList = -1;
        if (this.pDatabaseSettingName != null) {
            indexOfList = idInList(aID, this.pDatabaseSettingName);
        } else {
            this.pDatabaseSettingName = new ArrayList<>();
        }
        
        if (indexOfList < 0) {
            //New entry in list.
            this.pDatabaseSettingName.add(entry);
        } else {
            //Overwrite existing entry in list.
            this.pDatabaseSettingName.set(indexOfList, entry);
        }
    }
    
    public void setDatabaseType(int aID, String aDatabaseType) {
        //Create entry.
        clsIDEntry entry = new clsIDEntry();
        entry.setID(aID);
        entry.setValue(aDatabaseType);
        
        //Get index of ID if exists.
        Integer indexOfList = -1;
        if (this.pDatabaseType != null) {
            indexOfList = idInList(aID, this.pDatabaseType);
        } else {
            this.pDatabaseType = new ArrayList<>();
        }
        
        if (indexOfList < 0) {
            //New entry in list.
            this.pDatabaseType.add(entry);
        } else {
            //Overwrite existing entry in list.
            this.pDatabaseType.set(indexOfList, entry);
        }
    }
    
    public void setDatabaseLocation(int aID, String aDatabaseLocation) {
        //Create entry.
        clsIDEntry entry = new clsIDEntry();
        entry.setID(aID);
        entry.setValue(aDatabaseLocation);
        
        //Get index of ID if exists.
        Integer indexOfList = -1;
        if (this.pDatabaseLocation != null) {
            indexOfList = idInList(aID, this.pDatabaseLocation);
        } else {
            this.pDatabaseLocation = new ArrayList<>();
        }
        
        if (indexOfList < 0) {
            //New entry in list.
            this.pDatabaseLocation.add(entry);
        } else {
            //Overwrite existing entry in list.
            this.pDatabaseLocation.set(indexOfList, entry);
        }
    }
    
    public void setDatabasePort(int aID, String aDatabasePort) {
        //Create entry.
        clsIDEntry entry = new clsIDEntry();
        entry.setID(aID);
        entry.setValue(aDatabasePort);
        
        //Get index of ID if exists.
        Integer indexOfList = -1;
        if (this.pDatabasePort != null) {
            indexOfList = idInList(aID, this.pDatabasePort);
        } else {
            this.pDatabasePort = new ArrayList<>();
        }
        
        if (indexOfList < 0) {
            //New entry in list.
            this.pDatabasePort.add(entry);
        } else {
            //Overwrite existing entry in list.
            this.pDatabasePort.set(indexOfList, entry);
        }
    }
    
    public void setDatabaseName(int aID, String aDatabaseName) {
        //Create entry.
        clsIDEntry entry = new clsIDEntry();
        entry.setID(aID);
        entry.setValue(aDatabaseName);
        
        //Get index of ID if exists.
        Integer indexOfList = -1;
        if (this.pDatabaseName != null) {
            indexOfList = idInList(aID, this.pDatabaseName);
        } else {
            this.pDatabaseName = new ArrayList<>();
        }
        
        if (indexOfList < 0) {
            //New entry in list.
            this.pDatabaseName.add(entry);
        } else {
            //Overwrite existing entry in list.
            this.pDatabaseName.set(indexOfList, entry);
        }
    }
    
    public void setDatabasePrefix(int aID, String aDatabasePrefix) {
        //Create entry.
        clsIDEntry entry = new clsIDEntry();
        entry.setID(aID);
        entry.setValue(aDatabasePrefix);
        
        //Get index of ID if exists.
        Integer indexOfList = -1;
        if (this.pDatabasePrefix != null) {
            indexOfList = idInList(aID, this.pDatabasePrefix);
        } else {
            this.pDatabasePrefix = new ArrayList<>();
        }
        
        if (indexOfList < 0) {
            //New entry in list.
            this.pDatabasePrefix.add(entry);
        } else {
            //Overwrite existing entry in list.
            this.pDatabasePrefix.set(indexOfList, entry);
        }
    }
    
    public void setDatabaseUsername(int aID, String aDatabaseUsername) {
        //Create entry.
        clsIDEntry entry = new clsIDEntry();
        entry.setID(aID);
        entry.setValue(aDatabaseUsername);
        
        //Get index of ID if exists.
        Integer indexOfList = -1;
        if (this.pDatabaseUsername != null) {
            indexOfList = idInList(aID, this.pDatabaseUsername);
        } else {
            this.pDatabaseUsername = new ArrayList<>();
        }
        
        if (indexOfList < 0) {
            //New entry in list.
            this.pDatabaseUsername.add(entry);
        } else {
            //Overwrite existing entry in list.
            this.pDatabaseUsername.set(indexOfList, entry);
        }
    }
    
    public void setDatabasePassword(int aID, String aDatabasePassword) {
        //Create entry.
        clsIDEntry entry = new clsIDEntry();
        entry.setID(aID);
        entry.setValue(aDatabasePassword);
        
        //Get index of ID if exists.
        Integer indexOfList = -1;
        if (this.pDatabasePassword != null) {
            indexOfList = idInList(aID, this.pDatabasePassword);
        } else {
            this.pDatabasePassword = new ArrayList<>();
        }
        
        if (indexOfList < 0) {
            //New entry in list.
            this.pDatabasePassword.add(entry);
        } else {
            //Overwrite existing entry in list.
            this.pDatabasePassword.set(indexOfList, entry);
        }
    }
}
