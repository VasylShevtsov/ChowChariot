package com.stockcharts.interns.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.stockcharts.interns.commons2.sql.SQLResults;
import com.stockcharts.interns.commons2.sql.SQLRow;
import com.stockcharts.interns.commons2.sql.SQLUtil;
import com.stockcharts.interns.dao.RestaurantDAO;
import com.stockcharts.interns.model.Item;
import com.stockcharts.interns.model.Order;
import com.stockcharts.interns.model.Restaurant;

public class RestaurantDB implements RestaurantDAO {

    private static final Logger logger = LogManager.getLogger();
    private ComboPooledDataSource pool;
    
    public RestaurantDB(ComboPooledDataSource pool) {
        this.pool = pool;
    }

    @Override
    public Restaurant get(int restID) {
        String query = "SELECT * FROM Restaurants WHERE restID = ?";
        try {
            SQLResults results = SQLUtil.executeSelect(pool, query, restID);
            List<SQLRow> rows = results.getRows();

            if (rows == null || rows.isEmpty())
                return null;

            SQLRow singleRow = rows.get(0);
            Restaurant newRest = new Restaurant.Builder()
                .restID(restID)
                .name(singleRow.get("name"))
                .menuID(singleRow.getInteger("menuID"))
                .hash(singleRow.get("hash"))
                .email(singleRow.get("email"))
                .cuisineType(singleRow.get("cuisineType"))
                .location(singleRow.get("location"))
                .phone(singleRow.get("phone"))
                .hours(singleRow.get("hours"))
                .build();
            
            return newRest;
        } catch (SQLException e) {
            logger.error("An unexpected error occurred getting a restaurant byID", e);
        }
        return null;
    }

    @Override
    public int create(Restaurant restaurant) {
        String query = "INSERT INTO Restaurants (restID, name, menuID, location, phone, hours, hash) VALUES (NULL, ?, ?, ?, ?, ?, ?)";
        try {
            int result = SQLUtil.executeInsert(pool, query, 
                restaurant.getRestID(),
                restaurant.getName(),
                restaurant.getMenuID(),
                restaurant.getLocation(),
                restaurant.getPhone(),
                restaurant.getHours(),
                restaurant.getHash()
            );
            return result;
        } catch (SQLException e) {
            logger.error("An unexpected error occurred when adding a restaurant", e);
        }
        return -1;
    }

    @Override
    public boolean update(Restaurant restaurant) {
        String query = "UPDATE Restaurants SET name = ?, location = ?, phone = ?, hours = ? WHERE restID = ?";
        try {
            boolean result = SQLUtil.executeUpdate(pool, query, 
                restaurant.getName(),
                restaurant.getLocation(),
                restaurant.getPhone(),
                restaurant.getHours(),
                restaurant.getRestID()
            );
            return result;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when updating a restaurant", e);
        }
        return false;
    }

    @Override
    public boolean delete(int restID) {
        String query = "DELETE FROM Restaurants WHERE restID = ?";
        try {
            boolean result = SQLUtil.executeDelete(pool, query, restID);
            return result;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when deleting a restaurant", e);
        }    
        return false;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        String query = "SELECT * FROM Restaurants";

        try {
            SQLResults results = SQLUtil.executeSelect(pool, query);
            List<SQLRow> rows = results.getRows();

            if (rows == null || rows.isEmpty())
                return Collections.emptyList();

            List<Restaurant> allRestaurants = new ArrayList<>();
            for (SQLRow row : rows) {
                new Restaurant.Builder()
                    .restID(row.getInteger("restID"))
                    .name(row.get("name"))
                    .menuID(row.getInteger("menuID"))
                    .hash(row.get("hash"))
                    .email(row.get("email"))
                    .cuisineType(row.get("cuisineType"))
                    .location(row.get("location"))
                    .phone(row.get("phone"))
                    .hours(row.get("hours"))
                    .build();
            }
            return allRestaurants;
            
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when getting all restaurants", e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Order> getCurrentOrders(Restaurant restaurant) {
        String query = "SELECT * FROM Orders AS o WHERE o.restID == ? AND o.status = 'current';";

        try {
            SQLResults results = SQLUtil.executeSelect(pool, query, restaurant.getRestID());
            List<SQLRow> rows = results.getRows();

            if (rows == null || rows.isEmpty()) 
                return Collections.emptyList();
            
            List<Order> currentOrders = new ArrayList<>();
            for (SQLRow row : rows) {
                currentOrders.add(new Order.Builder()
                    .orderID(row.getInteger("orderID"))
                    .restID(restaurant.getRestID())
                    .userID(row.getInteger("userID"))
                    .driverID(row.getInteger("driverID"))
                    .total(row.getDouble("total"))
                    .status(row.get("status"))
                    .createdAt((Timestamp)row.getObject("createdAt"))
                    .build());
            }

            return currentOrders;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when getting all restaurants current orders", e);
            
        }
        return Collections.emptyList();
    }

    @Override
    public List<Order> getPreviousOrders(Restaurant restaurant) {
        String query = "SELECT * FROM Orders AS o WHERE o.restID == ? AND o.status = 'past';";

        try {
            SQLResults results = SQLUtil.executeSelect(pool, query, restaurant.getRestID());
            List<SQLRow> rows = results.getRows();

            if (rows == null || rows.isEmpty()) 
                return Collections.emptyList();
            
            List<Order> pastOrders = new ArrayList<>();
            for (SQLRow row : rows) {
                pastOrders.add(new Order.Builder()
                    .orderID(row.getInteger("orderID"))
                    .restID(restaurant.getRestID())
                    .userID(row.getInteger("userID"))
                    .driverID(row.getInteger("driverID"))
                    .total(row.getDouble("total"))
                    .status(row.get("status"))
                    .createdAt((Timestamp)row.getObject("createdAt"))
                    .build());
            }

            return pastOrders;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when getting all restaurants past orders", e);
        }
        return Collections.emptyList();
    }

    public int totalOrdersFulfilled(int restID) {
        String query = "SELECT COUNT(*) AS numberOfDeliveries FROM Orders WHERE restID = ?";
        try {
            SQLResults results = SQLUtil.executeSelect(pool, query, restID);
            List<SQLRow> rows = results.getRows();

            if (rows == null || rows.isEmpty())
                return -1;
            
            return rows.get(0).getInteger("numberOfDeliveries");
        }catch (SQLException e) {
            logger.error("Unexpected exception encountered when getting all restaurants total fulfilled orders", e);
        }

        return -1;
    }

    public double totalIncome(int restID) {
        String query = "SELECT SUM(total) AS totalIncome FROM Orders WHERE restID = ?";
        try {
            SQLResults results = SQLUtil.executeSelect(pool, query, restID);
            List<SQLRow> rows = results.getRows();

            if (rows == null || rows.isEmpty())
                return -1;
            
            return rows.get(0).getInteger("totalIncome");
        } catch (Exception e) {
            logger.error("Unexpected exception encountered when getting all restaurants total income", e);
        }

        return -1;
    }

    public int authenticate(String email, String hash) {
        String query = "SELECT restID from Restaurants as R where R.email = ? AND R.hash = ?;";

        try {
            SQLResults result = SQLUtil.executeSelect(pool, query, email, hash);
        } catch (Exception e) {
            logger.error("Unexpected exception encountered when Authenticating the Restaurant Admin", e);
        }

        return -1;
    }

    public List<Item> getMenuItems(int restID) {
        String query = "SELECT i.itemID, i.itemName, i.category, i.unitCost, i.menuID FROM Restaurants r JOIN Menus m ON r.menuID = m.menuID JOIN Items i ON m.menuID = i.menuID WHERE r.restID = ?;";

        try {
            SQLResults results = SQLUtil.executeSelect(pool, query, restID);

            List<Item> items = new ArrayList<>();
            
            for (SQLRow row : results.getRows())
                items.add(new Item.Builder()
                    .itemID(row.getInteger("itemID"))
                    .category(row.getString("category"))
                    .unitCost(row.getFloat("unitCost"))
                    .menuID(row.getInteger("menuID"))
                    .build()
                );

            return items;
        } catch (Exception e) {
            logger.error("Unexpected exception encountered when retrieving menu items", e);
        }
        return null;
    }
}
