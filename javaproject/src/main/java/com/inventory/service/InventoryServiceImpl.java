package com.inventory.service;

import com.inventory.dao.ItemDAO;
import com.inventory.dao.ItemDAOImpl;
import com.inventory.model.Item;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryServiceImpl implements InventoryService {
    private final ItemDAO itemDAO;
    
    public InventoryServiceImpl() {
        this.itemDAO = new ItemDAOImpl();
    }
    
    @Override
    public boolean addItem(Item item) {
        if (!isValidItem(item)) {
            return false;
        }
        return itemDAO.addItem(item);
    }
    
    @Override
    public boolean updateItem(Item item) {
        if (!isValidItem(item)) {
            return false;
        }
        return itemDAO.updateItem(item);
    }
    
    @Override
    public boolean deleteItem(int id) {
        return itemDAO.deleteItem(id);
    }
    
    @Override
    public Item getItem(int id) {
        return itemDAO.getItemById(id);
    }
    
    @Override
    public List<Item> getAllItems() {
        return itemDAO.getAllItems();
    }
    
    @Override
    public List<Item> searchItems(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllItems();
        }
        return itemDAO.searchItems(searchTerm.trim());
    }
    
    @Override
    public List<Item> getItemsByCategory(String category) {
        return itemDAO.getItemsByCategory(category);
    }
    
    @Override
    public boolean adjustQuantity(int itemId, int adjustment) {
        Item item = itemDAO.getItemById(itemId);
        if (item == null) {
            return false;
        }
        
        int newQuantity = item.getQuantity() + adjustment;
        if (newQuantity < 0) {
            newQuantity = 0;
        }
        
        return itemDAO.updateQuantity(itemId, newQuantity);
    }
    
    @Override
    public boolean setQuantity(int itemId, int newQuantity) {
        if (newQuantity < 0) {
            return false;
        }
        return itemDAO.updateQuantity(itemId, newQuantity);
    }
    
    @Override
    public int getTotalItemCount() {
        return itemDAO.getTotalItems();
    }
    
    @Override
    public double getTotalInventoryValue() {
        return itemDAO.getTotalInventoryValue();
    }
    
    @Override
    public List<String> getCategories() {
        return itemDAO.getAllCategories();
    }
    
    @Override
    public List<Item> getLowStockItems(int threshold) {
        return getAllItems().stream()
                .filter(item -> item.getQuantity() <= threshold)
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean isValidItem(Item item) {
        if (item == null) {
            return false;
        }
        
        if (item.getName() == null || item.getName().trim().isEmpty()) {
            return false;
        }
        
        if (item.getCategory() == null || item.getCategory().trim().isEmpty()) {
            return false;
        }
        
        if (item.getQuantity() < 0) {
            return false;
        }
        
        if (item.getPrice() < 0) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean itemExists(String name) {
        return getAllItems().stream()
                .anyMatch(item -> item.getName().equalsIgnoreCase(name.trim()));
    }
}