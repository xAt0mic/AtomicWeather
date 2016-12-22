package com.fredericborrel.atomicrss.business.rss;

import com.fredericborrel.atomicrss.data.model.RSSItem;

import java.util.List;

/**
 * Created by Frederic on 22/12/2016.
 */

public interface RSSManager {

    int READ_TIMEOUT = 10000; // 10sec
    int CONNECTION_TIMEOUT = 10000; // 10sec
    String INPUT_DATE_FORMAT = "EEE, DD MMM yyyy HH:mm:ss";
    String OUTPUT_DATE_FORMAT = "EEE, DD MMM yyyy HH:mm:ss";
    List<RSSItem> readRSS(String urlString);
}
