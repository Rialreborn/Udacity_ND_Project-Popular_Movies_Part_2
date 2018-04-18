package com.example.android.popularmoviesapp.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.android.popularmoviesapp.Model.Movie;
import com.example.android.popularmoviesapp.MovieDetailActivity;
import com.example.android.popularmoviesapp.MovieListAdapter;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Zane on 18/04/2018.
 */



public class LoadMovieData extends AsyncTask<URL, Void, ArrayList<Movie>>{

    private AsyncTaskCompleteListener<ArrayList<Movie>> listener;
    private AsyncTaskPreExecuteListener beforeListener;


    public LoadMovieData(AsyncTaskCompleteListener<ArrayList<Movie>> listener, AsyncTaskPreExecuteListener beforeListener){
        this.listener = listener;
        this.beforeListener = beforeListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        beforeListener.beforeAsyncExecute();
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

        listener.onTaskComplete(movies);
}
}
