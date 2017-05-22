package com.fredericborrel.atomicweather.business.webservices.yahoo.weather.dto;

import java.util.List;

/**
 * Created by Frederic on 22/05/2017.
 */

public class ForecastItemDto {

    private ForecastConditionDto condition;

    private List<ForecastDto> forecast;

    public List<ForecastDto> getForecastDtoList() {
        return forecast;
    }

    public ForecastConditionDto getConditionDto() {
        return condition;
    }
}