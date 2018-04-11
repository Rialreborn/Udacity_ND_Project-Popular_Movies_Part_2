package com.example.zane.popularmoviesapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.TextUtils;

import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v7.widget.Toolbar;


import com.example.zane.popularmoviesapp.Utils.Constants;
import com.example.zane.popularmoviesapp.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;

/**
 * Created by Zane on 08/04/2018.
 */

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView mBackdropImage;
    private TextView mMovieTitleTv;
    private ImageView mMovieImage;
    private TextView mUserRatingTv;
    private TextView mReleaseDateTv;
    private TextView mPlot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        ButterKnife.bind(MovieDetailActivity.this);

        mBackdropImage = findViewById(R.id.movie_backdrop);
        mMovieImage = findViewById(R.id.movie_image);
        mUserRatingTv = findViewById(R.id.user_rating_tv);
        mReleaseDateTv = findViewById(R.id.release_date_tv);
        mPlot = findViewById(R.id.plot_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnNullIntent();
        }

        String userRating = String.valueOf(intent.getDoubleExtra(Constants.INTENT_USER_RATING, 0.0)) + " / 10";
        mUserRatingTv.setText(userRating);
        mReleaseDateTv.setText(intent.getStringExtra(Constants.INTENT_RELEASE_DATE));
        mPlot.setText(intent.getStringExtra(Constants.INTENT_PLOT));
        Picasso.with(this)
                .load(NetworkUtils.buildMovieBackdropURL(intent.getStringExtra(Constants.INTENT_BACKDROP_URL)))
                .into(mBackdropImage);
        Picasso.with(this)
                .load(NetworkUtils.buildImageURL(intent.getStringExtra(Constants.INTENT_IMAGE_URL)))
                .into(mMovieImage);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(intent.getStringExtra(Constants.INTENT_TITLE));

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryRipple));

    }

    private void closeOnNullIntent() {
        finish();
        Toast.makeText(this, R.string.detail_intent_error, Toast.LENGTH_SHORT).show();
    }
}
