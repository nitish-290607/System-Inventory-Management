package com.inventory.model;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class ItemTest {
    
    @Test
    public void testItemCreation() {
        Item item = new Item("Test Item", "Test Description", 10, 99.99, "Test Category");
        
        assertEquals("Test Item", item.getName());
        assertEquals("Test Description", item.getDescription());
        assertEquals(10, item.getQuantity());
        assertEquals(99.99, item.getPrice(), 0.01);
        assertEquals("Test Category", item.getCategory());
        assertNotNull(item.getCreatedDate());
        assertNotNull(item.getLastUpdated());
    }
    
    @Test
    public void testItemUpdate() {
        Item item = new Item("Original", "Original Description", 5, 50.0, "Original Category");
        LocalDateTime originalUpdate = item.getLastUpdated();
        
        // Wait a moment to ensure timestamp difference
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // Ignore
        }
        
        item.setName("Updated");
        assertTrue(item.getLastUpdated().isAfter(originalUpdate));
        assertEquals("Updated", item.getName());
    }
    
    @Test
    public void testDefaultConstructor() {
        Item item = new Item();
        assertNotNull(item.getCreatedDate());
        assertNotNull(item.getLastUpdated());
        assertEquals(0, item.getId());
        assertEquals(0, item.getQuantity());
        assertEquals(0.0, item.getPrice(), 0.01);
    }
}