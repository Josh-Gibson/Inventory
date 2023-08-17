
/*
	Simple class that manages creating and 
	loading database files.
*/


import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileTools{
	
	public static String dbPath = "";
	
	//Create new database file
	public static void createDBFile(String filename){
		
		try {
			File f = new File("./db/" + filename + ".db");
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Check if any database files exist
	public static boolean databaseExists(){
		
		File folder = new File("./db");
		File[] list = folder.listFiles();
		if(list.length > 0){
			return true;
		}
		return false;
	}
	//sets current db path
	public static void setDBPath(){
		
		JFileChooser choose = new JFileChooser();
		choose.setCurrentDirectory(new File("./db"));
		int returnVal = choose.showOpenDialog(null);
		
		//if no file selected, don't do anything
		if(returnVal != 0){
			return;
		}
		
		dbPath = choose.getSelectedFile().getPath();
		SQLTools.setDBURL(dbPath);
		ArrayList<String> tables = SQLTools.getTables();
		
		//If no tables in file, create one
		if(tables.size() < 1){
			initDBTable();
		}
	}
	public static void initDBTable(){
		SQLTools.createTable(JOptionPane.showInputDialog("Database name:"));
	}

}