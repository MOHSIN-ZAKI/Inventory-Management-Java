
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
│   ├── dao/                         # DAO classes for DB access
│   │   └── InventoryDAO.java
│   ├── db/                          # DB connection utilities
│   │   └── DatabaseConnection.java
│   ├── gui/                         # JavaFX UI classes
│   │   └── InventoryFX.java
│   ├── model/                       # POJOs or model classes
│   │   └── Item.java
│   └── Main.java                    # Launches JavaFX app
└──  README.md                        # Project details

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies
    paste your JDBC executable jar file in lib.

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

## Create table in MySQL 

CREATE TABLE IF NOT EXISTS items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    category VARCHAR(50),
    quantity INT,
    price DECIMAL(10,2)
);

