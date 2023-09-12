package com.stockcharts.interns.dao.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.logging.log4j.*;

import com.stockcharts.interns.HomeServlet;
import com.stockcharts.interns.dao.OrderDAO;
import com.stockcharts.interns.dao.UserDAO;
import com.stockcharts.interns.model.*;

public class UserMem implements UserDAO {
    private static Map<Integer, Customer> users = new HashMap<>();
    public static int currentKey = 0;

    public Customer get(int userID) {
        return users.getOrDefault(userID, null);
    }

    public int create(Customer user) {
        if (users.containsKey(user.getUserID()))
            return -1;

        for (Customer u : getAllUsers()) 
            if (user.getEmail().equals(u.getEmail()))
                return -1;
        
        users.put(currentKey, user);
        user.setUserID(currentKey);
        return currentKey++;
    }

    public boolean update(Customer user) {
        if (!users.containsKey(user.getUserID()))
            return false;
        users.put(user.getUserID(), user);
        return true;
    }

    public boolean delete(int userID) {
        if (!users.containsKey(userID))
            return false;
        
        users.remove(userID);
        return true;
    }

    public List<Order> getPreviousOrders(Customer user) {
        OrderDAO orderDAO = new OrderMem();

        List<Order> allOrders = orderDAO.getAllOrders();
        List<Order> prevUserOrders = new ArrayList<>();

        for (Order order : allOrders) 
            if (order.getUserID() == user.getUserID() && order.getStatus().equals("past"))
                prevUserOrders.add(order);
        
        return prevUserOrders;

    }

    public List<Order> getCurrentOrders(Customer user) {
        OrderDAO orderDAO = new OrderMem();
        
        List<Order> allOrders = orderDAO.getAllOrders();
        List<Order> currentUserOrders = new ArrayList<>();

        for (Order order : allOrders) 
            if (order.getUserID() == user.getUserID() && order.getStatus().equals("current"))
                currentUserOrders.add(order);
        
        return currentUserOrders;


    }

    public List<Customer> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public int authenticate(String email, String password) {
        for (int id : users.keySet()) {
            Customer user = users.get(id);

            if (user.getEmail().equals(email) && user.getHash().equals(password))
                return id;
        }

        return -1;
    }
}
