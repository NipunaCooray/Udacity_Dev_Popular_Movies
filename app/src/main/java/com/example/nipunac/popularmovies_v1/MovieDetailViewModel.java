package com.example.nipunac.popularmovies_v1;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nipunac.popularmovies_v1.database.FavouriteDatabase;
import com.example.nipunac.popularmovies_v1.model.Movie;

import java.util.List;

public class MovieDetailViewModel extends ViewModel {

    private static final String TAG = MovieDetailViewModel.class.getSimpleName();

    private LiveData<Movie> movie;

    public LiveData<Movie> getMovie() {
        return movie;
    }

    public MovieDetailViewModel(FavouriteDatabase database, String movieID){

        Log.d(TAG,"Retrieving isFavourite using View Model");
        movie = database.movieDao().getMovieByID(movieID);
    }


}
