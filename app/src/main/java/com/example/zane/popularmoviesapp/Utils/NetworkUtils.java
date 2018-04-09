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



    public static URL buildMoviesUrl(String movieOrder) {
        Uri builtUri = Uri.parse(Constants.BASE_URL + movieOrder).buildUpon()
                .appendQueryParameter(Constants.API, Constants.API_KEY)
                .appendQueryParameter(Constants.LANGUAGE, Constants.LAN_EN_US)
                .appendQueryParameter(Constants.PAGE, Constants.PAGE_NUMBER)
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
        Uri builtUri = Uri.parse(Constants.IMAGE_BASE_URL + Constants.IMAGE_SIZE + imageUrl);

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
