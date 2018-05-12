package com.example.android.popularmoviesapppart2.Utils;

import android.os.AsyncTask;

import com.example.android.popularmoviesapppart2.Model.Trailers;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Zane on 11/05/2018.
 */

public class LoadTrailerData extends AsyncTask<URL, Void, ArrayList<Trailers>> {

    OnLoadFinished mOnLoadFinished;

    public LoadTrailerData(OnLoadFinished onLoadFinished){
        this.mOnLoadFinished = onLoadFinished;
    }


    @Override
    protected ArrayList<Trailers> doInBackground(URL... urls) {
        ArrayList<Trailers> trailersArrayList = new ArrayList<>();

        URL url = urls[0];

        try {
            trailersArrayList = JsonUtils.getTrailerList(NetworkUtils.getResponseFromHttpUrl(url));

        } catch (IOException e) {
            System.out.println("FAILED TO EXTRACT JSON FROM URL: " + e);
        } catch (JSONException e) {
            System.out.println("JSON EXCEPTION: " + e);
        }
        return trailersArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<Trailers> trailers) {
        super.onPostExecute(trailers);

        mOnLoadFinished.loadFinished(trailers);
    }

    /**
     * Inteerface for after Async has finished
     */
    public interface OnLoadFinished {
        void loadFinished(ArrayList<Trailers> trailersArrayList);
    }
}

