package com.example.android.popularmoviesapppart2.Utils;

import android.os.AsyncTask;

import com.example.android.popularmoviesapppart2.Model.Movie;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class LoadMovieData extends AsyncTask<URL, Void, ArrayList<Movie>>{

    ArrayList<Movie> mMovieArrayList;

    public interface BeforeTaskCompleteInterface {
         void beforeTaskComplete();
    }

    public interface AfterTaskCompleteInterface {
        void afterTaskComplete(ArrayList<Movie> movies);
    }

    private BeforeTaskCompleteInterface iBeforeTaskComplete;
    private AfterTaskCompleteInterface iAfterTaskComplete;


    public LoadMovieData(ArrayList<Movie> movieArrayList, BeforeTaskCompleteInterface beforeTaskComplete, AfterTaskCompleteInterface afterTaskComplete){
        this.mMovieArrayList = movieArrayList;
        this.iBeforeTaskComplete = beforeTaskComplete;
        this.iAfterTaskComplete = afterTaskComplete;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        iBeforeTaskComplete.beforeTaskComplete();
    }

    @Override
    protected ArrayList<Movie> doInBackground(URL... urls) {
        URL url = urls[0];
        try {
            mMovieArrayList = JsonUtils.getMovieList(NetworkUtils.getJsonResponseFromHttpUrl(url), mMovieArrayList);
        } catch (IOException e) {
            System.out.println("FAILED TO EXTRACT JSON FROM URL: " + e);
        } catch (JSONException e) {
            System.out.println("JSON EXCEPTION: " + e);
        }
        return mMovieArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);

        iAfterTaskComplete.afterTaskComplete(movies);
}
}
