package it.taffi.videoframe;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import it.taffi.videoframe.R;


/**
 * Movie player.
 * 
 * @author Onur Cinar
 */
public class MoviePlayerActivity extends Activity implements OnItemClickListener {
	
    	
	//private static final String LOG_TAG = "MoviePlayer";
	//private VideoList videolist;
	
	//public MoviePlayerActivity() {
		
	//	 videolist = new VideoList(); 		
		
	//}
	
	/**
	 * On create lifecycle method.
	 * 
	 * @param savedInstanceState saved state.
	 * @see Activity#onCreate(Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.videolist);
		
		// Define movie list adapter
		MovieListAdapter movieListAdapter = new MovieListAdapter(this, VideoFrame.getVideoList());

		// Set list view adapter to movie list adapter
		ListView movieListView = (ListView) findViewById(R.id.movieListView);
		movieListView.setAdapter(movieListAdapter);	
		
		// Set  item click listener  
		movieListView.setOnItemClickListener(this);
	}

	/**
	 * On item click listener.
	 */
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// Gets the selected movie
		Movie movie = (Movie) parent.getAdapter().getItem(position);
		
		// Plays the selected movie
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse(movie.getMoviePath()), movie.getMimeType());
		startActivity(intent);
	}
}
