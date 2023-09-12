package com.stockcharts.interns.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.*;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.stockcharts.interns.commons2.sql.*;


import com.stockcharts.interns.dao.OrderItemDAO;
import com.stockcharts.interns.model.Item;

public class ItemDB implements OrderItemDAO {
    private static final Logger logger = LogManager.getLogger();
    private ComboPooledDataSource pool;
    
    public ItemDB(ComboPooledDataSource pool) {
        this.pool = pool;
    }

    public Item get(int itemID) {
        String query = "SELECT * FROM Items WHERE itemID = ?;";

        try {
            SQLResults results = SQLUtil.executeSelect(this.pool, query, itemID);
            List<SQLRow> rows = results.getRows();

            if (rows == null || rows.isEmpty())
                return null;

                SQLRow singleRow = rows.get(0);
                Item newItem = new Item.Builder()
                    .itemID(itemID)
                    .menuID(singleRow.getInteger("menuID"))
                    .itemName(singleRow.get("itemName"))
                    .category(singleRow.get("category"))
                    .unitCost(singleRow.getFloat("unitCost"))
                    .build();

                return newItem;
        } catch (SQLException e) {
            logger.error("An unexpected error occurred in getting an itemByID", e);
        }
        return null;
    }

    public int create(Item item) {
        String query = "INSERT INTO Items (itemID, menuID, itemName, category, unitCost) VALUES (NULL, ?, ?, ?, ?);";

        try {
            int result = SQLUtil.executeInsert(pool, query,
                item.getItemID(),
                item.getMenuID(), 
                item.getItemName(), 
                item.getCategory(),
                item.getUnitCost()
            );
            return result;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when adding an item", e);
        }
        return -1;
    }

    public boolean update(Item item) {
        String query = "UPDATE Items SET menuID = ?, itemName = ?, category = ?, unitCost = ? WHERE itemID = ?;";
        
        try {
            boolean result = SQLUtil.executeUpdate(pool, query, 
                item.getMenuID(), 
                item.getItemName(), 
                item.getCategory(),
                item.getUnitCost()
            );

            return result;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when updating an item", e);

        }
        return false;
    }

    public boolean delete(int itemID) {
        String query = "DELETE FROM Items WHERE itemID = ?;";

        try {
            boolean result = SQLUtil.executeDelete(this.pool, query, itemID);
            return result;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when deleting an item", e);

        }
        return false;
    }

    public List<Item> getAllItems() {
        String query = "SELECT * FROM Items";

        try {
            SQLResults results = SQLUtil.executeSelect(pool, query);
            List<SQLRow> rows = results.getRows();
        
            List<Item> allItems = new ArrayList<>();
            if (rows == null || rows.isEmpty())
                return Collections.emptyList();

            for (SQLRow row : rows) {
                allItems.add(new Item.Builder()
                    .itemID(row.getInteger("itemID"))
                    .menuID(row.getInteger("menuID"))
                    .itemName(row.get("itemName"))
                    .category(row.get("category"))
                    .unitCost(row.getFloat("unitCost"))
                    .build());
            }

            return allItems;
        } catch (SQLException e) {
            logger.error("Unexpected exception encountered when getting all Items", e);
        }

        return Collections.emptyList();
    }
}
