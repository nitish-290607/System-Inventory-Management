package com.inventory.dao;

import com.inventory.model.Item;
import java.util.List;

public interface ItemDAO {
    // Create
    boolean addItem(Item item);
    
    // Read
    Item getItemById(int id);
    List<Item> getAllItems();
    List<Item> getItemsByCategory(String category);
    List<Item> searchItems(String searchTerm);
    
    // Update
    boolean updateItem(Item item);
    boolean updateQuantity(int id, int newQuantity);
    
    // Delete
    boolean deleteItem(int id);
    
    // Utility
    int getTotalItems();
    double getTotalInventoryValue();
    List<String> getAllCategories();
}