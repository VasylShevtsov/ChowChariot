package com.stockcharts.interns.dao.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.stockcharts.interns.HomeServlet;
import com.stockcharts.interns.commons2.sql.SQLResults;
import com.stockcharts.interns.commons2.sql.SQLUtil;
import com.stockcharts.interns.dao.*;
import com.stockcharts.interns.model.*;
import com.stockcharts.interns.utils.DAOs;

public class RestaurantMem implements RestaurantDAO {
    private static Map<Integer, Restaurant> restaurants = new HashMap<>();
    private static int currentKey = 0;

    @Override
    public Restaurant get(int restID) {
        return restaurants.getOrDefault(restID, null);
    }

    @Override
    public int create(Restaurant restaurant) {
        if (restaurants.containsKey(restaurant.getRestID()))
            return -1;

        for (Restaurant r : getAllRestaurants()) 
            if (r.getEmail().equals(restaurant.getEmail()))
                return -2;
        
        HomeServlet.logger.info("email: " + restaurant.getEmail() + " pass: " + restaurant.getHash());
        if (restaurant.getEmail() == null || restaurant.getHash() == null) 
            return -3;

        restaurants.put(currentKey, restaurant);
        restaurant.setRestID(currentKey);
        return currentKey++;
    }

    @Override
    public boolean update(Restaurant restaurant) {
        if (!restaurants.containsKey(restaurant.getRestID()))
            return false;

        restaurants.put(restaurant.getRestID(), restaurant);
        return true;
    }

    @Override
    public boolean delete(int restID) {
        if (!restaurants.containsKey(restID))
            return false;
        
        restaurants.remove(restID);
        return true;
    }

    @Override
    public List<Order> getCurrentOrders(Restaurant restaurant) {
        OrderDAO orderDAO = new OrderMem();

        List<Order> allOrders = orderDAO.getAllOrders();
        List<Order> restaurantOrders = new ArrayList<>();

        for (Order order : allOrders)
            if (order.getRestID() == restaurant.getRestID() && "current".equals(order.getStatus()))
                restaurantOrders.add(order);

        return restaurantOrders;

    }

    @Override
    public List<Order> getPreviousOrders(Restaurant restaurant) {
        OrderDAO orderDAO = new OrderMem();

        List<Order> allOrders = orderDAO.getAllOrders();
        List<Order> restaurantOrders = new ArrayList<>();

        for (Order order : allOrders)
            if (order.getRestID() == restaurant.getRestID() && "past".equals(order.getStatus()))
                restaurantOrders.add(order);

        return restaurantOrders;

    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return new ArrayList<>(restaurants.values());
    }

    @Override
    public int totalOrdersFulfilled(int restID) {
        Restaurant restaurant = restaurants.getOrDefault(restID, null);
        return getPreviousOrders(restaurant).size();
    }

    @Override
    public double totalIncome(int restID) {
        Restaurant restaurant = restaurants.getOrDefault(restID, null);
        List<Order> orders = getPreviousOrders(restaurant);
        double total = 0;
        for (Order order : orders) {
            total += order.getTotal();
        }
        return total;
    }

    public int authenticate(String email, String hash) {
        HomeServlet.logger.info(getAllRestaurants());

        for (Restaurant r : getAllRestaurants())
            if (r.getEmail() != null && 
                r.getEmail().equals(email) && 
                r.getHash() != null && 
                r.getHash().equals(hash))
                return r.getRestID();

        return -1;
    }

    public List<Item> getMenuItems(int restID) {
        if (!restaurants.containsKey(restID))
            return null;


        return DAOs.itemDAO
            .getAllItems()
            .stream()
            .filter((i) -> i.getMenuID() == restID)
            .collect(Collectors.toList());
    }
    
}
