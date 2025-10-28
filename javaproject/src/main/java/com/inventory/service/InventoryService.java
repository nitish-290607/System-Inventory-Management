package com.inventory.service;

import com.inventory.model.Item;
import java.util.List;

public interface InventoryService {
    // Item management
    boolean addItem(Item item);
    boolean updateItem(Item item);
    boolean deleteItem(int id);
    Item getItem(int id);
    List<Item> getAllItems();
    
    // Search and filter
    List<Item> searchItems(String searchTerm);
    List<Item> getItemsByCategory(String category);
    
    // Inventory operations
    boolean adjustQuantity(int itemId, int adjustment);
    boolean setQuantity(int itemId, int newQuantity);
    
    // Reports and statistics
    int getTotalItemCount();
    double getTotalInventoryValue();
    List<String> getCategories();
    List<Item> getLowStockItems(int threshold);
    
    // Validation
    boolean isValidItem(Item item);
    boolean itemExists(String name);
}