package com.fredericborrel.atomicweather.business.manager;

import com.fredericborrel.atomicweather.business.webservices.yahoo.weather.dto.ForecastAnswerDto;
import com.fredericborrel.atomicweather.business.webservices.yahoo.weather.dto.ForecastConditionDto;
import com.fredericborrel.atomicweather.business.webservices.yahoo.weather.dto.ForecastDto;
import com.fredericborrel.atomicweather.business.webservices.yahoo.weather.dto.ForecastLocationDto;
import com.fredericborrel.atomicweather.business.webservices.yahoo.weather.endpoints.YahooForecastEndpoint;
import com.fredericborrel.atomicweather.data.model.Forecast;
import com.fredericborrel.atomicweather.data.model.Location;
import com.fredericborrel.atomicweather.data.model.WeatherCondition;
import com.fredericborrel.atomicweather.utils.Constant;

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

    private final static String CITY_PARAMETER = "_CITY_";
    private final static String STATE_PARAMETER = "_STATE_";
    private final static String ENDPOINT = "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\""+CITY_PARAMETER+", "+STATE_PARAMETER+"\")";
    private final static String FORMAT = "json";

    private YahooForecastEndpoint mYahooForecastEndpoint;

    YahooForecastManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.YAHOO_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mYahooForecastEndpoint = retrofit.create(YahooForecastEndpoint.class);
    }

    public String buildForecastQuery(Location location) {
        return  ENDPOINT
                .replace(CITY_PARAMETER, location.getCity())
                .replace(STATE_PARAMETER, location.getRegion());
    }

    public WeatherCondition getWeatherCondition(Location location) {
        WeatherCondition weatherCondition = null;
        try {
            String query = buildForecastQuery(location);
            Response<ForecastAnswerDto> response = mYahooForecastEndpoint.getWeatherForecast(query, FORMAT).execute();
            if (response.body() != null) {
                ForecastAnswerDto resultDto = response.body();
                if (resultDto.getForecastDtoList() != null) {
                    List<Forecast> forecastList = new ArrayList<>();
                    // Create a factory to cast forecastdto into a forecast
                    List<ForecastDto> forecastDtoList = resultDto.getForecastDtoList();
                    for (ForecastDto forecastDto : forecastDtoList) {
                        forecastList.add(createForecast(forecastDto));
                    }
                    Location resultLocation = createLocation(resultDto.getLocationDto());
                    weatherCondition = createCondition(resultDto.getConditionDto(), resultLocation, forecastList);
                }
            }
        }
        catch (IOException e) {
            return null;
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
