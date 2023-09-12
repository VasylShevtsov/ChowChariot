package com.stockcharts.interns.utils;

import javax.servlet.http.*;

public class HttpUtils extends HttpServlet {

    public static String getRequestURL(HttpServletRequest request) {
        String url = new String(request.getRequestURL());
        String query = request.getQueryString();
        if (query != null && !query.isBlank())
            url += "?" + query;
        return url;
    }

    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null)
            ip = forwardedFor.split(",")[0];
        return ip;
    }

}
