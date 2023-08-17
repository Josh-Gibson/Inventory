
/*
	
	View for the application. This contains a JTable that 
	reads from a database to gather the items.

*/


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.swing.JTable;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.DefaultCellEditor;
import javax.swing.KeyStroke;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.MenuListener;
import javax.swing.event.MenuEvent;

import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



import java.util.Random;
public class ItemView extends JFrame implements TableSelectionListener{
	
	//Main JTable with table model for modifications
	JTable viewTable;
	JPanel screen;
	DefaultTableModel model;
	
	final String TITLE = "Inventory";
	
	
	JMenuBar menuBar = new JMenuBar();
	JMenu menu;
	
	//About section popup
	JFrame popFrame = new JFrame();
	JPanel popPanel = new JPanel();
	JLabel aboutSection = new JLabel("Created 2023 by Aleksandr");
	
	public ItemView(){
		
		setTitle(TITLE);
		
		//model for JTable
		model = new DefaultTableModel(){
			public Class<?> getColumnClass(int column){		//sets column 4 (checked_in) as a boolean class
				switch(column){
					case 3:
						return Boolean.class;
					default:
						return String.class;
				}
			}
			public boolean isCellEditable(int row, int column){		//disable editing in column 2 (item id)
				if(column == 1){
					return false;
				}
				return true;
			}
		};
		
		viewTable = new JTable(model);
		
		model.addColumn("Item Name");
		model.addColumn("ID");
		model.addColumn("Amount");
		model.addColumn("Checked In");
		
		//set colum 3 as combo box for item status
		TableColumn statusCombo = viewTable.getColumnModel().getColumn(2);
		JComboBox comboBox = new JComboBox(Item.STATUS.values());
		statusCombo.setCellEditor(new DefaultCellEditor(comboBox));
		
		
		//Main jpanel for all components
		screen = new JPanel();
		
		
		//Menu bar setup
		menu = new JMenu("File");
		
		//Create a new table in current database file
		JMenuItem newTable = new JMenuItem("New Table");
		newTable.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				newFile();
			}
		});
		
		//Create a new database file
		JMenuItem newDB = new JMenuItem("New Database");
		newDB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String dbName = JOptionPane.showInputDialog("Database name:");
				FileTools.createDBFile(dbName);
			}
		});
		
		//Load table in existing database
		JMenuItem loadOption = new JMenuItem("Load Table");
		loadOption.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new TableSelectionMenu(ItemView.this);
			}
		});
		
		//Load database file
		JMenuItem loadDBOption = new JMenuItem("Load Database");
		loadDBOption.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				FileTools.setDBPath();
			}
		});
		
		//Show about popup
		JMenuItem abouitOption = new JMenuItem("About");
		abouitOption.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Created 2023 by Aleksandr");
				popFrame.setVisible(true);
			}
		});
		
		//Exit program
		JMenuItem exitOption = new JMenuItem("Exit");
		exitOption.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				int input = JOptionPane.showConfirmDialog(screen, "Do you want to Quit?", "Select an Option...",JOptionPane.YES_NO_OPTION);	
				if(input ==0){
					System.exit(1);
				}
			}
		});
		
		
		//add all menu items
		menu.add(newTable);
		menu.add(newDB);
		menu.add(loadOption);
		menu.add(loadDBOption);
		menu.add(abouitOption);
		menu.add(exitOption);
		menuBar.add(menu);
		
		
		//tablelistener to update SQL on table update
		viewTable.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				
				int firstRow = e.getFirstRow();
				int lastRow = e.getLastRow();
				int index = e.getColumn();
				
				switch (e.getType()) {
					case TableModelEvent.UPDATE:
					
						//Update SQL on table update
						SQLTools.updateRow(viewTable.getSelectedColumn(), 
							viewTable.getValueAt(viewTable.getSelectedRow(), 1), 
							viewTable.getValueAt(viewTable.getSelectedRow(), viewTable.getSelectedColumn()));
						break;
				}
			}
		});

		//Mock button that simulates an item being scanned in
		JButton scan = new JButton("Scan In");
		scan.setSize(new Dimension(200, 100));
		
		scan.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				addItem(Item.randomItem());
			}  
		});  
		
		//Add blank item
		JButton add = new JButton("Add");
		add.setSize(new Dimension(200, 100));
		
		add.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
				addRow(Item.blankItem());
			}  
		});  
		
		//Delete selected items
		JButton click = new JButton("Delete");
		click.setSize(new Dimension(200, 100));
		
		click.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				removeItem(viewTable.getSelectedRow());
			}  
		});  
		
		//add everything to main JPanel
		screen.add(new JScrollPane(viewTable));
		screen.add(scan);
		screen.add(click);
		screen.add(add);
		
		//configure JFrame
		setJMenuBar(menuBar);
		setSize(new Dimension(500, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(screen);
		setFocusable(true);
		requestFocusInWindow();
		setLocationRelativeTo(null);
		setVisible(true);
		
		//configure popup panel
		popPanel.add(aboutSection);
		popFrame.setSize(new Dimension(300, 200));
		popFrame.add(popPanel);
		popFrame.setFocusable(true);
		popFrame.setLocationRelativeTo(null);
		popFrame.setVisible(false);
		
	}

	public void addItem(Item i){	//Generates a random Item for the table
		
		Random r = new Random();
		boolean ranBool = r.nextBoolean();
		
		Object[] input = {
			i.getItemName(),
			i.getID(),
			i.getItemStatus(),
			ranBool};
			
		SQLTools.insert(i.getItemName(), i.getID(), i.getItemStatus(), ranBool);
		SQLTools.viewTable();
		model.addRow(input);
	}
	public void addRow(Item i){	//Adds a blank row 
			
		Object[] input = {
			i.getItemName(),
			i.getID(),
			i.getItemStatus(),
			false};
		
		//Insert is index of current row
		//Inser new row at index, otherwise add one at bottom.
		int insert = viewTable.getSelectedRow();
		
		if(insert < 0){
			SQLTools.insert(i.getItemName(), i.getID(), i.getItemStatus(), false);
			model.addRow(input);
			return;
		}
		
		SQLTools.insert(i.getItemName(), i.getID(), i.getItemStatus(), false);
		model.insertRow(insert+1, input);
	}
	public void removeItem(int index){	//Removes a row 
		
		int[] selection = viewTable.getSelectedRows();	//gets all selected rows
		
		if(model.getRowCount() < 1 || selection.length < 1){	//If no rows or rows aren't selected, do nothing
			return;
		}
		//Delete all rows based on index
		//For loop is reversed to prevent index shift
		
		int runningIndex = selection[0];
		for(int i = selection.length; i > 0; i--){
			
			System.out.println(viewTable.getValueAt(selection[i-1],0).toString());
			SQLTools.deleteRow(viewTable.getValueAt(selection[i-1],0).toString(), viewTable.getValueAt(selection[i-1],1).toString());
			model.removeRow(selection[i-1]);
		}
		
		//Put cursor selection at row above if possible
		
		int currentSelect = selection[0];
		if(currentSelect < model.getRowCount()){
			viewTable.setRowSelectionInterval(currentSelect, currentSelect);	
		}else{
			if(model.getRowCount() < 1	){
				return;
			}
			viewTable.setRowSelectionInterval(currentSelect-1, currentSelect-1);	
		}
	}
	//Create a new database
	public void newFile(){
		
		String tableName = JOptionPane.showInputDialog("Table name:");
		if(SQLTools.tableExists(tableName)){
			JOptionPane.showMessageDialog(screen, "Table already exists!", "Table Error", 0);	
			return;
		}
		SQLTools.createTable(tableName);
		clear();
	}
	
	//Loads data from SQL table into JTable
	public void load(String table){
		
		clear();
		
		Object[] data = SQLTools.loadTable(table);
		
		for(int i = 0; i < data.length; i++){
			
			String[] dataSplice = data[i].toString().split(",");
			Object[] input = {
				dataSplice[0],
				dataSplice[1],
				Item.STATUS.valueOf(dataSplice[2]),
				intToBool(dataSplice[3]),
			};
			model.addRow(input);
		}
	}
	
	//clear JTable
	public void clear(){
		model.setRowCount(0);
	}
	
	//SQLITE uses int while JTable uses boolean 
	//This is used to convert SQL in to JTable boolean
	private boolean intToBool(String integer){
		try{
			Integer.parseInt(integer);
		}catch(NumberFormatException e){
			System.out.println("NaN");
			return false;
		}
		int num = Integer.parseInt(integer);
		
		if(num == 1){
			return true;
		}
		return false;
	}
	
	//TableSelectionListener methods for TableSelectionMenu
	@Override
	public void selectTable(String table){
		load(table);
		setTitle(table);
	}
	@Override
	public void deleteTable(String table){
		SQLTools.deleteTable(table);
	}
}