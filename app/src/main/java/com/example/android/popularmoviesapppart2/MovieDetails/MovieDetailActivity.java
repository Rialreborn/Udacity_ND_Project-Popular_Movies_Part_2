package com.example.android.popularmoviesapppart2.MovieDetails;

import android.animation.ValueAnimator;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v7.widget.Toolbar;

import com.example.android.popularmoviesapppart2.Database.DbHelper;
import com.example.android.popularmoviesapppart2.Database.MovieContract.MovieEntry;
import com.example.android.popularmoviesapppart2.Model.Movie;
import com.example.android.popularmoviesapppart2.Model.Reviews;
import com.example.android.popularmoviesapppart2.Model.Trailers;
import com.example.android.popularmoviesapppart2.R;
import com.example.android.popularmoviesapppart2.Utils.Constants;
import com.example.android.popularmoviesapppart2.MovieDetails.LoadReviews.OnReviewsLoadFinished;
import com.example.android.popularmoviesapppart2.MovieDetails.LoadTrailerData.OnTrailerLoadFinished;
import com.example.android.popularmoviesapppart2.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieDetailActivity extends AppCompatActivity
        implements OnTrailerLoadFinished,
        OnReviewsLoadFinished {

    @Nullable
    @BindView(R.id.movie_backdrop)
    ImageView backdropImage;
    @BindView(R.id.movie_image)
    ImageView movieImage;
    @BindView(R.id.user_rating_tv)
    TextView userRatingTv;
    @BindView(R.id.release_date_tv)
    TextView releaseDateTv;
    @BindView(R.id.plot_tv)
    TextView plotTv;
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_trailerList)
    RecyclerView rvTrailerList;
    @BindView(R.id.rv_reviews)
    RecyclerView rvReviews;
    @BindView(R.id.no_reviews_found)
    TextView tvNoReviews;
    @Nullable
    @BindView(R.id.title)
    TextView tvMovieTitle;
    @BindView(R.id.add_to_favourites)
    ImageView ivFavourites;

    int movieID;
    Intent mIntent;
    boolean isFavourited;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        ButterKnife.bind(MovieDetailActivity.this);

        mIntent = getIntent();
        if (mIntent == null) {
            closeOnNullIntent();
        }

        movieID = mIntent.getIntExtra(Constants.INTENT_MOVIE_ID, 0);

        /*
         * Populate Views with mIntent data
         */
        populateViews();

        /*
         * Set up Trailer RV
         */
        URL trailerUrl = NetworkUtils.buildTrailersUrl(movieID);
        new LoadTrailerData(this).execute(trailerUrl);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvTrailerList.setHasFixedSize(true);
        rvTrailerList.setLayoutManager(layoutManager);

        /*
         * Set up Reviews RV
         */
        URL reviewUrl = NetworkUtils.buildReviewsUrl(movieID);
        new LoadReviews(this).execute(reviewUrl);
        LinearLayoutManager reviewLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvReviews.setHasFixedSize(true);
        rvReviews.setLayoutManager(reviewLayoutManager);

        /*
         * Update Toolbar and Window
         */
        tvMovieTitle.setText(mIntent.getStringExtra(Constants.INTENT_TITLE));

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDarker));

        /*
        Set up Favourite Button
         */
        checkIfFavourite();
    }

    private void checkIfFavourite() {
        String[] projection = {MovieEntry.COLUMN_MOVIE_ID};
        String id = String.valueOf(mIntent.getIntExtra(Constants.INTENT_MOVIE_ID, 0));
        String[] selectionArgs = {id};

        Cursor cursor = getContentResolver().query(
                MovieEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(mIntent.getIntExtra(Constants.INTENT_MOVIE_ID, 0))).build(),
                projection,
                null,
                selectionArgs,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            System.out.println("Movie is Favourite");
            isFavourited = true;
        } else {
            System.out.println("Movie is NOT Favourite");
            isFavourited = false;
        }
        setFavouritesColor();
    }

    private void closeOnNullIntent() {
        finish();
        Toast.makeText(this, R.string.detail_intent_error, Toast.LENGTH_SHORT).show();
    }

    private void populateViews() {
                /*
        Populate Text Views with Data
         */
        String userRating = String.valueOf(
                mIntent != null ? mIntent.getDoubleExtra(Constants.INTENT_USER_RATING, 0.0) : 0) + " / 10";
        userRatingTv.setText(userRating);
        releaseDateTv.setText(mIntent.getStringExtra(Constants.INTENT_RELEASE_DATE));
        plotTv.setText(mIntent.getStringExtra(Constants.INTENT_PLOT));

        /*
        Set Movie Poster
        If Bytes exist in Favourites Table, load
        Else load from web
         */
        byte[] byteMovieImage = mIntent.getByteArrayExtra(Constants.INTENT_POSTER_BYTE);
        if (byteMovieImage != null) {
            movieImage.setImageBitmap(Movie.convertBytesToBitmap(byteMovieImage));
        } else {
            Uri moviePosterUri =
                    NetworkUtils.buildImageURL(mIntent.getStringExtra(Constants.INTENT_IMAGE_URL));
            Picasso.with(this)
                    .load(moviePosterUri)
                    .into(movieImage);
        }

        /*
        Set Movie Backdrop
        If Bytes exist in Favourites Table, load
        Else load from web
         */
        byte[] byteMovieBackdrop = mIntent.getByteArrayExtra(Constants.INTENT_BACKDROP_BYTE);
        if (backdropImage == null) {return;}
        if (byteMovieBackdrop != null) {
            backdropImage.setImageBitmap(Movie.convertBytesToBitmap(byteMovieBackdrop));
        } else {
            Uri backdropImageUri =
                    NetworkUtils.buildMovieBackdropUri(mIntent.getStringExtra(Constants.INTENT_BACKDROP_URL));
            Picasso.with(this)
                    .load(backdropImageUri)
                    .into(backdropImage);
        }


    }

    @Override
    public void trailersFinished(final ArrayList<Trailers> trailersArrayList) {

        TrailerListAdapter trailerListAdapter = new TrailerListAdapter(trailersArrayList, new TrailerListAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(int position) {
                String youtubeKey = trailersArrayList.get(position).getKey();
                Intent appIntent = new Intent(Intent.ACTION_VIEW, NetworkUtils.buildYoutubeAppUri(youtubeKey));
                Intent webIntent = new Intent(Intent.ACTION_VIEW, NetworkUtils.buildYoutubeUri(youtubeKey));

                try {
                    startActivity(appIntent);
                } catch (ActivityNotFoundException e) {
                    startActivity(webIntent);
                }
            }
        });


        rvTrailerList.setAdapter(trailerListAdapter);
    }

    @Override
    public void reviewsFinished(final ArrayList<Reviews> reviewsArrayList) {

        ReviewListAdapter reviewListAdapter = new ReviewListAdapter(reviewsArrayList, new ReviewListAdapter.OnReviewClickListener() {
            @Override
            public void onReviewClicked(final TextView reviewContent) {

                if (reviewContent.getLineCount() == 5) {
                    ValueAnimator animator = ValueAnimator.ofInt(5, 100).setDuration(400);
                    animateExpansion(animator, reviewContent);
                } else {
                    ValueAnimator animator = ValueAnimator.ofInt(100, 5).setDuration(400);
                    animateExpansion(animator, reviewContent);
                }
            }
        }
        );

        if (reviewListAdapter.getItemCount() == 0) {
            rvReviews.setVisibility(View.GONE);
            tvNoReviews.setVisibility(View.VISIBLE);
        } else {
            rvReviews.setAdapter(reviewListAdapter);
        }

    }

    public void animateExpansion(ValueAnimator animator, final TextView reviewContent) {
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            int lastValue = -1;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();

                if (value == lastValue) {
                    return;
                }

                lastValue = value;

                reviewContent.setMaxLines(value);
            }
        });
        animator.start();
    }


    public void favouritesClicked(View view) {

        if (isFavourited) {
            isFavourited = false;
            deleteMovie();
        } else {
            isFavourited = true;
            insertMovie();
        }

        setFavouritesColor();
    }

    private void setFavouritesColor() {
        if (isFavourited) {
            ivFavourites.setColorFilter(getColor(R.color.colorPrimary));
        } else {
            ivFavourites.setColorFilter(getColor(R.color.colorMinorDetailsText));
        }
    }

    private void insertMovie() {

        Bitmap poster = ((BitmapDrawable) movieImage.getDrawable()).getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        poster.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] img = bos.toByteArray();

        ContentValues cv = new ContentValues();

        cv.put(MovieEntry.COLUMN_TITLE, mIntent.getStringExtra(Constants.INTENT_TITLE));
        cv.put(MovieEntry.COLUMN_PLOT, mIntent.getStringExtra(Constants.INTENT_PLOT));
        cv.put(MovieEntry.COLUMN_USER_RATING,
                mIntent != null ? mIntent.getDoubleExtra(Constants.INTENT_USER_RATING, 0.0) : 0);
        cv.put(MovieEntry.COLUMN_RELEASE_DATE, mIntent.getStringExtra(Constants.INTENT_RELEASE_DATE));
        cv.put(MovieEntry.COLUMN_MOVIE_ID, mIntent.getIntExtra(Constants.INTENT_MOVIE_ID, 0));
        cv.put(MovieEntry.COLUMN_POSTER_IMAGE, img);


        Uri uri = getContentResolver().insert(MovieEntry.CONTENT_URI, cv);

        if (uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void deleteMovie() {
        String movieId = String.valueOf(mIntent.getIntExtra(Constants.INTENT_MOVIE_ID, 0));
        System.out.println("Moive ID Delete: " + movieId);

        Uri uri = MovieEntry.CONTENT_URI.buildUpon().appendPath(movieId).build();

        getContentResolver().delete(uri, null, null);
    }
}
