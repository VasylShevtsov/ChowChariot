package com.stockcharts.interns.dao.impl;

import java.util.*;

import com.stockcharts.interns.dao.OrderItemDAO;
import com.stockcharts.interns.model.Item;

public class ItemMem implements OrderItemDAO {
    private static Map<Integer, Item> items = new HashMap<>();
    private static int currentKey = 0;

    public Item get(int itemID) {
        return items.getOrDefault(itemID, null);
    }

    public int create(Item item) {
        if (items.containsKey(item.getItemID()))
            return -1;
        
        items.put(currentKey, item);
        item.setItemID(currentKey);
        return currentKey++;
    }

    public boolean update(Item item) {
        if (!items.containsKey(item.getItemID()))
            return false;

        items.put(item.getItemID(), item);
        return true;
    }

    public boolean delete(int itemID) {
        if (!items.containsKey(itemID))
            return false;

        items.remove(itemID);
        return true;
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(items.values());
    }
}
