package com.stockcharts.interns.model;

public class OrderItem {
    private int orderID;
    private int itemID;
    private int quantity;

    public OrderItem(int orderID, int itemID, int quantity) {
        setOrderID(orderID);
        setItemID(itemID);
        setQuantity(quantity);
    }

    // =============================== getters ==================================== \\

    public int getOrderID() { 
        return orderID;
    }

    public int getItemID() {
        return itemID;
    }

    public int getQuantity() {
        return quantity;
    }

    // =============================== setters ==================================== \\

    private void setOrderID(int newOrderID) {
        orderID = newOrderID;
    }

    private void setItemID(int newItemID) {
        itemID = newItemID;
    }

    public void setQuantity(int newQuantity) {
        quantity = newQuantity;
    }

    public boolean equals(OrderItem other) {
        return this.itemID == other.itemID;
    }

}
