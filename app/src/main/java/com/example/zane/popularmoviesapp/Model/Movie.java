package com.example.zane.popularmoviesapp.Model;

/**
 * Created by Zane on 03/04/2018.
 */

public class Movie {

    private String mTitle;
    private String mImageUrl;
    private String mPlot;
    private double mUserRating;
    private String mReleaseDate;
    private String mMovieBackdrop;

    public Movie(String title, String imageUrl, String plot, double userRating, String releaseDate, String movieBackdrop) {
        this.mTitle = title;
        this.mImageUrl = imageUrl;
        this.mPlot = plot;
        this.mUserRating = userRating;
        this.mReleaseDate = releaseDate;
        this.mMovieBackdrop = movieBackdrop;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getPlot() {
        return mPlot;
    }

    public double getUserRating() {
        return mUserRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getMovieBackdrop() {return  mMovieBackdrop; }
}
