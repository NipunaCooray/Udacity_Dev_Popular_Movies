package com.example.nipunac.popularmovies_v1;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.nipunac.popularmovies_v1.database.FavouriteDatabase;

public class MovieDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final FavouriteDatabase mDb;
    private final String mMovieID;

    public MovieDetailViewModelFactory(FavouriteDatabase database, String movieID){
        mDb = database;
        mMovieID = movieID;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieDetailViewModel(mDb,mMovieID);
    }
}
