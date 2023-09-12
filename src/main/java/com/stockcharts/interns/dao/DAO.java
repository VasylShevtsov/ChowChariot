package com.stockcharts.interns.dao;

public interface DAO<T> {
    int create(T entity);
    boolean update(T entity);
    T get(int recid);
    boolean delete(int recid);
}
