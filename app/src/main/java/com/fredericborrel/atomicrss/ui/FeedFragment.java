package com.fredericborrel.atomicrss.ui;

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

import com.fredericborrel.atomicrss.R;
import com.fredericborrel.atomicrss.business.sync.SyncDataManager;
import com.fredericborrel.atomicrss.data.dao.RSSItemDao;
import com.fredericborrel.atomicrss.data.event.RSSReadEvent;
import com.fredericborrel.atomicrss.data.local.DatabaseHelper;
import com.fredericborrel.atomicrss.data.model.RSSItem;
import com.fredericborrel.atomicrss.support.NetworkAvailability;
import com.fredericborrel.atomicrss.support.RSSItemAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_feed, container, false);

        // Instantiate pull to refresh
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);

        tvNoInternet = (TextView) view.findViewById(R.id.tv_network_not_available);

        lvFeed = (ListView) view.findViewById(R.id.feedview);

        listRSS = new ArrayList<>();
        itemAdapter = new RSSItemAdapter(getActivity(), R.layout.feed_list_item, listRSS);
        lvFeed.setAdapter(itemAdapter);
        lvFeed.setOnItemClickListener(this);

        // Load data from database
        readLocalRSS();

        if(NetworkAvailability.isNetworkAvailable()) {
            // Download News from RSS source
            readRemoteRSS(STREAM_URL);
        }
        else if(listRSS.isEmpty()){
            tvNoInternet.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh() {
        if(NetworkAvailability.isNetworkAvailable()) {
            readRemoteRSS(STREAM_URL);
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
        if(NetworkAvailability.isNetworkAvailable()) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RSSReadEvent event) {
        updateData(event.getRSSItemList());
        refreshLayout.setRefreshing(false);
    }

    private void readLocalRSS() {
        RSSItemDao rssItemDao = DatabaseHelper.getRSSItemDao();
        List<RSSItem> rssItemList = rssItemDao.getAll();
        updateData(rssItemList);
    }

    private void readRemoteRSS(final String urlString) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SyncDataManager.getInstance().syncRSSItems(urlString);
            }
        }).start();
    }

    private void updateData(List<RSSItem> rssItemList) {
        listRSS.clear();
        listRSS.addAll(rssItemList);
        itemAdapter.notifyDataSetChanged();
    }
}
