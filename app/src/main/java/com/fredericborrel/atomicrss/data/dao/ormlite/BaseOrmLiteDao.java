package com.fredericborrel.atomicrss.data.dao.ormlite;

import com.fredericborrel.atomicrss.AtomicRSS;
import com.fredericborrel.atomicrss.data.dao.BaseDao;
import com.fredericborrel.atomicrss.data.local.DatabaseHelper;
import com.fredericborrel.atomicrss.data.model.BaseEntity;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frederic on 22/12/2016.
 */

public abstract class BaseOrmLiteDao<T extends BaseEntity> implements BaseDao<T> {

    protected Dao<T, String> mOrmLiteDao;

    public BaseOrmLiteDao() {
        DatabaseHelper databaseHelper = OpenHelperManager.getHelper(AtomicRSS.context, DatabaseHelper.class);
        try {
            mOrmLiteDao = databaseHelper.getDao(getObjectType());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getCount() {
        try {
            return mOrmLiteDao.queryForAll().size();
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public T getById(String id) {
        try {
            return mOrmLiteDao.queryForId(id);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<T> getAll() {
        try {
            return mOrmLiteDao.queryForAll();
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean createOrUpdate(T object) {
        try {
            mOrmLiteDao.createOrUpdate(object);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean createOrUpdateList(List<T> objectList) {
        for(T object : objectList) {
            createOrUpdate(object);
        }
        return true;
    }

    @Override
    public boolean deleteAll() {
        try {
            mOrmLiteDao.delete(getAll());
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteList(List<T> objectList) {
        try {
            mOrmLiteDao.delete(objectList);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(T objectToDelete) {
        try {
            mOrmLiteDao.delete(objectToDelete);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}