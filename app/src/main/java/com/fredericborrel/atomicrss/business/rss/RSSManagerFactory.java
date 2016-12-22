package com.fredericborrel.atomicrss.business.rss;

import com.fredericborrel.atomicrss.business.rss.myrss.MyRSSManager;

/**
 * Created by Frederic on 22/12/2016.
 */

public class RSSManagerFactory {

    public static RSSManager createRSSManager() {
        return new MyRSSManager();
    }
}
