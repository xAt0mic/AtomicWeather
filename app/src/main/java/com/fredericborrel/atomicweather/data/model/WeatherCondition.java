package com.fredericborrel.atomicweather.data.model;

import java.util.List;

/**
 * Created by Frederic on 22/05/2017.
 */

public class WeatherCondition {

    private Integer mCurrentCode;
    private String mCurrentDate;
    private Integer mCurrentTemp;
    private Location mLocation;
    private List<Forecast> mForecastList;

    public WeatherCondition(Integer currentCode, String currentDate, Integer currentTemp, Location location, List<Forecast> forecastList) {
        mCurrentCode = currentCode;
        mCurrentDate = currentDate;
        mCurrentTemp = currentTemp;
        mLocation = location;
        mForecastList = forecastList;
    }

    public Integer getCurrentCode() {
        return mCurrentCode;
    }

    public String getCurrentDate() {
        return mCurrentDate;
    }

    public Integer getCurrentTemp() {
        return mCurrentTemp;
    }

    public Location getLocation() {
        return mLocation;
    }

    public List<Forecast> getForecastList() {
        return mForecastList;
    }
}
