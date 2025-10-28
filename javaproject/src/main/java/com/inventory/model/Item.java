package com.inventory.model;

import java.time.LocalDateTime;

public class Item {
    private int id;
    private String name;
    private String description;
    private int quantity;
    private double price;
    private String category;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdated;
    
    // Constructors
    public Item() {
        this.createdDate = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
    }
    
    public Item(String name, String description, int quantity, double price, String category) {
        this();
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { 
        this.name = name;
        this.lastUpdated = LocalDateTime.now();
    }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { 
        this.description = description;
        this.lastUpdated = LocalDateTime.now();
    }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { 
        this.quantity = quantity;
        this.lastUpdated = LocalDateTime.now();
    }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { 
        this.price = price;
        this.lastUpdated = LocalDateTime.now();
    }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { 
        this.category = category;
        this.lastUpdated = LocalDateTime.now();
    }
    
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
    
    @Override
    public String toString() {
        return String.format("Item{id=%d, name='%s', quantity=%d, price=%.2f}", 
                           id, name, quantity, price);
    }
}