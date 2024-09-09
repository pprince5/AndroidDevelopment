package com.example.taxfillingapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "company")
public class Company {

    @PrimaryKey(autoGenerate = true)
    public int cid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "catchPhrase")
    public String catchPhrase;

    @ColumnInfo(name = "bs")
    public String bs;

    // Constructors, getters, and setters
    public Company(String name, String catchPhrase, String bs) {
        this.name = name;
        this.catchPhrase = catchPhrase;
        this.bs = bs;
    }
}
