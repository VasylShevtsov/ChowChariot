package com.stockcharts.interns.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    void handle(HttpServletRequest req, HttpServletResponse res);
}
