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

public class DisplayCheckoutHandler implements Command {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res) {
        try {
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("userID") == null || session.getAttribute("type") == null
                    || !session.getAttribute("type").equals("customer"))
                res.sendRedirect("/chowchariot/home?page=home");

            String restIDString = req.getParameter("restID");
            if (restIDString == null)
                res.sendRedirect("/chowchariot/home?page=dashboard");

            Integer restID = Integer.parseInt(restIDString);
            double total = 0;
            List<OrderItem> orderItems = new ArrayList<>();

            List<Item> items = DAOs.itemDAO.getAllItems();
            for (Item item : items) {
                String quantityString = req.getParameter("" + item.getItemID());

                if (quantityString != null && !quantityString.isEmpty()) {
                    int quantity = Integer.parseInt(quantityString);
                    total += quantity * item.getUnitCost();
                    OrderItem orderItem = new OrderItem(-1, item.getItemID(), quantity);
                    orderItems.add(orderItem);
                }
            }

            Template template = HomeServlet.freemarkerConfig.getTemplate("checkout.ftl");
            Map<String, Object> model = new HashMap<>();
            model.put("orderItems", orderItems);
            model.put("total", total);
            model.put("restID", restID);

            try (PrintWriter out = res.getWriter();) {
                template.process(model, out);
            } catch (IOException e) {
                HomeServlet.logger.error("I/O Exception sending HTML to {} in response to {}", req.getRemoteAddr(),
                        req.getRequestURI(), e);
            } catch (TemplateException e) {
                HomeServlet.logger.error("TemplateException sending HTML to {} in response to {}", req.getRemoteAddr(),
                        req.getRequestURI(), e);
            }

        } catch (Exception e) {
            HomeServlet.logger.error("Unexpected exception encountered when processing: {}",
                    HttpUtils.getRequestURL(req), e);
        }
    }
}
