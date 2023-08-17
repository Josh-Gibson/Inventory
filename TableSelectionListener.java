
/*
	Small interface that deals with TableSelectionMenu logic.
	These are called when a table in the TableSelectionMenu are
	selected and loaded, or deleted.
*/

public interface TableSelectionListener{
		void selectTable(String msg);
		void deleteTable(String table);
}