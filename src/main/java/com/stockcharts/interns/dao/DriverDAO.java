package com.stockcharts.interns.dao;

import java.util.List;

import com.stockcharts.interns.model.Driver;
import com.stockcharts.interns.model.Order;

public interface DriverDAO extends DAO<Driver> {
    Driver get(int driverID);
    boolean delete(int driverID);
    boolean update(Driver driver);
    int create(Driver driver);
    List<Order> getCurrentAssignment(Driver driver);
    List<Driver> getAllDrivers();
    int totalDeliveries(int driverID);
    int totalIncome(int driverID);
    int authenticate(String email, String hash);
}
