package com.fredericborrel.atomicweather.data.model;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by Frederic on 19/05/2017.
 */

public enum ConditionCode {

    TORNADO,
    TROPICAL_STORM,
    HURRICANE,
    SEVERE_THUNDERSTORMS,
    THUNDERSTORMS,
    MIXED_RAIN_AND_SNOW,
    MIXED_RAIN_AND_SLEET,
    MIXED_SNOW_AND_SLEET,
    FREEZING_DRIZZLE,
    DRIZZLE,
    FREEZING_RAIN,
    SHOWERS,
    SNOW_FLURRIES,
    LIGHT_SNOW_SHOWERS,
    BLOWING_SNOW,
    SNOW,
    HAIL,
    SLEET,
    DUST,
    FOGGY,
    HAZE,
    SMOKY,
    BLUSTERY,
    WINDY,
    COLD,
    CLOUDY,
    MOSTLY_CLOUDY_NIGHT,
    MOSTLY_CLOUDY_DAY,
    PARTLY_CLOUDY_NIGHT,
    PARLTY_CLOUDY_DAY,
    CLEAR_NIGHT,
    SUNNY,
    FAIR_NIGHT,
    FAIR_DAY,
    MIXED_RAIN_AND_HAIL,
    HOT,
    ISOLATED_THUNDERSTORMS,
    SCATTERED_THUNDERSTORMS,
    SCATTERED_SHOWERS,
    HEAVY_SNOW,
    SCATTERED_SNOW_SHOWERS,
    PARTLY_CLOUDY,
    THUNDERSHOWERS,
    SNOW_SHOWERS,
    ISOLATED_THUNDERSHOWERS,
    NOT_AVAILABLE;

    private static final String RESOURCE_BASE_NAME = "ic_weather_";

    public static ConditionCode getCondition(int code) {
        switch (code) {
            case 0: return TORNADO;
            case 1: return TROPICAL_STORM;
            case 2: return HURRICANE;
            case 3: return SEVERE_THUNDERSTORMS;
            case 4: return THUNDERSTORMS;
            case 5: return MIXED_RAIN_AND_SNOW;
            case 6: return MIXED_RAIN_AND_SLEET;
            case 7: return MIXED_SNOW_AND_SLEET;
            case 8: return FREEZING_DRIZZLE;
            case 9: return DRIZZLE;
            case 10: return FREEZING_RAIN;
            case 11:case 12: return SHOWERS;
            case 13: return SNOW_FLURRIES;
            case 14: return LIGHT_SNOW_SHOWERS;
            case 15: return BLOWING_SNOW;
            case 16: return SNOW;
            case 17: return HAIL;
            case 18: return SLEET;
            case 19: return DUST;
            case 20: return FOGGY;
            case 21: return HAZE;
            case 22: return SMOKY;
            case 23: return BLUSTERY;
            case 24: return WINDY;
            case 25: return COLD;
            case 26: return CLOUDY;
            case 27: return MOSTLY_CLOUDY_NIGHT;
            case 28: return MOSTLY_CLOUDY_DAY;
            case 29: return PARTLY_CLOUDY_NIGHT;
            case 30: return PARLTY_CLOUDY_DAY;
            case 31: return CLEAR_NIGHT;
            case 32: return SUNNY;
            case 33: return FAIR_NIGHT;
            case 34: return FAIR_DAY;
            case 35: return MIXED_RAIN_AND_HAIL;
            case 36: return HOT;
            case 37: return ISOLATED_THUNDERSTORMS;
            case 38:case 39: return SCATTERED_THUNDERSTORMS;
            case 40: return SCATTERED_SHOWERS;
            case 41:case 43: return HEAVY_SNOW;
            case 42: return SCATTERED_SNOW_SHOWERS;
            case 44: return PARTLY_CLOUDY;
            case 45: return THUNDERSHOWERS;
            case 46: return SNOW_SHOWERS;
            case 47: return ISOLATED_THUNDERSHOWERS;
            case 3200: default: return NOT_AVAILABLE;
        }
    }

    public static int getConditionImage(Context context, int code) {
        Resources resources =  context.getResources();
        final int resourceID = resources.getIdentifier(
                RESOURCE_BASE_NAME + code,
                "drawable",
                context.getPackageName());
        return resourceID;
    }

    public static String getConditionString(int code) {
        String result = getCondition(code).toString()
                .replace("_", " ");
        result = String.valueOf(result.charAt(0)).toUpperCase() + result.substring(1, result.length()).toLowerCase();
        return result;
    }
}
