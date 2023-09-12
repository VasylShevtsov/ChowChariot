package com.stockcharts.interns.dao.impl;

import java.util.*;

import com.stockcharts.interns.dao.*;
import com.stockcharts.interns.model.*;

public class MenuMem implements MenuDAO {
    private static Map<Integer, Menu> menus = new HashMap<>();
    private static int currentKey = 0;

    public Menu get(int menuID) {
        return menus.getOrDefault(menuID, null);
    }

    public int create(Menu menu) {
        if (menus.containsKey(menu.getMenuID()))
            return -1;
        
        menus.put(currentKey, menu);
        menu.setMenuID(currentKey);
        return currentKey++;
    }

    public boolean delete(int menuID) {
        if (menus.containsKey(menuID))
            return false;
        
        menus.remove(menuID);
        return true;
    }

    public List<Item> getItems(Menu menu) {
        OrderItemDAO itemDAO = new ItemMem();

        List<Item> allItems = itemDAO.getAllItems();
        List<Item> menuItems = new ArrayList<>();

        for (Item item : allItems) 
            if (item.getMenuID() == menu.getMenuID())
                menuItems.add(item);

        return menuItems;
    }

    public List<Menu> getAllMenus() {
        return new ArrayList<>(menus.values());
    }

    @Override
    public boolean update(Menu menu) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
