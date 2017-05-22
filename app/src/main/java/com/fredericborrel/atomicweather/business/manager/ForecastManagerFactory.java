package com.fredericborrel.atomicweather.business.manager;

/**
 * Created by Frederic on 19/05/2017.
 */

public class ForecastManagerFactory {

    public static ForecastManager getForecastManager() {
        return new YahooForecastManager();
    }
}
