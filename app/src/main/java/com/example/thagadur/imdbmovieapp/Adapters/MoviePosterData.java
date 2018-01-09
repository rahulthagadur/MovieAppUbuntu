package com.example.thagadur.imdbmovieapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.thagadur.imdbmovieapp.Contants.Constant;
import com.example.thagadur.imdbmovieapp.Module.MoviePostersDB;
import com.example.thagadur.imdbmovieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Thagadur on 11/28/2017.
 */

/**
 * RecyclerView which displays single Poster list item
 */
public class MoviePosterData extends RecyclerView.Adapter<MoviePosterData.MovieViewHolder> {
    List<MoviePostersDB> moviePostersList;
    Context context;

    /**
     * Constructor of MoviePosterData
     *
     * @param context
     * @param moviePostersList Accepting moviePostersList ArrayList which consists of MoviePostersDB details
     */
    public MoviePosterData(Context context, List<MoviePostersDB> moviePostersList) {
        this.context = context;
        this.moviePostersList = moviePostersList;

    }

    /**
     * Setting RecyclerList View Adapter Layout
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_poster_image, parent, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);

        return movieViewHolder;

    }


    /**
     * onBindViewHolder() which binds the data to the RecyclerListAdapter
     * Like MoviePoster...
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        final MoviePostersDB moviePostersDB = moviePostersList.get(position);
        final String moviePoster = moviePostersDB.getFilePath();

        Picasso.with(context).load(Constant.POSTER_PATH + moviePoster).into(holder.imageViewMoviePoster);

    }

    /**
     * getItemCount() which returns moviePostersList list size
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return moviePostersList.size();
    }

    /**
     * Calling viewHolder SubClass
     */
    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMoviePoster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            imageViewMoviePoster = itemView.findViewById(R.id.poster_image_recycler);

        }
    }
}
