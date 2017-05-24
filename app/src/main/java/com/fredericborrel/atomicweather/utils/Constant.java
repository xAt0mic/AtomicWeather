package com.fredericborrel.atomicweather.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Frederic on 19/05/2017.
 */

public class Constant {

    public static final String YAHOO_API_BASE_URL = "https://query.yahooapis.com/v1/public/";
    public static final Locale LOCALE_DEFAULT = new Locale("US");
    public static final SimpleDateFormat YAHOO_DATE_FORMAT_INPUT = new SimpleDateFormat("dd MMM yyyy", LOCALE_DEFAULT);
    public static final SimpleDateFormat CARDVIEW_DATE_FORMAT_OUTPUT = new SimpleDateFormat("dd MMM", LOCALE_DEFAULT);
}
