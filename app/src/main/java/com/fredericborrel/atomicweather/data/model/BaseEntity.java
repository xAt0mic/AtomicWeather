package com.fredericborrel.atomicweather.data.model;

import com.j256.ormlite.field.DatabaseField;

import java.util.UUID;

/**
 * Created by Frederic on 22/12/2016.
 */

public abstract class BaseEntity {

    public static final String UUID_COLUMN = "Uuid";

    @DatabaseField(id = true, columnName = UUID_COLUMN)
    protected String uuid;

    protected BaseEntity() {
        uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
