package com.fredericborrel.atomicrss.data.event;

/**
 * Created by Frederic on 23/12/2016.
 */

public class RSSItemOnClickEvent extends Event {

    private String mLink;

    public RSSItemOnClickEvent(String link) {
        mLink = link;
    }

    public String getLink() {
        return mLink;
    }
}
