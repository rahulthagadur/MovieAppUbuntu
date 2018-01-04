package com.example.thagadur.imdbmovieapp.Module;

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thagadur on 11/4/2017.
 */

public class MovieDbJsonParse {

    /**
     * @param movieResultData
     * @return
     */
    public static List<MovieDB> parseMovieStringToJson(String movieResultData) {
        List<MovieDB> movieDBList = new ArrayList<>();
        try {
            JSONObject movieResultJsonObject = new JSONObject(movieResultData);
            JSONArray movieResultJsonArray = movieResultJsonObject.getJSONArray("results");
            for (int i = 0; i <= movieResultJsonArray.length(); i++) {
                MovieDB movieDB = new MovieDB();

                movieDB.setMovieTitle(movieResultJsonArray.getJSONObject(i).getString("original_title"));
                movieDB.setMovieRating(movieResultJsonArray.getJSONObject(i).getString("vote_average"));
                movieDB.setMovieDescription(movieResultJsonArray.getJSONObject(i).getString("overview"));
                movieDB.setMovieReleaseDate(movieResultJsonArray.getJSONObject(i).getString("release_date"));
                movieDB.setMoviePosters(movieResultJsonArray.getJSONObject(i).getString("poster_path"));
                movieDB.setMoviePopularity(movieResultJsonArray.getJSONObject(i).getString("popularity"));
                movieDB.setMovieVoteCount(movieResultJsonArray.getJSONObject(i).getString("vote_count"));
                movieDB.setMovieId(movieResultJsonArray.getJSONObject(i).getString("id"));

                movieDBList.add(movieDB);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieDBList;

    }


    /**
     * parseMovieDetailsStringToJson () is used to get the Json Object , Json arrays and we are going
     * to set the value to a list of type MovieDetailsDB so that the data can be Viewed in the Layout
     *
     * @param movieResultData- Consits of Movie Results data in the form of String
     * @return movieDetailsList which contains all the necessary of movie details data in the ArrayList
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static List<MovieDetailsDB> parseMovieDetailsStringToJson(String movieResultData) {
        //Log.i("Movie result Data", movieResultData);
        List<MovieDetailsDB> movieDetailsList = new ArrayList<>();

        try {
            JSONObject movieResultJsonObject = new JSONObject(movieResultData);
            JSONArray movieResultJsonArray = new JSONArray();
            movieResultJsonArray.put(movieResultJsonObject);

            //System.out.println("size of the Json is " + movieResultData.length());
            for (int i = 0; i < movieResultJsonArray.length(); i++) {

                MovieDetailsDB movieDetailsDB = new MovieDetailsDB();

                movieDetailsDB.setMovieTagId(movieResultJsonArray.getJSONObject(i).getString("id"));
                movieDetailsDB.setMovieIMDB_Id(movieResultJsonArray.getJSONObject(i).getString("imdb_id"));
                movieDetailsDB.setMovieImage(movieResultJsonArray.getJSONObject(i).getString("poster_path"));
                movieDetailsDB.setMovieTitle(movieResultJsonArray.getJSONObject(i).getString("title"));
                movieDetailsDB.setMovieTagLine(movieResultJsonArray.getJSONObject(i).getString("tagline"));
                movieDetailsDB.setMovieRealeaseDate(movieResultJsonArray.getJSONObject(i).getString("release_date"));
                movieDetailsDB.setMovieBudget(movieResultJsonArray.getJSONObject(i).getString("budget"));
                movieDetailsDB.setMovieRevenue(movieResultJsonArray.getJSONObject(i).getString("revenue"));
                movieDetailsDB.setMovieStatus(movieResultJsonArray.getJSONObject(i).getString("status"));
                movieDetailsDB.setMovieVoteAverage(movieResultJsonArray.getJSONObject(i).getString("vote_average"));
                movieDetailsDB.setMovieDescription(movieResultJsonArray.getJSONObject(i).getString("overview"));
                movieDetailsDB.setMovieVoteCountUsers(movieResultJsonArray.getJSONObject(i).getString("vote_count"));

                movieDetailsList.add(movieDetailsDB);
                // System.out.println("hi hhh" + movieDetailsList.get(0));
            }
            // Log.i("movieDbList Size", "" + movieDetailsList.size());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieDetailsList;

    }

    public static List<MoviePostersDB> parseMoviePostersStringToJson(String movieResultData) {
        //Log.i("Movie result Data", movieResultData);
        List<MoviePostersDB> moviePostersDBList = new ArrayList<>();

        try {
            JSONObject movieResultJsonObject = new JSONObject(movieResultData);
            JSONArray movieResultJsonArray = movieResultJsonObject.getJSONArray("backdrops");
            movieResultJsonArray.put(movieResultJsonObject);

            //System.out.println("size of the Json is " + movieResultData.length());
            for (int i = 0; i < movieResultJsonArray.length(); i++) {

                MoviePostersDB moviePostersDB = new MoviePostersDB();
                moviePostersDB.setFilePath(movieResultJsonArray.getJSONObject(i).get("file_path").toString());
                moviePostersDBList.add(moviePostersDB);
                System.out.println("Hello "+moviePostersDBList.get(i).getFilePath());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return moviePostersDBList;
    }

    public static List<MovieCastCrewDB> parseMovieCastStringToJson(String movieResultData) {
        //Log.i("Movie result Data", movieResultData);
        List<MovieCastCrewDB> movieCastDBList = new ArrayList<>();

        try {
            JSONObject movieResultJsonObject = new JSONObject(movieResultData);
            JSONArray movieResultJsonArray = movieResultJsonObject.getJSONArray("cast");
            movieResultJsonArray.put(movieResultJsonObject);

            //System.out.println("size of the Json is " + movieResultData.length());
            for (int i = 0; i < movieResultJsonArray.length(); i++) {

                MovieCastCrewDB movieCastDB = new MovieCastCrewDB();
                movieCastDB.setCastFilePath(movieResultJsonArray.getJSONObject(i).get("profile_path").toString());
                movieCastDB.setCastFullName(movieResultJsonArray.getJSONObject(i).get("name").toString());
                movieCastDB.setCastCharater(movieResultJsonArray.getJSONObject(i).get("character").toString());
                movieCastDBList.add(movieCastDB);
                System.out.println("Hello " + movieCastDBList.get(i).getCastFilePath());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieCastDBList;
    }

    public static List<MovieCastCrewDB> parseMovieCrewStringToJson(String movieResultData) {
        //Log.i("Movie result Data", movieResultData);
        List<MovieCastCrewDB> movieCrewDBList = new ArrayList<>();

        try {
            JSONObject movieResultJsonObject = new JSONObject(movieResultData);
            JSONArray movieResultJsonArray = movieResultJsonObject.getJSONArray("crew");
            movieResultJsonArray.put(movieResultJsonObject);

            //System.out.println("size of the Json is " + movieResultData.length());
            for (int i = 0; i < movieResultJsonArray.length(); i++) {

                MovieCastCrewDB movieCrewDB = new MovieCastCrewDB();
                movieCrewDB.setCrewFilePath(movieResultJsonArray.getJSONObject(i).get("profile_path").toString());
                movieCrewDB.setCrewFullName(movieResultJsonArray.getJSONObject(i).get("name").toString());
                movieCrewDB.setCrewjob(movieResultJsonArray.getJSONObject(i).get("job").toString());
                movieCrewDBList.add(movieCrewDB);
                System.out.println("Hello " + movieCrewDBList.get(i).getCrewFilePath());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieCrewDBList;
    }

    public static List<MovieTrailerDBs> parseMovieTrailerStringToJson(String movieResultData) {
        //Log.i("Movie result Data", movieResultData);
        List<MovieTrailerDBs> movieTrailerList = new ArrayList<>();

        try {
            JSONObject movieResultJsonObject = new JSONObject(movieResultData);
            JSONArray movieResultJsonArray = movieResultJsonObject.getJSONArray("results");
            movieResultJsonArray.put(movieResultJsonObject);

            //System.out.println("size of the Json is " + movieResultData.length());
            for (int i = 0; i < movieResultJsonArray.length(); i++) {

                MovieTrailerDBs movieTrailerDB = new MovieTrailerDBs();
                movieTrailerDB.setKey(movieResultJsonArray.getJSONObject(i).get("key").toString());
                movieTrailerDB.setTrailerName(movieResultJsonArray.getJSONObject(i).get("name").toString());
                movieTrailerDB.setTrailerType(movieResultJsonArray.getJSONObject(i).get("type").toString());
                movieTrailerList.add(movieTrailerDB);
                System.out.println("Hello " + movieTrailerList.get(i).getTrailerName());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieTrailerList;
    }

}
