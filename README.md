# 📦 Inventory Management System

A Java Swing + MySQL-based desktop application to manage inventory items with a user-friendly GUI.

---

## ✨ Features

- 🔄 Add, update, and delete items
- 🔍 Search by **ID** or **Name**
- 🛢️ Connected to **MySQL database**
- 🎨 Styled GUI using Java Swing
- 📁 Modular architecture (DAO + Model + GUI)

---

## 🛠️ Tech Stack

- **Java (Swing + JDBC)**
- **MySQL**
- **JDBC MySQL Connector (Paste the `.jar` file inside the `lib/` folder)**

---

## 🧱 MySQL Table Structure

Make sure the following table exists in your MySQL database:

```sql
CREATE TABLE IF NOT EXISTS items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    category VARCHAR(50),
    quantity INT,
    price DECIMAL(10,2)
);
