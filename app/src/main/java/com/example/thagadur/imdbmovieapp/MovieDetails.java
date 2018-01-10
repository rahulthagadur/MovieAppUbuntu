package com.example.thagadur.imdbmovieapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thagadur.imdbmovieapp.Adapters.MovieCastData;
import com.example.thagadur.imdbmovieapp.Adapters.MovieCrewData;
import com.example.thagadur.imdbmovieapp.Adapters.MoviePosterData;
import com.example.thagadur.imdbmovieapp.Adapters.MovieTrailerData;
import com.example.thagadur.imdbmovieapp.Contants.Constant;
import com.example.thagadur.imdbmovieapp.Module.MovieCastCrewDB;
import com.example.thagadur.imdbmovieapp.Module.MovieDbJsonParse;
import com.example.thagadur.imdbmovieapp.Module.MovieDetailsDB;
import com.example.thagadur.imdbmovieapp.Module.MoviePostersDB;
import com.example.thagadur.imdbmovieapp.Module.MovieTrailerDBs;
import com.example.thagadur.imdbmovieapp.utilities.DataBase;
import com.example.thagadur.imdbmovieapp.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Thagadur on 11/9/2017.
 */

public class MovieDetails extends AppCompatActivity {

    /**
     * Declared  the  Objects for required classes and member variable
     * Like TextView, String Variables , ArrayList ...
     */
    public static String movieId = null;
    public static String movieDetailsUrl = null;
    public static String moviePostersUrl = null;
    public static String movieTrailerUrl = null;
    public static String movieCastAndCrewsUrl = null;
    public static String apiKey = "?api_key=8496be0b2149805afa458ab8ec27560c";
    List<MovieDetailsDB> movieDetailsDBs;
    List<MoviePostersDB> moviePostersDBs;
    List<MovieCastCrewDB> movieCasstDBs;
    List<MovieCastCrewDB> movieCrewDBs;
    DataBase db;
    boolean FavId, WatchId, CheckMovie;
    List<MovieTrailerDBs> movieTrailerDBs;
    Bundle bundle;
    MoviePosterData moviePosterData;
    MovieCastData movieCastData;
    MovieCrewData movieCrewData;
    MovieTrailerData movieTrailerData;
    Movies movie;
    Context context;
    RecyclerView moviePoster, movieCast, movieCrew, movieTrailer;
    TextView movieTitleText, movieReleaseDateText, movieBudgetText, movieRevenueText, movieReleaseStatusText;
    TextView movieVoteAverageText, movieDescriptionText, movieTagLineText, movieVoteCountUsers;
    RatingBar movieRatingBar, movieSingleStarRatingBar;
    ImageView movieImage, favoriteImage, watchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_screen);

        Bundle intent = getIntent().getExtras();
        bundle = getIntent().getExtras();
        movieId = intent.getString("movieId");
        //initialisationOfId method is called
        initialisationOfId();
        isFavMovie(movieId);
        isssWatchMovie(movieId);
        checkMovie(movieId);
        movieDetailsUrl = "http://api.themoviedb.org/3/movie/" + movieId + apiKey;
        movieCastAndCrewsUrl = "http://api.themoviedb.org/3/movie/" + movieId + "/credits" + apiKey;
        moviePostersUrl = "http://api.themoviedb.org/3/movie/" + movieId + "/images" + apiKey;
        movieTrailerUrl = "http://api.themoviedb.org/3/movie/" + movieId + "/videos" + apiKey;
        loadMovieDetailsData(movieDetailsUrl);
        laodMoviePostersData(moviePostersUrl);
        loadMovieCastData(movieCastAndCrewsUrl);
        loadMovieTrailerData(movieTrailerUrl);

//        Onclick of favoriteImage Condition is checked whether movie is fav/not, if not added to DB
        favoriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                movie = new Movies();
                movie.setID(movieDetailsDBs.get(0).getMovieTagId());
                movie.setTitle(movieDetailsDBs.get(0).getMovieTitle());
                movie.setDate(movieDetailsDBs.get(0).getMovieRealeaseDate());
                // Toast.makeText(context, "POster="+movieDetailsDBs.get(0).getMovieImage(), Toast.LENGTH_SHORT).show();
                movie.setPosterPath(movieDetailsDBs.get(0).getMovieImage());

                if (CheckMovie) {
                    // check isFav
                    if (FavId) {
                        // if its is true update value to 0 // delete from fav list
                        boolean check = db.updateFav(movie.getID(), "0");

                        if (check) {
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                            favoriteImage.setImageResource(R.drawable.favorite_disable_normal);

                        } else {
                            //Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        boolean check = db.updateFav(movie.getID(), "1");

                        if (check) {
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                            favoriteImage.setImageResource(R.drawable.favorite_disable_normal);

                        } else {
                           // Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();

                        }
                        // false update value to 1 //adding to favlist

                    }

                } else {
                    //add data to the table
                    movie.setFav(1);
                    movie.setWatchList(0);
                    Toast.makeText(context, "ID=" + db.addMovie(movie), Toast.LENGTH_SHORT).show();
                }

                isFavMovie(movieId);
            }
        });
//        Onclick of watchList Condition is checked whether movie is present watchlist/not, if not added to DB
        watchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                movie = new Movies();
                movie.setID(movieDetailsDBs.get(0).getMovieTagId());
                movie.setTitle(movieDetailsDBs.get(0).getMovieTitle());
                movie.setDate(movieDetailsDBs.get(0).getMovieRealeaseDate());
                // Toast.makeText(context, "POster="+movieDetailsDBs.get(0).getMovieImage(), Toast.LENGTH_SHORT).show();
                movie.setPosterPath(movieDetailsDBs.get(0).getMovieImage());

                if (CheckMovie) {
                    // check isFav
                    if (WatchId) {
                        // if its is true update value to 0 // delete from fav list
                        boolean check = db.updateWatch(movie.getID(), "0");

                        if (check) {
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                            watchList.setImageResource(R.drawable.watchlist_disable_normal);

                        } else {
                            //Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        boolean check = db.updateWatch(movie.getID(), "1");

                        if (check) {
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                            watchList.setImageResource(R.drawable.watchlist_disable_normal);

                        } else {
                            //Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();

                        }
                        // false update value to 1 //adding to favlist

                    }

                } else {
                    //add data to the table
                    movie.setWatchList(1);
                    movie.setFav(0);
                    Toast.makeText(context, "ID=" + db.addMovie(movie), Toast.LENGTH_SHORT).show();
                }

                isssWatchMovie(movieId);
            }
        });
    }


    private void checkMovie(String id) {

        Boolean check = db.checkMovie(id);
        if (check) { //checks if movie does not existing in database
            favoriteImage.setImageResource(R.drawable.favorite_enable_normal);
            CheckMovie = true;

        } else {
            favoriteImage.setImageResource(R.drawable.favorite_disable_normal);
            CheckMovie = false;
        }


    }

    private void isFavMovie(String id) {

        Boolean check = db.checkMovieFav(id);
        if (check) { //checks if movie does not existing in database
            favoriteImage.setImageResource(R.drawable.favorite_enable_normal);
            FavId = true;

        } else {
            favoriteImage.setImageResource(R.drawable.favorite_disable_normal);
            FavId = false;
        }


    }

    private void isssWatchMovie(String id) {

        Boolean check = db.checkMovieWatch(id);
        if (check) { //checks if movie does not existing in database
            watchList.setImageResource(R.drawable.watchlist_enable_normal);
            WatchId = true;

        } else {
            watchList.setImageResource(R.drawable.watchlist_disable_normal);
            WatchId = false;
        }


    }


    public void loadMovieTrailerData(String movieTrailerUrl) {
        URL url = NetworkUtils.buildUrl(movieTrailerUrl);
        new RequestMovieTrailerdata().execute(url);
    }

    public void loadMovieCastData(String movieCastCrewUrl) {
        URL url = NetworkUtils.buildUrl(movieCastCrewUrl);
        new RequestMovieCastCrewdata().execute(url);
    }

    public void laodMoviePostersData(String moviePostersUrl) {
        URL url = NetworkUtils.buildUrl(moviePostersUrl);
        new RequestMoviePostersdata().execute(url);
    }

    public void loadMovieDetailsData(String movieDetailsUrl) {
        URL url = NetworkUtils.buildUrl(movieDetailsUrl);
        new RequestMovieDetailsdata().execute(url);
    }

    /**
     * Here initialized all the necessary objects,member variables and layout,TextView, ImageView,RecyclerView,ArrayAdapter etc..
     */
    public void initialisationOfId() {
        context = this;
        movieTitleText = (TextView) findViewById(R.id.title);
        movieTagLineText = (TextView) findViewById(R.id.tag_line);
        movieReleaseDateText = (TextView) findViewById(R.id.release_date);
        movieBudgetText = (TextView) findViewById(R.id.budget);
        movieRevenueText = (TextView) findViewById(R.id.revenue);
        movieReleaseStatusText = (TextView) findViewById(R.id.status);
        movieVoteAverageText = (TextView) findViewById(R.id.vote_average);
        movieDescriptionText = (TextView) findViewById(R.id.description);
        movieVoteCountUsers = (TextView) findViewById(R.id.vote_count_users);
        movieRatingBar = (RatingBar) findViewById(R.id.ratingBar2);
        movieImage = (ImageView) findViewById(R.id.movieImage);
        movieSingleStarRatingBar = (RatingBar) findViewById(R.id.movie_single_star_rating_bar);
        db = new DataBase(MovieDetails.this);

        moviePoster = (RecyclerView) findViewById(R.id.poster_image);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayout.HORIZONTAL, false);
        moviePoster.setLayoutManager(linearLayoutManager);

        movieTrailer = (RecyclerView) findViewById(R.id.trailer_list);
        LinearLayoutManager linearLayoutManagerTrailer = new LinearLayoutManager(context, LinearLayout.HORIZONTAL, false);
        movieTrailer.setLayoutManager(linearLayoutManagerTrailer);

        movieCast = (RecyclerView) findViewById(R.id.cast_list);
        LinearLayoutManager linearLayoutManagerCast = new LinearLayoutManager(context, LinearLayout.HORIZONTAL, false);
        movieCast.setLayoutManager(linearLayoutManagerCast);

        movieCrew = (RecyclerView) findViewById(R.id.crew_list);
        LinearLayoutManager linearLayoutManagerCrew = new LinearLayoutManager(context, LinearLayout.HORIZONTAL, false);
        movieCrew.setLayoutManager(linearLayoutManagerCrew);

        favoriteImage = (ImageView) findViewById(R.id.favourite_image_view);
        watchList = (ImageView) findViewById(R.id.watchlist_image_view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void loadMovieAdapter(String movieResponseData) {
        Log.i("Hi see the Json String ", movieResponseData);
        movieDetailsDBs = MovieDbJsonParse.parseMovieDetailsStringToJson(movieResponseData);
        Log.i("ArrayList Size", "" + movieDetailsDBs.size());
        setDataIntoLayoutFields(movieDetailsDBs);
    }

    private void loadMoviePostersAdapter(String movieResponsePosterData) {

        moviePostersDBs = MovieDbJsonParse.parseMoviePostersStringToJson(movieResponsePosterData);
        setPosterIntoLayoutFields(moviePostersDBs);

    }

    private void loadMovieCastCrewAdapter(String movieCastCrewsResponseData) {

        movieCasstDBs = MovieDbJsonParse.parseMovieCastStringToJson(movieCastCrewsResponseData);
        movieCrewDBs = MovieDbJsonParse.parseMovieCrewStringToJson(movieCastCrewsResponseData);
        setCastIntoLayoutFields(movieCasstDBs);
        setCrewIntoLayoutFields(movieCrewDBs);

        //setCrewerIntoLayoutFields(movieCrewDBs);

    }

    private void loadMovieTrailerAdapter(String movieResponsePosterData) {
        movieTrailerDBs = MovieDbJsonParse.parseMovieTrailerStringToJson(movieResponsePosterData);
        setTrailerIntoLayoutFields(movieTrailerDBs);
    }

    public void setTrailerIntoLayoutFields(List<MovieTrailerDBs> movieTrailerList) {
        movieTrailerData = new MovieTrailerData(context, movieTrailerList);
        movieTrailer.setAdapter(movieTrailerData);
    }

    public void setCrewIntoLayoutFields(List<MovieCastCrewDB> movieCrewList) {
        movieCrewData = new MovieCrewData(context, movieCrewList);
        movieCrew.setAdapter(movieCrewData);
    }

    public void setCastIntoLayoutFields(List<MovieCastCrewDB> movieCastList) {
        movieCastData = new MovieCastData(context, movieCastList);
        movieCast.setAdapter(movieCastData);
    }

    public void setPosterIntoLayoutFields(List<MoviePostersDB> moviePostersDBs) {

        moviePosterData = new MoviePosterData(context, moviePostersDBs);
        moviePoster.setAdapter(moviePosterData);

    }

    //    Setting Data into the Text fields From the movieDetailsDBs List
    public void setDataIntoLayoutFields(List<MovieDetailsDB> movieDetailsDBs) {
        //Formatting the Numbers into Readable Form
        int movieBudget = Integer.parseInt(movieDetailsDBs.get(0).getMovieBudget().toString());
        int movieRevenue = Integer.parseInt(movieDetailsDBs.get(0).getMovieRevenue().toString());
        int VoteCountUsers = Integer.parseInt(movieDetailsDBs.get(0).getMovieVoteCountUsers().toString());
        //float movieVoteAvg = Float.parseFloat(movieDetailsDBs.get(0).getMovieVoteAverage().toString());

        Picasso.with(context).load(Constant.POSTER_PATH + movieDetailsDBs.get(0).getMovieImage().toString()).into(movieImage);
        movieTitleText.setText(movieDetailsDBs.get(0).getMovieTitle().toString());
        movieTagLineText.setText(movieDetailsDBs.get(0).getMovieTagLine().toString());
        movieReleaseDateText.setText(movieDetailsDBs.get(0).getMovieRealeaseDate().toString());
        movieBudgetText.setText("Budget: " + NumberFormat.getIntegerInstance().format(movieBudget));
        movieRevenueText.setText("Revenue: " + NumberFormat.getIntegerInstance().format(movieRevenue));
        movieReleaseStatusText.setText("Staus: " + movieDetailsDBs.get(0).getMovieStatus().toString());
        movieVoteAverageText.setText(movieDetailsDBs.get(0).getMovieVoteAverage().toString() + "/10");
        movieDescriptionText.setText(movieDetailsDBs.get(0).getMovieDescription().toString());
        movieVoteCountUsers.setText(NumberFormat.getIntegerInstance().format(VoteCountUsers) + " users");
        //float d = movieVoteAvg * 10;
        movieRatingBar.setRating(Float.parseFloat(movieDetailsDBs.get(0).getMovieVoteAverage()) / 2);
        movieSingleStarRatingBar.setRating(Float.parseFloat(movieDetailsDBs.get(0).getMovieVoteAverage()) / 10);

        //movieRatingBar.setStepSize(d);
    }

    /**
     * Requesting Cast and Crew data from the api and data has been send to loadMovieCastCrewAdapter in
     * String form
     */
    class RequestMovieCastCrewdata extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String movieCastCrewsResponseData = null;
            URL url = urls[0];
            try {
                movieCastCrewsResponseData = NetworkUtils.getResponseFromMovieDb(url);
            } catch (IOException e) {
                e.printStackTrace();
                movieCastCrewsResponseData = e.getMessage();
            }


            return movieCastCrewsResponseData;
        }

        protected void onPostExecute(String movieCastCrewsResponseData) {
            super.onPostExecute(movieCastCrewsResponseData);
            Log.d("Data", movieCastCrewsResponseData);
            if (movieCastCrewsResponseData != null) {
                loadMovieCastCrewAdapter(movieCastCrewsResponseData);
            }
        }

    }

    /**
     * Requesting Posters data from the api and data has been send to loadMoviePostersAdapter in
     * String form
     */
    class RequestMoviePostersdata extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String moviePostersResponseData = null;
            URL url = urls[0];
            try {
                moviePostersResponseData = NetworkUtils.getResponseFromMovieDb(url);
            } catch (IOException e) {
                e.printStackTrace();
                moviePostersResponseData = e.getMessage();
            }


            return moviePostersResponseData;
        }

        protected void onPostExecute(String movieResponsePosterData) {
            super.onPostExecute(movieResponsePosterData);
            Log.d("Data", movieResponsePosterData);
            if (movieResponsePosterData != null) {
                loadMoviePostersAdapter(movieResponsePosterData);
            }
        }
    }

    /**
     * Requesting Moview Details data from the api and data has been send to loadMovieAdapter in
     * String form for Json conversion
     */
    class RequestMovieDetailsdata extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {

            String movieDetailsResponseData = null;
            URL url = urls[0];
            try {
                movieDetailsResponseData = NetworkUtils.getResponseFromMovieDb(url);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("ErrorMessage", e.getMessage());
            }
            return movieDetailsResponseData;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String movieResponseData) {
            super.onPostExecute(movieResponseData);
            Log.d("Data", movieResponseData);
            if (movieResponseData != null) {
                loadMovieAdapter(movieResponseData);
            }
        }
    }

    /**
     * Requesting Trailer Details data from the api and data has been send to loadMovieTrailerAdapter in
     * String form for Json conversion
     */
    class RequestMovieTrailerdata extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {

            String movieTrailerResponseData = null;
            URL url = urls[0];
            try {
                movieTrailerResponseData = NetworkUtils.getResponseFromMovieDb(url);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("ErrorMessage", e.getMessage());
            }
            return movieTrailerResponseData;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String movieResponseData) {
            super.onPostExecute(movieResponseData);
            Log.d("Data", movieResponseData);
            if (movieResponseData != null) {
                loadMovieTrailerAdapter(movieResponseData);
            }
        }
    }
}
