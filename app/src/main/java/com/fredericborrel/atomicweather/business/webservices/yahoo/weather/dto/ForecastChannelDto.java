package com.fredericborrel.atomicweather.business.webservices.yahoo.weather.dto;

import java.util.List;

/**
 * Created by Frederic on 22/05/2017.
 */

public class ForecastChannelDto {

    private ForecastItemDto item;

    private ForecastLocationDto location;

    public List<ForecastDto> getForecastDtoList() {
        return item.getForecastDtoList();
    }

    public ForecastConditionDto getConditionDto() {
        return item.getConditionDto();
    }

    public ForecastLocationDto getLocationDto() {
        return location;
    }
}
