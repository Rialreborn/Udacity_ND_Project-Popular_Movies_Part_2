package com.example.android.popularmoviesapppart2.Model;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{

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

    protected Movie(Parcel in) {
        mTitle = in.readString();
        mImageUrl = in.readString();
        mPlot = in.readString();
        mUserRating = in.readDouble();
        mReleaseDate = in.readString();
        mMovieBackdrop = in.readString();
        mMovieId = in.readInt();
        mPosterBytes = in.createByteArray();
        mBackdropBytes = in.createByteArray();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mImageUrl);
        dest.writeString(mPlot);
        dest.writeDouble(mUserRating);
        dest.writeString(mReleaseDate);
        dest.writeString(mMovieBackdrop);
        dest.writeInt(mMovieId);
        dest.writeByteArray(mPosterBytes);
        dest.writeByteArray(mBackdropBytes);
    }
}
