package com.example.android.popularmoviesapp.Utils;


import com.example.android.popularmoviesapp.BuildConfig;

public class Constants {

    // ENTER API KEY HERE
    public static final String API_KEY = BuildConfig.API_KEY;

    // Sort Order
    public static final String MOVIE_SORT_ORDER_KEY = "movie_sort_order";
    public static final String MOVIE_SORT_ORDER_POPULAR = "popular";
    public static final String MOVIE_SORT_ORDER_RATING = "top_rated";

    // JSON
    public static final String TITLE = "original_title";
    public static final String POSTER_URL = "poster_path";
    public static final String BACKDROP_POSTER = "backdrop_path";
    public static final String PLOT = "overview";
    public static final String USER_RATING = "vote_average";
    public static final String RELEASE_DATE = "release_date";
    public static final String JSON_RESULTS = "results";
    public static final String FAIL_TO_RETRIEVE = "Failed to Retrieve";

    // Detail Intent
    public static final String INTENT_TITLE = "title";
    public static final String INTENT_IMAGE_URL = "image_url";
    public static final String INTENT_USER_RATING = "user_rating";
    public static final String INTENT_RELEASE_DATE = "release_date";
    public static final String INTENT_PLOT = "plot";
    public static final String INTENT_BACKDROP_URL = "backdrop_url";
}
