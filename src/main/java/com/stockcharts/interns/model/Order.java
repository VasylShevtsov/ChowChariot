package com.stockcharts.interns.model;

import java.sql.Timestamp;
import java.util.*;

public class Order {
    private final List<OrderItem> orderItems = new ArrayList<>();
    private int orderID;
    private final int restID;
    private final int userID;
    private final int driverID;
    private double total;
    private String status;
    private Timestamp createdAt;

    private Order(Builder builder) {
        this.orderID = builder.orderID;
        this.restID = builder.restID;
        this.userID = builder.userID;
        this.driverID = builder.driverID;
        this.total = builder.total;
        this.status = builder.status;
        this.createdAt = builder.createdAt;
    }

    // Getters for the fields

    public int getOrderID() {
        return orderID;
    }

    public int getRestID() {
        return restID;
    }

    public int getUserID() {
        return userID;
    }

    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems.clear();
        this.orderItems.addAll(orderItems);
    }

    public void addOrderItem(OrderItem orderItem) {
        for (OrderItem item : orderItems) {
            if (item.getItemID() == orderItem.getItemID()) {
                item.setQuantity(item.getQuantity() + orderItem.getQuantity());
                return;
            }
        }

        orderItems.add(orderItem);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.removeIf((i) -> i.getItemID() == orderItem.getItemID());
    }

    public int getDriverID() {
        return driverID;
    }

    public double getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setOderID(int id) {
        this.orderID = id;
    }

    public void setTotal(double newTotal) {
        total = newTotal;
    }

    private void setStatus(String newStatus) {
        status = newStatus;
    }

    // Builder class

    public static class Builder {
        private int orderID = -1;
        private int restID;
        private int userID;
        private int driverID;
        private double total;
        private String status;
        private Timestamp createdAt;

        public Order build() {
            return new Order(this);
        }

        public Builder orderID(int orderID) {
            this.orderID = orderID;
            return this;
        }

        public Builder restID(int restID) {
            this.restID = restID;
            return this;
        }

        public Builder userID(int userID) {
            this.userID = userID;
            return this;
        }

        public Builder total(double total) {
            this.total = total;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder driverID(int driverID) {
            this.driverID = driverID;
            return this;
        }

        public Builder createdAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        
    }
}
