package net.hdc.hdcdemoapp.services;

import android.content.Context;
import android.content.Loader;
import android.os.AsyncTask;

import net.hdc.hdcdemoapp.models.MovieSearchResults;

/**
 * Created by NorrisJ on 9/1/14.
 */
public class MovieSearchLoader extends Loader<MovieSearchResults> {
    public static final String TERM_KEY = "TERM_KEY";
    public static final String COUNT_KEY = "COUNT_KEY";
    private final String term;
    private final int count;

    public MovieSearchLoader(Context context, String term, int count) {
        super(context);
        this.term = term;
        this.count = count;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        new AsyncTask<Void, Void, MovieSearchResults>() {
            @Override
            protected MovieSearchResults doInBackground(Void... voids) {
                MovieSearchService movieSearchService = new MovieSearchService(getContext());
                return movieSearchService.getMovieSearchResults(term, count);
            }

            @Override
            protected void onPostExecute(MovieSearchResults movieSearchResults) {
                deliverResult(movieSearchResults);
            }
        }.execute();
    }
}
