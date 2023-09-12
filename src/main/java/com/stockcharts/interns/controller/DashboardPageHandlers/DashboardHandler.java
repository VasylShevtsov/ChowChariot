            // String cmd = request.getParameter("cmd");
            // if (cmd == null || cmd.isBlank())
            //     cmd = "";

package com.stockcharts.interns.controller.DashboardPageHandlers;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import com.stockcharts.interns.HomeServlet;
import com.stockcharts.interns.controller.Command;
import com.stockcharts.interns.controller.DashboardPageHandlers.*;
import com.stockcharts.interns.controller.DriverHandler.*;
import com.stockcharts.interns.controller.HomePageHandlers.*;
import com.stockcharts.interns.controller.RestHandler.*;

import com.stockcharts.interns.utils.HttpUtils;

import freemarker.template.*;

public class DashboardHandler implements Command {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            Command handler = new HomeHandler();

            if (session == null || session.getAttribute("userID") == null || session.getAttribute("type") == null)
                response.sendRedirect("/chowchariot/home?page=home");
            
            String accountType = (String) session.getAttribute("type");

            switch (accountType) {
                case "customer": 
                    handler = new ViewRestHandler();
                    break;
                case "driver": 
                    handler = new DriverHandler();
                    break;
                case "restaurant":
                    handler = new RestaurantHandler();
                    break;
                default:
                    response.sendRedirect("/chowchariot/home?page=home");
                    break;
            }

            handler.handle(request, response);
            
        } catch (Exception e) {
            HomeServlet.logger.error("Unexpected exception encountered when processing: {}", HttpUtils.getRequestURL(request), e);
        } 
    }
}

