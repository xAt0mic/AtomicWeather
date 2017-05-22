package com.fredericborrel.atomicweather.data.dao;

import java.util.List;

/**
 * Created by Frederic on 22/12/2016.
 */

public interface BaseDao<T> {
    T getById(String id);

    List<T> getAll();

    long getCount();

    boolean createOrUpdate(T object);

    boolean createOrUpdateList(List<T> objectList);

    boolean deleteAll();

    boolean deleteList(List<T> objectList);

    boolean delete(T objectToDelete);

    Class getObjectType();
}