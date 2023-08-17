

/*
	This class is for a table selection menu.
	It gets all the current tables in the database and 
	allows you to load or delete them.

*/

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.BorderLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;

import java.util.ArrayList;

public class TableSelectionMenu extends JFrame{
	
	//Colors to make selection look easier
	static Color defaultColor = new Color(250, 250, 250);
	static Color hoverColor = Color.WHITE;
	static Color selectedColor = new Color(200, 200, 255);
	
	private static JLabel currentSelection;
	private static ArrayList<JLabel> items = new ArrayList<JLabel>();
	
	//takes in TableSelectionListener so that selection can call
	// selectTable and deleteTable in ItemView
	public TableSelectionMenu(TableSelectionListener listener){
		
		items.clear();
		
		setTitle("Table Selection");
		setLayout(new BorderLayout());
		
		//Helps with BorderLayout positioning
		JPanel mainPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
	
		ArrayList<String> tables = SQLTools.getTables();
		
		for(String table : tables){
				
			JLabel tableChoice = new JLabel(table, SwingConstants.CENTER);
			tableChoice.setOpaque(true);
			tableChoice.setPreferredSize(new Dimension(100, 40));
			tableChoice.setBackground(defaultColor);
			
			//Mouse listeners to show selections
			// JLabels instead of JButtons was a style choice
			tableChoice.addMouseListener(new MouseAdapter(){
				public void mouseEntered(MouseEvent e){
					if(tableChoice == currentSelection){
						return;
					}
					tableChoice.setBackground(hoverColor);
				}
				public void mouseExited(MouseEvent e){
					if(tableChoice == currentSelection){
						return;
					}
					tableChoice.setBackground(defaultColor);
				}
					
				public void mousePressed(MouseEvent e){
					currentSelection = tableChoice;
					
					for(JLabel item : items){
						if(item == currentSelection){
							item.setBackground(selectedColor);		
						}else{
							item.setBackground(defaultColor);
						}
					}
					
				}  
			});
			items.add(tableChoice);
			mainPanel.add(tableChoice);
		}
		
		//Load a table database
		JButton open = new JButton("Open");
		open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				listener.selectTable(currentSelection.getText());
				dispose();
			}
		});
		bottomPanel.add(open);
		
		//Delete a table
		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				listener.deleteTable(currentSelection.getText());
				items.remove(currentSelection);
				currentSelection.setVisible(false);
			}
		});
		bottomPanel.add(delete);
		
		//Cancel menu
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
		bottomPanel.add(cancel);
		
		add(bottomPanel, BorderLayout.PAGE_END);
		add(mainPanel, BorderLayout.CENTER);
		setSize(new Dimension(500, 500));
		
		setVisible(true);
	}
}