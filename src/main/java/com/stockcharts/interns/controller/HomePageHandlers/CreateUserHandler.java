package com.stockcharts.interns.controller.HomePageHandlers;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.*;



import com.stockcharts.interns.HomeServlet;
import com.stockcharts.interns.controller.Command;
import com.stockcharts.interns.model.*;
import com.stockcharts.interns.utils.DAOs;
import com.stockcharts.interns.utils.HttpUtils;

import freemarker.template.*;

public class CreateUserHandler implements Command {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {

        try {
            String accountType = request.getParameter("type");
            HomeServlet.logger.info("account type: " + accountType);

            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirm-password");

            if (accountType == null)
                response.sendRedirect("/chowchariot/home?page=home&cmd=home");
            
            if (email == null) {
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", "Enter a valid email.");
                response.sendRedirect("/chowchariot/home?page=home&cmd=signup");
                return;
            }
            
            if (password == null || confirmPassword == null || !password.equals(confirmPassword)){
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", "Passwords do not match.");
                response.sendRedirect("/chowchariot/home?page=home&cmd=signup");
                return;
            }

            int recid = -1;
    
            switch (accountType) {
                case "customer":
                    Customer user = new Customer.Builder()
                        .email(email)
                        .hash(password)
                        .build();

                    recid = DAOs.userDAO.create(user);
                    user.setUserID(recid);
                    break;
                case "restaurant":
                    Restaurant restaurant = new Restaurant.Builder()
                        .email(email)
                        .hash(password)
                        .build();
                    
                    recid = DAOs.restaurantDAO.create(restaurant);
                    restaurant.setRestID(recid);
                    break;
                case "driver":
                    Driver driver = new Driver.Builder()
                        .withEmail(email)
                        .withHash(password)
                        .build();

                    recid = DAOs.driverDAO.create(driver);
                    driver.setDriverID(recid);
                    break;
                default:
                    HomeServlet.logger.info("invalid account type");

            }

            HomeServlet.logger.info("generated recid: " +recid);

            if (recid == -1) {
                Map<String, Object> model = new HashMap<>();
                model.put("errorMessage", "Error creating account.");

                Template t = HomeServlet.freemarkerConfig.getTemplate("signup.ftl");
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();

                try {
                    t.process(model, out);
                    return;
                } catch (TemplateException e) {
                    throw new ServletException("Error while processing FreeMarker template ", e);
                }
            }

            HttpSession session = request.getSession(); // get the current session, or create one if it doesn't exist
            session.setAttribute("userID", recid);
            session.setAttribute("type", accountType);
            // setting session to expire in 30 mins
            session.setMaxInactiveInterval(30 * 60);
            Cookie userName = new Cookie("userID", "" + recid);
            response.addCookie(userName);
            
            switch (accountType) {
                case "customer":
                    response.sendRedirect("/chowchariot/home?page=dashboard&cmd=customer");
                    break;
                case "restaurant":
                    response.sendRedirect("/chowchariot/home?page=dashboard&cmd=restaurant-admin");
                    break;
                case "driver":
                    response.sendRedirect("/chowchariot/home?page=dashboard&cmd=driver");
                    break;
                default:
            }
            
        } catch (Exception e) {
            HomeServlet.logger.error("Unexpected exception encountered when processing: {}",
                    HttpUtils.getRequestURL(request), e);
        }
    }
}
