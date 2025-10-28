package com.inventory.gui;

import com.inventory.model.Item;
import com.inventory.service.InventoryService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemManagementPanel extends JPanel {
    private final InventoryService inventoryService;
    
    // Modern colors
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final Color LIGHT_TEXT = new Color(127, 140, 141);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    
    // Form components
    private JTextField nameField;
    private JTextArea descriptionArea;
    private JSpinner quantitySpinner;
    private JTextField priceField;
    private JComboBox<String> categoryCombo;
    private JButton addButton;
    private JButton updateButton;
    private JButton clearButton;
    
    // Current item being edited
    private Item currentItem;
    
    public ItemManagementPanel(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        setBackground(BACKGROUND_COLOR);
        initializeComponents();
        setupLayout();
        setupEventListeners();
    }
    
    private void initializeComponents() {
        // Modern styled form components
        nameField = createModernTextField(20);
        
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descriptionArea.setBorder(new EmptyBorder(8, 8, 8, 8));
        
        quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        quantitySpinner.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        ((JSpinner.DefaultEditor) quantitySpinner.getEditor()).getTextField().setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        priceField = createModernTextField(10);
        
        // Category combo with modern styling
        String[] categories = {"Electronics", "Clothing", "Books", "Food", "Tools", "Office Supplies", "Furniture", "Sports", "Other"};
        categoryCombo = new JComboBox<>(categories);
        categoryCombo.setEditable(true);
        categoryCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        categoryCombo.setBackground(Color.WHITE);
        
        // Modern styled buttons
        addButton = MainFrame.createModernButton("➕ Add Item", SUCCESS_COLOR);
        updateButton = MainFrame.createModernButton("✏️ Update Item", PRIMARY_COLOR);
        clearButton = MainFrame.createModernButton("🗑️ Clear Form", WARNING_COLOR);
        
        updateButton.setEnabled(false);
    }
    
    private JTextField createModernTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 221, 225), 1),
            new EmptyBorder(8, 8, 8, 8)
        ));
        return field;
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content with modern card layout
        JPanel mainContent = new JPanel(new GridBagLayout());
        mainContent.setBackground(BACKGROUND_COLOR);
        mainContent.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        // Form card
        JPanel formCard = createFormCard();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 20, 0, 20);
        
        mainContent.add(formCard, gbc);
        add(mainContent, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        JLabel titleLabel = new JLabel("Item Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        
        JLabel subtitleLabel = new JLabel("Add new items or update existing inventory");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(LIGHT_TEXT);
        
        JPanel titleContainer = new JPanel();
        titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.Y_AXIS));
        titleContainer.setBackground(BACKGROUND_COLOR);
        titleContainer.add(titleLabel);
        titleContainer.add(Box.createVerticalStrut(5));
        titleContainer.add(subtitleLabel);
        
        headerPanel.add(titleContainer, BorderLayout.WEST);
        return headerPanel;
    }
    
    private JPanel createFormCard() {
        JPanel formCard = new JPanel(new BorderLayout());
        formCard.setBackground(CARD_COLOR);
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 221, 225), 1),
            new EmptyBorder(30, 30, 30, 30)
        ));
        
        // Form content
        JPanel formContent = new JPanel(new GridBagLayout());
        formContent.setBackground(CARD_COLOR);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Name field
        gbc.gridx = 0; gbc.gridy = 0;
        formContent.add(createFieldLabel("Item Name *"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formContent.add(nameField, gbc);
        
        // Description field
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formContent.add(createFieldLabel("Description"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 0.3;
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        descScrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 221, 225), 1));
        formContent.add(descScrollPane, gbc);
        
        // Quantity and Price in same row
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; gbc.weighty = 0;
        formContent.add(createFieldLabel("Quantity *"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 0.5;
        formContent.add(quantitySpinner, gbc);
        
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        gbc.insets = new Insets(15, 30, 15, 15);
        formContent.add(createFieldLabel("Price *"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 15, 15, 15);
        formContent.add(priceField, gbc);
        
        // Category field
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formContent.add(createFieldLabel("Category *"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        formContent.add(categoryCombo, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(CARD_COLOR);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(clearButton);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        gbc.insets = new Insets(30, 15, 15, 15);
        formContent.add(buttonPanel, gbc);
        
        formCard.add(formContent, BorderLayout.CENTER);
        
        // Instructions panel
        JPanel instructionsPanel = createInstructionsPanel();
        formCard.add(instructionsPanel, BorderLayout.SOUTH);
        
        return formCard;
    }
    
    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(TEXT_COLOR);
        return label;
    }
    
    private JPanel createInstructionsPanel() {
        JPanel instructionsPanel = new JPanel(new BorderLayout());
        instructionsPanel.setBackground(new Color(248, 249, 250));
        instructionsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 221, 225), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel instructionTitle = new JLabel("💡 Quick Tips");
        instructionTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        instructionTitle.setForeground(TEXT_COLOR);
        
        JTextArea instructions = new JTextArea(
            "• Fill in all required fields (*) and click 'Add Item' to create a new inventory item\n" +
            "• To edit an existing item, select it from the Inventory View and it will load here\n" +
            "• Use 'Clear Form' to reset all fields and start fresh\n" +
            "• Categories can be selected from dropdown or type a custom one"
        );
        instructions.setEditable(false);
        instructions.setBackground(new Color(248, 249, 250));
        instructions.setForeground(LIGHT_TEXT);
        instructions.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        instructions.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        instructionsPanel.add(instructionTitle, BorderLayout.NORTH);
        instructionsPanel.add(instructions, BorderLayout.CENTER);
        
        return instructionsPanel;
    }
    
    private void setupEventListeners() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });
        
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateItem();
            }
        });
        
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
    }
    
    private void addItem() {
        try {
            Item item = createItemFromForm();
            if (item == null) return;
            
            if (inventoryService.itemExists(item.getName())) {
                JOptionPane.showMessageDialog(this,
                    "An item with this name already exists!",
                    "Duplicate Item",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (inventoryService.addItem(item)) {
                showModernMessage("✅ Item Added Successfully!", 
                    "The item '" + item.getName() + "' has been added to your inventory.", 
                    SUCCESS_COLOR);
                clearForm();
            } else {
                showModernMessage("❌ Failed to Add Item", 
                    "Please check your input and try again.", 
                    DANGER_COLOR);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error adding item: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateItem() {
        if (currentItem == null) return;
        
        try {
            Item updatedItem = createItemFromForm();
            if (updatedItem == null) return;
            
            updatedItem.setId(currentItem.getId());
            updatedItem.setCreatedDate(currentItem.getCreatedDate());
            
            if (inventoryService.updateItem(updatedItem)) {
                showModernMessage("✅ Item Updated Successfully!", 
                    "The item '" + updatedItem.getName() + "' has been updated.", 
                    SUCCESS_COLOR);
                clearForm();
            } else {
                showModernMessage("❌ Failed to Update Item", 
                    "Please check your input and try again.", 
                    DANGER_COLOR);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error updating item: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private Item createItemFromForm() {
        String name = nameField.getText().trim();
        String description = descriptionArea.getText().trim();
        int quantity = (Integer) quantitySpinner.getValue();
        String priceText = priceField.getText().trim();
        String category = (String) categoryCombo.getSelectedItem();
        
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        
        if (category == null || category.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Category is required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        
        double price;
        try {
            price = Double.parseDouble(priceText);
            if (price < 0) {
                JOptionPane.showMessageDialog(this, "Price cannot be negative!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return null;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        
        return new Item(name, description, quantity, price, category.trim());
    }
    
    public void editItem(Item item) {
        this.currentItem = item;
        
        nameField.setText(item.getName());
        descriptionArea.setText(item.getDescription());
        quantitySpinner.setValue(item.getQuantity());
        priceField.setText(String.valueOf(item.getPrice()));
        categoryCombo.setSelectedItem(item.getCategory());
        
        addButton.setEnabled(false);
        updateButton.setEnabled(true);
    }
    
    private void clearForm() {
        nameField.setText("");
        descriptionArea.setText("");
        quantitySpinner.setValue(0);
        priceField.setText("");
        categoryCombo.setSelectedIndex(0);
        
        currentItem = null;
        addButton.setEnabled(true);
        updateButton.setEnabled(false);
    }
    
    private void showModernMessage(String title, String message, Color color) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Notification", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(CARD_COLOR);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(color);
        
        JLabel messageLabel = new JLabel("<html>" + message + "</html>");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        messageLabel.setForeground(TEXT_COLOR);
        
        JButton okButton = MainFrame.createModernButton("OK", color);
        okButton.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(CARD_COLOR);
        buttonPanel.add(okButton);
        
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(messageLabel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(contentPanel);
        dialog.setVisible(true);
    }
}