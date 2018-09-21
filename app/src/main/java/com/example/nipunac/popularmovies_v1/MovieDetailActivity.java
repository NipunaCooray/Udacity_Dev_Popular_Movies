package com.example.nipunac.popularmovies_v1;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nipunac.popularmovies_v1.database.FavouriteDatabase;
import com.example.nipunac.popularmovies_v1.model.Movie;
import com.example.nipunac.popularmovies_v1.model.Reviews;
import com.example.nipunac.popularmovies_v1.model.Trailer;
import com.example.nipunac.popularmovies_v1.utilities.NetworkUtils;
import com.example.nipunac.popularmovies_v1.utilities.TheMovieDBJSonUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
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

    private String mMovieID;

    private static final String baseMovieImageUrl="http://image.tmdb.org/t/p/w500/";

    private boolean isFavourite;

    ImageView mFavouriteButton ;

    private FavouriteDatabase mDb;

    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);


        mDb = FavouriteDatabase.getInstance(getApplicationContext());

        mMovieTitleTextView =  findViewById(R.id.tv_movie_title);
        mReleaseDateTextView =  findViewById(R.id.tv_release_date);
        mVoteAverageTextView =  findViewById(R.id.tv_user_rating);
        mPlotSynopsisTextView = findViewById(R.id.tv_plot_data);
        mPoster = findViewById(R.id.iv_poster);
        mFavouriteButton = findViewById(R.id.buttonFavourite);


        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("MOVIE_ITEM")) {
                HashMap<String, String> movieItem = (HashMap<String, String>)intentThatStartedThisActivity.getSerializableExtra("MOVIE_ITEM");

                mMovieID = movieItem.get("ID");

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

        checkFavourite();


        new MovieDetailActivity.FetchTrailerTask().execute();
        new MovieDetailActivity.FetchReviewsTask().execute();

    }




    public class FetchTrailerTask extends AsyncTask<Void, Void, ArrayList<Trailer>> {

        URL trailerRequestURL = NetworkUtils.buildTrailerUrl(mMovieID);

        @Override
        protected ArrayList<Trailer> doInBackground(Void... voids) {

            try {
                String jsonTrailerResponse = NetworkUtils
                        .getResponseFromHttpUrl(trailerRequestURL);

                ArrayList trailerList = TheMovieDBJSonUtils.getTrailerListFromJson(jsonTrailerResponse);

                return trailerList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(ArrayList<Trailer> trailers) {
            super.onPostExecute(trailers);

            if (trailers != null) {

                Log.d(TAG, "Trailers not null");

                LinearLayout linearLayoutParent = (LinearLayout)findViewById(R.id.trailerList);

                final Context context = MovieDetailActivity.this;

                for(int i=0;i<trailers.size();i++){

                    final Trailer trailer = trailers.get(i);

                    String name = trailer.getTrailerDescription();

                    LinearLayout layoutChild = new LinearLayout(context);

                    layoutChild.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


                    LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,4.0f);
                    //textViewParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

                    LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
                    //imageViewParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);


                    TextView trailerName = new TextView(context);
                    trailerName.setText(name);
                    trailerName.setLayoutParams(textViewParams);
                    trailerName.setPadding(converttDPtoPX(10),0,0,converttDPtoPX(10));


                    RelativeLayout imageViewHolder = new RelativeLayout(context);
                    RelativeLayout.LayoutParams imageViewHolderParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                    imageViewHolderParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

                    ImageView trailerImage = new ImageView(context);
                    trailerImage.setLayoutParams(imageViewParams);
                    trailerImage.setImageResource(R.drawable.ic_play_button);
                    trailerImage.setPadding(0,0,converttDPtoPX(20), converttDPtoPX(10));
                    trailerImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getUrl()));

                            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://www.youtube.com/watch?v=" + trailer.getUrl()));

                            try {
                                context.startActivity(appIntent);
                            } catch (ActivityNotFoundException ex) {
                                context.startActivity(webIntent);
                            }
                        }
                    });

                    imageViewHolder.addView(trailerImage);


                    layoutChild.addView(trailerName);
                    layoutChild.addView(imageViewHolder);

                    linearLayoutParent.addView(layoutChild);
                }

            } else {
                //Show some error message
            }
        }
    }

    public class FetchReviewsTask extends AsyncTask<Void, Void, ArrayList<Reviews>> {

        URL reviewRequestURL = NetworkUtils.buildReviewUrl(mMovieID);

        @Override
        protected ArrayList<Reviews> doInBackground(Void... voids) {
            try {
                String jsonReviewResponse = NetworkUtils
                        .getResponseFromHttpUrl(reviewRequestURL);

                ArrayList reviewList = TheMovieDBJSonUtils.getReviewListFromJson(jsonReviewResponse);

                return reviewList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Reviews> reviews) {
            super.onPostExecute(reviews);

            if (reviews != null) {
                Log.d("Review list",""+reviews.size());

                LinearLayout linearLayoutReviewList = (LinearLayout)findViewById(R.id.reviewList);
                final Context context = MovieDetailActivity.this;

                for(int i=0;i<reviews.size();i++){
                    Reviews review = reviews.get(i);

                    String author = review.getAuthor();
                    String content = review.getContent();

                    LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    LinearLayout.LayoutParams lineViewParams = new LinearLayout.LayoutParams(converttDPtoPX(60), converttDPtoPX(1));
                    lineViewParams.setMargins(converttDPtoPX(10), 0, 0, converttDPtoPX(10));

                    TextView tv_author = new TextView(context);
                    tv_author.setText(author);
                    tv_author.setLayoutParams(textViewParams);
                    tv_author.setPadding(converttDPtoPX(10),0,0,converttDPtoPX(10));

                    TextView tv_content = new TextView(context);
                    tv_content.setText(content);
                    tv_content.setLayoutParams(textViewParams);
                    tv_content.setPadding(converttDPtoPX(10),0,0,converttDPtoPX(10));

                    View view_shortLine = new View(context);
                    view_shortLine.setLayoutParams(lineViewParams);
                    view_shortLine.setBackgroundColor(fetchColor());


                    linearLayoutReviewList.addView(tv_author);
                    linearLayoutReviewList.addView(tv_content);
                    linearLayoutReviewList.addView(view_shortLine);


                }

            }

        }
    }

    private int fetchColor() {
        TypedValue typedValue = new TypedValue();

        TypedArray a = this.obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorAccent });
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }



    private int converttDPtoPX(int dpValue){
        final float scale = getResources().getDisplayMetrics().density;
        int padding_in_px = (int) (dpValue * scale + 0.5f);
        return padding_in_px;
    }

    public void makeFavourite(View view){

        if(isFavourite){

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {

                    final Movie movieToDelete = new Movie();
                    movieToDelete.setId(mMovieID);
                    mDb.movieDao().deleteMovie(movieToDelete);
                    Log.d("Deleted","no movie");

                }
            });


            isFavourite = false;
            mFavouriteButton.setImageResource(R.drawable.ic_star_border_white_24px);


        }else{
            final Movie movieToSave = new Movie();
            movieToSave.setId(mMovieID);
            movieToSave.setOriginalTitle(mMovieTitle);
            movieToSave.setPosterURL(mPosterURL);
            movieToSave.setReleaseDate(mReleaseDate);
            movieToSave.setUserRating(mVoteAverage);
            movieToSave.setPlotSynopsis(mPlotSynopsis);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.movieDao().insertMovie(movieToSave);
                    Log.d("Saved","movie");

                }
            });

            isFavourite = true;
            mFavouriteButton.setImageResource(R.drawable.ic_star_white_24px);
        }

    }


    private void checkFavourite(){

        MovieDetailViewModelFactory factory = new MovieDetailViewModelFactory(mDb,mMovieID);
        final MovieDetailViewModel viewModel = ViewModelProviders.of(this,factory).get(MovieDetailViewModel.class);

        viewModel.getMovie().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {

                viewModel.getMovie().removeObserver(this);

                if(movie != null){
                    isFavourite = true;
                    mFavouriteButton.setImageResource(R.drawable.ic_star_white_24px);
                }else{
                    isFavourite = false;
                    mFavouriteButton.setImageResource(R.drawable.ic_star_border_white_24px);
                }
            }
        });



    }





}
