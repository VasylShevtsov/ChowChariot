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

public class ProcessCheckoutHandler implements Command {

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

            // Get user's personal and payment information from the form
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String address = req.getParameter("address");
            String phoneNumber = req.getParameter("phoneNumber");
            String paymentInfo = req.getParameter("paymentInfo");

            // TODO: validate these inputs

            // If validation passed, create the order
            Order order = new Order.Builder()
                    .restID(restID)
                    .status("current")
                    .userID(Integer.parseInt("" + session.getAttribute("userID")))
                    .build();

            int orderID = DAOs.orderDAO.create(order);
            double total = 0;
            List<Item> items = DAOs.itemDAO.getAllItems();
            for (Item item : items) {
                String quantityString = req.getParameter("" + item.getItemID());
                if (quantityString != null && !quantityString.isEmpty()) {
                    int quantity = Integer.parseInt(quantityString);
                    total += quantity * item.getUnitCost();
                    order.addOrderItem(new OrderItem(orderID, item.getItemID(), quantity));
                }
            }
            order.setTotal(total);
            boolean result = DAOs.orderDAO.update(order);

            res.sendRedirect("/chowchariot/home?page=dashboard&cmd=confirmation");

        } catch (Exception e) {
            HomeServlet.logger.error("Unexpected exception encountered when processing: {}",
                    HttpUtils.getRequestURL(req), e);
        }
    }
}