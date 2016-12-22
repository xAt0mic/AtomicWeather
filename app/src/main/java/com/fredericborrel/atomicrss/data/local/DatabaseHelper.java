package com.fredericborrel.atomicrss.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fredericborrel.atomicrss.data.dao.RSSItemDao;
import com.fredericborrel.atomicrss.data.dao.ormlite.RSSItemOrmLiteDao;
import com.fredericborrel.atomicrss.data.model.RSSItem;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Frederic on 22/12/2016.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "atomicRSS.db";
    private final static int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, RSSItem.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        onCreate(database, connectionSource);
    }

    public static RSSItemDao getRSSItemDao() {
        return new RSSItemOrmLiteDao();
    }
}