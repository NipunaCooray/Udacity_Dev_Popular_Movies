package com.example.nipunac.popularmovies_v1.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.nipunac.popularmovies_v1.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie ")
    List<Movie> selectAllMovies();

    @Insert
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT * FROM movie WHERE id=:movieID")
    Movie getMovieByID(String movieID);


}
