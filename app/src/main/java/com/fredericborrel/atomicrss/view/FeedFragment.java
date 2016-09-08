package com.fredericborrel.atomicrss.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.fredericborrel.atomicrss.support.NetworkAvailability;
import com.fredericborrel.atomicrss.R;
import com.fredericborrel.atomicrss.support.RSSItemAdapter;
import com.fredericborrel.atomicrss.model.RSSItem;

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
import java.util.Locale;


/**
 * Created by Frederic on 12/04/16.
 */
public class FeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener{

    private static final String STREAM_URL = "http://feeds.feedburner.com/Mobilecrunch";
    private static final String STREAM_URL_BIS = "http://feeds.gawker.com/gizmodo/full";
    private static final String TAG = "FeedActivity";

    private ArrayList<RSSItem> listRSS;
    private RSSItemAdapter itemAdapter;
    private SwipeRefreshLayout refreshLayout;

    private TextView tvNoInternet;
    private ListView lvFeed;

    public enum RSSXMLTag {
        TITLE, DATE, LINK, IGNORETAG, THUMBNAIL;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_feed, container, false);

        // Instantiate pull to refresh
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);

        tvNoInternet = (TextView) view.findViewById(R.id.tv_network_not_available);

        lvFeed = (ListView) view.findViewById(R.id.feedview);

        // Load data from database
        listRSS = new ArrayList<>(RSSItem.getAllRSSItem());

        if(NetworkAvailability.isNetworkAvailable(this.getContext())) {
            // Download News from RSS source
            new RSSDownloadTask().execute(STREAM_URL);
        }
        else if(listRSS.isEmpty()){
            tvNoInternet.setVisibility(View.VISIBLE);
        }

        itemAdapter = new RSSItemAdapter(getActivity(), R.layout.feed_list_item, listRSS);
        lvFeed.setAdapter(itemAdapter);
        lvFeed.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onRefresh() {
        if(NetworkAvailability.isNetworkAvailable(this.getContext())) {
            new RSSDownloadTask().execute(STREAM_URL);
            if(tvNoInternet.getVisibility() == View.VISIBLE)
                tvNoInternet.setVisibility(View.GONE);
        } else {
            Toast.makeText(getContext(), this.getString(R.string.tv_network_not_available),Toast.LENGTH_LONG).show();
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Make sure that we have internet before displaying the content
        if(NetworkAvailability.isNetworkAvailable(this.getContext())) {
            final NewsFragment newsFragment = NewsFragment.newInstance(listRSS.get(position).getRssLink());
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                    .replace(R.id.main_fragment_container, newsFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            Toast.makeText(getContext(), this.getString(R.string.tv_network_not_available),Toast.LENGTH_LONG).show();
        }
    }

    public class RSSDownloadTask extends AsyncTask<String, Integer, ArrayList<RSSItem>> {

        private static final int READ_TIMEOUT = 10000; // 10sec
        private static final int CONNECTION_TIMEOUT = 10000; // 10sec
        private static final String TAG = "RSSDownloadTask";
        private static final String INPUT_DATE_FORMAT = "EEE, DD MMM yyyy HH:mm:ss";
        private static final String OUTPUT_DATE_FORMAT = "EEE, DD MMM yyyy HH:mm:ss";

        private RSSXMLTag currentTag;

        @Override
        protected ArrayList<RSSItem> doInBackground(String... params) {
            String urlString = params[0];
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
                            if(itemRSS != null){
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

            // Replace news in the database if everything goes well
            ActiveAndroid.beginTransaction();
            try{
                for(int i=0;i<rssItemList.size();i++){
                    if(listRSS.size()>0)
                        listRSS.get(i).delete();
                    rssItemList.get(i).save();
                }
                ActiveAndroid.setTransactionSuccessful();
            }
            finally {
                ActiveAndroid.endTransaction();
            }

            return rssItemList;
        }

        @Override
        protected void onPostExecute(ArrayList<RSSItem> rssItems) {
            super.onPostExecute(rssItems);
            listRSS.clear();
            listRSS.addAll(rssItems);
            itemAdapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        }
    }
}
