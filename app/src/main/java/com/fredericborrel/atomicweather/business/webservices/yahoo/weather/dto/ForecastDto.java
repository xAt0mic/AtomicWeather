package com.fredericborrel.atomicweather.business.webservices.yahoo.weather.dto;

/**
 * Created by Frederic on 22/05/2017.
 */

public class ForecastDto {

    private Integer code;
    private String date;
    private Integer high;
    private Integer low;

    public Integer getCode() {
        return code;
    }

    public String getDate() {
        return date;
    }

    public Integer getHigh() {
        return high;
    }

    public Integer getLow() {
        return low;
    }
}