package com.example.taxfillingapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "geo")
public class Geo {

    @PrimaryKey(autoGenerate = true)
    public int gid;
    @ColumnInfo(name = "lat")
    public String lat;

    @ColumnInfo(name = "lng")
    public String lng;

    // Constructors, getters, and setters
    public Geo(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
