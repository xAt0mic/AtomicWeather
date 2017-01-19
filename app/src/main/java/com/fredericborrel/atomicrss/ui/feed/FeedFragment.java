package com.fredericborrel.atomicrss.ui.feed;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fredericborrel.atomicrss.R;
import com.fredericborrel.atomicrss.business.sync.SyncDataManager;
import com.fredericborrel.atomicrss.data.dao.RSSItemDao;
import com.fredericborrel.atomicrss.data.event.RSSItemOnClickEvent;
import com.fredericborrel.atomicrss.data.event.RSSReadEvent;
import com.fredericborrel.atomicrss.data.local.DatabaseHelper;
import com.fredericborrel.atomicrss.data.model.RSSItem;
import com.fredericborrel.atomicrss.databinding.FragmentFeedBinding;
import com.fredericborrel.atomicrss.support.NetworkAvailability;
import com.fredericborrel.atomicrss.ui.NewsFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


/**
 * Created by Frederic on 12/04/16.
 */
public class FeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String STREAM_URL = "http://feeds.feedburner.com/Mobilecrunch";
    private static final String STREAM_URL_BIS = "http://feeds.gawker.com/gizmodo/full";
    private static final String TAG = "FeedActivity";

    private FragmentFeedBinding mBinding;
    private FeedAdapter mItemAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false);
        mBinding.refreshLayout.setOnRefreshListener(this);

        mItemAdapter = new FeedAdapter();
        mLayoutManager = new LinearLayoutManager(getActivity());

        mBinding.feedview.setHasFixedSize(true);
        mBinding.feedview.setLayoutManager(mLayoutManager);
        mBinding.feedview.setAdapter(mItemAdapter);

        // Load data from database
        readLocalRSS();

        if(NetworkAvailability.isNetworkAvailable()) {
            // Download News from RSS source
            readRemoteRSS(STREAM_URL);
        }
        else if(mItemAdapter.getItemCount() < 1){
            mBinding.tvNetworkNotAvailable.setVisibility(View.VISIBLE);
        }

        return mBinding.getRoot();
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
            if(mBinding.tvNetworkNotAvailable.getVisibility() == View.VISIBLE)
                mBinding.tvNetworkNotAvailable.setVisibility(View.GONE);
        } else {
            Toast.makeText(getContext(), this.getString(R.string.tv_network_not_available),Toast.LENGTH_LONG).show();
            mBinding.refreshLayout.setRefreshing(false);
        }
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
        mItemAdapter.setRSSItemsList(rssItemList);
    }

    //==============================================================================================
    // Events
    //==============================================================================================

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RSSItemOnClickEvent event) {
        // Make sure that we have internet before displaying the content
        if(NetworkAvailability.isNetworkAvailable()) {
            final NewsFragment newsFragment = NewsFragment.newInstance(event.getLink());
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
        mBinding.refreshLayout.setRefreshing(false);
    }
}
