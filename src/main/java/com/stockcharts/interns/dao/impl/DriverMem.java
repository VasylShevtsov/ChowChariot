package com.stockcharts.interns.dao.impl;

import java.util.*;

import com.stockcharts.interns.dao.*;
import com.stockcharts.interns.model.*;

public class DriverMem implements DriverDAO {
    private static Map<Integer, Driver> drivers = new HashMap<>();
    private static int currentKey = 0;

    public Driver get(int driverID) {
        return drivers.getOrDefault(driverID, null);
    }

    public boolean delete(int driverID) {
        if (!drivers.containsKey(driverID))
            return false;

        drivers.remove(driverID);
        return true;
    }

    public boolean update(Driver driver) {
        if (!drivers.containsKey(driver.getDriverID()))
            return false;

        drivers.put(driver.getDriverID(), driver);
        return true;
    }

    public int create(Driver driver) {
        if (drivers.containsKey(driver.getDriverID()))
            return -1;

        for (Driver d : getAllDrivers())
            if (d.getEmail().equals(driver.getEmail()))
                return -1;

        drivers.put(currentKey, driver);
        driver.setDriverID(currentKey);
        return currentKey++;
    }

    public List<Order> getCurrentAssignment(Driver driver) {
        OrderDAO orderDAO = new OrderMem();

        List<Order> allOrders = orderDAO.getAllOrders();
        List<Order> currentOrders = new ArrayList<>();

        for (Order order : allOrders)
            if (order.getDriverID() == driver.getDriverID() && order.getStatus().equals("current"))
                currentOrders.add(order);
        
        return currentOrders;
    }

    public List<Driver> getAllDrivers() {
        return new ArrayList<>(drivers.values());
    }

    @Override
    public int totalDeliveries(int driverID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'totalDeliveries'");
    }

    @Override
    public int totalIncome(int driverID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'totalIncome'");
    }

    public int authenticate(String email, String hash){
        for (Driver  d : getAllDrivers()) 
            if (d.getEmail().equals(email) && d.getHash().equals(hash))
                return d.getDriverID();
        
        return -1;
    }

}
