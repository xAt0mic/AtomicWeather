package com.fredericborrel.atomicweather.business.webservices.yahoo.weather.dto;

/**
 * Created by Frederic on 22/05/2017.
 */

public class ForecastConditionDto {

    private Integer code;
    private String date;
    private Integer temp;

    public Integer getCode() {
        return code;
    }

    public String getDate() {
        return date;
    }

    public Integer getTemp() {
        return temp;
    }
}
