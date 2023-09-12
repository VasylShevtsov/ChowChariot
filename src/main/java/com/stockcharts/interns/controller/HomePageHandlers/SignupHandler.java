package com.stockcharts.interns.controller.HomePageHandlers;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import com.stockcharts.interns.HomeServlet;
import com.stockcharts.interns.controller.Command;
import com.stockcharts.interns.utils.HttpUtils;

import freemarker.template.*;

public class SignupHandler implements Command {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);

            if (session != null && session.getAttribute("userID") != null)
                response.sendRedirect("/chowchariot/home?page=dashboard");

            Template template = HomeServlet.freemarkerConfig.getTemplate("signup.ftl");
            Map<String, Object> model = new HashMap<>();

            if (session != null && session.getAttribute("errorMessage") != null) {
                model.put("errorMessage", (String) session.getAttribute("errorMessage"));
                session.removeAttribute("errorMessage");
            }


            try (PrintWriter out = response.getWriter(); ) {
                template.process(model, out);
                HomeServlet.logger.info("displayed the signup page");
            }
            
        } catch (Exception e) {
            HomeServlet.logger.error("Unexpected exception encountered when processing: {}", HttpUtils.getRequestURL(request), e);
        } 
    }
}
