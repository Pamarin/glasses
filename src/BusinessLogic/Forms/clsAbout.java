package BusinessLogic.Forms;

import BusinessLogic.clsProgram;
import DataAccess.clsFileAccess;
import java.io.File;
import javax.swing.Icon;

/**
 *
 * @author jean
 */
public class clsAbout {
    private clsProgram pProgram;
    private clsFileAccess pFileAccess;

    /**
        * The business logic for the About form.
        * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
        * @since 1
        */
    public clsAbout(clsProgram aProgram) {
        this.pProgram = aProgram;
        this.pFileAccess = new clsFileAccess();
    }

    /**
        * Gets the logo of the program.
        * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
        * @since 1
        * @return The logo.
        */
    public Icon getLogo() {
            //Create the path.
            File mPath = new File(System.getProperties().getProperty("user.dir") + System.getProperty("file.separator") + "images" + System.getProperty("file.separator") + "logo.png");
            //Get the logo.
            return this.pFileAccess.getIcon(mPath);
    }

    public String getProgramName() {
        return this.pProgram.ProgramName;
    }

    public int getProgramVersion() {
        return this.pProgram.ProgramVersion;
    }

    public String getDatabaseType() {
        return this.pProgram.getConfiguration().getDatabaseType();
    }
    
    public int getDatabaseVersion() {
        return this.pProgram.getProgramDatabaseVersion();
    }
}
