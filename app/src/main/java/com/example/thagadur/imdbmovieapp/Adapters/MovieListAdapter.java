package com.example.thagadur.imdbmovieapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.thagadur.imdbmovieapp.Contants.Constant;
import com.example.thagadur.imdbmovieapp.Module.MovieDB;
import com.example.thagadur.imdbmovieapp.MovieDetails;
import com.example.thagadur.imdbmovieapp.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Thagadur on 11/4/2017.
 */


/**
 * RecyclerView which displays single movie list item
 */
public  class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    List<MovieDB> MovieDBList;
    Context context;

    /**
     * Constructor of MovieListAdapter
     *
     * @param context
     * @param  MovieDBList Accepting movieDBList ArrayList which consists of MovieDB details
     */
    public MovieListAdapter(Context context, List<MovieDB> MovieDBList) {
        this.context = context;
        this.MovieDBList = MovieDBList;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_row_item, parent, false);
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
        final MovieDB MovieDB = MovieDBList.get(position);
        final String moviePoster = MovieDB.getMoviePosters();
        final String movieTitle = MovieDB.getMovieTitle();
       // final String movieDescription = MovieDB.getMovieDescription();
        final String movieReleaseDate = MovieDB.getMovieReleaseDate();
        final String movieRating = MovieDB.getMovieRating();
        final String movieId = MovieDB.getMovieId();
        final String movieVoteCount= MovieDB.getMovieVoteCount();
        final float movieRatingBar=Float.parseFloat(MovieDB.getMovieRating())/10;
        final float movieRatingBarFive=Float.parseFloat(MovieDB.getMovieRating())/2;

        holder.movieTitle.setText(movieTitle);
        holder.movieVoteCount.setText("("+movieRating+"/10) voted by "+NumberFormat.getIntegerInstance().format(Integer.parseInt(movieVoteCount))+" users");
        holder.movieReleaseDate.setText("Released on : "+movieReleaseDate);
        ///holder.movieVoteCount.setText("voted by "+ NumberFormat.getIntegerInstance().format(movieVoteCount)+"users");
        holder.movieSingleStarRatingBar.setRating(movieRatingBar);
        holder.movieRatingBarFive.setRating(movieRatingBarFive);

        Picasso.with(context).load(Constant.POSTER_PATH + moviePoster).into(holder.imageViewMoviePoster);
/**
 * on click of imageViewMoviePoster adding all data from list adding to   Intent bundle
 * and redirecting to MovieDetailedView Activity
 */
        holder.imageViewMoviePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, MovieDetails.class);
                intent.putExtra("movieId",movieId);
//                intent.putExtra("moviePoster",moviePoster);
//                intent.putExtra("movieTitle",movieTitle);
//                intent.putExtra("movieReleaseDate",movieReleaseDate);
//                intent.putExtra("movieRating",movieRating);
//                intent.putExtra("movieDescription",movieDescription);
                context.startActivity(intent);
            }
        });
    }

    /**
     * getItemCount() which returns movieDBList list size
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return MovieDBList.size();
    }

    /**
     * Calling viewHolder SubClass
     */

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMoviePoster,moviePopularityStar;
        TextView movieTitle, movieReleaseDate, moviePopularity,movieVoteCount,movieVoteAverage;
        RatingBar movieSingleStarRatingBar,movieRatingBarFive;

        public MovieViewHolder(View itemView) {
            super(itemView);
            imageViewMoviePoster = itemView.findViewById(R.id.movie_image);
            movieTitle=itemView.findViewById(R.id.movie_title);
            movieReleaseDate=itemView.findViewById(R.id.movie_release_date);
            moviePopularity=itemView.findViewById(R.id.movie_popularity);
            movieRatingBarFive=itemView.findViewById(R.id.ratingBar);
            movieSingleStarRatingBar=itemView.findViewById(R.id.user_rating);
            movieVoteAverage=itemView.findViewById(R.id.movie_vote_average);
            movieVoteCount=itemView.findViewById(R.id.movie_vote_count);

        }
    }
}
