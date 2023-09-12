package com.stockcharts.interns.dao.impl;

import java.util.*;

import com.stockcharts.interns.dao.*;
import com.stockcharts.interns.model.*;

public class OrderMem implements OrderDAO {
    private static Map<Integer, Order> orders = new HashMap<>();
    private static int currentKey = 0;

    public Order get(int orderID) {
        return orders.getOrDefault(orderID, null);
    }

    public int create(Order order) {
        if (orders.containsKey(order.getOrderID()))
            return -1;

        orders.put(currentKey, order);
        order.setOderID(currentKey);
        return currentKey++;
        
    }

    public boolean update(Order order) {
        if (!orders.containsKey(order.getOrderID()))
            return false;
        
        orders.put(order.getOrderID(), order);
        return true;
    }

    public boolean delete(int orderID) {
        if (!orders.containsKey(orderID))
            return false;
        
        orders.remove(orderID);
        return true;
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }
}
