package com.fredericborrel.atomicrss.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Frederic on 12/04/16.
 */
@Table(name = "RSSItem")
public class RSSItem extends Model {

    @Column private String rssImage;
    @Column private String rssTitle;
    @Column private String rssDate;
    @Column private String rssLink;

    public RSSItem(){
        super();
    }

    public RSSItem(String rssImage, String rssTitle, String rssDate, String rssLink) {
        super();
        this.rssImage = rssImage;
        this.rssTitle = rssTitle;
        this.rssDate = rssDate;
        this.rssLink = rssLink;
    }

    public static List<RSSItem> getAllRSSItem() {
        return new Select()
                .from(RSSItem.class)
                .execute();
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
