package com.stockcharts.interns.controller.HomePageHandlers;

import java.io.IOException;
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

public class AuthenticationHandler implements Command {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String accountType = request.getParameter("type");
            HomeServlet.logger.info("inside authentication");
            HomeServlet.logger.info("email: " + email);
            HomeServlet.logger.info("passowrd: " + password);
            HomeServlet.logger.info("account type: " + accountType);

            HttpSession session = request.getSession();
    
            int recid = -1;
            switch (accountType) {
                case "customer":
                    HomeServlet.logger.info("Checking switch case customer");
                    recid = DAOs.userDAO.authenticate(email, password); 
                    break;
                case "driver":
                    HomeServlet.logger.info("Checking switch case driver");
                    recid = DAOs.driverDAO.authenticate(email, password); 
                    break;
                case "restaurant":
                    HomeServlet.logger.info("Checking switch case restaurant");
                    recid = DAOs.restaurantDAO.authenticate(email, password);
                    break;
                default:
                    HomeServlet.logger.info("Switch case default");
                    recid = -1;
            }

            HomeServlet.logger.info("checked switch cases, recID is: " + recid);
            
            if (recid == -1) {
                session.invalidate();
                sendError(response);
            }

            HomeServlet.logger.info("Authentication check passed, setting session attributes");
            session.setAttribute("userID", recid);
            session.setAttribute("type", accountType);
            session.setMaxInactiveInterval(30*60);

            HomeServlet.logger.info("about to redirect to " + accountType);
            response.sendRedirect("/chowchariot/home?page=dashboard&cmd=" + accountType);
        } catch (Exception e) {
            HomeServlet.logger.error("Unexpected exception encountered when processing: {}", HttpUtils.getRequestURL(request), e);
        }
    }

    private void sendError(HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("errorMessage", "Either email or password is wrong.");

        Template t = HomeServlet.freemarkerConfig.getTemplate("login.ftl");
        response.setContentType("text/html");

        try (PrintWriter out = response.getWriter()){
            t.process(model, out);
        }
        
    }
}
