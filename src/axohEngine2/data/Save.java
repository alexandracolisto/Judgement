/**
 * Saves the data to a text file
 * @author Travis R. Dewitt; edited by Team A2
 */
//Packages
package axohEngine2.data;

//Imports
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Save {
	//instance variables
	private transient PrintWriter writer;
	private File newfile; 
	
	/********************************************************
	 * Save the Game state
	 * 
	 * @param fileName - A string name of a file
	 * @param data - 'Data.java' class object that holds the variables
	 *********************************************************/
	public void saveState(String current ,int mainHealth, 
			String mapName, String mapOverlay, String direction, int level) {
		try{
    		//to output the data in a text file
    		//PrintWriter on top of a FileWriter
			PrintWriter out = new PrintWriter(new FileWriter("C:/gamedata/saves/" + current));
			out.print(mainHealth + " " + mapName + " " + mapOverlay + " " + direction + " " + level);	    	
			out.close();//close the file
    	} catch(IOException e){
    		e.printStackTrace();
		}
	}
	
	/********************************************************
	 * Create a new file in the system directory
	 * 
	 * @param file - A String
	 ********************************************************/
	public void newFile(String file) {
		newfile = new File("C:/gamedata/saves/" + file);
		newfile.getParentFile().mkdirs();
		
		try {
			writer = new PrintWriter(newfile);
		} catch (Exception e) {
			System.err.println("Unable to make new file...");
		}
		writer.close();
	}	
}