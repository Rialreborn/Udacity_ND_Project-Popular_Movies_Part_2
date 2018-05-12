package com.example.android.popularmoviesapppart2.Utils;

import android.os.AsyncTask;

import com.example.android.popularmoviesapppart2.Model.Movie;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class LoadMovieData extends AsyncTask<URL, Void, ArrayList<Movie>>{

    public interface BeforeTaskCompleteInterface {
         void beforeTaskComplete();
    }

    public interface AfterTaskCompleteInterface {
        void afterTaskComplete(ArrayList<Movie> movies);
    }

    private BeforeTaskCompleteInterface iBeforeTaskComplete;
    private AfterTaskCompleteInterface iAfterTaskComplete;


    public LoadMovieData(BeforeTaskCompleteInterface beforeTaskComplete, AfterTaskCompleteInterface afterTaskComplete){
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
        ArrayList<Movie> movieArray = null;
        try {
            movieArray = JsonUtils.getMovieList(NetworkUtils.getJsonResponseFromHttpUrl(url));
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

        iAfterTaskComplete.afterTaskComplete(movies);
}
}
