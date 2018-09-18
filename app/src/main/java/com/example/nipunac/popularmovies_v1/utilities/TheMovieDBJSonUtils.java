package com.example.nipunac.popularmovies_v1.utilities;

import android.content.Context;
import android.util.Log;

import com.example.nipunac.popularmovies_v1.model.Movie;
import com.example.nipunac.popularmovies_v1.model.Reviews;
import com.example.nipunac.popularmovies_v1.model.Trailer;

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

    public static ArrayList<Trailer> getTrailerListFromJson(String trailerListJsonStr)
            throws JSONException {


        ArrayList mTrailerList = new ArrayList();

        /* All movies are in the results array */
        final String TRAILER_LIST = "results";


        final String ID ="id";

        final String KEY ="key";

        final String NAME ="name";

        final String SITE ="site";

        final String TYPE ="type";


        JSONObject trailerListJson = new JSONObject(trailerListJsonStr);

        JSONArray trailerArray = trailerListJson.getJSONArray(TRAILER_LIST);


        for (int i = 0; i < trailerArray.length(); i++) {

            String id;
            String key;
            String name;
            String site;
            String type;

            /* Get the JSON object representing the day */
            JSONObject trailerJson = trailerArray.getJSONObject(i);
            id = trailerJson.getString(ID);
            key = trailerJson.getString(KEY);
            name = trailerJson.getString(NAME);
            site = trailerJson.getString(SITE);
            type = trailerJson.getString(TYPE);



            Trailer trailer = new Trailer();
            trailer.setId(id);
            trailer.setUrl(key);
            trailer.setTrailerDescription(name);
            trailer.setSite(site);
            trailer.setType(type);


            mTrailerList.add(trailer);
        }


        return mTrailerList;
    }

    public static ArrayList<Reviews> getReviewListFromJson(String reviewListJsonStr)
            throws JSONException {


        ArrayList mReviewList = new ArrayList();

        /* All movies are in the results array */
        final String REVIEW_LIST = "results";


        final String AUTHOR ="author";

        final String CONTENT ="content";



        JSONObject reviewListJson = new JSONObject(reviewListJsonStr);

        JSONArray reviewArray = reviewListJson.getJSONArray(REVIEW_LIST);


        for (int i = 0; i < reviewArray.length(); i++) {

            String author;
            String content;


            /* Get the JSON object representing the day */
            JSONObject reviewJson = reviewArray.getJSONObject(i);
            author = reviewJson.getString(AUTHOR);
            content = reviewJson.getString(CONTENT);

            Reviews review = new Reviews();
            review.setAuthor(author);
            review.setContent(content);



            mReviewList.add(review);
        }


        return mReviewList;
    }

}
