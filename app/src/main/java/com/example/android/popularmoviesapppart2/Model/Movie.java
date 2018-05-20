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
    private final byte[] mPosterBytes;
    private final byte[] mBackdropBytes;

    public Movie(String title, String imageUrl, String plot, double userRating, String releaseDate, String movieBackdrop,
                 int movieId) {
        this.mTitle = title;
        this.mImageUrl = imageUrl;
        this.mPlot = plot;
        this.mUserRating = userRating;
        this.mReleaseDate = releaseDate;
        this.mMovieBackdrop = movieBackdrop;
        this.mMovieId = movieId;
        this.mPosterBytes = null;
        this.mBackdropBytes = null;
    }

    public Movie(String title, String plot, double userRating, String releaseDate,
                 int movieId, byte[] poster, byte[] backdrop) {
        this.mTitle = title;
        this.mPlot = plot;
        this.mUserRating = userRating;
        this.mReleaseDate = releaseDate;
        this.mMovieBackdrop = null;
        this.mMovieId = movieId;
        this.mPosterBytes = poster;
        this.mImageUrl = null;
        this.mBackdropBytes = backdrop;
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

    public String getMovieBackdropUrl() {return  mMovieBackdrop; }

    public Bitmap getMoviePoster() {
        return convertBytesToBitmap(mPosterBytes);
    }

    public byte[] getMoviePosterByte() {return mPosterBytes;}

    public byte[] getMovieBackdropByte() {return mBackdropBytes;}

    public int getMovieId() {return mMovieId;}

    public static Bitmap convertBytesToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
