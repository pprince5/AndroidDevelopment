package com.example.movieapp;

public class Movie {

    private String movietitle;
    private int movieyear;
    private int movieid;
    private String movietype;
    private String movieimgurl;

    private String moviedesc;

    public Movie(String movietitle, int movieyear, int movieid, String movietype, String movieimgurl, String moviedesc){
        this.movietitle = movietitle;
        this.movieyear = movieyear;
        this.movieid = movieid;
        this.movietype = movietype;
        this.movieimgurl = movieimgurl;
        this.moviedesc = moviedesc;
    }

    public String gettitle(){
        return movietitle;
    }
    public int getid(){
        return movieid;
    }
    public String gettype(){
        return movietype;
    }
    public int getyear(){
        return movieyear;
    }
    public String getimgurl(){
        return movieimgurl;
    }

    public String getdesc(){return moviedesc;}
}
