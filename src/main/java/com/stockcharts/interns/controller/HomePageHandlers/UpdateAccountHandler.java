package com.stockcharts.interns.controller.HomePageHandlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.*;
import javax.servlet.http.*;

import com.stockcharts.interns.HomeServlet;
import com.stockcharts.interns.controller.Command;
import com.stockcharts.interns.model.*;
import com.stockcharts.interns.utils.DAOs;
import com.stockcharts.interns.utils.HttpUtils;

import freemarker.template.*;

public class UpdateAccountHandler implements Command {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            if (session == null) 
                response.sendRedirect("/chowchariot/home?cmd=home");

            String accountType = (String) session.getAttribute("type");
            Integer recid = (Integer) session.getAttribute("userID"); 

            if (accountType == null || accountType.isBlank() || recid == null) 
                response.sendRedirect("/chowchariot/home?cmd=home");

            String fieldType = request.getParameter("fieldType");
            String fieldValue = request.getParameter(fieldType);
            boolean result;

            switch (accountType) {
                case "customer":
                    Customer user = DAOs.userDAO.get(recid);
                    result = DAOs
                        .userDAO
                        .update(updateUser(fieldType, fieldValue, user));
                    break;
                case "restaurant":
                    Restaurant restaurant = DAOs.restaurantDAO.get(recid);
                    result = DAOs
                        .restaurantDAO
                        .update(updateRestaurant(fieldType, fieldValue, restaurant));
                    break;
                case "driver":
                    Driver driver = DAOs.driverDAO.get(recid);
                    result = DAOs
                        .driverDAO
                        .update(updateDriver(fieldType, fieldValue, driver));
                    break;
                default:
                    result = false;
                    HomeServlet.logger.error("no account type specified to updateAccount handler");
            }

            if (result == false)
                HomeServlet.logger.error("error while updating account");

            response.sendRedirect("/chowchariot/home?page=dashboard&cmd=" + accountType);
            
        } catch (Exception e) {
            HomeServlet.logger.error("Unexpected exception encountered when processing: {}", HttpUtils.getRequestURL(request), e);
        } 
    }

    private Customer updateUser(String field, String value, Customer user) {
            switch (field) {
                case "firstName":
                    user.setFirstName(value);
                    break;
                case "lastName":
                    user.setLastName(value);
                    break;
                case "email":
                    user.setEmail(value);
                    break;
                case "password":
                    user.setHash(value);
                    break;
                case "phone":
                    user.setPhone(value);
                    break;
                case "location":
                    user.setLocation(value);
                    break;
                default:
            }

            return user;
        }

        private Restaurant updateRestaurant(String field, String value, Restaurant restaurant) {
            
            switch (field) {
                case "name":
                    restaurant.setName(value);
                    break;
                case "email":
                    restaurant.setEmail(value);
                    break;
                case "password":
                    restaurant.setHash(value);
                    break;
                case "cuisineType":
                    restaurant.setCuisineType(value);
                    break;
                case "phone":
                    restaurant.setPhone(value);
                    break;
                case "location":
                    restaurant.setLocation(value);
                    break;
                case "hours":
                    restaurant.setHours(value);
                    break;
                default:
            }

            return restaurant;
        }

        private Driver updateDriver(String field, String value, Driver driver) {
            switch (field) {
                case "firstName":
                    driver.setFirstName(value);
                    break;
                case "lastName":
                    driver.setLastName(value);
                    break;
                case "email":
                    driver.setEmail(value);
                    break;
                case "password":
                    driver.setHash(value);
                    break;
                case "phone":
                    driver.setPhone(value);
                    break;
                case "location":
                    driver.setLocation(value);
                    break;
                default:
            }

            return driver;
        }
}
