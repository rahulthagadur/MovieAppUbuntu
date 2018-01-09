package com.example.thagadur.imdbmovieapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thagadur.imdbmovieapp.Contants.Constant;
import com.example.thagadur.imdbmovieapp.Module.MovieCastCrewDB;
import com.example.thagadur.imdbmovieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Thagadur on 11/29/2017.
 */

/**
 * RecyclerView which displays single Cast list item
 */
public class MovieCastData extends RecyclerView.Adapter<MovieCastData.MovieViewHolder> {

    List<MovieCastCrewDB> movieCastList;
    Context context;
    /**
     * Constructor of MovieCastData
     *
     * @param context
     * @param  movieCastList Accepting movieCastList ArrayList which consists of MovieCastCrewDB details
     */
    public MovieCastData(Context context, List<MovieCastCrewDB> movieCastList) {
        this.context = context;
        this.movieCastList = movieCastList;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_cast_list, parent, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);

        return movieViewHolder;

    }
    /**
     * onBindViewHolder() which binds the data to the RecyclerListAdapter
     * Like MovieCast...
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        final MovieCastCrewDB movieCastDB = movieCastList.get(position);
        final String movieCastPoster = movieCastDB.getCastFilePath();
        final String movieCastFullName = movieCastDB.getCastFullName();
        final String movieCastCharacterName = movieCastDB.getCastCharater();

        holder.castFullName.setText(movieCastFullName);
        holder.castCharacterName.setText(movieCastCharacterName);
        Picasso.with(context).load(Constant.POSTER_PATH + movieCastPoster).into(holder.imageViewMovieCastPoster);

    }
    /**
     * getItemCount() which returns movieCrewList list size
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return movieCastList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMovieCastPoster;
        TextView castFullName, castCharacterName;

        public MovieViewHolder(View itemView) {
            super(itemView);
            imageViewMovieCastPoster = itemView.findViewById(R.id.cast_image);
            castFullName = itemView.findViewById(R.id.cast_full_name);
            castCharacterName = itemView.findViewById(R.id.cast_character_name);

        }
    }
}
