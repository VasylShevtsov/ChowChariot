package com.stockcharts.interns.dao;

import java.util.List;

import com.stockcharts.interns.model.Item;

public interface OrderItemDAO extends DAO<Item> {
    Item get(int itemID);
    int create(Item item);
    boolean update(Item item);
    boolean delete(int itemID);
    List<Item> getAllItems();
}

