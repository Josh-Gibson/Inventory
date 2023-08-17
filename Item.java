
/*
	Item class
*/

import java.util.ArrayList;

public class Item{
	
	public static ArrayList<Item> items = new ArrayList<Item>();
	
	private String ID;
	private String itemName;
	private int packAmount;
	
	public static enum STATUS{WAREHOUSE, DELIVERED, STORED};
	private STATUS itemStatus = STATUS.WAREHOUSE;
	
	
	public Item(){}
	
	public String getID(){
		return ID;
	}
	public void setID(String i){
		ID = i;
	}
	public String getItemName(){
		return itemName;
	}
	public void setItemName(String name){
		itemName = name;
	}
	public STATUS getItemStatus(){
		return itemStatus;
	}
	public void setItemStatus(STATUS itemStat){
		itemStatus = itemStat;
	}
	
	//generate randomly simulated item
	public static Item randomItem(){
		Item ii = new Item();
		ii.setItemName("Item #" + (int)Math.round(Math.random()*10000));
		ii.setID(randomID());
		ii.setItemStatus(STATUS.WAREHOUSE);
		return ii;
	}
	//generate blank item
	public static Item blankItem(){
		Item ii = new Item();
		ii.setItemName("");
		ii.setID(randomID());
		ii.setItemStatus(STATUS.WAREHOUSE);
		return ii;
	}
	
	//generate random ID for item
	//converts string into char array and picks 10 from that.
	public static String randomID(){
		
		String charPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		char[] charArr = new char[charPool.length()];

		for (int i = 0; i < charPool.length(); i++) {
		  charArr[i] = charPool.charAt(i);
		}		
		int size = 10;
		String newID = "";
		
		for(int i = 0; i < size; i++){
			newID+= charArr[Math.round((int)(Math.random()*charArr.length))];
		}
		System.out.println(newID);
		return newID;
	}
}