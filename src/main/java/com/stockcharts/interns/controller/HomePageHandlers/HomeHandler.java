package com.stockcharts.interns.controller.HomePageHandlers;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import com.stockcharts.interns.HomeServlet;
import com.stockcharts.interns.controller.Command;
import com.stockcharts.interns.utils.HttpUtils;

import freemarker.template.*;

public class HomeHandler implements Command {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
        
            if (session != null && "customer".equals((String)session.getAttribute("userType")))  {
                response.sendRedirect("/chowchariot/home?page=dashboard");
            } else if (session != null && "driver".equals((String)session.getAttribute("userType"))) {
                response.sendRedirect("/chowchariot/home?page=driver");
            } else if (session != null && "restaurant".equals((String)session.getAttribute("userType"))) {
                response.sendRedirect("/chowchariot/home?page=restaurant");
            }
        

            String cmd = request.getParameter("cmd");
            if (cmd == null || cmd.isBlank())
                cmd = "";
            
            Command handler = HomeServlet.commands.get(cmd);
            if (handler != null) {
                handler.handle(request, response);
            }

            Template template = HomeServlet.freemarkerConfig.getTemplate("home.ftl");
            Map<String, Object> model = new HashMap<>();

            try (PrintWriter out = response.getWriter()) {
                template.process(model, out);
            }

        } catch (Exception e) {
            HomeServlet.logger.error("Unexpected exception encountered when processing: {}", HttpUtils.getRequestURL(request), e);
        }
    }
}
