package it.taffi.videoframe;



import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.AdapterView.OnItemClickListener;
//import it.taffi.greenpanda.R;
import it.taffi.videoframe.MovieListAdapter;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class VideoFrame extends Activity {
	
	private static final String TAG = "TaxiVideoFrame";

	
	private Button mStart;
	private Button mVideoList;

	private VideoView mVideoView; 

//	private VideoList videolist = new VideoList((Activity)this); 

	static private ArrayList<Movie> movieList = new ArrayList<Movie>();
    
	private void loadfileList() {  //popola la lista dei video 
		
//		movieList = new ArrayList<Movie>();

		
		// Media columns to query
		String[] mediaColumns = { MediaStore.Video.Media._ID,
				MediaStore.Video.Media.TITLE, MediaStore.Video.Media.DURATION,
				MediaStore.Video.Media.DATA,
				MediaStore.Video.Media.MIME_TYPE };

		// Thumbnail columns to query
		String[] thumbnailColumns = { MediaStore.Video.Thumbnails.DATA };


		// Query external movie content for selected media columns
		Cursor mediaCursor = managedQuery(
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns,
				null, null, null);

		
		// Loop through media results
		if ((mediaCursor != null) && mediaCursor.moveToFirst()) {
			do {
				// Get the video id
				int id = mediaCursor.getInt(mediaCursor
						.getColumnIndex(MediaStore.Video.Media._ID));

				// Get the thumbnail associated with the video
				Cursor thumbnailCursor = managedQuery(
						MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
						thumbnailColumns, MediaStore.Video.Thumbnails.VIDEO_ID
								+ "=" + id, null, null);

				// New movie object from the data
				Movie movie = new Movie(mediaCursor, thumbnailCursor);
				//Log.d(LOG_TAG, movie.toString());

				// Add to movie list
				movieList.add(movie);

			} while (mediaCursor.moveToNext());

			
		} 
		}

	/*
	 * 
	 * */
	

	static public ArrayList<Movie> getVideoList() {
		return movieList;
	
	}  
	
	
	/*
	 * 
	 * */
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadfileList();
		
		setContentView(R.layout.welcome); 
		
        //getFileList();

//		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		mStart = (Button) findViewById(R.id.start);
		mVideoList = (Button) findViewById(R.id.videolist);
		  
		
		/*
		 *  Start Videoplayer!!!
		 *  
		 * */
        mStart.setOnClickListener(new OnClickListener() {
        	
			public void onClick(View view) {

	//			Intent intent= new Intent(VideoFrame.this, SimpleVideoPlayer.class);
				Intent intent= new Intent(VideoFrame.this, SurfaceVideoPlayer.class);
				
				startActivity(intent);
		

				//mVideoView.setKeepScreenOn(true);
				//mVideoView.start();
			//	MediaController mediacontroller = new MediaController(getParent());
			//	mVideoView.setMediaController(mediacontroller);
			//	mediacontroller.setMediaPlayer(new MediaPlayerControl() {
					
//					public void start() {
//						mediaPlayer.start();
//					}
					
//				});
						
				// Set list view adapter to movie list adapter
				//ListView movieListView = (ListView) findViewById(R.id.movieListView);
				//movieListView.setAdapter(movieListAdapter);	
				
				
				//	Intent intent= new Intent(VideoFrame.this, MoviePlayerActivity.class);
				//	startActivity(intent);
				/*
				 * 
				 * */

		//		runOnUiThread(new Runnable(){   //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//			public void run() {
		//				playVideo();
		//			}
		//		});
			
				
				
			}
		});
        
        /*
         * *
         * */
        
        mVideoList.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				

					Intent intent= new Intent(VideoFrame.this, MoviePlayerActivity.class);
					startActivity(intent);
			
				
			}
		});

        /**
    	 * On item click listener.
    	 */
    /*	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    		// Gets the selected movie
    		Movie movie = (Movie) parent.getAdapter().getItem(position);
    		
    		// Plays the selected movie
    		Intent intent = new Intent(Intent.ACTION_VIEW);
    		intent.setDataAndType(Uri.parse(movie.getMoviePath()), movie.getMimeType());
    		startActivity(intent);
    	} 	
		*/
/*	
	//Faccio partire il player in un thread!!
		runOnUiThread(new Runnable(){   //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			public void run() {
//				playVideo();
			}
		});
*/	
	
	}

		
	


//ecco il player!	
	
	
	

}


	
