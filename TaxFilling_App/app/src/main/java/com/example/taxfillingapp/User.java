    package com.example.taxfillingapp;

    import androidx.room.ColumnInfo;
    import androidx.room.Embedded;
    import androidx.room.Entity;
    import androidx.room.PrimaryKey;

    @Entity(tableName = "users")
    public class User {

        @PrimaryKey(autoGenerate = true)
        public int uid;

        @ColumnInfo(name = "name")
        public String name;

        @ColumnInfo(name = "username")
        public String username;

        @ColumnInfo(name = "email")
        public String email;

        @ColumnInfo(name = "password")
        public String password;

        @Embedded
        public Address address;

        @ColumnInfo(name = "phone")
        public String phone;

        @ColumnInfo(name = "website")
        public String website;

        @Embedded(prefix = "company_")
        public Company company;

        @ColumnInfo(name = "status")
        public String status = "awaited";

        // Constructors, getters, and setters
        public User(int uid, String name, String username, String email, String password, Address address, String phone, String website, Company company) {
            this.uid = uid;
            this.name = name;
            this.username = username;
            this.password=password;
            this.email = email;
            this.address = address;
            this.phone = phone;
            this.website = website;
            this.company = company;
        }
    }
