package com.stockcharts.interns.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.sql.Timestamp;

import org.apache.logging.log4j.*;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.stockcharts.interns.commons2.sql.*;
import com.stockcharts.interns.dao.UserDAO;
import com.stockcharts.interns.model.Order;
import com.stockcharts.interns.model.Customer;

public class UserDB implements UserDAO {
    private ComboPooledDataSource pool;
    private static final Logger logger = LogManager.getLogger();

    public UserDB(ComboPooledDataSource pool) {
        this.pool = pool;
    }

    public Customer get(int userID) {
        String query = "SELECT * FROM Users WHERE userID = ?";
        try {
            SQLResults results = SQLUtil.executeSelect(pool, query, userID);
            List<SQLRow> rows = results.getRows();

            if (rows == null || rows.isEmpty()) 
                return null;
            
            SQLRow singleRow = rows.get(0);
            Customer newUser = new Customer.Builder()
                .userID(userID)
                .firstName(singleRow.get("firstName"))
                .email(singleRow.get("email"))
                .hash(singleRow.get("hash"))
                .phone(singleRow.get("phone"))
                .location(singleRow.get("location"))
                .build();

            return newUser;

            
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when getting a userByID:", e);
        }
        return null;
    }

    public int create(Customer user) {
        String query = "INSERT INTO Users (userID, firstName, lastName, email, hash, phone, location) VALUES (NULL, ?, ?, ?, ?, ?, ?)";
        try {
            int result = SQLUtil.executeInsert(pool, query,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getHash(),
                user.getPhone(),
                user.getLocation());

            return result;

        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when adding a user", e);
        }
        return -1;
    }

    public boolean update(Customer user) {
        String query = "UPDATE Users SET firstName = ?, lastName = ?, email = ?, hash = ?, phone = ?, location = ?";
        try {
            boolean result = SQLUtil.executeUpdate(pool, query, 
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getHash(),
                user.getPhone(),
                user.getLocation());
            return result;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when updating a user", e);
        }
        return false;
    }

    public boolean delete(int userID) {
        String query = "DELETE FROM Users WHERE userID = ?";
        try {
            boolean result = SQLUtil.executeDelete(pool, query, userID);
            return result;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when deleting a user", e);
        }
        return false;
    }

    public List<Customer> getAllUsers() {
        String query = "SELECT * FROM Users";
        try {
            SQLResults result = SQLUtil.executeSelect(pool, query);
            List<SQLRow> rows = result.getRows();

            if (rows == null || rows.isEmpty())
                return Collections.emptyList();

            List<Customer> allUsers = new ArrayList<>();
            for (SQLRow row : rows) {
                allUsers.add(new Customer.Builder()
                    .userID(row.getInteger("userID"))
                    .firstName(row.get("firstName"))
                    .email(row.get("email"))
                    .hash(row.get("hash"))
                    .phone(row.get("phone"))
                    .location(row.get("location"))
                    .build());
            }
            
            return allUsers;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when getting all users", e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Order> getPreviousOrders(Customer user) {
        String query = "SELECT * FROM Orders JOIN Users on Orders.userID = ? AND status = 'past'";
        try {
            SQLResults result = SQLUtil.executeSelect(pool, query, user.getUserID());
            List<SQLRow> rows = result.getRows();

            if (rows == null || rows.isEmpty())
                return Collections.emptyList();

            List<Order> previousOrders = new ArrayList<>();
            for (SQLRow row : rows) {
                previousOrders.add(new Order.Builder()
                    .orderID(row.getInteger("orderID"))
                    .restID(row.getInteger("restID"))
                    .userID(row.getInteger("userID"))
                    .driverID(row.getInteger("driverID"))
                    .total(row.getFloat("total"))
                    .status(row.get("status"))
                    .createdAt((Timestamp) row.getObject("createdAt"))
                    .build());
            }

            return previousOrders;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when getting previous orders", e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Order> getCurrentOrders(Customer user) {
        String query = "SELECT * FROM Orders JOIN Users on Orders.userID = ? AND status = 'current'";
        try {
            SQLResults result = SQLUtil.executeSelect(pool, query, user.getUserID());
            List<SQLRow> rows = result.getRows();

            if (rows == null || rows.isEmpty())
                return Collections.emptyList();

            List<Order> currentOrders = new ArrayList<>();
            for (SQLRow row : rows) {
                currentOrders.add(new Order.Builder()
                    .orderID(row.getInteger("orderID"))
                    .restID(row.getInteger("restID"))
                    .userID(row.getInteger("userID"))
                    .driverID(row.getInteger("driverID"))
                    .total(row.getFloat("total"))
                    .status(row.get("status"))
                    .createdAt((Timestamp) row.getObject("createdAt"))
                    .build());
            }

            return currentOrders;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when getting current orders", e);
        }
        return Collections.emptyList();
        

    }

    public int authenticate(String email, String password) {
        List<Customer> users = getAllUsers();

        for (Customer user : users) {
            if (user.getEmail().equals(email) && user.getHash().equals(password))
                return user.getUserID();
        }

        return -1;

    }
}
