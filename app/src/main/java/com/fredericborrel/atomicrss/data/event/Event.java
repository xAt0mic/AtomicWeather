package com.fredericborrel.atomicrss.data.event;

/**
 * Created by Frederic on 22/12/2016.
 */

public class Event {
    private String name;

    public Event() {
        this.name = getClass().getSimpleName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                '}';
    }
}
