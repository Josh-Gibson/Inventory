/*

	This is a helpful class with all needed SQL queries.
	Including insert, remove, view, and getting all tables in database.
	
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;

public class SQLTools{
	
	//url of database 
	private static String url = "jdbc:sqlite:C:/Users/witherherobrine/Desktop/Program_Languages/Java/Projects/Inventory/db/inventory.db";
	
	//current working table
	private static String currentDB = "inventory";
	
	//Connect to db
	public static Connection connect(){
		
        Connection conn = null;
		
        try {
            conn = DriverManager.getConnection(url);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return conn;
	}
	//View table schema in print form
	public static void viewTable(){
		
		String sql = "SELECT * FROM " + currentDB;
		
		try(Connection con = connect();){
				
			Statement pstmt = con.createStatement();
			ResultSet result = pstmt.executeQuery(sql);
			
			System.out.println("item_name |" + " id |" + " status |" + " checked_in");
			while(result.next()){
				
				System.out.println(result.getString("item_name")+ "|" + 
					result.getString("id") + "|" +
					result.getString("status") + "|"+
					result.getString("checked_in"));
			}
			
		}catch(SQLException e){}
		
	}
	
	//Get list of all tables
	public static ArrayList<String> getTables(){
		
		ArrayList<String> tables = new ArrayList<String>();

		try (Connection conn = connect();) {
			
			ResultSet rs = conn.getMetaData().getTables(null, null, null, null);
			
			while (rs.next()) {
				if(rs.getString("TABLE_NAME").equals("sqlite_schema")){
					continue;
				}
				String table = rs.getString("TABLE_NAME");
				System.out.println(table);
				tables.add(table);
			}
			
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return tables;
	}
	
	//Simple function to check if a table exists
	public static boolean tableExists(String tableName){
		ArrayList<String> tables = getTables();
		if(tables.contains(tableName)){
			return true;
		}
		return false;
	}
	
	
	public static void createTable(String tableName){
		
		String sql = "CREATE TABLE " + tableName + 
			"(item_name TEXT," +
			"id TEXT," +
			"status TEXT," +
			"checked_in INTEGER)";
			
		try(Connection con = connect();){
				
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			
		}catch(SQLException e){}
		
		setCurrentDB(tableName);
	}
	
	public static void deleteTable(String tableName){
		
		String sql = "DROP TABLE " + tableName + ";";
		
		try(Connection con = connect();){
				
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			
		}catch(SQLException e){}
		
	}
	public static void insert(String itemName, String id, Item.STATUS status, boolean checkedIn){
		
		String sql = "INSERT INTO " + currentDB + 
			" VALUES (?, ?, ?, ?);";

        try (Connection conn = connect();) {
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, itemName);
			pstmt.setString(2, id);
			pstmt.setString(3, status.name());
			pstmt.setInt(4, boolToInt(checkedIn));
			
			pstmt.executeUpdate();
			
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		
	}
	//Insert an empty row into database
	public static void insertBlank(){
		String sql = "INSERT INTO " + currentDB + 
			" VALUES (?, ?, ?, ?);";

        try (Connection conn = connect();) {
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "");
			pstmt.setString(2, "");
			pstmt.setString(3, Item.STATUS.WAREHOUSE.name());
			pstmt.setInt(4, 0);
			
			pstmt.executeUpdate();
			
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	//Update row in database
	//No support for changing ID
	public static void updateRow(int column, Object id, Object data){
		
		String sql;
		 try (Connection con = connect();) {
				
			sql = "UPDATE " + currentDB + " SET item_name = ? WHERE id = ?;";
			PreparedStatement 	pstmt = con.prepareStatement(sql);
				
				
				System.out.println("col===" + column);
			switch(column){
				case 0:
					 sql = "UPDATE " + currentDB + " SET item_name = ? WHERE id = ?;";
					pstmt = con.prepareStatement(sql);
				
					pstmt.setString(1,data.toString());
					pstmt.setString(2,id.toString());
					break;
				case 2:
					sql = "UPDATE " + currentDB + " SET status = ? WHERE id = ?;";
					pstmt = con.prepareStatement(sql);
					
					pstmt.setString(1,data.toString());
					pstmt.setString(2,id.toString());
					break;
				case 3:
					sql = "UPDATE " + currentDB + " SET checked_in = ? WHERE id = ?;";
					pstmt = con.prepareStatement(sql);
					
					pstmt.setInt(1,boolToInt((boolean)data));
					pstmt.setString(2,id.toString());
					break;
			}
			
			pstmt.executeUpdate();
			viewTable();
			
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		
		
	}
	
	public static void deleteRow(String itemName, String id){
		
		String sql = "DELETE FROM " + currentDB + " WHERE item_name IS '" + itemName +"' AND id IS '" + id + "';";
		System.out.println(sql);
		try (Connection con = connect();) {
			Statement stat = con.createStatement();
			stat.executeUpdate(sql);
			
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		
	}
	
	//returns all data from table
	//Passes it as an array of combined strings
	public static Object[] loadTable(String table){
		
		setCurrentDB(table);
		
		ArrayList<Object> loadedTableData = new ArrayList<Object>();
		
		String sql = "SELECT * FROM " + currentDB;
		
		try(Connection con = connect();){
				
			Statement pstmt = con.createStatement();
			ResultSet result = pstmt.executeQuery(sql);
			
			
			while(result.next()){
			
				String data = result.getString("item_name") + "," +
					result.getString("id") + "," +
					result.getString("status") + "," +
					result.getString("checked_in");
					
				System.out.println(data);
				loadedTableData.add(data);
			}
			
		}catch(SQLException e){}
		System.out.println("dddomne");
		
		return loadedTableData.toArray();
	}
	//Converts boolean from JTable into int for SQLite
	public static int boolToInt(boolean bool){
		return bool ? 1 : 0;
	}
	public static String getCurrentDB(){
		return currentDB;
	}
	public static void setCurrentDB(String db){
		currentDB = db;
	}
	//sets working directory of current database file
	public static void setDBURL(String path){
		url = "jdbc:sqlite:" + path;
	}
}