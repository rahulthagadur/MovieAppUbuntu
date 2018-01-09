package com.example.thagadur.imdbmovieapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.thagadur.imdbmovieapp.Adapters.MovieFavAndWatchListAdapter;
import com.example.thagadur.imdbmovieapp.Adapters.MovieListAdapter;
import com.example.thagadur.imdbmovieapp.Contants.Constant;
import com.example.thagadur.imdbmovieapp.Module.MovieDB;
import com.example.thagadur.imdbmovieapp.Module.MovieDbJsonParse;
import com.example.thagadur.imdbmovieapp.utilities.DataBase;
import com.example.thagadur.imdbmovieapp.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    RecyclerView movieRecyclerView;
    Context context;
    DataBase db;
    List<MovieDB> movieDBList;
    MovieListAdapter movieListAdapter;
    MovieFavAndWatchListAdapter movieFavAndWatchListAdapter;
    ArrayList<HashMap<String, String>> movieList;
    String movieDbUrlPopular;
    String movieUrlQuery;
    boolean CheckData=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialisation of all ID's are done here
        initialiseLayoutFields();
        movieUrlQuery = movieDbUrlPopular;
        //loadMovieData(movieUrlQuery);
    }

    /**
     * Here calling isOnline()
     * If not connected redirecting to OffLineActivity class
     * If connected called loadMovieData()
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (!isOnline()) {
            Intent intent = new Intent();
            intent.setClass(this, OffLineActivity.class);
            startActivity(intent);
            finish();
        } else {
            loadMovieData(movieUrlQuery);
        }
    }

    /**
     * we are going to check whether the device is connected to the internet
     *
     * @return
     */
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Here initialized all the necessary objects,member variables and layout
     */
    public void initialiseLayoutFields(){
        context=this;
        db= new DataBase(context);
        movieDBList=new ArrayList<>();
        movieDbUrlPopular=Constant.SORT_BY_POPULAR;
        movieRecyclerView=(RecyclerView)findViewById(R.id.movie_recyler_view);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        movieRecyclerView.setLayoutManager(layoutManager);

    }
    /**
     * @param movieDbUrl which consists of movieDbUrl  and movieSort
     *                   Which as been sent to buildUrl() inside NetworkUtils class
     *                   Finally calling Async Task with  returned url from  buildUrl()
     */
    public void loadMovieData(String movieDbUrl){
        URL url= NetworkUtils.buildUrl(movieDbUrl);
        new RequestMovieDBdata().execute(url);
    }

    /**
     * Here  movieListAdapter class  has been initialized
     * and setting recyclerViewMovieList Adopter
     */

    private void loadMovieAdapter(String movieResponseData) {
        movieDBList = MovieDbJsonParse.parseMovieStringToJson(movieResponseData);
        movieListAdapter = new MovieListAdapter(context, movieDBList);
        movieRecyclerView.setAdapter(movieListAdapter);
    }

    /**
     * Calling onCreateOptionsMenu()
     * and initialized menu layout
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.sort_movies_list,menu);
        return true;

    }

    /**
     * Calling onOptionsItemSelected() which is used to select particular option menu based on id
     * and called loadMovieData() which loads based upon movieSort type
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId=item.getItemId();
        if(selectedItemId==R.id.most_popular)
        {

            String movieUrlQuery=Constant.SORT_BY_POPULAR;
            loadMovieData(movieUrlQuery);
        }
        else if(selectedItemId==R.id.now_playing)
        {

            String movieUrlQuery=Constant.SORT_BY_NOW_PLAYING;
            loadMovieData(movieUrlQuery);
        }

        else if(selectedItemId==R.id.upcoming_movies)
        {

            String movieUrlQuery=Constant.SORT_BY_UPCOMING;
            loadMovieData(movieUrlQuery);
        }

        else if(selectedItemId==R.id.latest_movies)
        {

            String movieUrlQuery=Constant.SORT_BY_LATEST;
            loadMovieData(movieUrlQuery);
        }
        else if(selectedItemId==R.id.top_rated)
        {

            String movieUrlQuery=Constant.SORT_BY_TOP_RATED;
            loadMovieData(movieUrlQuery);
        } else if (selectedItemId == R.id.Fav) {
            CheckData=true;
            //String poster=dataBase.getAllData().get(0).getImagePath();
            //Toast.makeText(context,"DataPoster="+dataBase.getAllData().get(0).getImagePath(),Toast.LENGTH_LONG).show();
            movieFavAndWatchListAdapter = new MovieFavAndWatchListAdapter(context, db.getAllDataFav());
            movieRecyclerView.setAdapter(movieFavAndWatchListAdapter);

        }
        else if (selectedItemId == R.id.watchlist) {
            CheckData = true;
            //String poster=dataBase.getAllData().get(0).getImagePath();
            //Toast.makeText(context,"DataPoster="+dataBase.getAllData().get(0).getImagePath(),Toast.LENGTH_LONG).show();
            movieFavAndWatchListAdapter = new MovieFavAndWatchListAdapter(context, db.getAllDataWatch());
            movieRecyclerView.setAdapter(movieFavAndWatchListAdapter);
        }

            return super.onOptionsItemSelected(item);
    }

    class RequestMovieDBdata extends AsyncTask<URL,Void,String>{
        /**
         * In the doInBackground() network request  set up has been done
         *
         * @param urls
         * @return MovieDb resultData in String
         */
        @Override
        protected String doInBackground(URL... urls) {

            String movieResponseData = null;
            URL url = urls[0];
            try {
                movieResponseData = NetworkUtils.getResponseFromMovieDb(url);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("ErrorMessage", e.getMessage());
            }

            return movieResponseData;
        }

        /**
         * Accepted data from doInBackground and handling onPostExecute()
         * calling loadMovieAdapter() which sets the data to recyclerViewAdapter
         *
         * @param movieResponseData
         */
        @Override
        protected void onPostExecute(String movieResponseData) {
            super.onPostExecute(movieResponseData);
            Log.d("Data", movieResponseData);
            if (movieResponseData != null) {
                loadMovieAdapter(movieResponseData);
            }
        }

    }

}


