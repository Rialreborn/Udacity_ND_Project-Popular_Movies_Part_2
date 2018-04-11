package com.example.zane.popularmoviesapp.Utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Zane on 03/04/2018.
 */

public class NetworkUtils {

    // Constants
    public static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    public static final String API = "api_key";

    public static final String LANGUAGE = "language";
    public static final String LAN_EN_US = "en-US";
    public static final String PAGE = "page";
    public static final String PAGE_NUMBER = "1";

    // Image related
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String IMAGE_SIZE_W185 = "w185";
    public static final String IMAGE_SIZE_W342 = "w342";



    public static URL buildMoviesUrl(String movieOrder) {
        Uri builtUri = Uri.parse(BASE_URL + movieOrder).buildUpon()
                .appendQueryParameter(API, Constants.API_KEY)
                .appendQueryParameter(LANGUAGE, LAN_EN_US)
                .appendQueryParameter(PAGE, PAGE_NUMBER)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static Uri buildImageURL(String imageUrl){
        Uri builtUri = Uri.parse(IMAGE_BASE_URL + IMAGE_SIZE_W185 + imageUrl);

        return builtUri;
    }

    public static Uri buildMovieBackdropURL(String imageUrl){
        Uri builtUri = Uri.parse(IMAGE_BASE_URL + IMAGE_SIZE_W342 + imageUrl);

        return builtUri;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
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

}
