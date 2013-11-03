package DataAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ImageIcon;

/**
 *
 * @author jean
 */
public class clsFileAccess {
	/**
	 * Options that are available for the choice between files and/or directories.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @since 0.0.0.8
	 */
	private enum enumFileAndDirectory {
		Both,
		Directory,
		File
	}
	
	/**
	 * Searches for a given file and directory at a given location.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @since 0.0.0.8
	 * @param path The path of the file or directory.
	 * @param regexname The name including regex capabilities.
	 * @param recursive The option whether the path shall be searched recursively.
	 * @param faf The type of file to look for.
	 * @return Returns an ArrayList<File> of files matching the options.
	 */
	private ArrayList<File> find(File path, String regexname, boolean recursive, enumFileAndDirectory faf) {
		//New instance of ArrayList for the matches.
		ArrayList<File> matches = new ArrayList<>();
		//Get all files and directories from the search directory.
		File[] files = path.listFiles();
		//Preset for files and directories to be included in the matches.
		boolean withFiles = false;
		boolean withDirectories = false;
		switch(faf) {
			case Both:
				withFiles = true;
				withDirectories = true;
				break;
			case File:
				withFiles = true;
				break;
			case Directory:
				withDirectories = true;
				break;
		}
			
		//If files are found, then check them for matches.
		if(files != null) {
			//Go through all of the files.
			for(int i=0; i<files.length; i++) {
				
				//Check for matches, ignoring lower and upper case characters.
				if(files[i].getName().matches(regexname)) {
					//Add the matching to the ArrayList.
					if((withFiles && files[i].isFile()) || (withDirectories && files[i].isDirectory())) {
						matches.add(files[i]);
					}
				}
					
				//If recursive is set to true, continue same method in subdirectory.
				if(recursive && files[i].isDirectory()) {
					//Add all matches in this directory to the ArrayList of matches.
					matches.addAll(this.find(files[i], regexname, recursive, faf));
				}
			}
		}
			
		return matches;
	}

	/**
	 * Options that are available for a text to be written into a file.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @since 0.0.0.8
	 */
	public enum enmTextAppendMode {
		Bottom,
		Replace,
		Top
	}
	
	/**
	 * Checks for the write ability of a directory.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @since 0.0.0.8
	 * @param addr Requires an address to be checked.
	 * @return Returns whether a directory could be found.
	 */
	public boolean isDirectoryWritable(File addr) {
		//Default variable for the return value.
		boolean returnval = false;
		
		if(addr.exists()) {
			if(addr.isDirectory() && addr.canWrite()) {
				returnval = true;
			}
		}
		
		return returnval;
	}

	/**
	 * Checks for the write ability of a file.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @since 0.0.0.8
	 * @param addr Requires an address to be checked.
	 * @return Returns whether a file was writable.
	 */
	public boolean isFileWritable(File addr) {
		//Default variable for the return value.
		boolean returnval = false;
		
		if(addr.exists()) {
			if(addr.isFile() && addr.canWrite()) {
				returnval = true;
			}
		}
		
		return returnval;
	}
	
	/**
	 * Searches for a given file at a given location.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @since 0.0.0.8
	 * @param filename The name of the file.
	 * @param path The path to search in.
	 * @param recursive The option whether the path shall be searched recursively.
	 * @return Returns an ArrayList<File> of files matching the options.
	 */
	public ArrayList<File> findFiles(File path, String filename, boolean recursive) {
		return find(path, filename, recursive, enumFileAndDirectory.File);
	}
	
	/**
	 * Searches for a given file at a given location.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
         * @param directoryname
	 * @since 0.0.0.8
	 * @param path The path to search in.
	 * @param recursive The option whether the path shall be searched recursively.
	 * @return Returns an ArrayList<File> of files matching the options.
	 */
	public ArrayList<File> findDirectories(File path, String directoryname, boolean recursive) {
		return find(path, directoryname, recursive, enumFileAndDirectory.Directory);
	}
	
	/**
	 * Searches for a given file or directory at a given location.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @since 0.0.0.8
	 * @param fdname The name of the file or directory.
	 * @param path The path to search in.
	 * @param recursive The option whether the path shall be searched recursively.
	 * @return Returns an ArrayList<File> of files matching the options.
	 */
	public ArrayList<File> findFilesAndDirectories(File path, String fdname, boolean recursive) {
		return find(path, fdname, recursive, enumFileAndDirectory.Both);
	}
	
	/**
	 * Creates a directory.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @since 0.0.0.8
	 * @param d The name and address of the directory.
	 * @return Returns the creation success.
	 * @throws SecurityException when there is a problem with the directory creation.
	 */
	public boolean createDirectory(File d) throws SecurityException {
		//Default variable for the return value.
		boolean returnval = false;
		
		//Check if file or directory exists and is writable.
		if(this.isDirectoryWritable(d)) {
			//Create the new directory.
			returnval = d.mkdir();
		}
		
		return returnval;
	}
	
	/**
	 * Creates a file.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @since 0.0.0.8
	 * @param f The name and address of the file.
	 * @return Returns the creation success.
	 * @throws SecurityException when there is a problem with the file creation.
	 */
	public boolean createFile(File f) throws SecurityException {
		//Default variable for the return value.
		boolean returnval = false;
		
		try {
			//Check if file exists and is writable.
			if(this.isFileWritable(f)) {
				//Create the new file.
				returnval = f.createNewFile();
			}
		} catch (IOException e) {
			returnval = false;
		}
		
		return returnval;
	}
	
	/**
	 * Deletes a directory optionally recursive.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @since 0.0.0.8
	 * @param d Name and path of the directory.
	 * @param recursive The option whether to delete all directories and files contained in the given folder.
	 * @return Returns the state of success.
	 */
	public boolean deleteDirectory(File d, boolean recursive) {
		//Default variable for the return value.
		boolean returnval = false;
		
		try {
			//Check if is directory.
			if(d.isDirectory()) {
				//Delete all subdirectories and subfiles when the recursive option is set.
				if(recursive) {
					//Get all files and directories in the given directory.
					ArrayList<File> filesanddirectories = this.find(d, "", true, enumFileAndDirectory.Both);
					//Delete file and every directory.
					for(File f: filesanddirectories) {
						f.delete();
					}
				}
				
				//Delete the main directory.
				returnval = d.delete();
			}
		} catch (SecurityException e) {
			returnval = false;
		}
		
		return returnval;
	}
	
	/**
	 * Deletes a file.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @since 0.0.0.8
	 * @param f The name and address of the file.
	 * @return Returns the deletion success.
	 */
	public boolean deleteFile(File f) {
		//Default variable for the return value.
		boolean returnval = false;
		
		try {
			//Delete the file.
			returnval = f.delete();
		} catch (SecurityException e) {
			returnval = false;
		}
		
		return returnval;
	}
	
	/**
	 * Reads a given file.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @since 0.0.0.8
	 * @param addr The address of the file.
	 * @return Returns the total content of the file.
	 * @throws FileNotFoundException when the file could not be found. 
	 * @throws SecurityException when there were problems connecting to the file.
	 * @throws IllegalArgumentException when the Scanner object could not build up its instance.
	 */
	public String readFileText(File addr) throws FileNotFoundException, SecurityException, IllegalArgumentException {
		//Create a text constructor.
		StringBuilder text = new StringBuilder();
		//Create a read-only access to a file.
		Scanner scanner = new Scanner(new FileInputStream(addr.getAbsoluteFile()), "UTF-8");
		
		//Go through each line.
		while (scanner.hasNextLine()) {
			//Append each line to the StringBuilder object including a line separator.
			text.append(scanner.nextLine() + System.getProperty("line.separator"));
		}
		
		if(scanner != null) {
			//In any case close the file access.
			scanner.close();	
		}
		
		return text.toString();
	}
	
	/**
	 * Writes text into a given file.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @since 0.0.0.8
	 * @param addr The address of the file.
	 * @param content The text to be written into the file.
	 * @param assure_file_existance Makes sure that the file is created if it is not available.
	 * @param appendmode Sets the inserted text to the top or the bottom of the file or replaces it completely.
	 * @return Returns the state of success.
	 * @throws UnsupportedEncodingException when the preset UTF-8 format could not be used.
	 * @throws FileNotFoundException when the file could not be found.
	 * @throws SecurityException when the file does not allow writing.
	 */
	public boolean writeFileText(File addr, String content, boolean assure_file_existance, enmTextAppendMode appendmode) throws FileNotFoundException, SecurityException, UnsupportedEncodingException {
		//Default variable for the return value.
		boolean returnval = false;
		//Variable for the modified content.
		String modifiedcontent = "";
		
		//Set ready the file access for its manipulation.
		Writer out = new OutputStreamWriter(new FileOutputStream(addr.getAbsoluteFile()), "UTF-8");
		try {
			//Create file if wanted.
			if(assure_file_existance) {
				//Check if file exists and is writable.
				if(this.isFileWritable(addr) == false) {
					//Create the file.
					if(this.createFile(addr)) {
						//Successful file creation.
						returnval = true;
					}
				}
			}
			
			//Put the inserted text to the top or the bottom of the file or replace it completely.
			switch (appendmode) {
			case Bottom:
				modifiedcontent = this.readFileText(addr) + System.getProperty("line.separator") + content;
				break;
			case Replace:
				modifiedcontent = content;
				break;
			case Top:
				modifiedcontent = content + System.getProperty("line.separator") + this.readFileText(addr);
				break;
			}
			
			//Write the content into the file.
			out.write(modifiedcontent);
			//Set the default variable for the correct response.
			returnval = true;
			
		} catch (IOException e) {
			returnval = false;
		} finally {
			if(out != null) {
				try {
					//In any case close the file access
					out.close();
				} catch (IOException e) {
					returnval = true;
				}
			}
		}
		
		return returnval;
	}

	/**
	 * Checks if file exists.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @since 0.0.0.8
	 * @param addr The address to be checked.
	 * @return Returns whether a file exists.
	 */
	public boolean isFileExistent(File addr) {
		//Default variable for the return value.
		boolean returnval = false;
		
		if(addr.exists()) {
			if(addr.isFile()) {
				returnval = true;
			}
		}
		
		return returnval;
	}

	/**
	 * Checks if directory exists.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @since 0.0.0.8
	 * @param addr The address to be checked.
	 * @return Returns whether a directory exists.
	 */
	public boolean isDirectoryExistent(File addr) {
		//Default variable for the return value.
		boolean returnval = false;
		
		if(addr.exists()) {
			if(addr.isDirectory()) {
				returnval = true;
			}
		}
		
		return returnval;
	}
	
	/**
	 * Gets a picture.
	 * @author Jean-Luc Burot (http://www.audienz.eu/en/home/contact)
	 * @since 0.0.0.8
	 * @param addr The address to the picture.
	 * @return Returns the picture.
	 */
	public ImageIcon getIcon(File addr) {
		ImageIcon mImageIcon = null;
		
		if(isFileExistent(addr)) {
			mImageIcon = new ImageIcon(addr.getAbsolutePath());
		}
		
		return mImageIcon;
	}
}
