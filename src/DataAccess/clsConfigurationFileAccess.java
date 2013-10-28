package DataAccess;

import java.io.*;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 *
 * @author jean
 */
public class clsConfigurationFileAccess {
	private clsFileAccess fa;
	
	/**
	 * Manipulating the configuration file.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 */
	public clsConfigurationFileAccess() {
            this.fa = new clsFileAccess();
	}
	
	/**
	 * Loads the complete configuration file into a Properties object.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @return Returns the Properties object.
	 * @throws IOException when the Properties.loadFromXML(...) method fails.
	 * @throws InvalidPropertiesFormatException when the Properties.loadFromXML(...) method could not properly read the XML file entries.
	 */
	private Properties getConfigurationTotal() throws IOException, InvalidPropertiesFormatException {
            //Define the properties.
            Properties p = null;

            try {
                    //Create the input file stream.
                    FileInputStream fis = new FileInputStream(getConfigurationFileName());
                    //Create the Properties.
                    p = new Properties();
                    //Load XML
                    p.loadFromXML(fis);
                    //Close the input file stream.
                    fis.close();
            } catch (FileNotFoundException e) {
                    p = new Properties();
            }

            return p;
	}
	
	/**
	 * Sets the configuration of on value tu a key.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @param String k The key for the value.
	 * @param String v The value for the key.
	 * @param boolean keepconfigfile True when the existing config file shall be kept, False when it may be overwritten.
	 * @return Returns the state of success.
	 * @throws IOException when the Properties.storeToXML(...) method fails.
	 */
	public boolean setConfiguration(String k, String v, boolean keepconfigfile) throws IOException {
            //Define the properties.
            Properties p = null;

            if(keepconfigfile) {
                    p = getConfigurationTotal();
            } else {
                    p = new Properties();
            }

            //Create the output file stream.
            FileOutputStream fos = new FileOutputStream(getConfigurationFileName());
            //Set the Property
            p.setProperty(k, v);
            //Create the configuration XML.
            p.storeToXML(fos, "Configuration");
            //Close the file stream.
            fos.close();

            return true;
	}
	
	/**
	 * Gets the configuration for a key.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @param String k The key for the value.
	 * @return Returns the value for the key.
	 * @throws IOException when the Properties.loadFromXML(...) method fails.
	 * @throws InvalidPropertiesFormatException when the Properties.loadFromXML(...) method could not properly read the XML file entries.
	 * @throws FileNotFoundException when the FileInputStream could not find the configuration file.
	 */
	public String getConfiguration(String k) throws IOException, InvalidPropertiesFormatException, FileNotFoundException {
            //Set the file name.
            String configfilename = getConfigurationFileName();
            //Create the Properties.
            Properties p = new Properties();
            //Create the file stream.
            FileInputStream fis = new FileInputStream(configfilename);
            //Load XML
            p.loadFromXML(fis);
            //Set the return value
            String returnval = p.getProperty(k);
            //Close the file stream.
            fis.close();

            return returnval;
	}
	
	/**
	 * Deletes the configuration file.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @return Returns the state of success.
	 */
	public boolean deleteConfigurationFile() {
            //Define the file.
            File f = new File(getConfigurationFileName());
            //Delete the file.
            return this.fa.deleteFile(f);
	}
	
	/**
	 * Creates the config file name using the program name and adding .config to the end. 
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @return Returns the config file name.
	 */
	public String getConfigurationFileName() {
            //Set the file name.
            return System.getProperty("program.name") + ".config";
	}
        
        public Boolean ConfigurationFileExists() {
            //Define the file.
            File f = new File(getConfigurationFileName());
            //Return the status of existance.
            return this.fa.isFileExistent(f);
        }
}
