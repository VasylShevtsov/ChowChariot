package com.stockcharts.interns.dao;

import java.util.List;

import com.stockcharts.interns.model.Order;
import com.stockcharts.interns.model.OrderItem;

public interface OrderDAO extends DAO<Order> {
    Order get(int orderID);
    int create(Order order);
    boolean update(Order order);
    boolean delete(int orderID);
    List<Order> getAllOrders();
}

