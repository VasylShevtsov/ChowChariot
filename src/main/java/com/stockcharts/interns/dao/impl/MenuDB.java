package com.stockcharts.interns.dao.impl;

import java.sql.SQLException;
import java.util.*;

import org.apache.logging.log4j.*;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.stockcharts.interns.commons2.sql.*;
import com.stockcharts.interns.dao.MenuDAO;
import com.stockcharts.interns.model.Item;
import com.stockcharts.interns.model.Menu;

public class MenuDB implements MenuDAO {
    private static final Logger logger = LogManager.getLogger();
    private ComboPooledDataSource pool;
    
    public MenuDB(ComboPooledDataSource pool) {
        this.pool = pool;
    }

    public Menu get(int menuID) {
        String query = "SELECT * FROM Menus WHERE menuID = ?";

        try {
            SQLResults results = SQLUtil.executeSelect(pool, query, menuID);
            List<SQLRow> rows = results.getRows();

            if (rows == null || rows.isEmpty())
                return null;

            SQLRow singleRow = rows.get(0);
            return new Menu(menuID, singleRow.getInteger("restID"));
        } catch (SQLException e) {
            logger.error("An unexpected error occurred getting a menu byID", e);
        }
        return null;
    }



    public int create(Menu menu) {
        String query = "INSERT INTO Menus (menuID, restID) VALUES (NULL, ?)";

        try {
            int result = SQLUtil.executeInsert(pool, query,
                menu.getMenuID(),
                menu.getRestID()
            );
            
            return result;
        } catch (SQLException e) {
            logger.error("An unexpected error occurred when adding a menu", e);
        }
        return -1;
    }

    public boolean delete(int menuID) {
        String query = "DELETE FROM Menus WHERE menuID = ?;";

        try {
            boolean result = SQLUtil.executeDelete(pool, query, menuID);
            return result;
        } catch (SQLException e) {
            logger.error("An unexpected error occurred when deleting a menu", e);
        }
        return false;
    }

    public List<Menu> getAllMenus() {
        String query = "SELECT * FROM Menus";

        try {
            SQLResults results = SQLUtil.executeSelect(pool, query);
            List<SQLRow> rows = results.getRows();

            if (rows == null || rows.isEmpty())
                return Collections.emptyList();
            
            List<Menu> allMenus = new ArrayList<>();
            for (SQLRow row : rows) {
                allMenus.add(new Menu(row.getInteger("menuID"), row.getInteger("restID")));
            }

            return allMenus;
        } catch (SQLException e) {
            logger.error("An unexpected error occurred when getting all menus", e);
        }

        return Collections.emptyList();
    }

    @Override
    public List<Item> getItems(Menu menu) {
        String query = "SELECT * FROM Items WHERE Items.menuID = ?";

        try {
            SQLResults results = SQLUtil.executeSelect(pool, query, menu.getMenuID());
            List<SQLRow> rows = results.getRows();

            if (rows == null || rows.isEmpty()) 
                return Collections.emptyList();

            List<Item> items = new ArrayList<>();
            for (SQLRow row : rows) {
                items.add(new Item.Builder()
                    .itemID(row.getInteger("itemID"))
                    .menuID(row.getInteger("menuID"))
                    .itemName(row.get("itemName"))
                    .category(row.get("category"))
                    .unitCost(row.getFloat("unitCost"))
                    .build());
            }

            return items;
        } catch (SQLException e) {
            logger.error("An unexpected error occurred when getting all menu items", e);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean update(Menu menu) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

}
