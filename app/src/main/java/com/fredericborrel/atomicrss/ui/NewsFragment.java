package com.fredericborrel.atomicrss.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fredericborrel.atomicrss.R;
import com.fredericborrel.atomicrss.databinding.FragmentNewsBinding;
import com.fredericborrel.atomicrss.support.RSSWebViewClient;


/**
 * Created by Frederic on 12/04/16.
 */
public class NewsFragment extends Fragment {

    private static final String URL_LINK_ARG = "url_link";

    private String mUrlLink;
    private FragmentNewsBinding mBinding;

    public static NewsFragment newInstance(String url){
        NewsFragment newsFragment = new NewsFragment();

        // Forward the args to the Fragment
        Bundle args = new Bundle();
        args.putString(URL_LINK_ARG, url);
        newsFragment.setArguments(args);

        return newsFragment;
    }

    public NewsFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrlLink = getArguments().getString(URL_LINK_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false);

        mBinding.newsWebview.setWebViewClient(new RSSWebViewClient(mBinding.newsProgressRing));
        mBinding.newsWebview.loadUrl(mUrlLink);

        // Make the progressbar being an indeterminate ring
        mBinding.newsProgressRing.setIndeterminate(true);

        return mBinding.getRoot();
    }
}
