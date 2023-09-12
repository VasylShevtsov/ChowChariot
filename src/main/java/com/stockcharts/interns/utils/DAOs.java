package com.stockcharts.interns.utils;

import com.stockcharts.interns.dao.*;
import com.stockcharts.interns.dao.impl.*;
import com.stockcharts.interns.model.*;

public class DAOs {
    public static UserDAO userDAO = new UserMem();
    public static RestaurantDAO restaurantDAO = new RestaurantMem();
    public static DriverDAO driverDAO = new DriverMem();
    public static MenuDAO menuDAO = new MenuMem();
    public static OrderItemDAO itemDAO = new ItemMem();
    public static OrderDAO orderDAO = new OrderMem();
};
