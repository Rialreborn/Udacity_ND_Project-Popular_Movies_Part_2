package com.example.zane.popularmoviesapp;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.zane.popularmoviesapp.Model.Movie;
import com.example.zane.popularmoviesapp.Utils.Constants;
import com.example.zane.popularmoviesapp.Utils.JsonUtils;
import com.example.zane.popularmoviesapp.Utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Zane on 03/04/2018.
 */

public class MovieListFragment extends Fragment {

    SharedPreferences sharedPreferences;

    private int columnCount = 3;
    ArrayList<Movie> moviesList;
    RecyclerView mRecyclerView;
    TextView mApiKeyNeededTv;
    ProgressBar progressBar;
    String mMovieOrder;

    public MovieListFragment() {}


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            mMovieOrder = sharedPreferences.getString(Constants.MOVIE_SORT_ORDER_KEY, Constants.MOVIE_SORT_ORDER_POPULAR);


        final View rootView = inflater.inflate(R.layout.movie_list_fragment, container, false);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mApiKeyNeededTv = rootView.findViewById(R.id.api_key_needed_tv);
        progressBar = rootView.findViewById(R.id.progress_bar);


        if (Constants.API_KEY == null) {
            noApiKeyFound();
        } else {
            GridLayoutManager layoutManager = new GridLayoutManager(container.getContext(), columnCount);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);
            loadMovieData();
        }

        return rootView;
    }

    private void noApiKeyFound() {
        mApiKeyNeededTv.setVisibility(View.VISIBLE);
        mApiKeyNeededTv.setText(getString(R.string.api_key_needed));
        mRecyclerView.setVisibility(View.GONE);
    }

    private void loadMovieData() {
        URL url = NetworkUtils.buildMoviesUrl(mMovieOrder);
        new LoadMovieDataAsync().execute(url);
    }

    private void showProgressBar() {
        mRecyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
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
                public void onItemClick(View itemView, int position) {
                    Toast.makeText(getContext(), "CLICKED ITEM: " + position, Toast.LENGTH_SHORT).show();
                }
            });

            mRecyclerView.setAdapter(movieListAdapter);
        }
    }
}
