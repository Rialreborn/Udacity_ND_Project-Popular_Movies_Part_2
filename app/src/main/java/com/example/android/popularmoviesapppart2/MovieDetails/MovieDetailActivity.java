package com.example.android.popularmoviesapppart2.MovieDetails;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v7.widget.Toolbar;

import com.example.android.popularmoviesapppart2.Model.Reviews;
import com.example.android.popularmoviesapppart2.Model.Trailers;
import com.example.android.popularmoviesapppart2.R;
import com.example.android.popularmoviesapppart2.Utils.Constants;
import com.example.android.popularmoviesapppart2.Utils.LoadReviews;
import com.example.android.popularmoviesapppart2.Utils.LoadReviews.OnReviewsLoadFinished;
import com.example.android.popularmoviesapppart2.Utils.LoadTrailerData;
import com.example.android.popularmoviesapppart2.Utils.LoadTrailerData.OnTrailerLoadFinished;
import com.example.android.popularmoviesapppart2.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieDetailActivity extends AppCompatActivity
        implements OnTrailerLoadFinished,
        OnReviewsLoadFinished {

    @Nullable @BindView(R.id.movie_backdrop)
    ImageView backdropImage;
    @BindView(R.id.movie_image)
    ImageView movieImage;
    @BindView(R.id.user_rating_tv)
    TextView userRatingTv;
    @BindView(R.id.release_date_tv)
    TextView releaseDateTv;
    @BindView(R.id.plot_tv)
    TextView plotTv;
    @Nullable @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_trailerList)
    RecyclerView rvTrailerList;
    @BindView(R.id.rv_reviews)
    RecyclerView rvReviews;
    @BindView(R.id.no_reviews_found)
    TextView tvNoReviews;
    @Nullable @BindView(R.id.title)
            TextView tvMovieTitle;

    int movieID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        ButterKnife.bind(MovieDetailActivity.this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnNullIntent();
        }

        /*
         * Init. Movie ID
         */
        movieID = intent.getIntExtra(Constants.INTENT_MOVIE_ID, 0);

        /*
         * Populate Views with intent data
         */
        populateViews(intent);

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
        if (toolbar != null) {
            toolbar.setTitle(intent.getStringExtra(Constants.INTENT_TITLE));
        } else {
            tvMovieTitle.setText(intent.getStringExtra(Constants.INTENT_TITLE));
        }

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
                NetworkUtils.buildMovieBackdropUri(intent.getStringExtra(Constants.INTENT_BACKDROP_URL));
        Uri moviePosterUri =
                NetworkUtils.buildImageURL(intent.getStringExtra(Constants.INTENT_IMAGE_URL));

        // Populate Views with Data
        userRatingTv.setText(userRating);
        releaseDateTv.setText(intent.getStringExtra(Constants.INTENT_RELEASE_DATE));
        plotTv.setText(intent.getStringExtra(Constants.INTENT_PLOT));

        /**
         * Load Images
         */
        if (backdropImage != null) {
            Picasso.with(this)
                    .load(backdropImageUri)
                    .into(backdropImage);
        }
        Picasso.with(this)
                .load(moviePosterUri)
                .into(movieImage);
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
}
