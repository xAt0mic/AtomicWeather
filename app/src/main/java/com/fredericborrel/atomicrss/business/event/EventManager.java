package com.fredericborrel.atomicrss.business.event;

import android.util.Log;

import com.fredericborrel.atomicrss.data.event.Event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Frederic on 22/12/2016.
 */

public class EventManager {

    public static void publishEvent(String tag, Event event) {
        Log.i(tag, "Publishing event: " + event.toString());
        EventBus.getDefault().post(event);
    }

    public  static void publishStickyEvent(String tag, Event event) {
        Log.i(tag, "Publishing STICKY event: " + event.toString());
        EventBus.getDefault().postSticky(event);
    }
}
