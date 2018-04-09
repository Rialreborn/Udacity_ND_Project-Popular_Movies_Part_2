package com.example.zane.popularmoviesapp.Utils;

import com.example.zane.popularmoviesapp.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Zane on 03/04/2018.
 */

public class JsonUtils {


    public JsonUtils() {
    }

    public static ArrayList<Movie> getPopularMovieList(String json) throws JSONException {
        ArrayList<Movie> movieArray = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray(Constants.JSON_RESULTS);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject movieDetails = (JSONObject) jsonArray.get(i);
            String movieTitle = movieDetails.optString(Constants.TITLE, Constants.FAIL_TO_RETRIEVE);
            String imageURL = movieDetails.optString(Constants.POSTER_URL, Constants.FAIL_TO_RETRIEVE);
            String plot = movieDetails.optString(Constants.PLOT, Constants.FAIL_TO_RETRIEVE);
            String releaseDate = movieDetails.optString(Constants.RELEASE_DATE, Constants.FAIL_TO_RETRIEVE);
            double userRating = movieDetails.optDouble(Constants.USER_RATING, 0.0);
            movieArray.add(new Movie(movieTitle, imageURL, plot, userRating, releaseDate));
        }

        return movieArray;
    }
}
