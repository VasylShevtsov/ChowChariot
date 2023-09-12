package com.stockcharts.interns.utils;

import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.*;

import com.stockcharts.interns.HomeServlet;
import com.stockcharts.interns.commons2.sql.*;

public class Pool {
    private static volatile ComboPooledDataSource pool = null;

    public ComboPooledDataSource getInstance() {
        if (pool == null) {
            
            synchronized (Pool.class) {
                
                if (pool == null) {
                    try {
                        Pool.pool = SQLUtil.createSqlConnectionPool(null, null, null, 0); // update with real values
                    } catch (SQLException e) {
                        HomeServlet.logger.error("error while creating pool: " + e.toString());
                    }
                }
            }
            
        }

        return pool;
    }
}
