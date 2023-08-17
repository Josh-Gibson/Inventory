
# Inventory
A mock application that simulates basic inventory management 


![main](https://github.com/Josh-Gibson/Inventory/assets/22622013/9a427c1f-4203-4ae7-aa2a-7aca67ddd293)
<br>
![db](https://github.com/Josh-Gibson/Inventory/assets/22622013/51ca269c-0158-4898-bf3f-1f4a5a7dfb0b)
<br>
![table](https://github.com/Josh-Gibson/Inventory/assets/22622013/d86d59cc-15be-454c-973e-b9e422d2aada)



This is a simple application that shows the basics of using Java with JDBC and SQLite

<b>Running</b>

*JDBC Sqlite is required (I used ver. 3.42.0.0)*
To run, use any build tool to include the JDBC SQLite jar, and compile as you would.

Inventory expects there to be a /db folder in the main directory. If it is not there, you can create one. This is where the databases are stored

<h2>Manual</h2>
<b>Creating a database</b>

When you start the program, if you do not have any .db files in the /db folder, you can create one by clicking "New Database" in the dropdown menu. From there, a dialoug will pop up asking for a database name. After creating the database file, another dialoug will pop up asking for a table name. After creating a table, you are then able to start using the application.

Alternatively, you can simply go into the /db folder and create a file yourself

<b>Creating a table</b>

You are prompted to create a table once you create a new database. But you are also able to create new tables within the current database.

To create a table, you can click "New Table" in the dropdown. From there you will be prompted for a table name. If the table already exists, you will be alerted and the action will be cancelled. Otherwise, the table will be created.

<b>Loading a database</b>

You can load in different databases by clicking "load database" in the dropdown. This will open a JFileChooser that will allow you to select the database file. You are able to load databases outside of the /db folder.

<b>Loading a table</b>

You can load a table by clicking "load table" in the dropdown. This will open a menu that shows all the tables available in the database. Click on the desired table name and click "open." Alternatively, you are able to delete unwanted tables in this menu.

<b>Deleting a table</b>

Click "load table" in the dropdown, select the desired table, and click the delete button

<b>Control Buttons</b>

There are three buttons on the bottom: Scan In, Add, and Delete

[Scan In]

This is a mock button that simulates scanning in a random item. It will generate a random item name with ID and boolean of it being checked in. 

[Add]

This will add a blank item with a set ID

[Delete]

This will delete the selected rows from the database
