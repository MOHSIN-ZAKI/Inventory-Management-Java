package dao;

import db.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Item;

public class InventoryDAO {

    // 🔹 Add a new item
    public boolean addItem(Item item) {
        String query = "INSERT INTO items (name, category, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, item.getName());
            ps.setString(2, item.getCategory());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getPrice());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 Get all items
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Item item = new Item(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("quantity"),
                    rs.getDouble("price")
                );
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // 🔹 Update item
    public boolean updateItem(Item item) {
        String query = "UPDATE items SET name = ?, category = ?, quantity = ?, price = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, item.getName());
            ps.setString(2, item.getCategory());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getPrice());
            ps.setInt(5, item.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 Delete item
    public boolean deleteItem(int id) {
        String query = "DELETE FROM items WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
