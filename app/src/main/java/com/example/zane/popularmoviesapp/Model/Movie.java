package com.example.zane.popularmoviesapp.Model;


public class Movie {

    private final String mTitle;
    private final String mImageUrl;
    private final String mPlot;
    private final double mUserRating;
    private final String mReleaseDate;
    private final String mMovieBackdrop;

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
