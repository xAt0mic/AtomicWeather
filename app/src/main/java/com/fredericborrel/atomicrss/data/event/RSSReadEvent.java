package com.fredericborrel.atomicrss.data.event;

import com.fredericborrel.atomicrss.data.model.RSSItem;

import java.util.List;

/**
 * Created by Frederic on 22/12/2016.
 */

public class RSSReadEvent extends Event{

    private List<RSSItem> mRSSItemList;
    public RSSReadEvent(List<RSSItem> rssItemList) {
        mRSSItemList = rssItemList;
    }
    public List<RSSItem> getRSSItemList() {
        return mRSSItemList;
    }
}
