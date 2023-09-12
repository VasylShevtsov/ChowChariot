package com.stockcharts.interns.controller.HomePageHandlers;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import com.stockcharts.interns.HomeServlet;
import com.stockcharts.interns.controller.Command;
import com.stockcharts.interns.utils.HttpUtils;

import freemarker.core.*;
import freemarker.template.*;
public class LoginHandler implements Command {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);

            try {
                if (session != null && session.getAttribute("userID") != null && session.getAttribute("type") != null)
                    response.sendRedirect("/chowchariot/home?page=dashboard&cmd=" + session.getAttribute("type"));
            } catch (IOException e) {
                HomeServlet.logger.info("IOException thrown when redirecting to dashboard");
            }

            HomeServlet.logger.info("in login page");

            Template template = HomeServlet.freemarkerConfig.getTemplate("login.ftl");
            Map<String, Object> model = new HashMap<>();

            String userType = request.getParameter("type");

            if (userType == null || userType.isBlank())
                userType = "customer";

            model.put("userType", userType);

            if (session != null && session.getAttribute("errorMessage") != null) {
                model.put("errorMessage", (String) session.getAttribute("errorMessage"));
                session.removeAttribute("errorMessage");
            }

            try (PrintWriter out = response.getWriter(); ) {
                template.process(model, out);
            } 
            
        } catch (Exception e) {
            HomeServlet.logger.error("Unexpected exception encountered when processing: {}", HttpUtils.getRequestURL(request), e);
        } 
    }
}
