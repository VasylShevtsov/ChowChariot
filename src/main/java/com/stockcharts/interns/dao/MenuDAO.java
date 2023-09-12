package com.stockcharts.interns.dao;

import java.util.List;

import com.stockcharts.interns.model.Item;
import com.stockcharts.interns.model.Menu;

public interface MenuDAO extends DAO<Menu> {
    Menu get(int menuID);
    int create(Menu menu);
    boolean delete(int menuID);
    boolean update(Menu menu);
    List<Item> getItems(Menu menu);
    List<Menu> getAllMenus();
}

