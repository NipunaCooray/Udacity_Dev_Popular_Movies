package com.example.nipunac.popularmovies_v1.utilities;

import android.net.Uri;
import android.util.Log;


import com.example.nipunac.popularmovies_v1.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String MOVIE_API_URL = "https://api.themoviedb.org/3/discover/movie";

    final static String SORT_BY_PARAM = "sort_by";

    final static String API_KEY_PARAM = "api_key";

    private static String sort_by = "popularity.desc";

    private static final String api_key = "2fb86dfa5a07ff983f498596f3de995a";

    //Trying adding api_key from build gradle, didn't work
    //String api = BuildConfig.ApiKey;


    /**
     * Builds the URL used to talk to the movie api
     * @return The URL to use to query the movie api
     */

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(MOVIE_API_URL).buildUpon()
                .appendQueryParameter(SORT_BY_PARAM, sort_by)
                .appendQueryParameter(API_KEY_PARAM, api_key)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
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

    public static void changeSortParameter(int sortBy){
        switch(sortBy) {
            case 0 :
                sort_by = "popularity.desc";
                break;

            case 1 :
                sort_by = "vote_average.desc";
                break;


        }
    }




}
