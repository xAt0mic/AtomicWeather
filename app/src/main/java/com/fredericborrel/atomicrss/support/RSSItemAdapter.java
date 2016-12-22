package com.fredericborrel.atomicrss.support;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fredericborrel.atomicrss.R;
import com.fredericborrel.atomicrss.data.model.RSSItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Frederic on 12/04/16.
 */
public class RSSItemAdapter extends ArrayAdapter<RSSItem> {

    private Activity myContext;
    private ArrayList<RSSItem> data;

    public static class RSSViewHolder {
        TextView itemTitleTV;
        TextView itemDateTV;
        ImageView itemThumbnailIV;
        String itemThumbnailURL;
    }

    public RSSItemAdapter(Context context, int textViewResourceId, ArrayList<RSSItem> objects) {
        super(context, textViewResourceId, objects);
        myContext = (Activity) context;
        data = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        RSSViewHolder rssViewHolder;

        if(convertView == null){
            LayoutInflater inflater = myContext.getLayoutInflater();
            convertView = inflater.inflate(R.layout.feed_list_item,null);

            rssViewHolder = new RSSViewHolder();
            rssViewHolder.itemTitleTV = (TextView) convertView.findViewById(R.id.itemTitle);
            rssViewHolder.itemDateTV = (TextView) convertView.findViewById(R.id.itemDate);
            rssViewHolder.itemThumbnailIV = (ImageView) convertView.findViewById(R.id.itemImage);
            convertView.setTag(rssViewHolder);
        }
        else {
            rssViewHolder = (RSSViewHolder) convertView.getTag();
        }

        rssViewHolder.itemThumbnailURL = data.get(position).getRssImage();
        rssViewHolder.itemTitleTV.setText(data.get(position).getRssTitle());
        rssViewHolder.itemDateTV.setText(data.get(position).getRssDate());
        Picasso.with(myContext)
                .load(rssViewHolder.itemThumbnailURL)
                .fit()
                .centerCrop()
                .into(rssViewHolder.itemThumbnailIV);
        return convertView;
    }

}
