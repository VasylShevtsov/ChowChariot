package com.stockcharts.interns.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.logging.log4j.*;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.stockcharts.interns.commons2.sql.*;
import com.stockcharts.interns.dao.OrderDAO;
import com.stockcharts.interns.model.*;

public class OrderDB implements OrderDAO {
    private static final Logger logger = LogManager.getLogger();
    private ComboPooledDataSource pool;
    
    public OrderDB(ComboPooledDataSource pool) {
        this.pool = pool;
    }

    public Order get(int orderID) {
        String query = "SELECT * FROM Orders WHERE orderID = ?;";

        try {
            SQLResults results = SQLUtil.executeSelect(pool, query, orderID);
            List<SQLRow> rows = results.getRows();

            if (rows == null || rows.isEmpty())
                return null;
            
            SQLRow singleRow = rows.get(0);

            Order newOrder = new Order.Builder()
                .orderID(orderID)
                .restID(singleRow.getInteger("restID"))
                .userID(singleRow.getInteger("userID"))
                .driverID(singleRow.getInteger("driverID"))
                .total(singleRow.getFloat("total"))
                .status(singleRow.get("status"))
                .createdAt((Timestamp) singleRow.getObject("createdAt"))
                .build();
    
            return newOrder;


        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when getting an orderByID:", e);
        }
        return null;
    }

    public int create(Order order) {
        String query = "INSERT INTO Orders (restID, userID, total, status) VALUES (?, ?, ?, ?);";
        
        try {
            int result = SQLUtil.executeInsert(pool, query, 
                order.getRestID(),
                order.getUserID(),
                order.getTotal(),
                order.getStatus()
            );

            return result;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when adding an order", e);
        }
        return -1;
    }

    public boolean update(Order order) {
        String query = "UPDATE Orders SET total = ?, status = ? WHERE orderID = ?;";
        String removeItems = "DELETE FROM OrderItems WHERE orderID = ?";
        String addItems = "INSERT INTO OrderItems (orderID, itemID, quantity) VALUES (?, ?, ?)";

        try {
            boolean result = SQLUtil.executeUpdate(pool, query, 
                order.getTotal(),
                order.getStatus(),
                order.getDriverID()
            );

            result = result && SQLUtil.executeDelete(pool, removeItems, order.getOrderID());

            for (OrderItem item : order.getOrderItems())
                SQLUtil.executeInsert(pool, addItems, 
                    item.getOrderID(),
                    item.getItemID(),
                    item.getQuantity()
                );

            return result;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when updating an order", e);
        }
        return false;
    }

    public boolean delete(int orderID) {
        String query = "DELETE FROM Orders WHERE orderID = ?;";
    
        try {
            boolean result = SQLUtil.executeDelete(pool, query, orderID);
            return result;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when deleting an order", e);
        }
        return false;
    }   

    public List<Order> getAllOrders() {
        String query = "SELECT * FROM Orders;";
    
        try {
            SQLResults results = SQLUtil.executeSelect(pool, query);
            List<SQLRow> rows = results.getRows();
            List<Order> allOrders = new ArrayList<>();

            if (rows == null || rows.isEmpty())
                return Collections.emptyList();

            for (SQLRow row : rows) {
                allOrders.add(new Order.Builder()
                    .orderID(row.getInteger("orderID"))
                    .restID(row.getInteger("restID"))
                    .userID(row.getInteger("userID"))
                    .driverID(row.getInteger("driverID"))
                    .total(row.getFloat("total"))
                    .status(row.get("status"))
                    .createdAt((Timestamp)row.getObject("createdAt"))
                    .build());
            }
            
            return allOrders;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when getting all orders", e);
        }
        return Collections.emptyList();
    }
}
