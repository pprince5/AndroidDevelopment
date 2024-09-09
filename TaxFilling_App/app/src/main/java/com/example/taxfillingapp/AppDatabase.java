package com.example.taxfillingapp;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Company.class, Geo.class, Address.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}
