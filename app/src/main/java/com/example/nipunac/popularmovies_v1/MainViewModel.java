package com.example.nipunac.popularmovies_v1;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nipunac.popularmovies_v1.database.FavouriteDatabase;
import com.example.nipunac.popularmovies_v1.model.Movie;

import java.util.List;

public class MainViewModel extends AndroidViewModel{


    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<Movie>> movies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        FavouriteDatabase database = FavouriteDatabase.getInstance(this.getApplication());
        Log.d(TAG,"Actively retrieving the tasks from the database");
        movies = database.movieDao().selectAllMovies();

    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
