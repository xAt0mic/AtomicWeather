package com.fredericborrel.atomicweather.business.webservices.yahoo.weather.endpoints;

import com.fredericborrel.atomicweather.business.webservices.yahoo.weather.dto.ForecastAnswerDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Frederic on 19/05/2017.
 */

public interface YahooForecastEndpoint {

    @GET("yql?")
    Call<ForecastAnswerDto> getWeatherForecast(@Query("q") String query, @Query("format") String json);
}
