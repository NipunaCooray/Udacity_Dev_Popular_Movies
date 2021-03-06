package com.example.nipunac.popularmovies_v1.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.nipunac.popularmovies_v1.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class FavouriteDatabase extends RoomDatabase {

    private static final String LOG_TAG = FavouriteDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "favouritemovies";
    private static FavouriteDatabase sInstance;

    public static FavouriteDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        FavouriteDatabase.class, FavouriteDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract MovieDao movieDao();

}
