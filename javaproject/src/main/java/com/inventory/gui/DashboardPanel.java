package com.inventory.gui;

import com.inventory.model.Item;
import com.inventory.service.InventoryService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class DashboardPanel extends JPanel {
    private final InventoryService inventoryService;
    
    // Modern colors
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final Color LIGHT_TEXT = new Color(127, 140, 141);
    
    // Dashboard components
    private JLabel totalItemsLabel;
    private JLabel totalValueLabel;
    private JLabel lowStockLabel;
    private JLabel categoriesLabel;
    private JPanel recentActivityPanel;
    
    public DashboardPanel(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        initializeComponents();
        setupLayout();
        refreshData();
    }
    
    private void initializeComponents() {
        setBackground(new Color(236, 240, 241));
        setLayout(new BorderLayout());
        
        // Initialize labels
        totalItemsLabel = new JLabel("0");
        totalValueLabel = new JLabel("$0.00");
        lowStockLabel = new JLabel("0");
        categoriesLabel = new JLabel("0");
        
        recentActivityPanel = new JPanel();
        recentActivityPanel.setLayout(new BoxLayout(recentActivityPanel, BoxLayout.Y_AXIS));
        recentActivityPanel.setBackground(CARD_COLOR);
    }
    
    private void setupLayout() {
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(new Color(236, 240, 241));
        
        // Stats cards
        JPanel statsPanel = createStatsPanel();
        mainContent.add(statsPanel, BorderLayout.NORTH);
        
        // Charts and activity
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        bottomPanel.setBackground(new Color(236, 240, 241));
        bottomPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        JPanel chartPanel = createChartPanel();
        JPanel activityPanel = createActivityPanel();
        
        bottomPanel.add(chartPanel);
        bottomPanel.add(activityPanel);
        
        mainContent.add(bottomPanel, BorderLayout.CENTER);
        add(mainContent, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(236, 240, 241));
        headerPanel.setBorder(new EmptyBorder(0, 0, 30, 0));
        
        JLabel titleLabel = new JLabel("Dashboard Overview");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        
        JLabel subtitleLabel = new JLabel("Real-time inventory insights and statistics");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(LIGHT_TEXT);
        
        JPanel titleContainer = new JPanel();
        titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.Y_AXIS));
        titleContainer.setBackground(new Color(236, 240, 241));
        titleContainer.add(titleLabel);
        titleContainer.add(Box.createVerticalStrut(5));
        titleContainer.add(subtitleLabel);
        
        JButton refreshButton = MainFrame.createModernButton("🔄 Refresh", PRIMARY_COLOR);
        refreshButton.addActionListener(e -> refreshData());
        
        headerPanel.add(titleContainer, BorderLayout.WEST);
        headerPanel.add(refreshButton, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        statsPanel.setBackground(new Color(236, 240, 241));
        
        JPanel totalItemsCard = createStatCard("📦", "Total Items", totalItemsLabel, PRIMARY_COLOR);
        JPanel totalValueCard = createStatCard("💰", "Total Value", totalValueLabel, SUCCESS_COLOR);
        JPanel lowStockCard = createStatCard("⚠️", "Low Stock", lowStockLabel, WARNING_COLOR);
        JPanel categoriesCard = createStatCard("📂", "Categories", categoriesLabel, new Color(155, 89, 182));
        
        statsPanel.add(totalItemsCard);
        statsPanel.add(totalValueCard);
        statsPanel.add(lowStockCard);
        statsPanel.add(categoriesCard);
        
        return statsPanel;
    }
    
    private JPanel createStatCard(String icon, String title, JLabel valueLabel, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 221, 225), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        // Icon and title
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        headerPanel.setBackground(CARD_COLOR);
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(LIGHT_TEXT);
        titleLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        
        headerPanel.add(iconLabel);
        headerPanel.add(titleLabel);
        
        // Value
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(accentColor);
        valueLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        card.add(headerPanel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createChartPanel() {
        JPanel chartPanel = new JPanel(new BorderLayout());
        chartPanel.setBackground(CARD_COLOR);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 221, 225), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel chartTitle = new JLabel("Category Distribution");
        chartTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        chartTitle.setForeground(TEXT_COLOR);
        
        // Simple text-based chart
        JPanel chartContent = new JPanel();
        chartContent.setLayout(new BoxLayout(chartContent, BoxLayout.Y_AXIS));
        chartContent.setBackground(CARD_COLOR);
        
        chartPanel.add(chartTitle, BorderLayout.NORTH);
        chartPanel.add(chartContent, BorderLayout.CENTER);
        
        return chartPanel;
    }
    
    private JPanel createActivityPanel() {
        JPanel activityPanel = new JPanel(new BorderLayout());
        activityPanel.setBackground(CARD_COLOR);
        activityPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 221, 225), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel activityTitle = new JLabel("Recent Activity");
        activityTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        activityTitle.setForeground(TEXT_COLOR);
        
        JScrollPane scrollPane = new JScrollPane(recentActivityPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(CARD_COLOR);
        scrollPane.getViewport().setBackground(CARD_COLOR);
        
        activityPanel.add(activityTitle, BorderLayout.NORTH);
        activityPanel.add(scrollPane, BorderLayout.CENTER);
        
        return activityPanel;
    }
    
    public void refreshData() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            private int totalItems;
            private double totalValue;
            private int lowStockCount;
            private int categoryCount;
            private List<Item> recentItems;
            
            @Override
            protected Void doInBackground() throws Exception {
                totalItems = inventoryService.getTotalItemCount();
                totalValue = inventoryService.getTotalInventoryValue();
                lowStockCount = inventoryService.getLowStockItems(5).size();
                categoryCount = inventoryService.getCategories().size();
                recentItems = inventoryService.getAllItems();
                return null;
            }
            
            @Override
            protected void done() {
                // Update stat cards
                totalItemsLabel.setText(String.valueOf(totalItems));
                totalValueLabel.setText(String.format("$%.2f", totalValue));
                lowStockLabel.setText(String.valueOf(lowStockCount));
                categoriesLabel.setText(String.valueOf(categoryCount));
                
                // Update recent activity
                updateRecentActivity(recentItems);
                
                // Update chart
                updateCategoryChart();
            }
        };
        
        worker.execute();
    }
    
    private void updateRecentActivity(List<Item> items) {
        recentActivityPanel.removeAll();
        
        if (items.isEmpty()) {
            JLabel noDataLabel = new JLabel("No items found");
            noDataLabel.setForeground(LIGHT_TEXT);
            noDataLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
            recentActivityPanel.add(noDataLabel);
        } else {
            // Show last 5 items
            int count = Math.min(5, items.size());
            for (int i = items.size() - count; i < items.size(); i++) {
                Item item = items.get(i);
                JPanel activityItem = createActivityItem(item);
                recentActivityPanel.add(activityItem);
                if (i < items.size() - 1) {
                    recentActivityPanel.add(Box.createVerticalStrut(10));
                }
            }
        }
        
        recentActivityPanel.revalidate();
        recentActivityPanel.repaint();
    }
    
    private JPanel createActivityItem(Item item) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setBackground(CARD_COLOR);
        itemPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        JLabel nameLabel = new JLabel(item.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        nameLabel.setForeground(TEXT_COLOR);
        
        JLabel detailsLabel = new JLabel(String.format("Qty: %d | $%.2f", 
            item.getQuantity(), item.getPrice()));
        detailsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        detailsLabel.setForeground(LIGHT_TEXT);
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(CARD_COLOR);
        textPanel.add(nameLabel);
        textPanel.add(detailsLabel);
        
        // Status indicator
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(CARD_COLOR);
        Color statusColor = item.getQuantity() <= 5 ? WARNING_COLOR : SUCCESS_COLOR;
        statusPanel.setBorder(BorderFactory.createLineBorder(statusColor, 2));
        statusPanel.setPreferredSize(new Dimension(4, 30));
        
        itemPanel.add(statusPanel, BorderLayout.WEST);
        itemPanel.add(textPanel, BorderLayout.CENTER);
        
        return itemPanel;
    }
    
    private void updateCategoryChart() {
        // This would be where you'd add a proper chart
        // For now, we'll keep it simple with text representation
    }
}