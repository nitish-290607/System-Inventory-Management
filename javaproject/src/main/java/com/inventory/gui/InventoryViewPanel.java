package com.inventory.gui;

import com.inventory.model.Item;
import com.inventory.service.InventoryService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class InventoryViewPanel extends JPanel {
    private final InventoryService inventoryService;
    
    // Components
    private JTable itemTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> categoryFilter;
    private JButton refreshButton;
    private JButton editButton;
    private JButton deleteButton;
    private JLabel statusLabel;
    
    // Table columns
    private final String[] columnNames = {"ID", "Name", "Description", "Quantity", "Price", "Category", "Last Updated"};
    
    public InventoryViewPanel(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        initializeComponents();
        setupLayout();
        setupEventListeners();
        refreshData();
    }
    
    private void initializeComponents() {
        // Table
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        itemTable = new JTable(tableModel);
        itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemTable.setAutoCreateRowSorter(true);
        
        // Search and filter components
        searchField = new JTextField(20);
        categoryFilter = new JComboBox<>();
        categoryFilter.addItem("All Categories");
        
        // Buttons
        refreshButton = new JButton("Refresh");
        editButton = new JButton("Edit Selected");
        deleteButton = new JButton("Delete Selected");
        
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        
        statusLabel = new JLabel("Ready");
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Inventory View"));
        
        // Top panel with search and filters
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchField);
        topPanel.add(new JLabel("Category:"));
        topPanel.add(categoryFilter);
        topPanel.add(refreshButton);
        
        // Center panel with table
        JScrollPane scrollPane = new JScrollPane(itemTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        
        // Bottom panel with buttons and status
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        
        bottomPanel.add(buttonPanel, BorderLayout.WEST);
        bottomPanel.add(statusLabel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventListeners() {
        // Search functionality
        searchField.addActionListener(e -> filterTable());
        
        // Category filter
        categoryFilter.addActionListener(e -> filterTable());
        
        // Refresh button
        refreshButton.addActionListener(e -> refreshData());
        
        // Table selection
        itemTable.getSelectionModel().addListSelectionListener(e -> {
            boolean hasSelection = itemTable.getSelectedRow() != -1;
            editButton.setEnabled(hasSelection);
            deleteButton.setEnabled(hasSelection);
        });
        
        // Double-click to edit
        itemTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && itemTable.getSelectedRow() != -1) {
                    editSelectedItem();
                }
            }
        });
        
        // Edit button
        editButton.addActionListener(e -> editSelectedItem());
        
        // Delete button
        deleteButton.addActionListener(e -> deleteSelectedItem());
    }
    
    public void refreshData() {
        try {
            // Clear existing data
            tableModel.setRowCount(0);
            
            // Load all items
            List<Item> items = inventoryService.getAllItems();
            
            for (Item item : items) {
                Object[] row = {
                    item.getId(),
                    item.getName(),
                    item.getDescription(),
                    item.getQuantity(),
                    String.format("$%.2f", item.getPrice()),
                    item.getCategory(),
                    item.getLastUpdated().toString()
                };
                tableModel.addRow(row);
            }
            
            // Update category filter
            updateCategoryFilter();
            
            // Update status
            statusLabel.setText(String.format("Total items: %d", items.size()));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading data: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateCategoryFilter() {
        String selectedCategory = (String) categoryFilter.getSelectedItem();
        categoryFilter.removeAllItems();
        categoryFilter.addItem("All Categories");
        
        List<String> categories = inventoryService.getCategories();
        for (String category : categories) {
            categoryFilter.addItem(category);
        }
        
        // Restore selection if possible
        if (selectedCategory != null) {
            categoryFilter.setSelectedItem(selectedCategory);
        }
    }
    
    private void filterTable() {
        String searchText = searchField.getText().trim().toLowerCase();
        String selectedCategory = (String) categoryFilter.getSelectedItem();
        
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        itemTable.setRowSorter(sorter);
        
        RowFilter<DefaultTableModel, Object> filter = new RowFilter<DefaultTableModel, Object>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                // Search filter
                boolean matchesSearch = searchText.isEmpty();
                if (!matchesSearch) {
                    for (int i = 0; i < entry.getValueCount(); i++) {
                        String value = entry.getStringValue(i).toLowerCase();
                        if (value.contains(searchText)) {
                            matchesSearch = true;
                            break;
                        }
                    }
                }
                
                // Category filter
                boolean matchesCategory = "All Categories".equals(selectedCategory) ||
                                        selectedCategory.equals(entry.getStringValue(5));
                
                return matchesSearch && matchesCategory;
            }
        };
        
        sorter.setRowFilter(filter);
    }
    
    private void editSelectedItem() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow == -1) return;
        
        // Convert view row to model row (in case of sorting/filtering)
        int modelRow = itemTable.convertRowIndexToModel(selectedRow);
        int itemId = (Integer) tableModel.getValueAt(modelRow, 0);
        
        Item item = inventoryService.getItem(itemId);
        if (item != null) {
            // Find the main frame and switch to item management tab
            Container parent = getParent();
            while (parent != null && !(parent instanceof MainFrame)) {
                parent = parent.getParent();
            }
            
            if (parent instanceof MainFrame) {
                MainFrame mainFrame = (MainFrame) parent;
                // This would require exposing the item management panel
                // For now, show a simple edit dialog
                showEditDialog(item);
            }
        }
    }
    
    private void showEditDialog(Item item) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Item", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Create form fields
        JTextField nameField = new JTextField(item.getName(), 20);
        JTextArea descArea = new JTextArea(item.getDescription(), 3, 20);
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(item.getQuantity(), 0, Integer.MAX_VALUE, 1));
        JTextField priceField = new JTextField(String.valueOf(item.getPrice()), 10);
        JTextField categoryField = new JTextField(item.getCategory(), 15);
        
        // Layout form
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        dialog.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        dialog.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        dialog.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH;
        dialog.add(new JScrollPane(descArea), gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        dialog.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        dialog.add(quantitySpinner, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        dialog.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        dialog.add(priceField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        dialog.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        dialog.add(categoryField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
            try {
                item.setName(nameField.getText().trim());
                item.setDescription(descArea.getText().trim());
                item.setQuantity((Integer) quantitySpinner.getValue());
                item.setPrice(Double.parseDouble(priceField.getText()));
                item.setCategory(categoryField.getText().trim());
                
                if (inventoryService.updateItem(item)) {
                    JOptionPane.showMessageDialog(dialog, "Item updated successfully!");
                    dialog.dispose();
                    refreshData();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to update item!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void deleteSelectedItem() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow == -1) return;
        
        int modelRow = itemTable.convertRowIndexToModel(selectedRow);
        int itemId = (Integer) tableModel.getValueAt(modelRow, 0);
        String itemName = (String) tableModel.getValueAt(modelRow, 1);
        
        int option = JOptionPane.showConfirmDialog(this,
            String.format("Are you sure you want to delete '%s'?", itemName),
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (option == JOptionPane.YES_OPTION) {
            if (inventoryService.deleteItem(itemId)) {
                JOptionPane.showMessageDialog(this, "Item deleted successfully!");
                refreshData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete item!");
            }
        }
    }
}