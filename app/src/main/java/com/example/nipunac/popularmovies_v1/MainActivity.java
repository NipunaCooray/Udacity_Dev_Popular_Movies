package com.example.nipunac.popularmovies_v1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.nipunac.popularmovies_v1.model.Movie;
import com.example.nipunac.popularmovies_v1.utilities.NetworkUtils;
import com.example.nipunac.popularmovies_v1.utilities.TheMovieDBJSonUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this,  2);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter();

        mRecyclerView.setAdapter(mMovieAdapter);

        loadMovieData();

    }

    private void loadMovieData() {

        new FetchMovieTask().execute();
    }


    public class FetchMovieTask extends AsyncTask<Void, Void, ArrayList<Movie>>{

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
            if (movies != null) {

               mMovieAdapter.setMovieData(movies);


            } else {

            }
        }
    }
}
