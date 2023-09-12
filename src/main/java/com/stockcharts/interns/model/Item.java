package com.stockcharts.interns.model;

public class Item {
    private int itemID;
    private final int menuID;
    private String itemName;
    private String category;
    private float unitCost;
    
    private Item(Builder builder) {
        this.itemID = builder.itemID;
        this.menuID = builder.menuID;
        this.itemName = builder.itemName;
        this.category = builder.category;
        this.unitCost = builder.unitCost;
    }
    
    // Getters and setters for the fields
    
    public int getItemID() {
        return itemID;
    }

    public void setItemID(int id) {
        this.itemID = id;
    }
    
    public int getMenuID() {
        return menuID;
    }

    public String getItemName() {
        return itemName;
    }
    
    private void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public String getCategory() {
        return category;
    }
    
    private void setCategory(String category) {
        this.category = category;
    }
    
    public float getUnitCost() {
        return unitCost;
    }
    // readded this method after test commit
    private void setUnitCost(float unitCost) {
        this.unitCost = unitCost;
    }
    
    // Builder class
    
    public static class Builder {
        private int itemID = -1;
        private int menuID;
        private String itemName;
        private String category;
        private float unitCost;

        public Item build() {
            return new Item(this);
        }
        
        public Builder itemID(int itemID) {
            this.itemID = itemID;
            return this;
        }
        
        public Builder menuID(int menuID) {
            this.menuID = menuID;
            return this;
        }
        
        public Builder itemName(String itemName) {
            this.itemName = itemName;
            return this;
        }
        
        public Builder category(String category) {
            this.category = category;
            return this;
        }
        
        public Builder unitCost(float unitCost) {
            this.unitCost = unitCost;
            return this;
        }
    }
}
