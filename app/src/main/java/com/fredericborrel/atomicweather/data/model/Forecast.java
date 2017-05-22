package com.fredericborrel.atomicweather.data.model;

import java.util.Date;

/**
 * Created by Frederic on 19/05/2017.
 */

public class Forecast {

    private Integer mCode;
    private String mDate;
    private Integer mHigh;
    private Integer mLow;

    public Forecast(Integer code, String date, Integer high, Integer low) {
        mCode = code;
        mDate = date;
        mHigh = high;
        mLow = low;
    }

    public Integer getCode() {
        return mCode;
    }

    public String getDate() {
        return mDate;
    }

    public Integer getHigh() {
        return mHigh;
    }

    public Integer getLow() {
        return mLow;
    }
}
