package com.stockcharts.interns.controller.DashboardPageHandlers;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import com.stockcharts.interns.*;
import com.stockcharts.interns.controller.Command;
import com.stockcharts.interns.model.*;
import com.stockcharts.interns.utils.DAOs;
import com.stockcharts.interns.utils.HttpUtils;

import freemarker.template.*;

public class ViewRestHandler implements Command {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res) {
        try {
            HttpSession session = req.getSession(false);

            if (session == null || session.getAttribute("userID") == null || session.getAttribute("type") == null)
                res.sendRedirect("/chowchariot/home?page=home");

            String menuId = req.getParameter("menu");
    
            if (menuId == null) {
                // No menu parameter, so display the list of restaurants
                List<Restaurant> restaurants = DAOs.restaurantDAO.getAllRestaurants();
                
                Template template = HomeServlet.freemarkerConfig.getTemplate("dashboard.ftl");
                Map<String, Object> model = new HashMap<>();
                model.put("restaurants", restaurants);
    
                // Send the output as the response
                try (PrintWriter out = res.getWriter(); ) {
                    template.process(model, out);
                } catch (IOException e) {
                    HomeServlet.logger.error("I/O Exception sending HTML to {} in response to {}", req.getRemoteAddr(), req.getRequestURI(), e);
                } catch (TemplateException e) {
                    HomeServlet.logger.error("TemplateException sending HTML to {} in response to {}", req.getRemoteAddr(), req.getRequestURI(), e);
                }
            } else {
                // Menu parameter provided, so display the menu for that restaurant
                Menu menu = DAOs.menuDAO.get(Integer.parseInt(menuId));
                List<Item> items = DAOs.menuDAO.getItems(menu);
                req.setAttribute("items", items);
                RequestDispatcher dispatcher = req.getRequestDispatcher("menu.ftl");
                try {
                    dispatcher.forward(req, res);
                } catch (ServletException e) {
                    HomeServlet.logger.error("ServletException sending HTML to {} in response to {}", req.getRemoteAddr(), req.getRequestURI(), e);
                } catch (IOException e) {
                    HomeServlet.logger.error("I/O Exception sending HTML to {} in response to {}", req.getRemoteAddr(), req.getRequestURI(), e);
                }
            }

        } catch (Exception e) {
            HomeServlet.logger.error("Unexpected exception encountered when processing: {}", HttpUtils.getRequestURL(req), e);
        }        
    


        
    }
    
}
