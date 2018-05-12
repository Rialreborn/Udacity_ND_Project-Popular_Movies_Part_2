package com.example.android.popularmoviesapppart2.MovieDetails;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v7.widget.Toolbar;

import com.example.android.popularmoviesapppart2.Model.Trailers;
import com.example.android.popularmoviesapppart2.R;
import com.example.android.popularmoviesapppart2.Utils.Constants;
import com.example.android.popularmoviesapppart2.Utils.LoadTrailerData;
import com.example.android.popularmoviesapppart2.Utils.LoadTrailerData.OnLoadFinished;
import com.example.android.popularmoviesapppart2.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieDetailActivity extends AppCompatActivity
    implements OnLoadFinished {

    @BindView(R.id.movie_backdrop) ImageView backdropImage;
    @BindView(R.id.movie_image) ImageView movieImage;
    @BindView(R.id.user_rating_tv) TextView userRatingTv;
    @BindView(R.id.release_date_tv) TextView releaseDateTv;
    @BindView(R.id.plot_tv) TextView plotTv;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.movie_id) TextView movieId;
    @BindView(R.id.rv_trailerList) RecyclerView rvTrailerList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        ButterKnife.bind(MovieDetailActivity.this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnNullIntent();
        }

        populateViews(intent);

        /*
         * Get Trailers
         */
        URL trailerUrl = NetworkUtils.buildTrailersUrl(
                intent.getIntExtra(
                        Constants.INTENT_MOVIE_ID, 0));
        new LoadTrailerData(this).execute(trailerUrl);

        /*
         * Set up RV
         */
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvTrailerList.addItemDecoration(new DividerItemDecoration(MovieDetailActivity.this, layoutManager.HORIZONTAL));
        rvTrailerList.setHasFixedSize(true);
        rvTrailerList.setLayoutManager(layoutManager);



        /*
         * Update Toolbar and Window
         */
        toolbar.setTitle(intent.getStringExtra(Constants.INTENT_TITLE));

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDarker));
    }

    private void closeOnNullIntent() {
        finish();
        Toast.makeText(this, R.string.detail_intent_error, Toast.LENGTH_SHORT).show();
    }

    private void populateViews(Intent intent) {
        // Get Info for Movie Details
        String userRating = String.valueOf(
                intent != null ? intent.getDoubleExtra(Constants.INTENT_USER_RATING, 0.0) : 0) + " / 10";
        Uri backdropImageUri =
                NetworkUtils.buildMovieBackdropURL(intent.getStringExtra(Constants.INTENT_BACKDROP_URL));
        Uri moviePosterUri =
                NetworkUtils.buildImageURL(intent.getStringExtra(Constants.INTENT_IMAGE_URL));

        // Populate Views with Data
        userRatingTv.setText(userRating);
        releaseDateTv.setText(intent.getStringExtra(Constants.INTENT_RELEASE_DATE));
        plotTv.setText(intent.getStringExtra(Constants.INTENT_PLOT));
        movieId.setText(intent.getStringExtra(Constants.MOVIE_ID));

        /**
         * Load Images
         */
        Picasso.with(this)
                .load(backdropImageUri)
                .into(backdropImage);
        Picasso.with(this)
                .load(moviePosterUri)
                .into(movieImage);
    }

    @Override
    public void loadFinished(final ArrayList<Trailers> trailersArrayList) {
        final String BASE_YOUTUBE_URL = "https://m.youtube.com/watch/?v=";

        TrailerListAdapter trailerListAdapter = new TrailerListAdapter(trailersArrayList, new TrailerListAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(int position) {
                String youtubeKey = trailersArrayList.get(position).getKey();
                Intent appIntent = new Intent(Intent.ACTION_VIEW, NetworkUtils.youtubeAppUri(youtubeKey));
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(BASE_YOUTUBE_URL + youtubeKey));

                try {
                    startActivity(appIntent);
                } catch (ActivityNotFoundException e){
                    startActivity(webIntent);
                }
            }
        });


        rvTrailerList.setAdapter(trailerListAdapter);

    }
}
