
/*

	This class is the main controller for Items

*/


public class ItemController{
	
	private Item model;
	private ItemView view;
	
	public ItemController(){
		this.model = model;
		this.view = view;
	}
	public String getItemID(){
		return model.getID();
	}
	public void setItemID(String i){
		model.setID(i);
	}
	public String getItemName(){
		return model.getItemName();
	}
	public void setItemName(String name){
		model.setItemName(name);
	}
	public Item.STATUS getItemStatus(){
		return model.getItemStatus();
	}
	public void setItemStatus(Item.STATUS itemStat){
		model.setItemStatus(itemStat);
	}
	public void addItem(Item i){
		view.addItem(i);
	}
	public void removeItem(int index){
		view.removeItem(index);
	}
}