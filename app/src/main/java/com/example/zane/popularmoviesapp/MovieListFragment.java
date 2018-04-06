package com.example.zane.popularmoviesapp;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    private int columnCount = 2;
    ArrayList<Movie> moviesList = null;
    RecyclerView mRecyclerView = null;
    TextView mApiKeyNeededTv = null;

    public MovieListFragment() {}


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView = inflater.inflate(R.layout.movie_list_fragment, container, false);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mApiKeyNeededTv = rootView.findViewById(R.id.api_key_needed_tv);

        if (NetworkUtils.getApiKey() == null) {
            mApiKeyNeededTv.setVisibility(View.VISIBLE);
            mApiKeyNeededTv.setText(getString(R.string.api_key_needed));
            mRecyclerView.setVisibility(View.GONE);
        } else {

            GridLayoutManager layoutManager = new GridLayoutManager(container.getContext(), columnCount);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);

            URL url = NetworkUtils.buildPopularMoviesUrl();
            new LoadMovieData().execute(url);
        }

        return rootView;
    }

    public class LoadMovieData extends AsyncTask<URL, Void, ArrayList<Movie>> {

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

            moviesList = movies;
            MovieListAdapter movieListAdapter = new MovieListAdapter(moviesList);
            mRecyclerView.setAdapter(movieListAdapter);
        }
    }
}
