package com.example.nipunac.popularmovies_v1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nipunac.popularmovies_v1.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private ArrayList<Movie> mMovieList;

    private static final String baseMovieImageUrl="http://image.tmdb.org/t/p/w185/";

    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    private final MovieItemOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface MovieItemOnClickHandler {
        void onClick(Movie selectedMovie);
    }

    public MovieAdapter(MovieItemOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }


    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {
        Movie selectedMovie = mMovieList.get(position);
        //Picasso.get().load(selectedMovie.getPosterURL()).into(holder.mMovieImageView);
        Picasso.get().load(baseMovieImageUrl+selectedMovie.getPosterURL()).into(holder.mMovieImageView);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieList) return 0;
        return mMovieList.size();
    }

    /**
     * Cache of the children views for a forecast list item.
     */
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mMovieImageView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mMovieImageView = (ImageView) view.findViewById(R.id.IV_displayPoster);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie currentMovie = mMovieList.get(adapterPosition);
            mClickHandler.onClick(currentMovie);
        }
    }

    public void setMovieData(ArrayList movieData) {
        mMovieList = movieData;
        notifyDataSetChanged();
    }


}
