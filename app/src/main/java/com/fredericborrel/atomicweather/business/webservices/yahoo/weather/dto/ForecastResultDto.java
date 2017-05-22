package com.fredericborrel.atomicweather.business.webservices.yahoo.weather.dto;

import java.util.List;

/**
 * Created by Frederic on 22/05/2017.
 */

public class ForecastResultDto {

    private ForecastChannelDto channel;

    List<ForecastDto> getForecastDtoList() {
        return channel.getForecastDtoList();
    }

    public ForecastConditionDto getConditionDto() {
        return channel.getConditionDto();
    }

    public ForecastLocationDto getLocationDto() {
        return channel.getLocationDto();
    }

}