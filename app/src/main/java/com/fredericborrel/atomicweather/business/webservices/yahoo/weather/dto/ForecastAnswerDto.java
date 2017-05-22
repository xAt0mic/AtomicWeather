package com.fredericborrel.atomicweather.business.webservices.yahoo.weather.dto;

import com.fredericborrel.atomicweather.data.model.Forecast;

import java.util.List;

/**
 * Created by Frederic on 19/05/2017.
 */

public class ForecastAnswerDto {

    private ForecastQueryDto query;

    public List<ForecastDto> getForecastDtoList() {
        return query.getForecastDtoList();
    }

    public ForecastConditionDto getConditionDto() {
        return query.getConditionDto();
    }

    public ForecastLocationDto getLocationDto() {
        return query.getLocationDto();
    }
}
