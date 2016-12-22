package com.fredericborrel.atomicrss.support;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fredericborrel.atomicrss.AtomicRSS;

/**
 * Created by Frederic on 15/04/16.
 */
public class NetworkAvailability {
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) AtomicRSS.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
