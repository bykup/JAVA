package com.asuper.byku.myapplication;

import android.util.Log;

/**
 * Created by Lincoln on 15/01/16.
 */
public class Movie { //nothing new here
    private String title, genre, year;

    private Movie() {
    }

    public Movie(String title, String genre, String year) {
        Log.i("Log","Movie: (Creating a movie class");
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
