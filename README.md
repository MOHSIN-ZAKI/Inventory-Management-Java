
# Inventory Management System
A Java Swing + MySQL-based Inventory App to manage items.
## Features
- Add, update, delete items
- Search by ID or Name
- MySQL database integration
- Styled Java Swing GUI
## Folder Structure
InventoryManagement/
├──bin/   
├── lib/ 
├── src/
    ├── dao/                         
       └── InventoryDAO.java
    ├── db/                          
       └── DatabaseConnection.java
    ├── gui/                        
        └── InventoryFX.java
    ├── model/                      
        └── Item.java
    └── Main.java                    
└──  README.md                       
paste your JDBC executable jar file in lib.

## Create table in MySQL 
CREATE TABLE IF NOT EXISTS items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    category VARCHAR(50),
    quantity INT,
    price DECIMAL(10,2)
);

