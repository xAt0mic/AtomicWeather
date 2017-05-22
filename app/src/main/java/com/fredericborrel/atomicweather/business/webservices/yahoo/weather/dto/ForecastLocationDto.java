package com.fredericborrel.atomicweather.business.webservices.yahoo.weather.dto;

/**
 * Created by Frederic on 22/05/2017.
 */

public class ForecastLocationDto {

    private String city;
    private String country;
    private String region;

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }
}
