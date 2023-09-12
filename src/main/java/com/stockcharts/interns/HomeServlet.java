package com.stockcharts.interns;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import freemarker.template.*;
import org.apache.logging.log4j.*;

import com.stockcharts.interns.controller.*;
import com.stockcharts.interns.controller.DashboardPageHandlers.*;
import com.stockcharts.interns.controller.HomePageHandlers.*;
import com.stockcharts.interns.controller.RestHandler.*;
import com.stockcharts.interns.dao.*;
import com.stockcharts.interns.utils.HttpUtils;

@WebServlet(name = "home", urlPatterns = { "/home" }, loadOnStartup = 0)
public class HomeServlet extends HttpServlet {

    public static final Map<String, Command> commands = new HashMap<>();
    public static Configuration freemarkerConfig;
    public static final Logger logger = LogManager.getLogger();

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("===============================================");
        logger.warn(" home             - init()");
        logger.info("===============================================");

        freemarkerConfig = new Configuration(Configuration.VERSION_2_3_32);

        File templatePath = new File(config.getServletContext().getRealPath("/WEB-INF/classes/templates"));

        try {
            freemarkerConfig.setDirectoryForTemplateLoading(templatePath);
        } catch (IOException e) {
            logger.fatal("IOException setting the template directory to {}", templatePath, e);
            throw new UnavailableException("Unable to set the Freemarker directory:");
        }

        commands.put("home", new HomeHandler());
        commands.put("dashboard", new DashboardHandler());
        commands.put("login", new LoginHandler());
        commands.put("signup", new SignupHandler());
        commands.put("auth", new AuthenticationHandler());
        commands.put("create", new CreateUserHandler());
        commands.put("customer", new ViewRestHandler());
        commands.put("restaurant", new RestaurantHandler());
        commands.put("driver", new SignupHandler());
        commands.put("add-to-menu", new AddMenuItemHandler());
        commands.put("remove-from-menu", new RemoveMenuItemHandler());
        commands.put("update-account", new UpdateAccountHandler());
        commands.put("process-checkout", new ProcessCheckoutHandler());
        commands.put("display-checkout", new DisplayCheckoutHandler());



        logger.info("============ init() complete! ============");
        logger.info("Debugging URL: http://localhost:8080/chowchariot/home?page=home");
    }

    @Override
    public void destroy() {
        logger.info(":::::::::::::::::::::::::::::::::::::::::::::::");
        logger.warn(" home             - destroy()");
        logger.info(":::::::::::::::::::::::::::::::::::::::::::::::");

        logger.info("============ destroy() complete! ============");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            long startTimeMillis = System.currentTimeMillis();

            logger.debug("IN - {}", HttpUtils.getRequestURL(request));
            logger.info("in home servlet home page");

            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");

            String page = request.getParameter("page");
            if (page == null || page.isBlank())
                page = "home";

            Command handler = commands.get(page);
            if (handler != null) {
                handler.handle(request, response);
            } else {
                logger.info("invalid page was selected");
            }

            long latency = System.currentTimeMillis() - startTimeMillis;
            logger.info("OT - {}ms {}", latency, HttpUtils.getRequestURL(request));
        } catch (Exception e) {
            logger.error("Unexpected exception encountered when processing: {}", HttpUtils.getRequestURL(request), e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            logger.info("inside the dopost of the home servlet");

            String cmd = request.getParameter("cmd");

            if (cmd == null || cmd.isBlank())
                cmd = "home";

            if ("logout".equals(cmd)) {
                HttpSession session = request.getSession(false);
                if (session != null)
                    session.invalidate();
                response.sendRedirect("/chowchariot/home?page=home");
            } else {
                Command handler = commands.get(cmd);
                if (handler != null) {
                    handler.handle(request, response);
                } else {
                    logger.info("invalid page was selected");
                }
            }

        } catch (Exception e) {
            logger.error("Unexpected exception encountered when processing: {}", HttpUtils.getRequestURL(request), e);
        }
    }
}
