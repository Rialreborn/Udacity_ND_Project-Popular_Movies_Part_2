package com.example.android.popularmoviesapppart2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.popularmoviesapppart2.Model.Movie;
import com.example.android.popularmoviesapppart2.MovieDetails.MovieDetailActivity;
import com.example.android.popularmoviesapppart2.Utils.Constants;
import com.example.android.popularmoviesapppart2.Utils.LoadMovieData;
import com.example.android.popularmoviesapppart2.Utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieListFragment extends Fragment
        implements
        LoadMovieData.BeforeTaskCompleteInterface,
        LoadMovieData.AfterTaskCompleteInterface

{

    private String mMovieOrder;

    @BindView(com.example.android.popularmoviesapppart2.R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(com.example.android.popularmoviesapppart2.R.id.progress_bar)
    ProgressBar progressBar;


    public MovieListFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mMovieOrder = sharedPreferences.getString(Constants.MOVIE_SORT_ORDER_KEY, Constants.MOVIE_SORT_ORDER_POPULAR);

        final View rootView = inflater.inflate(com.example.android.popularmoviesapppart2.R.layout.movie_list_fragment, container, false);

        ButterKnife.bind(MovieListFragment.this, rootView);


        GridLayoutManager layoutManager = setLayoutManager(container);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        loadMovieData();

        return rootView;
    }


    private void loadMovieData() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = null;
        if (connectivityManager != null) {
            info = connectivityManager.getActiveNetworkInfo();
        }

        if (info != null && info.isConnectedOrConnecting()) {
            URL url = NetworkUtils.buildMoviesUrl(mMovieOrder);
            new LoadMovieData(this, this).execute(url);
        } else {
            Toast.makeText(getActivity(), getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }


    }

    private void showProgressBar() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @SuppressWarnings("ConstantConditions")
    private GridLayoutManager setLayoutManager(ViewGroup container) {
        if (container != null) {
            container.removeAllViews();
        }

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return new GridLayoutManager(container.getContext(), 5);
        } else {
            return new GridLayoutManager(container.getContext(), 3);
        }
    }


    /**
     * Override method for LoadMovieData, before Async has run
     * Show Progress Bar
     */
    //
    @Override
    public void beforeTaskComplete() {
        showProgressBar();
    }

    /**
     * Override method for LoadMovieData, after AsyncTask has completed
     * Hide Progress Bar
     * Set Adapter to RV
     *
     * @param result - The ArrayList<Movie> filled with data after AsyncTask has run
     */
    @Override
    public void afterTaskComplete(final ArrayList<Movie> result) {
        hideProgressBar();

        MovieListAdapter movieListAdapter = new MovieListAdapter(result, new MovieListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Movie movie = result.get(position);
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra(Constants.INTENT_TITLE, movie.getTitle())
                        .putExtra(Constants.INTENT_IMAGE_URL, movie.getImageUrl())
                        .putExtra(Constants.INTENT_USER_RATING, movie.getUserRating())
                        .putExtra(Constants.INTENT_RELEASE_DATE, movie.getReleaseDate())
                        .putExtra(Constants.INTENT_PLOT, movie.getPlot())
                        .putExtra(Constants.INTENT_BACKDROP_URL, movie.getMovieBackdrop())
                    .putExtra(Constants.INTENT_MOVIE_ID, movie.getMovieId())
                ;
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(movieListAdapter);
    }
}



