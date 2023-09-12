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

public class ViewMenuHandler implements Command {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res) {
        try {
            HttpSession session = req.getSession(false);

            if (session == null || session.getAttribute("userID") == null || session.getAttribute("type") == null)
                res.sendRedirect("/chowchariot/home?page=home");

            // if (session.getAttribute("type") == null || !session.getAttribute("type").equals("customer"))
            //     res.sendRedirect("/chowchariot/dashboard");

        
            List<Menu> menus = DAOs.menuDAO.getAllMenus();

            String menuIDString = req.getParameter("menuId");
            if (menuIDString == null) 
                res.sendRedirect("/chowchariot/home?page=dashboard");

            String restIDString = req.getParameter("restID");
            if (restIDString == null)
                res.sendRedirect("/chowchariot/home?page=dashboard");

            Integer restID = Integer.parseInt(restIDString);
            Integer menuId = Integer.parseInt(menuIDString);

            // Menu parameter provided, so display the menu for that restaurant
            Menu menu = DAOs.menuDAO.get(menuId);
            if (menu == null)
                res.sendRedirect("chowchariot/home?page=dashboard");

            List<Item> items = DAOs.menuDAO.getItems(menu);

            Template template = HomeServlet.freemarkerConfig.getTemplate("menu.ftl");
            Map<String, Object> model = new HashMap<>();
            model.put("items", items);
            model.put("restID", restID);

            // Send the output as the response
            try (PrintWriter out = res.getWriter(); ) {
                template.process(model, out);
            } catch (IOException e) {
                HomeServlet.logger.error("I/O Exception sending HTML to {} in response to {}", req.getRemoteAddr(), req.getRequestURI(), e);
            } catch (TemplateException e) {
                HomeServlet.logger.error("TemplateException sending HTML to {} in response to {}", req.getRemoteAddr(), req.getRequestURI(), e);
            }


        } catch (Exception e) {
            HomeServlet.logger.error("Unexpected exception encountered when processing: {}", HttpUtils.getRequestURL(req), e);
        }        
    


        
    }
    
}
