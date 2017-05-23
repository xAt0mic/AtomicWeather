package com.fredericborrel.atomicweather.business.manager;

import com.fredericborrel.atomicweather.data.model.Location;
import com.fredericborrel.atomicweather.data.model.WeatherCondition;

import retrofit2.http.Path;

/**
 * Created by Frederic on 19/05/2017.
 */

public interface ForecastManager {
    WeatherCondition getWeatherCondition(Location location);

}
