package com.example.taxfillingapp;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "address")
public class Address {

    @PrimaryKey(autoGenerate = true)
    public int aid;
    @ColumnInfo(name = "street")
    public String street;

    @ColumnInfo(name = "suite")
    public String suite;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "zipcode")
    public String zipcode;

    @Embedded(prefix = "geo_")
    public Geo geo;
    public Address() {
    }

    // Constructors, getters, and setters
    public Address(String street, String suite, String city, String zipcode, Geo geo) {
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.geo = geo;
    }
}
