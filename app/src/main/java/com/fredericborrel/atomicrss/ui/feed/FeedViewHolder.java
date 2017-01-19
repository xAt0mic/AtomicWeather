package com.fredericborrel.atomicrss.ui.feed;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fredericborrel.atomicrss.business.event.EventManager;
import com.fredericborrel.atomicrss.data.event.RSSItemOnClickEvent;
import com.fredericborrel.atomicrss.data.model.RSSItem;
import com.fredericborrel.atomicrss.databinding.ItemFeedBinding;
import com.fredericborrel.atomicrss.support.CropTrapeziumTransformation;
import com.squareup.picasso.Picasso;

/**
 * Created by Frederic on 23/12/2016.
 */

public class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private static final String TAG = "FeedViewHolder";

    private ItemFeedBinding mBinding;
    private RSSItem mRSSItem;

    public FeedViewHolder(View view) {
        super(view);
        mBinding = DataBindingUtil.bind(view);
        view.setOnClickListener(this);
    }

    public void bindRSSItem(Context context, RSSItem item) {
        mRSSItem = item;
        mBinding.itemTitle.setText(mRSSItem.getRssTitle());
        mBinding.itemDate.setText(mRSSItem.getRssDate());
        Picasso.with(context)
                .load(mRSSItem.getRssImage())
                .fit()
                .centerCrop()
                .transform(new CropTrapeziumTransformation())
                .into(mBinding.itemImage);
    }

    @Override
    public void onClick(View view) {
        EventManager.publishEvent(TAG, new RSSItemOnClickEvent(mRSSItem.getRssLink()));
    }
}
