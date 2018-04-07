package com.example.zane.popularmoviesapp.Utils;

import com.example.zane.popularmoviesapp.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Zane on 03/04/2018.
 */

public class JsonUtils {
    private static final String TITLE = "original_title";
    private static final String POSTER_URL = "poster_path";
    private static final String PLOT = "overview";
    private static final String USER_RATING = "vote_average";
    private static final String RELEASE_DATE = "release_date";
    private static final String POPULAR = "popular";
    private static final String JSON_RESULTS = "results";
    private static final String FAIL_TO_RETRIEVE = "Failed to Retrieve";

    public JsonUtils() {
    }

    public static ArrayList<Movie> getPopularMovieList(String json) throws JSONException {
        ArrayList<Movie> movieArray = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray(JSON_RESULTS);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject movieDetails = (JSONObject) jsonArray.get(i);
            String movieTitle = movieDetails.optString(TITLE, FAIL_TO_RETRIEVE);
            String imageURL = movieDetails.optString(POSTER_URL, FAIL_TO_RETRIEVE);
            String plot = movieDetails.optString(PLOT, FAIL_TO_RETRIEVE);
            String releaseDate = movieDetails.optString(RELEASE_DATE, FAIL_TO_RETRIEVE);
            double userRating = movieDetails.optDouble(USER_RATING, 0.0);
            movieArray.add(new Movie(movieTitle, imageURL, plot, userRating, releaseDate));
        }

        return movieArray;
    }
}
