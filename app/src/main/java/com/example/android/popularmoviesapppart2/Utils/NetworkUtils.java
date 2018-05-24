package com.example.android.popularmoviesapppart2.Utils;

import android.net.Uri;

import com.example.android.popularmoviesapppart2.Model.Movie;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class NetworkUtils {

    // Constants
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String VIDEOS = "/videos";
    private static final String API = "api_key";

    private static final String LANGUAGE = "language";
    private static final String LAN_EN_US = "en-US";
    private static final String PAGE = "page";
    private static final String PAGE_NUMBER = "1";
    private static final String REVIEWS = "/reviews";

    // Youtube
    private static final String BASE_YOUTUBE_URL = "https://youtube.com/watch";
    private static final String BASE_YOUTUBE_IMAGE_URL = "https://img.youtube.com/vi/";
    private static final String BASE_YOUTUBE_URL_APP = "vnd.youtube:";
    private static final String IMAGE_NUMBER = "/0.jpg";
    private static final String VIDEO = "v";


    // Image related
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE_W185 = "w185";
    private static final String IMAGE_SIZE_W342 = "w342";

    public static URL buildTrailersUrl(int id) {
        Uri builtUri = Uri.parse(BASE_URL + id + VIDEOS).buildUpon()
                .appendQueryParameter(API, Constants.API_KEY)
                .appendQueryParameter(LANGUAGE, LAN_EN_US)
                .build();

        return convertUriToUrl(builtUri);
    }

    public static URL buildMoviesUrl(String movieOrder, ArrayList<Movie> movieArrayList) {
        int pageNumber;
        if (movieArrayList == null || movieArrayList.isEmpty()) {
            pageNumber = 1;
        } else {
         pageNumber = (movieArrayList.size() / 20) + 1;
        }
        String page = String.valueOf(pageNumber);
        Uri builtUri = Uri.parse(BASE_URL + movieOrder).buildUpon()
                .appendQueryParameter(API, Constants.API_KEY)
                .appendQueryParameter(LANGUAGE, LAN_EN_US)
                .appendQueryParameter(PAGE, page)
                .build();

        return convertUriToUrl(builtUri);
    }

    public static Uri buildYoutubeUri(String key) {
        return Uri.parse(BASE_YOUTUBE_URL).buildUpon()
                .appendQueryParameter(VIDEO, key)
                .build();
    }

    public static Uri buildYoutubeAppUri(String key) {
        return Uri.parse(BASE_YOUTUBE_URL_APP + key);
    }

    public static Uri buildYoutubeThumbnailUri(String key) {

        return Uri.parse(BASE_YOUTUBE_IMAGE_URL + key + IMAGE_NUMBER);
    }

    public static URL buildReviewsUrl(int id) {
        Uri builtUri = Uri.parse(BASE_URL + id + REVIEWS).buildUpon()
                .appendQueryParameter(API, Constants.API_KEY)
                .appendQueryParameter(LANGUAGE, LAN_EN_US)
                .build();

        return convertUriToUrl(builtUri);
    }



    public static Uri buildImageURL(String imageUrl){

        return Uri.parse(IMAGE_BASE_URL + IMAGE_SIZE_W185 + imageUrl);
    }

    public static Uri buildMovieBackdropUri(String imageUrl){

        return Uri.parse(IMAGE_BASE_URL + IMAGE_SIZE_W342 + imageUrl);
    }

    public static String getJsonResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    private static URL convertUriToUrl(Uri uri) {
        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
}
