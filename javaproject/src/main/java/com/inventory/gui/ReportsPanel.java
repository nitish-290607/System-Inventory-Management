package com.inventory.gui;

import com.inventory.model.Item;
import com.inventory.service.InventoryService;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReportsPanel extends JPanel {
    private final InventoryService inventoryService;
    
    // Components
    private JLabel totalItemsLabel;
    private JLabel totalValueLabel;
    private JLabel categoriesLabel;
    private JTextArea lowStockArea;
    private JTextArea categoryBreakdownArea;
    private JSpinner lowStockThresholdSpinner;
    
    public ReportsPanel(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        initializeComponents();
        setupLayout();
        refreshData();
    }
    
    private void initializeComponents() {
        totalItemsLabel = new JLabel("0");
        totalValueLabel = new JLabel("$0.00");
        categoriesLabel = new JLabel("0");
        
        lowStockArea = new JTextArea(10, 30);
        lowStockArea.setEditable(false);
        lowStockArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        categoryBreakdownArea = new JTextArea(10, 30);
        categoryBreakdownArea.setEditable(false);
        categoryBreakdownArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        lowStockThresholdSpinner = new JSpinner(new SpinnerNumberModel(5, 0, 100, 1));
        lowStockThresholdSpinner.addChangeListener(e -> updateLowStockReport());
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Inventory Reports"));
        
        // Summary panel
        JPanel summaryPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary Statistics"));
        
        summaryPanel.add(new JLabel("Total Items:"));
        summaryPanel.add(totalItemsLabel);
        summaryPanel.add(Box.createHorizontalGlue());
        
        summaryPanel.add(new JLabel("Total Value:"));
        summaryPanel.add(totalValueLabel);
        summaryPanel.add(Box.createHorizontalGlue());
        
        // Reports panel
        JPanel reportsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        
        // Low stock panel
        JPanel lowStockPanel = new JPanel(new BorderLayout());
        lowStockPanel.setBorder(BorderFactory.createTitledBorder("Low Stock Items"));
        
        JPanel lowStockControlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lowStockControlPanel.add(new JLabel("Threshold:"));
        lowStockControlPanel.add(lowStockThresholdSpinner);
        
        lowStockPanel.add(lowStockControlPanel, BorderLayout.NORTH);
        lowStockPanel.add(new JScrollPane(lowStockArea), BorderLayout.CENTER);
        
        // Category breakdown panel
        JPanel categoryPanel = new JPanel(new BorderLayout());
        categoryPanel.setBorder(BorderFactory.createTitledBorder("Category Breakdown"));
        categoryPanel.add(new JScrollPane(categoryBreakdownArea), BorderLayout.CENTER);
        
        reportsPanel.add(lowStockPanel);
        reportsPanel.add(categoryPanel);
        
        // Main layout
        add(summaryPanel, BorderLayout.NORTH);
        add(reportsPanel, BorderLayout.CENTER);
        
        // Refresh button
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton refreshButton = new JButton("Refresh Reports");
        refreshButton.addActionListener(e -> refreshData());
        buttonPanel.add(refreshButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public void refreshData() {
        updateSummaryStatistics();
        updateLowStockReport();
        updateCategoryBreakdown();
    }
    
    private void updateSummaryStatistics() {
        try {
            int totalItems = inventoryService.getTotalItemCount();
            double totalValue = inventoryService.getTotalInventoryValue();
            List<String> categories = inventoryService.getCategories();
            
            totalItemsLabel.setText(String.valueOf(totalItems));
            totalValueLabel.setText(String.format("$%.2f", totalValue));
            categoriesLabel.setText(String.valueOf(categories.size()));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error updating summary: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateLowStockReport() {
        try {
            int threshold = (Integer) lowStockThresholdSpinner.getValue();
            List<Item> lowStockItems = inventoryService.getLowStockItems(threshold);
            
            StringBuilder report = new StringBuilder();
            report.append(String.format("Items with quantity <= %d:\n\n", threshold));
            
            if (lowStockItems.isEmpty()) {
                report.append("No low stock items found.");
            } else {
                report.append(String.format("%-20s %-10s %-10s\n", "Name", "Quantity", "Category"));
                report.append("-".repeat(45)).append("\n");
                
                for (Item item : lowStockItems) {
                    report.append(String.format("%-20s %-10d %-10s\n",
                        truncate(item.getName(), 20),
                        item.getQuantity(),
                        truncate(item.getCategory(), 10)));
                }
                
                report.append("\nTotal low stock items: ").append(lowStockItems.size());
            }
            
            lowStockArea.setText(report.toString());
            lowStockArea.setCaretPosition(0);
            
        } catch (Exception e) {
            lowStockArea.setText("Error generating low stock report: " + e.getMessage());
        }
    }
    
    private void updateCategoryBreakdown() {
        try {
            List<Item> allItems = inventoryService.getAllItems();
            List<String> categories = inventoryService.getCategories();
            
            StringBuilder report = new StringBuilder();
            report.append("Category Breakdown:\n\n");
            report.append(String.format("%-15s %-8s %-12s\n", "Category", "Items", "Total Value"));
            report.append("-".repeat(40)).append("\n");
            
            double grandTotal = 0;
            int totalItems = 0;
            
            for (String category : categories) {
                List<Item> categoryItems = inventoryService.getItemsByCategory(category);
                int itemCount = categoryItems.size();
                double categoryValue = categoryItems.stream()
                    .mapToDouble(item -> item.getQuantity() * item.getPrice())
                    .sum();
                
                report.append(String.format("%-15s %-8d $%-11.2f\n",
                    truncate(category, 15),
                    itemCount,
                    categoryValue));
                
                grandTotal += categoryValue;
                totalItems += itemCount;
            }
            
            report.append("-".repeat(40)).append("\n");
            report.append(String.format("%-15s %-8d $%-11.2f\n", "TOTAL", totalItems, grandTotal));
            
            categoryBreakdownArea.setText(report.toString());
            categoryBreakdownArea.setCaretPosition(0);
            
        } catch (Exception e) {
            categoryBreakdownArea.setText("Error generating category breakdown: " + e.getMessage());
        }
    }
    
    private String truncate(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }
}