package com.example.zane.popularmoviesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.zane.popularmoviesapp.Model.Movie;
import com.example.zane.popularmoviesapp.Utils.Constants;
import com.example.zane.popularmoviesapp.Utils.JsonUtils;
import com.example.zane.popularmoviesapp.Utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieListFragment extends Fragment {

    private String mMovieOrder;

    private ArrayList<Movie> moviesList;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;


    public MovieListFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mMovieOrder = sharedPreferences.getString(Constants.MOVIE_SORT_ORDER_KEY, Constants.MOVIE_SORT_ORDER_POPULAR);

        final View rootView = inflater.inflate(R.layout.movie_list_fragment, container, false);

        ButterKnife.bind(MovieListFragment.this, rootView);


        GridLayoutManager layoutManager = setLayoutManager(container);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        loadMovieData();

        return rootView;
    }


    private void loadMovieData() {
        URL url = NetworkUtils.buildMoviesUrl(mMovieOrder);
        new LoadMovieDataAsync().execute(url);
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


    public class LoadMovieDataAsync extends AsyncTask<URL, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressBar();
        }

        @Override
        protected ArrayList<Movie> doInBackground(URL... urls) {
            URL url = urls[0];
            ArrayList<Movie> movieArray = null;
            try {
                movieArray = JsonUtils.getPopularMovieList(NetworkUtils.getResponseFromHttpUrl(url));
            } catch (IOException e) {
                System.out.println("FAILED TO EXTRACT JSON FROM URL: " + e);
            } catch (JSONException e) {
                System.out.println("JSON EXCEPTION: " + e);
            }
            return movieArray;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);

            hideProgressBar();

            moviesList = movies;


            MovieListAdapter movieListAdapter = new MovieListAdapter(moviesList, new MovieListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Movie movie = moviesList.get(position);
                    Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                    intent.putExtra(Constants.INTENT_TITLE, movie.getTitle())
                            .putExtra(Constants.INTENT_IMAGE_URL, movie.getImageUrl())
                            .putExtra(Constants.INTENT_USER_RATING, movie.getUserRating())
                            .putExtra(Constants.INTENT_RELEASE_DATE, movie.getReleaseDate())
                            .putExtra(Constants.INTENT_PLOT, movie.getPlot())
                            .putExtra(Constants.INTENT_BACKDROP_URL, movie.getMovieBackdrop());
                    startActivity(intent);
                }
            });

            recyclerView.setAdapter(movieListAdapter);
        }
    }
}
