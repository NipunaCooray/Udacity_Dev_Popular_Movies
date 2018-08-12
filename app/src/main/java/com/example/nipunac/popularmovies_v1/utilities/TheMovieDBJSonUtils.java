package com.example.nipunac.popularmovies_v1.utilities;

import android.content.Context;
import android.util.Log;

import com.example.nipunac.popularmovies_v1.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class TheMovieDBJSonUtils {

    public static ArrayList<Movie> getMovieListFromJson( String movieListJsonStr)
            throws JSONException {


        ArrayList mMovieList = new ArrayList();

        /* All movies are in the results array */
        final String MOVIE_LIST = "results";

        final String POSTER_URL = "poster_path";

        final String TITLE = "title";

        final String VOTE_AVERAGE = "vote_average";

        final String ID ="id";

        final String PLOT_SYNOPSIS ="overview";

        final String RELEASE_DATE ="release_date";

        JSONObject movieListJson = new JSONObject(movieListJsonStr);

        JSONArray movieArray = movieListJson.getJSONArray(MOVIE_LIST);


        for (int i = 0; i < movieArray.length(); i++) {

            String posterURL;
            String title;
            String votesAverage;
            String id;
            String plotSynopsis;
            String releaseDate;



            /* Get the JSON object representing the day */
            JSONObject movieJson = movieArray.getJSONObject(i);
            posterURL = movieJson.getString(POSTER_URL);
            title = movieJson.getString(TITLE);
            votesAverage = movieJson.getString(VOTE_AVERAGE);
            id = movieJson.getString(ID);
            plotSynopsis = movieJson.getString(PLOT_SYNOPSIS);
            releaseDate = movieJson.getString(RELEASE_DATE);



            Movie movie = new Movie();
            movie.setOriginalTitle(title);
            movie.setPosterURL(posterURL);
            movie.setUserRating(votesAverage);
            movie.setId(id);
            movie.setPlotSynopsis(plotSynopsis);
            movie.setReleaseDate(releaseDate);

            mMovieList.add(movie);
        }


        return mMovieList;
    }

}
