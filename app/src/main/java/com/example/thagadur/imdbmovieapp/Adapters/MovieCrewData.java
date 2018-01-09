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
 * RecyclerView which displays single Crew list item
 */
public class MovieCrewData extends RecyclerView.Adapter<MovieCrewData.MovieViewHolder> {
    List<MovieCastCrewDB> movieCrewList;
    Context context;
    /**
     * Constructor of MovieCrewData
     *
     * @param context
     * @param  movieCrewList Accepting movieCrewList ArrayList which consists of MovieCastCrewDB details
     */
    public MovieCrewData(Context context, List<MovieCastCrewDB> movieCrewList) {
        this.context = context;
        this.movieCrewList = movieCrewList;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_crew_list, parent, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);

        return movieViewHolder;

    }
    /**
     * onBindViewHolder() which binds the data to the RecyclerListAdapter
     * Like MovieCrewData...
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        final MovieCastCrewDB movieCrewDB = movieCrewList.get(position);
        final String movieCrewPoster = movieCrewDB.getCrewFilePath();
        final String movieCrewFullName = movieCrewDB.getCrewFullName();
        final String movieCrewCharacterName = movieCrewDB.getCrewjob();

        holder.crewFullName.setText(movieCrewFullName);
        holder.crewCharacterName.setText(movieCrewCharacterName);
        Picasso.with(context).load(Constant.POSTER_PATH + movieCrewPoster).into(holder.imageViewMovieCrewPoster);

    }
    /**
     * getItemCount() which returns movieCrewList list size
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return movieCrewList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMovieCrewPoster;
        TextView crewFullName, crewCharacterName;

        public MovieViewHolder(View itemView) {
            super(itemView);
            imageViewMovieCrewPoster = itemView.findViewById(R.id.crew_image);
            crewFullName = itemView.findViewById(R.id.crew_full_name);
            crewCharacterName = itemView.findViewById(R.id.crew_character_name);

        }
    }
}
