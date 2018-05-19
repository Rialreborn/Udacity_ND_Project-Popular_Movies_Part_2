package com.example.android.popularmoviesapppart2.MovieDetails;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.example.android.popularmoviesapppart2.Model.Reviews;
import com.example.android.popularmoviesapppart2.Utils.JsonUtils;
import com.example.android.popularmoviesapppart2.Utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

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
