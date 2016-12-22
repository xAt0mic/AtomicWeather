package com.fredericborrel.atomicrss.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.fredericborrel.atomicrss.R;
import com.fredericborrel.atomicrss.support.RSSWebViewClient;


/**
 * Created by Frederic on 12/04/16.
 */
public class NewsFragment extends Fragment {

    private static final String URL_LINK_ARG = "url_link";

    private String urlLink;
    private WebView newsWebview;
    private ProgressBar progressRing;

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
            urlLink = getArguments().getString(URL_LINK_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_news, container, false);

        newsWebview = (WebView) view.findViewById(R.id.news_webview);
        progressRing = (ProgressBar) view.findViewById(R.id.news_progress_ring);

        newsWebview.setWebViewClient(new RSSWebViewClient(progressRing));
        newsWebview.loadUrl(urlLink);

        // Make the progressbar being an indeterminate ring
        progressRing.setIndeterminate(true);

        return view;
    }
}
