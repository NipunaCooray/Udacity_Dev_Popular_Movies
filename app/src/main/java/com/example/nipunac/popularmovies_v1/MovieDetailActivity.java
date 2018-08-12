package com.example.nipunac.popularmovies_v1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class MovieDetailActivity extends AppCompatActivity {


    private String mMovieTitle;
    private TextView mMovieTitleTextView;

    private String mPosterURL;
    private ImageView mPoster;

    private String mReleaseDate;
    private TextView mReleaseDateTextView;

    private String mVoteAverage;
    private TextView mVoteAverageTextView;

    private String mPlotSynopsis;
    private TextView mPlotSynopsisTextView;

    private static final String baseMovieImageUrl="http://image.tmdb.org/t/p/w500/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mMovieTitleTextView =  findViewById(R.id.tv_movie_title);
        mReleaseDateTextView =  findViewById(R.id.tv_release_date);
        mVoteAverageTextView =  findViewById(R.id.tv_user_rating);
        mPlotSynopsisTextView = findViewById(R.id.tv_plot_data);
        mPoster = findViewById(R.id.iv_poster);


        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("MOVIE_ITEM")) {
                HashMap<String, String> movieItem = (HashMap<String, String>)intentThatStartedThisActivity.getSerializableExtra("MOVIE_ITEM");
                mMovieTitle = movieItem.get("TITLE");
                mMovieTitleTextView.setText(mMovieTitle);

                mPosterURL = movieItem.get("URL");
                Picasso.get().load(baseMovieImageUrl+mPosterURL).into(mPoster);

                mReleaseDate = movieItem.get("RELEASE_DATE");
                mReleaseDateTextView.setText(mReleaseDate);

                mVoteAverage = movieItem.get("VOTES");
                mVoteAverageTextView.setText(mVoteAverage);

                mPlotSynopsis = movieItem.get("PLOT_SYNOPSIS");
                mPlotSynopsisTextView.setText(mPlotSynopsis);


            }
        }
    }
}
