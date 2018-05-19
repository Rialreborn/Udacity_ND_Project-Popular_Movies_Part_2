package com.example.android.popularmoviesapppart2.Model;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Movie {

    private final String mTitle;
    private final String mImageUrl;
    private final String mPlot;
    private final double mUserRating;
    private final String mReleaseDate;
    private final String mMovieBackdrop;
    private final int mMovieId;
    private final byte[] mPoster;

    public Movie(String title, String imageUrl, String plot, double userRating, String releaseDate, String movieBackdrop,
                 int movieId) {
        this.mTitle = title;
        this.mImageUrl = imageUrl;
        this.mPlot = plot;
        this.mUserRating = userRating;
        this.mReleaseDate = releaseDate;
        this.mMovieBackdrop = movieBackdrop;
        this.mMovieId = movieId;
        this.mPoster = null;
    }

    public Movie(String title, String plot, double userRating, String releaseDate,
                 int movieId, byte[] poster) {
        this.mTitle = title;
        this.mPlot = plot;
        this.mUserRating = userRating;
        this.mReleaseDate = releaseDate;
        this.mMovieBackdrop = null;
        this.mMovieId = movieId;
        this.mPoster = poster;
        this.mImageUrl = null;
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

    public Bitmap getMoviePoster() {
        return BitmapFactory.decodeByteArray(mPoster, 0, mPoster.length);
    }

    public int getMovieId() {return mMovieId;}
}
