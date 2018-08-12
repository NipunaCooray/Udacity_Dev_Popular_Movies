package com.example.nipunac.popularmovies_v1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nipunac.popularmovies_v1.model.Movie;
import com.example.nipunac.popularmovies_v1.utilities.NetworkUtils;
import com.example.nipunac.popularmovies_v1.utilities.TheMovieDBJSonUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieItemOnClickHandler {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    private ProgressBar mLoadingIndicator;

    private TextView mErrorMessageDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        /* This TextView is used to display errors and will be hidden if there are no errors */
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this,  2);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadMovieData();

    }

    private void loadMovieData() {

        if(isOnline()){
            showMovieDataView();
            new FetchMovieTask().execute();
        }else{
            showErrorMessage();
        }

    }

    @Override
    public void onClick(Movie selectedMovie) {


        Log.d("Movie Title", selectedMovie.getOriginalTitle());
        Log.d("Movie URL",selectedMovie.getPosterURL());
        Log.d("Average Votes",selectedMovie.getUserRating());
        Log.d("ID",selectedMovie.getId());
        Log.d("PLOT SYNOPSIS",selectedMovie.getPlotSynopsis());
        Log.d("RELEASE DATE",selectedMovie.getReleaseDate());


        //Need to check whether all the movie data is available


        HashMap<String, String> movieItem = new HashMap<String, String>();
        movieItem.put("TITLE",selectedMovie.getOriginalTitle());
        movieItem.put("URL",selectedMovie.getPosterURL());
        movieItem.put("VOTES",selectedMovie.getUserRating());
        movieItem.put("PLOT_SYNOPSIS",selectedMovie.getPlotSynopsis());
        movieItem.put("RELEASE_DATE",selectedMovie.getReleaseDate());

        Context context = this;
        Class destinationClass = MovieDetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("MOVIE_ITEM", movieItem);
        startActivity(intentToStartDetailActivity);
    }


    public class FetchMovieTask extends AsyncTask<Void, Void, ArrayList<Movie>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {


            URL movieRequestUrl = NetworkUtils.buildUrl();

            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                ArrayList movieList = TheMovieDBJSonUtils.getMovieListFromJson(jsonMovieResponse);

                return movieList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {

            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (movies != null) {

                showMovieDataView();
                mMovieAdapter.setMovieData(movies);


            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main_menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sort_by_popularity) {
            NetworkUtils.changeSortParameter(0);
            loadMovieData();
            return true;
        } else if (id == R.id.action_sort_by_rating) {
            NetworkUtils.changeSortParameter(1);
            loadMovieData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void showMovieDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private boolean isOnline() {
        try
        {
            ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        }
        catch (Exception e)
        {
            return false;
        }
    }


}
