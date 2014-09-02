package net.hdc.hdcdemoapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.hdc.hdcdemoapp.models.Movie;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by NorrisJ on 8/30/14.
 */
public class MovieResultAdapter extends ArrayAdapter<Movie> {

    private final Context context;
    private final Movie[] movies;

    public MovieResultAdapter(Context context, int resource, Movie[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.movies = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Movie movie = this.movies[position];

        if(convertView == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=vi.inflate(R.layout.search_result_list_item, null);
        }

        final ImageView thumb = (ImageView) convertView.findViewById(R.id.movie_thumbnail);

        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... voids) {
                InputStream in = null;
                try {
                    in = new java.net.URL(movie.getPosters().getThumbnail()).openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                return BitmapFactory.decodeStream(in);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if(bitmap == null)
                {
                    Toast.makeText(context, "Error loading thumbnail for " + movie.getTitle(), Toast.LENGTH_SHORT).show();
                    return;
                }

                thumb.setImageBitmap(bitmap);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        TextView titleView = (TextView) convertView.findViewById(R.id.title);
        titleView.setText(movie.getTitle());
        try {
            String cast = movie.getAbridgedCast().get(0).getName() + ", " + movie.getAbridgedCast().get(1).getName();
            ((TextView) convertView.findViewById(R.id.cast)).setText(cast);
        } catch (Exception e) {
            ((TextView) convertView.findViewById(R.id.cast)).setText("Cast Unknown!");
        }
        String year_rating = "Year: " + movie.getYear() + " Rating: " + movie.getRatings().getAudienceScore();
        ((TextView) convertView.findViewById(R.id.year_rating)).setText(year_rating);

        return convertView;
    }
}
