/*
	Entry point
*/


public class Launch{
	public static void main(String args[]){
		
		Item i1 = new Item();
		i1.setItemName("test");
		i1.setID("");
		i1.setItemStatus(Item.STATUS.WAREHOUSE);
		
		
		ItemView view = new ItemView();
		ItemController controller = new ItemController();
		
	}
}