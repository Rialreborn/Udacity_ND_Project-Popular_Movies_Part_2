package com.example.android.popularmoviesapppart2.Utils;

import com.example.android.popularmoviesapppart2.Model.Movie;
import com.example.android.popularmoviesapppart2.Model.Reviews;
import com.example.android.popularmoviesapppart2.Model.Trailers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class JsonUtils {


    public static ArrayList<Movie> getMovieList(String json) throws JSONException {
        ArrayList<Movie> movieArray = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray(Constants.JSON_RESULTS);
        SimpleDateFormat sdf;

        for (int i = 0; i < jsonArray.length(); i++) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            JSONObject movieDetails = (JSONObject) jsonArray.get(i);
            String movieTitle = movieDetails.optString(Constants.TITLE, Constants.FAIL_TO_RETRIEVE);
            String imageURL = movieDetails.optString(Constants.POSTER_URL, Constants.FAIL_TO_RETRIEVE);
            String plot = movieDetails.optString(Constants.PLOT, Constants.FAIL_TO_RETRIEVE);
            double userRating = movieDetails.optDouble(Constants.USER_RATING, 0.0);
            String movieBackdrop = movieDetails.optString(Constants.BACKDROP_POSTER, Constants.FAIL_TO_RETRIEVE);
            int movieId = movieDetails.optInt(Constants.MOVIE_ID, 0);

            String releaseDate = movieDetails.optString(Constants.RELEASE_DATE, Constants.FAIL_TO_RETRIEVE);
            try {
                Date newDate = sdf.parse(releaseDate);
                sdf = new SimpleDateFormat("dd-MMM-yyyy");
                releaseDate = sdf.format(newDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            movieArray.add(new Movie(movieTitle, imageURL, plot, userRating, releaseDate, movieBackdrop, movieId));
        }

        return movieArray;
    }


    public static ArrayList<Trailers> getTrailerList(String json) throws JSONException {
        ArrayList<Trailers> trailerArray = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray(Constants.JSON_RESULTS);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject trailerDetails = jsonArray.getJSONObject(i);

            if (
                    trailerDetails.getString(Constants.TRAILER_TYPE).equals(Constants.TRAILER_TRAILER)
                            ||
                    trailerDetails.getString(Constants.TRAILER_TYPE).equals(Constants.TRAILER_TEASER))
            {
                String trailerName = trailerDetails.optString(Constants.TRAILER_NAME, Constants.FAIL_TO_RETRIEVE);
                String trailerKey = trailerDetails.optString(Constants.TRAILER_KEY, Constants.FAIL_TO_RETRIEVE);
                trailerArray.add(new Trailers(trailerKey, trailerName));
            }
        }

        return trailerArray;
    }

    public static ArrayList<Reviews> getReviewList(String json) throws JSONException {
        ArrayList<Reviews> arrayList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray(Constants.JSON_RESULTS);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject reviewObject = jsonArray.getJSONObject(i);

            String author = reviewObject.getString(Constants.REVIEWS_AUTHOR);
            String content = reviewObject.getString(Constants.REVIEWS_CONTENT);

            arrayList.add(new Reviews(author, content));
        }

        return arrayList;
    }
}
