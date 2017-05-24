package com.fredericborrel.atomicweather.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.fredericborrel.atomicweather.AtomicWeather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Frederic on 19/01/2017.
 */

public class Utils {

    public static float convertPixelsToDp(float px){
        Resources resources = AtomicWeather.context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static float convertDpToPixel(float dp){
        Resources resources = AtomicWeather.context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static String convertStringDate(String dateInput, SimpleDateFormat formatInput, SimpleDateFormat formatOutput) {
        try {
            Date date = formatInput.parse(dateInput);
            return formatOutput.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
