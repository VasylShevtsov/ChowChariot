package com.stockcharts.interns.controller.RestHandler;

import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.stockcharts.interns.*;
import com.stockcharts.interns.controller.Command;
import com.stockcharts.interns.model.*;
import com.stockcharts.interns.utils.DAOs;
import com.stockcharts.interns.utils.HttpUtils;

import freemarker.template.*;

public class AddMenuItemHandler implements Command {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        
        try {
            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("userID") == null)
                response.sendRedirect("/chowchariot/home?cmd=home");

            Restaurant restaurant = DAOs.restaurantDAO.get(
                Integer.parseInt(session.getAttribute("userID").toString())
            );

            String category = request.getParameter("category");
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));

            Item item = new Item.Builder()
                .category(category)
                .itemName(name)
                .unitCost((float)price)
                .menuID(restaurant.getMenuID())
                .build();

            int recid = DAOs.itemDAO.create(item);

            item.setItemID(recid);

            response.sendRedirect("/chowchariot/home?cmd=restaurant");

        } catch (Exception e) {
            HomeServlet.logger.error("Unexpected exception encountered when processing: {}", HttpUtils.getRequestURL(request), e);
        }
    }
}
