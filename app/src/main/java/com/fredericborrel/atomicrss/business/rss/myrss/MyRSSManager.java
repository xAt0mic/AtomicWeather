package com.fredericborrel.atomicrss.business.rss.myrss;

import android.util.Log;

import com.fredericborrel.atomicrss.business.rss.RSSManager;
import com.fredericborrel.atomicrss.data.model.RSSItem;
import com.fredericborrel.atomicrss.data.model.RSSXMLTag;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Frederic on 22/12/2016.
 */

public class MyRSSManager implements RSSManager {

    private static final String TAG = "MyRSSManager";

    public MyRSSManager() {
        Log.i(TAG, "MyRSSManager() - starting ...");
        init();
    }

    private void init(){
        Log.i(TAG, "init()");
    }

    @Override
    public List<RSSItem> readRSS(String urlString) {

        RSSXMLTag currentTag = null;

        InputStream is;
        ArrayList<RSSItem> rssItemList = new ArrayList<>();
        try{
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.connect();
            is = connection.getInputStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(is, null);

            int eventType = xpp.getEventType();
            RSSItem itemRSS = null;

            SimpleDateFormat inputDateFormat = new SimpleDateFormat(INPUT_DATE_FORMAT, Locale.US);
            SimpleDateFormat outputDateFormat = new SimpleDateFormat(OUTPUT_DATE_FORMAT,Locale.FRANCE);

            while (eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        switch (xpp.getName()){
                            case "item":
                                itemRSS = new RSSItem();
                                currentTag = RSSXMLTag.IGNORETAG;
                                break;
                            case "title":
                                currentTag = RSSXMLTag.TITLE;
                                break;
                            case "link":
                                currentTag = RSSXMLTag.LINK;
                                break;
                            case "pubDate":
                                currentTag = RSSXMLTag.DATE;
                                break;
                            case "description":
                                currentTag = RSSXMLTag.THUMBNAIL;
                                break;
                            default:
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(xpp.getName().equals("item")){
                            Date dateItem = inputDateFormat.parse(itemRSS.getRssDate());
                            itemRSS.setRssDate(outputDateFormat.format(dateItem));
                            rssItemList.add(itemRSS);
                        }
                        else {
                            currentTag = RSSXMLTag.IGNORETAG;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        String content = xpp.getText();
                        content = content.trim();
                        if(itemRSS != null && currentTag != null){
                            switch (currentTag){
                                case TITLE:
                                    // Several marks are named "title", just get the first one
                                    if(content.length() != 0 && itemRSS.getRssTitle() == null){
                                        itemRSS.setRssTitle(content);
                                    }
                                    break;
                                case LINK:
                                    if(content.length() != 0){
                                        itemRSS.setRssLink(content);
                                    }
                                    break;
                                case DATE:
                                    if(content.length() != 0){
                                        itemRSS.setRssDate(content);
                                    }
                                    break;
                                case THUMBNAIL:
                                    if(content.length() != 0){
                                        // Only retrieve the image address
                                        String[] splitDesc = content.split("src=\"");
                                        String urlThumbnail = splitDesc[1].split("\"")[0];
                                        itemRSS.setRssImage(urlThumbnail);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                    default:
                        break;
                }
                eventType = xpp.next();
            }

        } catch (MalformedURLException e ){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rssItemList;
    }
}
