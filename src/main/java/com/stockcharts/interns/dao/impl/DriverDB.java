package com.stockcharts.interns.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.*;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.stockcharts.interns.commons2.sql.SQLResults;
import com.stockcharts.interns.commons2.sql.SQLRow;
import com.stockcharts.interns.commons2.sql.SQLUtil;
import com.stockcharts.interns.dao.DriverDAO;
import com.stockcharts.interns.model.Driver;
import com.stockcharts.interns.model.Order;

public class DriverDB implements DriverDAO {
    private static final Logger logger = LogManager.getLogger();
    private ComboPooledDataSource pool;
    
    public DriverDB(ComboPooledDataSource pool) {
        this.pool = pool;
    }

    public Driver get(int driverID) {
        String query = "SELECT * FROM Driver WHERE driverID = ?";

        try {
            SQLResults results = SQLUtil.executeSelect(pool, query, driverID);
            List<SQLRow> rows = results.getRows();

            if (rows == null || rows.isEmpty()) 
                return null;
            
            SQLRow singleRow = rows.get(0);
            Driver newDriver = new Driver.Builder()
                .withDriverID(driverID)
                .withFirstName(singleRow.get("firstName"))
                .withEmail(singleRow.get("email"))
                .withHash(singleRow.get("hash"))
                .withPhone(singleRow.get("phone"))
                .withLocation(singleRow.get("location"))
                .build();

            return newDriver;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when getting a driverByID:", e);
        }
        return null;
    }

    public boolean delete(int driverID) {
        String query = "DELETE FROM Driver WHERE driverID = ?;";

        try {
            boolean result = SQLUtil.executeDelete(pool, query, driverID);
            return result;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when deleting a driver", e);
        }
        return false;
    }

    public boolean update(Driver driver) {
        String query = "UPDATE Driver SET firstName = ?, lastName = ?, email = ?, hash = ?, phone = ?, location = ? WHERE driverID = ?";

        try {
            boolean result = SQLUtil.executeUpdate(pool, query, 
                driver.getFirstName(),
                driver.getLastName(),
                driver.getEmail(),
                driver.getHash(),
                driver.getPhone(),
                driver.getLocation(),
                driver.getDriverID()
            );
            return result;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when updating a user", e);
        }
        return false;
    }

    public int create(Driver driver) {
        String query = "INSERT INTO Driver (driverID, firstName, lastName, email, hash, phone, location) VALUES (NULL, ?, ?, ?, ?, ?, ?)";

        try {
            int result = SQLUtil.executeInsert(pool, query, 
                driver.getFirstName(),
                driver.getLastName(),
                driver.getEmail(),
                driver.getHash(),
                driver.getPhone(),
                driver.getLocation()
            );
            return result;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when adding a user", e);
        }
        return -1;
    }

    public List<Order> getCurrentAssignment(Driver driver) {
        String query = "SELECT * FROM Orders WHERE driverID = ? AND status = 'assigned' JOIN Users ON Orders.userID = Users.userID";

        try {
            SQLResults results = SQLUtil.executeSelect(pool, query, driver.getDriverID());
            List<SQLRow> rows = results.getRows();
            
            if (rows == null || rows.isEmpty()) 
                return Collections.emptyList();

            List<Order> currentAssignments = new ArrayList<>();
            for (SQLRow row : rows) {
                currentAssignments.add(new Order.Builder()
                    .orderID(row.getInteger("orderID"))
                    .restID(row.getInteger("restID"))
                    .userID(row.getInteger("userID"))
                    .driverID(row.getInteger("driverID"))
                    .total(row.getFloat("total"))
                    .status(row.get("status"))
                    .createdAt((Timestamp) row.getObject("createdAt"))
                    .build());
            }
            return currentAssignments;

        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when getting currentAssignments", e);
        }
        return Collections.emptyList();
    }

    public List<Driver> getAllDrivers() {
        String query = "SELECT * FROM Driver";

        try {
            SQLResults results = SQLUtil.executeSelect(pool, query);
            List<SQLRow> rows = results.getRows();

            if (rows == null || rows.isEmpty()) 
                return Collections.emptyList();

            List<Driver> allDrivers = new ArrayList<>();
            for (SQLRow row : rows) {
                allDrivers.add(new Driver.Builder()
                .withDriverID(row.getInteger("driverID"))
                .withFirstName(row.get("firstName"))
                .withEmail(row.get("email"))
                .withHash(row.get("hash"))
                .withPhone(row.get("phone"))
                .withLocation(row.get("location"))
                .build());
            }

        } catch (SQLException e) {
            logger.error("An unexpected error occurred when getting the all the drivers", e);

        }
        return Collections.emptyList();
    } 

    public int totalDeliveries(int driverID) {
        String query = "SELECT COUNT(*) AS numberOfDeliveries FROM Orders WHERE driverID = ?";
        try {
            SQLResults results = SQLUtil.executeSelect(pool, query, driverID);
            List<SQLRow> rows = results.getRows();

            if (rows == null || rows.isEmpty()) 
                return -1;
            
            SQLRow singleRow = rows.get(0);
            return singleRow.getInteger("numberOfDeliveries");
        } catch (SQLException e) {
            logger.error("An unexpected error occurred when getting the total deliveries of the driver", e);
        }

        return -1;
    }

    public int totalIncome(int driverID) {
        String query = "SELECT SUM(total) AS totalIncome FROM Orders WHERE driverID = ?";
        try {
            SQLResults results = SQLUtil.executeSelect(pool, query, driverID);
            List<SQLRow> rows = results.getRows();

            if (rows == null || rows.isEmpty()) 
                return -1;
            
            SQLRow singleRow = rows.get(0);
            return singleRow.getInteger("totalIncome");
        } catch (SQLException e) {
                logger.error("An unexpected error occurred when getting the total income of the driver", e);

        }

        return -1;
    }

    public int authenticate(String email, String hash) {
        String query = "SELECT driverID from Drivers as D where D.email = ? AND D.hash = ?;";

        try {
            SQLResults result = SQLUtil.executeSelect(pool, query, email, hash);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return -1;
    }

}


