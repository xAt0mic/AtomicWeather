package com.fredericborrel.atomicweather.data.model;

/**
 * Created by Frederic on 22/05/2017.
 */

public class Location {

    private String mCity;
    private String mCountry;
    private String mRegion;

    public Location(String city, String country, String region) {
        mCity = city;
        mCountry = country;
        mRegion = region;
    }

    public String getCity() {
        return mCity;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getRegion() {
        return mRegion;
    }

    public String getFormattedLocation() {
        return mCity + "," + mRegion + " - " + mCountry;
    }
}
