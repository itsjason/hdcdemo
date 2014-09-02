package net.hdc.hdcdemoapp.services;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import net.hdc.hdcdemoapp.R;
import net.hdc.hdcdemoapp.models.MovieSearchResults;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by NorrisJ on 9/1/14.
 */
public class MovieSearchService {

    private final Context context;

    public MovieSearchService(Context context) {
        this.context = context;
    }

    public MovieSearchResults getMovieSearchResults(String searchTerm) {
        String apiBaseUrl = context.getString(R.string.api_base_url);
        RestClient restClient = new RestClient(apiBaseUrl + "movies.json");
        restClient.AddParam("apikey", context.getString(R.string.rotten_api_key));
        try {
            restClient.AddParam("q", URLEncoder.encode(searchTerm, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        restClient.AddParam("page_limit", "10");
        restClient.AddParam("page", "1");
        try {
            restClient.Execute(RestClient.RequestMethod.GET);
        } catch (Exception e) {
            e.printStackTrace();

        }

        int responseCode = restClient.getResponseCode();
        if(responseCode != 200) {
            Log.e("MOVIE SEARCH", "Error Response: " + responseCode);
            return null;
        }
        String response = restClient.getResponse();

        try {
            MovieSearchResults movieSearchResults = new Gson().fromJson(response, MovieSearchResults.class);
            return movieSearchResults;
        } catch (Exception e) {
            return null;
        }
    }
}
