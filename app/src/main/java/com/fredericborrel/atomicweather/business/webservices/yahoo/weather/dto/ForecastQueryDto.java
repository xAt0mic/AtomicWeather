package com.fredericborrel.atomicweather.business.webservices.yahoo.weather.dto;

import java.util.List;

/**
 * Created by Frederic on 22/05/2017.
 */

public class ForecastQueryDto {

    private ForecastResultDto results;

    List<ForecastDto> getForecastDtoList() {
        return results.getForecastDtoList();
    }

    public ForecastConditionDto getConditionDto() {
        return results.getConditionDto();
    }

    public ForecastLocationDto getLocationDto() {
        return results.getLocationDto();
    }
}