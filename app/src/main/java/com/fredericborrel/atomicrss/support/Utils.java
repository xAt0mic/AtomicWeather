package com.fredericborrel.atomicrss.support;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.fredericborrel.atomicrss.AtomicRSS;

/**
 * Created by Frederic on 19/01/2017.
 */

public class Utils {

    public static float convertPixelsToDp(float px){
        Resources resources = AtomicRSS.context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static float convertDpToPixel(float dp){
        Resources resources = AtomicRSS.context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
}
