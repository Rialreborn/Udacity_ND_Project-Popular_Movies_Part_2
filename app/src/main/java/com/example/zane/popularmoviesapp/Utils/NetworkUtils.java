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
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

    private static final String API = "api_key";
    private static final String API_KEY = null;
    private static final String LANGUAGE = "language";
    private static final String LAN_EN_US = "en-US";
    private static final String PAGE = "page";
    private static final String PAGE_NUMBER = "1";

    // Image related
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE = "w185";


    public static URL buildMoviesUrl(String movieOrder) {
        Uri builtUri = Uri.parse(BASE_URL + movieOrder).buildUpon()
                .appendQueryParameter(API, API_KEY)
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
        Uri builtUri = Uri.parse(IMAGE_BASE_URL + IMAGE_SIZE + imageUrl);

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

    public static String getApiKey() {
        return API_KEY;
    }
}
