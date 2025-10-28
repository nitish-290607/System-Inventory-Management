-- System Inventory Management Database Setup
-- This file contains SQL scripts for setting up the database

-- =====================================================
-- SQLite Setup (Default)
-- =====================================================
-- SQLite tables are created automatically by the application
-- This section is for reference only

-- Items table structure for SQLite
/*
CREATE TABLE IF NOT EXISTS items (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    description TEXT,
    quantity INTEGER NOT NULL DEFAULT 0,
    price REAL NOT NULL DEFAULT 0.0,
    category TEXT NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
*/

-- =====================================================
-- MySQL Setup (Optional)
-- =====================================================

-- Create database
CREATE DATABASE IF NOT EXISTS inventory_db;
USE inventory_db;

-- Create items table
CREATE TABLE IF NOT EXISTS items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    quantity INT NOT NULL DEFAULT 0,
    price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    category VARCHAR(100) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_name (name),
    INDEX idx_category (category),
    INDEX idx_quantity (quantity)
);

-- =====================================================
-- Sample Data (Optional)
-- =====================================================

-- Insert sample categories and items
INSERT INTO items (name, description, quantity, price, category) VALUES
('Laptop Dell XPS 13', 'High-performance ultrabook with 16GB RAM', 5, 1299.99, 'Electronics'),
('Wireless Mouse', 'Ergonomic wireless mouse with USB receiver', 25, 29.99, 'Electronics'),
('Office Chair', 'Ergonomic office chair with lumbar support', 8, 199.99, 'Office Supplies'),
('Java Programming Book', 'Complete guide to Java programming', 12, 49.99, 'Books'),
('Smartphone Samsung Galaxy', 'Latest Android smartphone with 128GB storage', 3, 699.99, 'Electronics'),
('Desk Lamp LED', 'Adjustable LED desk lamp with USB charging', 15, 39.99, 'Office Supplies'),
('Mechanical Keyboard', 'RGB mechanical keyboard with blue switches', 10, 89.99, 'Electronics'),
('Notebook Set', 'Pack of 5 lined notebooks', 50, 12.99, 'Office Supplies'),
('Tablet iPad Air', '10.9-inch iPad Air with 64GB storage', 7, 599.99, 'Electronics'),
('Coffee Mug', 'Ceramic coffee mug with company logo', 30, 8.99, 'Office Supplies');

-- =====================================================
-- Useful Queries for Beekeeper Studio
-- =====================================================

-- View all items
-- SELECT * FROM items ORDER BY name;

-- View items by category
-- SELECT category, COUNT(*) as item_count, SUM(quantity * price) as total_value 
-- FROM items GROUP BY category ORDER BY total_value DESC;

-- Find low stock items (quantity <= 5)
-- SELECT name, quantity, category FROM items WHERE quantity <= 5 ORDER BY quantity;

-- Search items by name or description
-- SELECT * FROM items WHERE name LIKE '%laptop%' OR description LIKE '%laptop%';

-- Get inventory statistics
-- SELECT 
--     COUNT(*) as total_items,
--     SUM(quantity) as total_quantity,
--     SUM(quantity * price) as total_value,
--     AVG(price) as average_price
-- FROM items;

-- =====================================================
-- Maintenance Queries
-- =====================================================

-- Update item quantity
-- UPDATE items SET quantity = quantity + 10, last_updated = CURRENT_TIMESTAMP WHERE id = 1;

-- Update item price
-- UPDATE items SET price = 1199.99, last_updated = CURRENT_TIMESTAMP WHERE name = 'Laptop Dell XPS 13';

-- Delete item
-- DELETE FROM items WHERE id = 1;

-- Clean up old records (example: items not updated in 1 year)
-- DELETE FROM items WHERE last_updated < DATE_SUB(NOW(), INTERVAL 1 YEAR);

-- =====================================================
-- Backup and Restore (MySQL)
-- =====================================================

-- Create backup
-- mysqldump -u username -p inventory_db > inventory_backup.sql

-- Restore from backup
-- mysql -u username -p inventory_db < inventory_backup.sql