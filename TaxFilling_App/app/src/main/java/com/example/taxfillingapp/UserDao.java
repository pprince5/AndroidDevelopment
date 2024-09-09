package com.example.taxfillingapp;


import static android.icu.text.MessagePattern.ArgType.SELECT;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT username FROM users")
    List<String> getUsername();

//    @Query("SELECT name FROM users")
//    List<String> getName();

    @Query("SELECT * FROM users WHERE uid IN(:userIds)")
    List<User>loadAllByIds(int[] userIds);

    @Query("SELECT * FROM Users WHERE name LIKE :name LIMIT 1")
    User findByName(String name);

    @Query("SELECT * FROM users WHERE email = :email")
    User findByEmail(String email);

    @Insert
    void insert(User user);

    @Insert
    void insertall(User...users);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

}




