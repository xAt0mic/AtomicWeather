package com.fredericborrel.atomicweather.business.manager;

import com.fredericborrel.atomicweather.business.webservices.yahoo.weather.dto.ForecastConditionDto;
import com.fredericborrel.atomicweather.business.webservices.yahoo.weather.dto.ForecastDto;
import com.fredericborrel.atomicweather.business.webservices.yahoo.weather.dto.ForecastLocationDto;
import com.fredericborrel.atomicweather.data.model.WeatherCondition;
import com.fredericborrel.atomicweather.data.model.Location;
import com.fredericborrel.atomicweather.utils.Constant;
import com.fredericborrel.atomicweather.business.webservices.yahoo.weather.dto.ForecastAnswerDto;
import com.fredericborrel.atomicweather.business.webservices.yahoo.weather.endpoints.ForecastEndpoint;
import com.fredericborrel.atomicweather.data.model.Forecast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Frederic on 19/05/2017.
 */

public class YahooForecastManager implements ForecastManager {

    private ForecastEndpoint mForecastEndpoint;
    private Retrofit mRetrofit;

    public YahooForecastManager() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.YAHOO_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mForecastEndpoint = mRetrofit.create(ForecastEndpoint.class);
    }

    public WeatherCondition getWeatherCondition() {
        WeatherCondition weatherCondition = null;
        try {
            Response<ForecastAnswerDto> response = mForecastEndpoint.getWeatherForecast().execute();
            if (response.body() != null) {
                ForecastAnswerDto resultDto = response.body();
                if (resultDto.getForecastDtoList() != null) {
                    List<Forecast> forecastList = new ArrayList<>();
                    // Create a factory to cast forecastdto into a forecast
                    List<ForecastDto> forecastDtoList = resultDto.getForecastDtoList();
                    for (ForecastDto forecastDto : forecastDtoList) {
                        forecastList.add(createForecast(forecastDto));
                    }
                    Location location = createLocation(resultDto.getLocationDto());
                    weatherCondition = createCondition(resultDto.getConditionDto(), location, forecastList);
                }
            }
        }
        catch (IOException e) {
            return weatherCondition;
        }
        return weatherCondition;
    }

    private Forecast createForecast(ForecastDto forecastDto) {
        return new Forecast(
                forecastDto.getCode(),
                forecastDto.getDate(),
                forecastDto.getHigh(),
                forecastDto.getLow());
    }

    private WeatherCondition createCondition(ForecastConditionDto conditionDto, Location location, List<Forecast> forecastList) {
        return new WeatherCondition(
                conditionDto.getCode(),
                conditionDto.getDate(),
                conditionDto.getTemp(),
                location,
                forecastList
        );
    }

    private Location createLocation(ForecastLocationDto locationDto) {
        return new Location(
                locationDto.getCity(),
                locationDto.getCountry(),
                locationDto.getRegion()
        );
    }
}
