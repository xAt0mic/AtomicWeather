package com.fredericborrel.atomicrss.business.sync;

import android.util.Log;

import com.fredericborrel.atomicrss.business.event.EventManager;
import com.fredericborrel.atomicrss.business.rss.RSSManager;
import com.fredericborrel.atomicrss.business.rss.RSSManagerFactory;
import com.fredericborrel.atomicrss.data.event.RSSReadEvent;
import com.fredericborrel.atomicrss.data.local.DatabaseHelper;
import com.fredericborrel.atomicrss.data.model.RSSItem;
import com.fredericborrel.atomicrss.support.NetworkAvailability;

import java.util.List;

/**
 * Created by Frederic on 22/12/2016.
 */

public class SyncDataManager {

    private static final String TAG = "SyncDataManager";
    private static SyncDataManager instance;
    private RSSManager mRSSManager;

    public static synchronized SyncDataManager getInstance() {
        if (instance == null) {
            instance = new SyncDataManager();
        }
        return instance;
    }

    private SyncDataManager() {
        Log.i(TAG, "SyncDataManager() - starting");
        init();
    }

    private void init() {
        Log.i(TAG, "init()");
        mRSSManager = RSSManagerFactory.createRSSManager();
    }

    public void syncRSSItems(String urlString) {
        if (isNetworkAvailable()) {
            List<RSSItem> rssItemList = mRSSManager.readRSS(urlString);
            DatabaseHelper.getRSSItemDao().createOrUpdateList(rssItemList);
            EventManager.publishEvent(TAG, new RSSReadEvent(rssItemList));
        }
    }

    private boolean isNetworkAvailable() {
        return NetworkAvailability.isNetworkAvailable();
    }
}
