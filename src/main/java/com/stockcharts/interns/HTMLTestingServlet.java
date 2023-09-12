package com.stockcharts.interns;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import freemarker.template.*;
import org.apache.logging.log4j.*;

import com.stockcharts.interns.utils.HttpUtils;

@WebServlet(name="htmltesting", urlPatterns={"/htmltesting"}, loadOnStartup=0)
public class HTMLTestingServlet extends HttpServlet {

    private static Configuration freemarkerConfig;
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("===============================================");
        logger.warn(" htmltesting             - init()");
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

        logger.info("============ init() complete! ============");
        logger.info("Debugging URL: http://localhost:8080/chowchariot/htmltesting");
    }

    @Override
    public void destroy() {
        logger.info(":::::::::::::::::::::::::::::::::::::::::::::::");
        logger.warn(" htmltesting             - destroy()");
        logger.info(":::::::::::::::::::::::::::::::::::::::::::::::");

        // Destroy long-lived objects here

        logger.info("============ destroy() complete! ============");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            long startTimeMillis = System.currentTimeMillis();

            logger.debug("IN - {}", HttpUtils.getRequestURL(request));

            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");

            String page = request.getParameter("page");
            
            if (page == null || page.isBlank()) 
                page = "";
            

            String templateName = "404.ftl";
            switch (page) {
                case "home":
                    templateName = "home.ftl";
                    break;
                case "home2":
                    templateName = "home2.ftl";
                    break;
                case "htmltesting":
                    templateName = "htmltesting.ftl";
                    break;
                case "signup":
                    templateName = "signup.ftl";
                    break;
                case "dashboard":
                    templateName = "dashboard.ftl";
                    break;
                case "menu":
                    templateName = "menu.ftl";
                    break;
                case "vieworder":
                    templateName = "vieworder.ftl";
                    break;
                case "checkout":
                    templateName = "checkout.ftl";
                    break;
                case "orderplaced":
                    templateName = "orderplaced.ftl";
                    break;
                default:
                    templateName = "404.ftl";
                    break;
            }

            Template template = freemarkerConfig.getTemplate(templateName);


            Map<String, Object> model = new HashMap<>();

            try (PrintWriter out = response.getWriter(); ) {
                template.process(model, out);
            } catch (IOException e) {
                logger.error("I/O Exception sending HTML to {} in response to {}", request.getRemoteAddr(), request.getRequestURI(), e);
            }

            long latency = System.currentTimeMillis() - startTimeMillis;
            logger.info("OT - {}ms {}", latency, HttpUtils.getRequestURL(request));
        } catch (Exception e) {
            logger.error("Unexpected exception encountered when processing: {}", HttpUtils.getRequestURL(request), e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        // Handle POST requests here
        doGet(request, response);
    }

}
