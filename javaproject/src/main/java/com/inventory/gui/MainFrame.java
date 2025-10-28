package com.inventory.gui;

import com.inventory.service.InventoryService;
import com.inventory.service.InventoryServiceImpl;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame {
    private final InventoryService inventoryService;
    private JPanel mainPanel;
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private ItemManagementPanel itemManagementPanel;
    private InventoryViewPanel inventoryViewPanel;
    private ReportsPanel reportsPanel;
    private DashboardPanel dashboardPanel;
    
    // Modern color scheme
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 73, 94);
    private static final Color ACCENT_COLOR = new Color(46, 204, 113);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    
    public MainFrame() {
        this.inventoryService = new InventoryServiceImpl();
        setupModernLookAndFeel();
        initializeComponents();
        setupFrame();
    }
    
    private void setupModernLookAndFeel() {
        try {
            // Set custom UI properties for modern look
            UIManager.put("Panel.background", BACKGROUND_COLOR);
            UIManager.put("Button.background", PRIMARY_COLOR);
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 12));
            UIManager.put("Label.foreground", TEXT_COLOR);
            UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 12));
            UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 12));
            UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 11));
            UIManager.put("Table.gridColor", new Color(189, 195, 199));
            UIManager.put("TabbedPane.selected", CARD_COLOR);
            
            // Try to use system look and feel with custom modifications
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName()) || "Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Using default look and feel");
        }
    }
    
    private void initializeComponents() {
        // Create main panel with modern layout
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        // Create sidebar
        createSidebar();
        
        // Create content area
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Create panels
        dashboardPanel = new DashboardPanel(inventoryService);
        itemManagementPanel = new ItemManagementPanel(inventoryService);
        inventoryViewPanel = new InventoryViewPanel(inventoryService);
        reportsPanel = new ReportsPanel(inventoryService);
        
        // Add panels to content area
        contentPanel.add(dashboardPanel, "Dashboard");
        contentPanel.add(itemManagementPanel, "ItemManagement");
        contentPanel.add(inventoryViewPanel, "InventoryView");
        contentPanel.add(reportsPanel, "Reports");
        
        // Add components to main panel
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void createSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(SECONDARY_COLOR);
        sidebarPanel.setPreferredSize(new Dimension(250, 0));
        sidebarPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        // Logo/Title area
        JPanel titlePanel = createTitlePanel();
        sidebarPanel.add(titlePanel);
        sidebarPanel.add(Box.createVerticalStrut(30));
        
        // Navigation buttons
        JButton dashboardBtn = createSidebarButton("🏠 Dashboard", "Dashboard");
        JButton itemMgmtBtn = createSidebarButton("📦 Item Management", "ItemManagement");
        JButton inventoryBtn = createSidebarButton("📋 Inventory View", "InventoryView");
        JButton reportsBtn = createSidebarButton("📊 Reports", "Reports");
        
        sidebarPanel.add(dashboardBtn);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(itemMgmtBtn);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(inventoryBtn);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(reportsBtn);
        
        // Add flexible space
        sidebarPanel.add(Box.createVerticalGlue());
        
        // Footer
        JLabel footerLabel = new JLabel("<html><center>Inventory System<br>v2.0</center></html>");
        footerLabel.setForeground(new Color(149, 165, 166));
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarPanel.add(footerLabel);
    }
    
    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(SECONDARY_COLOR);
        titlePanel.setMaximumSize(new Dimension(250, 80));
        
        JLabel titleLabel = new JLabel("INVENTORY");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Management System");
        subtitleLabel.setForeground(new Color(149, 165, 166));
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);
        
        return titlePanel;
    }
    
    private JButton createSidebarButton(String text, String cardName) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(220, 45));
        button.setPreferredSize(new Dimension(220, 45));
        button.setBackground(SECONDARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(SECONDARY_COLOR);
            }
        });
        
        // Click action
        button.addActionListener(e -> {
            CardLayout cl = (CardLayout) contentPanel.getLayout();
            cl.show(contentPanel, cardName);
            
            // Refresh data when switching to certain panels
            switch (cardName) {
                case "Dashboard":
                    dashboardPanel.refreshData();
                    break;
                case "InventoryView":
                    inventoryViewPanel.refreshData();
                    break;
                case "Reports":
                    reportsPanel.refreshData();
                    break;
            }
        });
        
        return button;
    }
    
    private void setupFrame() {
        setTitle("Modern Inventory Management System");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 600));
        
        // Modern window styling
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            try {
                // Try to set modern Windows styling
                setIconImage(createAppIcon());
            } catch (Exception e) {
                // Ignore if icon creation fails
            }
        }
        
        // Window closing event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                showExitDialog();
            }
        });
    }
    
    private Image createAppIcon() {
        // Create a simple app icon
        BufferedImage icon = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = icon.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(PRIMARY_COLOR);
        g2d.fillRoundRect(2, 2, 28, 28, 8, 8);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
        g2d.drawString("I", 12, 22);
        g2d.dispose();
        return icon;
    }
    
    private void showExitDialog() {
        JDialog exitDialog = new JDialog(this, "Exit Application", true);
        exitDialog.setLayout(new BorderLayout());
        exitDialog.setSize(350, 150);
        exitDialog.setLocationRelativeTo(this);
        
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(CARD_COLOR);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel messageLabel = new JLabel("Are you sure you want to exit?");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setForeground(TEXT_COLOR);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(CARD_COLOR);
        
        JButton yesButton = createModernButton("Yes", ACCENT_COLOR);
        JButton noButton = createModernButton("No", new Color(231, 76, 60));
        
        yesButton.addActionListener(e -> System.exit(0));
        noButton.addActionListener(e -> exitDialog.dispose());
        
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        
        contentPane.add(messageLabel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        
        exitDialog.add(contentPane);
        exitDialog.setVisible(true);
    }
    
    public static JButton createModernButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBorder(new EmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        Color originalColor = backgroundColor;
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
        
        return button;
    }
    
    public static void main(String[] args) {
        // Enable anti-aliasing for better text rendering
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}