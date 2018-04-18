package com.example.android.popularmoviesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v7.widget.Toolbar;


import com.example.android.popularmoviesapp.Utils.Constants;
import com.example.android.popularmoviesapp.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieDetailActivity extends AppCompatActivity {

    @BindView(com.example.android.popularmoviesapp.R.id.movie_backdrop) ImageView backdropImage;
    @BindView(com.example.android.popularmoviesapp.R.id.movie_image) ImageView movieImage;
    @BindView(com.example.android.popularmoviesapp.R.id.user_rating_tv) TextView userRatingTv;
    @BindView(com.example.android.popularmoviesapp.R.id.release_date_tv) TextView releaseDateTv;
    @BindView(com.example.android.popularmoviesapp.R.id.plot_tv) TextView plotTv;
    @BindView(com.example.android.popularmoviesapp.R.id.toolbar) Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.example.android.popularmoviesapp.R.layout.movie_detail);

        ButterKnife.bind(MovieDetailActivity.this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnNullIntent();
        }

        // Get Info for Movie Details
        String userRating = String.valueOf(intent != null ? intent.getDoubleExtra(Constants.INTENT_USER_RATING, 0.0) : 0) + " / 10";
        Uri backdropImageUri = NetworkUtils.buildMovieBackdropURL(intent.getStringExtra(Constants.INTENT_BACKDROP_URL));
        Uri moviePosterUri = NetworkUtils.buildImageURL(intent.getStringExtra(Constants.INTENT_IMAGE_URL));

        // Populate Views with Data
        userRatingTv.setText(userRating);
        releaseDateTv.setText(intent.getStringExtra(Constants.INTENT_RELEASE_DATE));
        plotTv.setText(intent.getStringExtra(Constants.INTENT_PLOT));
        Picasso.with(this)
                .load(backdropImageUri)
                .into(backdropImage);
        Picasso.with(this)
                .load(moviePosterUri)
                .into(movieImage);

        // Update Toolbar and Window
        toolbar.setTitle(intent.getStringExtra(Constants.INTENT_TITLE));

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, com.example.android.popularmoviesapp.R.color.colorPrimaryDarker));

    }

    private void closeOnNullIntent() {
        finish();
        Toast.makeText(this, com.example.android.popularmoviesapp.R.string.detail_intent_error, Toast.LENGTH_SHORT).show();
    }
}
