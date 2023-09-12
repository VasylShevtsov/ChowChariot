package com.stockcharts.interns.dao;

import java.util.List;

import com.stockcharts.interns.model.*;

public interface RestaurantDAO extends DAO<Restaurant> {
    Restaurant get(int restID);
    int create(Restaurant restaurant);
    boolean update(Restaurant restaurant);
    boolean delete(int restID);
    List<Order> getCurrentOrders(Restaurant restaurant);
    List<Order> getPreviousOrders(Restaurant restaurant);
    List<Restaurant> getAllRestaurants();
    int totalOrdersFulfilled(int restID);
    double totalIncome(int restID);
    int authenticate(String email, String hash);
    List<Item> getMenuItems(int restID);
}

