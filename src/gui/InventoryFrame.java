package gui;

import dao.InventoryDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import model.Item;

public class InventoryFrame extends JFrame {
    private JTextField nameField, categoryField, quantityField, priceField, idField;
    private JTable itemTable;
    private DefaultTableModel tableModel;
    private InventoryDAO dao = new InventoryDAO();

    public InventoryFrame() {
        setTitle("📦 Inventory Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);

        // 🎨 Colors & Fonts
        Color bgColor = new Color(245, 245, 245);
        Color headerColor = new Color(0, 102, 153);
        Color btnColor = new Color(0, 153, 76);
        Color deleteColor = new Color(204, 0, 0);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);

        // 🔍 Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(bgColor);

        JTextField searchField = new JTextField(15);
        String[] searchOptions = {"ID", "Name"};
        JComboBox<String> searchType = new JComboBox<>(searchOptions);
        JButton searchButton = createStyledButton("Search", headerColor);
        JButton refreshButton = createStyledButton("Reset", Color.DARK_GRAY);

        searchPanel.add(new JLabel("Search by:"));
        searchPanel.add(searchType);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);

        // 🔹 Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Item Details"));
        inputPanel.setBackground(bgColor);

        idField = new JTextField();
        nameField = new JTextField();
        categoryField = new JTextField();
        quantityField = new JTextField();
        priceField = new JTextField();

        inputPanel.add(getLabeledField("ID (for update/delete):", idField, labelFont));
        inputPanel.add(getLabeledField("Name:", nameField, labelFont));
        inputPanel.add(getLabeledField("Category:", categoryField, labelFont));
        inputPanel.add(getLabeledField("Quantity:", quantityField, labelFont));
        inputPanel.add(getLabeledField("Price:", priceField, labelFont));

        // 🔹 Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Category", "Quantity", "Price"}, 0);
        itemTable = new JTable(tableModel);
        itemTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        itemTable.setRowHeight(22);
        itemTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        itemTable.getTableHeader().setBackground(headerColor);
        itemTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(itemTable);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < itemTable.getColumnCount(); i++) {
            itemTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // 🔹 Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(bgColor);

        JButton addButton = createStyledButton("Add", btnColor);
        JButton updateButton = createStyledButton("Update", headerColor);
        JButton deleteButton = createStyledButton("Delete", deleteColor);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // 🔹 Combine top panels
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(bgColor);
        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(inputPanel, BorderLayout.CENTER);

        // ✅ Padding wrapper
        JPanel paddedPanel = new JPanel(new BorderLayout());
        paddedPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // top, left, bottom, right
        paddedPanel.setBackground(bgColor);

        paddedPanel.add(topPanel, BorderLayout.NORTH);
        paddedPanel.add(scrollPane, BorderLayout.CENTER);
        paddedPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the padded panel to JFrame
        add(paddedPanel);

        // 🔄 Load initial data
        loadItems();

        // 🧠 Actions

        // Add Item
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String category = categoryField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());

                Item item = new Item(name, category, quantity, price);
                if (dao.addItem(item)) {
                    JOptionPane.showMessageDialog(this, "Item added successfully.");
                    loadItems();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add item.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Invalid input. Please check fields.");
            }
        });

        // Update Item
        updateButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String category = categoryField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());

                Item item = new Item(id, name, category, quantity, price);
                if (dao.updateItem(item)) {
                    JOptionPane.showMessageDialog(this, "Item updated.");
                    loadItems();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Update failed.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Invalid input for update.");
            }
        });

        // Delete Item
        deleteButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                if (dao.deleteItem(id)) {
                    JOptionPane.showMessageDialog(this, "Item deleted.");
                    loadItems();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Delete failed.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Invalid ID for deletion.");
            }
        });

        // Search
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (keyword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter something to search.");
                return;
            }

            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
            itemTable.setRowSorter(sorter);

            int columnIndex = searchType.getSelectedItem().equals("ID") ? 0 : 1;
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword, columnIndex));
        });

        // Reset Search
        refreshButton.addActionListener(e -> {
            itemTable.setRowSorter(null);
            searchField.setText("");
        });

        setVisible(true);
    }

    // 🔧 Reusable input layout
    private JPanel getLabeledField(String label, JTextField field, Font font) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));
        JLabel lbl = new JLabel(label);
        lbl.setFont(font);
        panel.add(lbl, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    // 🔧 Reusable styled buttons
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    // 🔄 Load table data
    private void loadItems() {
        tableModel.setRowCount(0);
        List<Item> itemList = dao.getAllItems();
        for (Item item : itemList) {
            tableModel.addRow(new Object[]{
                    item.getId(), item.getName(), item.getCategory(),
                    item.getQuantity(), item.getPrice()
            });
        }
    }

    // 🧹 Clear inputs
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        categoryField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }
}
