package com.fredericborrel.atomicweather;

import android.app.Application;
import android.content.Context;

/**
 * Created by Frederic on 22/12/2016.
 */

public class AtomicWeather extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
