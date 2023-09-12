package com.stockcharts.interns.controller.RestHandler;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;
import com.stockcharts.interns.*;
import com.stockcharts.interns.controller.Command;
import com.stockcharts.interns.model.*;
import com.stockcharts.interns.utils.*;
import com.stockcharts.interns.utils.HttpUtils;

import freemarker.template.*;

public class RestaurantHandler implements Command {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res) {
        try {
            HttpSession session = req.getSession(false);

            if (session == null || session.getAttribute("userID") == null)
                res.sendRedirect("/chowchariot/home?page=home");

            // if (session.getAttribute("type") == null || !session.getAttribute("type").equals("restaurant"))
            //     res.sendRedirect("/chowchariot/dashboard");

            Template template = HomeServlet.freemarkerConfig.getTemplate("restaurant-admin.ftl");
            Map<String, Object> model = new HashMap<>();
            
            Restaurant restaurant = DAOs.restaurantDAO.get(
                Integer.parseInt(session.getAttribute("userID").toString())
            );

            Menu menu = DAOs.menuDAO.get(restaurant.getMenuID());
            
            if (menu == null || menu.getMenuID() == -1) {
                menu = new Menu(-1, restaurant.getMenuID());
                menu.setMenuID(DAOs.menuDAO.create(menu));
            }

            List<Item> menuItems = DAOs.menuDAO.getItems(menu);

            model.put("menuItems", menuItems);
            model.put("restaurant", restaurant);

            try (PrintWriter out = res.getWriter(); ) {
                template.process(model, out);
            }

        } catch (Exception e) {
            HomeServlet.logger.error("Unexpected exception encountered when processing: {}", HttpUtils.getRequestURL(req), e);
        }
        
    }
}
