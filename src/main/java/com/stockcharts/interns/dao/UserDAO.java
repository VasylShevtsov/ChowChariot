package com.stockcharts.interns.dao;

import java.util.List;

import com.stockcharts.interns.model.Order;
import com.stockcharts.interns.model.Customer;

public interface UserDAO extends DAO<Customer> {
    Customer get(int userID);
    int create(Customer user);
    boolean update(Customer user);
    boolean delete(int userID);
    List<Order> getPreviousOrders(Customer user);
    List<Order> getCurrentOrders(Customer user);
    List<Customer> getAllUsers();
    int authenticate(String email, String password);
}
