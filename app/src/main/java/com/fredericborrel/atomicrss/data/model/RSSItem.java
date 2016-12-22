package com.fredericborrel.atomicrss.data.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Frederic on 12/04/16.
 */
@DatabaseTable(tableName = "RSSItem")
public class RSSItem extends BaseEntity implements Serializable {

    public static final String IMAGE_COLUMN = "ImageRss";
    public static final String TITLE_COLUMN = "TitleRss";
    public static final String DATE_COLUMN = "DateRss";
    public static final String LINK_COLUMN = "LinkRss";

    @DatabaseField(columnName = IMAGE_COLUMN)
    private String rssImage;

    @DatabaseField(columnName = TITLE_COLUMN)
    private String rssTitle;

    @DatabaseField(columnName = DATE_COLUMN)
    private String rssDate;

    @DatabaseField(columnName = LINK_COLUMN)
    private String rssLink;

    public RSSItem() {}

    public RSSItem(String rssImage, String rssTitle, String rssDate, String rssLink) {
        super();
        this.rssImage = rssImage;
        this.rssTitle = rssTitle;
        this.rssDate = rssDate;
        this.rssLink = rssLink;
    }

    public String getRssLink() {
        return rssLink;
    }

    public void setRssLink(String rssLink) {
        this.rssLink = rssLink;
    }

    public String getRssImage() {
        return rssImage;
    }

    public void setRssImage(String rssImage) {
        this.rssImage = rssImage;
    }

    public String getRssTitle() {
        return rssTitle;
    }

    public void setRssTitle(String rssTitle) {
        this.rssTitle = rssTitle;
    }

    public String getRssDate() {
        return rssDate;
    }

    public void setRssDate(String rssDate) {
        this.rssDate = rssDate;
    }
}
