package com.fredericborrel.atomicrss.ui.feed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fredericborrel.atomicrss.R;
import com.fredericborrel.atomicrss.data.model.RSSItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frederic on 12/04/16.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private Context myContext;
    private List<RSSItem> mRSSItemsList = new ArrayList<>();

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        myContext = parent.getContext();
        final View view = LayoutInflater.from(myContext).inflate(R.layout.item_feed, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        final RSSItem rssItem = mRSSItemsList.get(position);
        if (rssItem != null) {
            holder.bindRSSItem(myContext, rssItem);
        }
    }

    @Override
    public int getItemCount() {
        return mRSSItemsList != null ? mRSSItemsList.size() : 0;
    }

    public void setRSSItemsList(List<RSSItem> rssItemsList) {
        mRSSItemsList.clear();
        mRSSItemsList.addAll(rssItemsList);
        notifyDataSetChanged();
    }
}
