package com.stockcharts.interns.controller.RestHandler;

import javax.servlet.http.*;

import com.stockcharts.interns.*;
import com.stockcharts.interns.controller.Command;
import com.stockcharts.interns.model.*;
import com.stockcharts.interns.utils.DAOs;
import com.stockcharts.interns.utils.HttpUtils;

public class RemoveMenuItemHandler implements Command {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        
        try {
            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("userID") == null || session.getAttribute("type") == null)
                response.sendRedirect("/chowchariot/home?cmd=home");

            Restaurant restaurant = DAOs.restaurantDAO.get(
                Integer.parseInt(session.getAttribute("userID").toString())
            );
            
            int itemID = Integer.parseInt(request.getParameter("removeItem"));

            boolean result = DAOs.itemDAO.delete(itemID);

            HomeServlet.logger.info("remve item result: " + result);

            response.sendRedirect("/chowchariot/home?page=dashboard&cmd=restaurant");

        } catch (Exception e) {
            HomeServlet.logger.error("Unexpected exception encountered when processing: {}", HttpUtils.getRequestURL(request), e);
        }
    }
}
