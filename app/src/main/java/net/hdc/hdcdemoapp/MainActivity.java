package net.hdc.hdcdemoapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import net.hdc.hdcdemoapp.models.Movie;
import net.hdc.hdcdemoapp.models.MovieSearchResults;
import net.hdc.hdcdemoapp.services.MovieSearchService;

import java.util.List;


public class MainActivity extends Activity {

    static final String TAG = "MAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editText = (EditText) findViewById(R.id.search_box);
        editText.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                searchButtonClicked(null);
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void searchButtonClicked(View v) {
        // Check Input
        final String input = ((EditText)findViewById(R.id.search_box)).getText().toString();
        if(input.isEmpty())
        {
            Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get search results
        new AsyncTask<Void, Void, MovieSearchResults>() {
            @Override
            protected MovieSearchResults doInBackground(Void... voids) {
                MovieSearchService movieSearchService = new MovieSearchService(MainActivity.this);
                return movieSearchService.getMovieSearchResults(input);
            }

            @Override
            protected void onPostExecute(MovieSearchResults movieSearchResults) {
                if(movieSearchResults == null) {
                    displaySearchError();
                    return;
                }

                displaySearchResults(movieSearchResults);
            }
        }.execute();
    }

    private void displaySearchError() {
        Toast.makeText(this, "There was an error retrieving the movie search results.", Toast.LENGTH_SHORT).show();
    }

    private void displaySearchResults(MovieSearchResults movieSearchResults) {
        List<Movie> movies = movieSearchResults.getMovies();
        String[] titles = new String[movies.size()];
        for(int i = 0; i < movies.size(); i++) {
            titles[i] = movies.get(i).getTitle();
        }

        ListView searchResults = (ListView) findViewById(R.id.search_results);
        MovieResultAdapter adapter = new MovieResultAdapter(this, 0, movies.toArray(new Movie[0]));
        //searchResults.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, titles));
        searchResults.setAdapter(adapter);
    }
}
