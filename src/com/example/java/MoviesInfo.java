package com.example.java;

import java.util.Date;

/**
 * Created by Hiumathy on 5/1/17.
 */
public class MoviesInfo {
    String title;
    String rating;
    Date date;

    public MoviesInfo(String title, String rating, Date release_date) {
        this.title = title;
        this.rating = rating;
        date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public String getRating() {
        return rating;
    }

    public Date getDate() {
        return date;
    }

}
