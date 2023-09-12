// package com.stockcharts.interns;

// import java.io.*;
// import java.util.*;

// import javax.servlet.*;
// import javax.servlet.http.*;
// import javax.servlet.annotation.*;

// import freemarker.template.*;
// import org.apache.logging.log4j.*;

// import com.stockcharts.interns.controller.*;
// import com.stockcharts.interns.controller.DashboardPageHandlers.ViewRestHandler;
// import com.stockcharts.interns.controller.HomePageHandlers.SignupHandler;
// import com.stockcharts.interns.controller.HomePageHandlers.UpdateAccountHandler;
// import com.stockcharts.interns.controller.RestHandler.AddMenuItemHandler;
// import com.stockcharts.interns.controller.RestHandler.RemoveMenuItemHandler;
// import com.stockcharts.interns.controller.RestHandler.RestaurantHandler;
// import com.stockcharts.interns.dao.*;
// import com.stockcharts.interns.model.*;
// import com.stockcharts.interns.utils.*;
// import com.stockcharts.interns.utils.HttpUtils;

// @WebServlet(name = "dashboard", urlPatterns = { "/dashboard" }, loadOnStartup = 0)
// public class DashboardServlet extends HttpServlet {

//     private static final Map<String, Command> commands = new HashMap<>();
//     public static Configuration freemarkerConfig;
//     public static final Logger logger = LogManager.getLogger();

//     @Override
//     public void init(ServletConfig config) throws ServletException {
//         logger.info("===============================================");
//         logger.warn(" dashboard             - init()");
//         logger.info("===============================================");

//         // Load properties here

//         // Create long-lived objects here
//         freemarkerConfig = new Configuration(Configuration.VERSION_2_3_32);

//         File templatePath = new File(config.getServletContext().getRealPath("/WEB-INF/classes/templates"));
//         try {
//             freemarkerConfig.setDirectoryForTemplateLoading(templatePath);
//         } catch (IOException e) {
//             logger.fatal("IOException setting the template directory to {}", templatePath, e);
//             throw new UnavailableException("Unable to set the Freemarker directory:");
//         }

//         commands.put("customer", new ViewRestHandler());
//         commands.put("restaurant", new RestaurantHandler());
//         commands.put("driver", new SignupHandler());
//         commands.put("add-to-menu", new AddMenuItemHandler());
//         commands.put("remove-from-menu", new RemoveMenuItemHandler());
//         commands.put("update-account", new UpdateAccountHandler());

//         logger.info("============ init() complete! ============");
//         logger.info("Debugging URL: http://localhost:8080/chowchariot/dashboard");
//     }

//     @Override
//     public void destroy() {
//         logger.info(":::::::::::::::::::::::::::::::::::::::::::::::");
//         logger.warn(" dashboard             - destroy()");
//         logger.info(":::::::::::::::::::::::::::::::::::::::::::::::");

//         // Destroy long-lived objects here

//         logger.info("============ destroy() complete! ============");
//     }

//     @Override
//     public void doGet(HttpServletRequest request, HttpServletResponse response) {
//         try {
//             long startTimeMillis = System.currentTimeMillis();

//             logger.debug("IN - {}", HttpUtils.getRequestURL(request));

//             HttpSession session = request.getSession(false);

//             if (session == null || session.getAttribute("userID") == null) 
//                 response.sendRedirect("/chowchariot/home?cmd=home");
            
//             response.setCharacterEncoding("UTF-8");
//             response.setContentType("text/html");

//             String cmd = request.getParameter("cmd");

//             if (cmd == null || cmd.isBlank()) 
//                 cmd = (String) session.getAttribute("type");

//             Command handler = commands.get(cmd);

//             if (handler != null) 
//                 handler.handle(request, response);
//             else 
//                 logger.info("invalid cmd was selected");
            

//             long latency = System.currentTimeMillis() - startTimeMillis;
//             logger.info("OT - {}ms {}", latency, HttpUtils.getRequestURL(request));
//         } catch (Exception e) {
//             logger.error("Unexpected exception encountered when processing: {}", HttpUtils.getRequestURL(request), e);
//             response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//         }
//     }

//     @Override
//     public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//         try {
//             String cmd = request.getParameter("cmd");
    
//             if ("logout".equals(cmd)) {
                
//                 HttpSession session = request.getSession(false); 
//                 if (session != null)
//                     session.invalidate(); 
                
//                 response.sendRedirect("/chowchariot/home?cmd=home"); 
//             }
    
//             doGet(request, response);
//         } catch (Exception e) {
//             logger.error("Unexpected exception encountered when processing: {}", HttpUtils.getRequestURL(request), e);
//             response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//         }
//     }

// }
