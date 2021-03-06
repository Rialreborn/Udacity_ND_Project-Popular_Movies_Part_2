package com.zanerast.android.popularmoviesapppart2.Utils;


import com.zanerast.android.popularmoviesapppart2.BuildConfig;

public class Constants {

    // ENTER API KEY HERE
    public static final String API_KEY = BuildConfig.API_KEY;

    // Sort Order
    public static final String MOVIE_SORT_ORDER_KEY = "movie_sort_order";
    public static final String MOVIE_SORT_ORDER_POPULAR = "popular";
    public static final String MOVIE_SORT_ORDER_RATING = "top_rated";
    public static final String MOVIE_SORT_ORDER_FAVOURITES = "favourites";

    // JSON
    public static final String TITLE = "original_title";
    public static final String POSTER_URL = "poster_path";
    public static final String BACKDROP_POSTER = "backdrop_path";
    public static final String PLOT = "overview";
    public static final String USER_RATING = "vote_average";
    public static final String RELEASE_DATE = "release_date";
    public static final String JSON_RESULTS = "results";
    public static final String MOVIE_ID = "id";
    public static final String FAIL_TO_RETRIEVE = "Failed to Retrieve";
    public static final String TRAILER_KEY = "key";
    public static final String TRAILER_NAME = "name";

    // Detail Intent
    public static final String INTENT_MOVIE_OBJECT = "object";

    // Trailer
    public static final String TRAILER_TRAILER = "Trailer";
    public static final String TRAILER_TEASER = "Teaser";
    public static final String TRAILER_TYPE = "type";

    // Review
    public static final String REVIEWS_AUTHOR = "author";
    public static final String REVIEWS_CONTENT = "content";
}
