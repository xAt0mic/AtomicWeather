package com.fredericborrel.atomicweather.business.webservices.yahoo.weather.endpoints;

import com.fredericborrel.atomicweather.business.webservices.yahoo.weather.dto.ForecastAnswerDto;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Frederic on 19/05/2017.
 */

public interface ForecastEndpoint {

    @GET("yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22atlanta%2C%20ga%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys")
    Call<ForecastAnswerDto> getWeatherForecast();
}
