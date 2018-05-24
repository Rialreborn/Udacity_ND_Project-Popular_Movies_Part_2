package com.zanerast.android.popularmoviesapppart2.MovieDetails;

import android.os.AsyncTask;

import com.zanerast.android.popularmoviesapppart2.Model.Reviews;
import com.zanerast.android.popularmoviesapppart2.Utils.JsonUtils;
import com.zanerast.android.popularmoviesapppart2.Utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Zane on 12/05/2018.
 */

public class LoadReviews extends AsyncTask<URL, Void, ArrayList<Reviews>> {

    OnReviewsLoadFinished mOnReviewsLoadFinished;

    public LoadReviews(OnReviewsLoadFinished onReviewsLoadFinished) {
        this.mOnReviewsLoadFinished = onReviewsLoadFinished;
    }

    @Override
    protected ArrayList<Reviews> doInBackground(URL... urls) {
        URL url = urls[0];
        ArrayList<Reviews> reviewsArrayList = new ArrayList<>();

        try {
            String json = NetworkUtils.getJsonResponseFromHttpUrl(url);
            reviewsArrayList = JsonUtils.getReviewList(json);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviewsArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<Reviews> reviews) {
        super.onPostExecute(reviews);

        mOnReviewsLoadFinished.reviewsFinished(reviews);
    }

    public interface OnReviewsLoadFinished {
        void reviewsFinished(ArrayList<Reviews> reviewsArrayList);
    }

}
