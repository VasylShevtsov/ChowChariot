package com.stockcharts.interns;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import freemarker.template.*;
import org.apache.logging.log4j.*;

import com.stockcharts.interns.controller.*;
import com.stockcharts.interns.controller.DashboardPageHandlers.DisplayCheckoutHandler;
import com.stockcharts.interns.dao.*;
import com.stockcharts.interns.model.*;
import com.stockcharts.interns.utils.*;
import com.stockcharts.interns.utils.HttpUtils;

@WebServlet(name = "checkout", urlPatterns = { "/checkout" }, loadOnStartup = 0)
public class CheckoutServlet extends HttpServlet {

    private static final Map<String, Command> commands = new HashMap<>();
    public static Configuration freemarkerConfig;
    public static final Logger logger = LogManager.getLogger();

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("===============================================");
        logger.warn(" checkout             - init()");
        logger.info("===============================================");

        // Load properties here

        // Create long-lived objects here
        freemarkerConfig = new Configuration(Configuration.VERSION_2_3_32);

        File templatePath = new File(config.getServletContext().getRealPath("/WEB-INF/classes/templates"));
        try {
            freemarkerConfig.setDirectoryForTemplateLoading(templatePath);
        } catch (IOException e) {
            logger.fatal("IOException setting the template directory to {}", templatePath, e);
            throw new UnavailableException("Unable to set the Freemarker directory:");
        }

        commands.put("checkout", new DisplayCheckoutHandler());

        logger.info("============ init() complete! ============");
        logger.info("Debugging URL: http://localhost:8080/chowchariot/checkout");
    }

    @Override
    public void destroy() {
        logger.info(":::::::::::::::::::::::::::::::::::::::::::::::");
        logger.warn(" checkout             - destroy()");
        logger.info(":::::::::::::::::::::::::::::::::::::::::::::::");

        // Destroy long-lived objects here

        logger.info("============ destroy() complete! ============");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info("in dashboard servlet");
            long startTimeMillis = System.currentTimeMillis();

            logger.debug("IN - {}", HttpUtils.getRequestURL(request));

            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("userID") == null) {
                logger.info("this is bad....");
                response.sendRedirect("/chowchariot/home?cmd=home");
            } else {
                logger.info("inside the correct place");
            }

            String cmd = request.getParameter("cmd");
            if (cmd == null || cmd.isBlank()) {
                cmd = (String) session.getAttribute("type");
            }

            // Set response defaults (so that "normal" handlers don't need to)
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");


            Command handler = commands.get(cmd);
            if (handler != null) {
                handler.handle(request, response);
            } else {
                logger.info("invalid cmd was selected");
            }

            

            long latency = System.currentTimeMillis() - startTimeMillis;
            logger.info("OT - {}ms {}", latency, HttpUtils.getRequestURL(request));
        } catch (Exception e) {
            logger.error("Unexpected exception encountered when processing: {}", HttpUtils.getRequestURL(request),
                    e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle POST requests here
        String cmd = request.getParameter("cmd");

        if ("logout".equals(cmd)) {
            HttpSession session = request.getSession(false); // Fetch the session and if doesn't exists then return null
            if (session != null) {
                session.invalidate(); // removes all session attributes bound to the session
            }
            response.sendRedirect("/chowchariot/home?cmd=home"); // redirecting to the home page.
        }

        doGet(request, response);
    }

}
