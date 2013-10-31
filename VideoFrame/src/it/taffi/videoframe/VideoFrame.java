package it.taffi.videoframe;



import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class VideoFrame extends Activity {
	
	private static final String TAG = "VideoFrame";

	
	private Button mStart_VideoView;
	private Button mStart_Surface;
	private Button mStart_GLSurface;

	
	private Button mVideoList;

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
				Log.d(TAG, movie.getTitle());

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
		mStart_VideoView = (Button) findViewById(R.id.start_videoview);
		mStart_Surface = (Button) findViewById(R.id.start_surface);
		mStart_GLSurface = (Button) findViewById(R.id.start_GLSurface);
		
		
		mVideoList = (Button) findViewById(R.id.videolist);
		  
		
		/*
		 *  Start Videoplayer!!!
		 *  	private Button 

		 * */
        mStart_VideoView.setOnClickListener(new OnClickListener() {
        	
			public void onClick(View view) {

				Intent intent= new Intent(VideoFrame.this, SimpleVideoPlayer.class);
				
				startActivity(intent);				
			}
		});
        
        
  mStart_Surface.setOnClickListener(new OnClickListener() {
        	
			public void onClick(View view) {

				Intent intent= new Intent(VideoFrame.this, SurfaceVideoPlayer.class);
				
				startActivity(intent);				
			}
		});
  
  mStart_GLSurface.setOnClickListener(new OnClickListener() {
  	
		public void onClick(View view) {

//			Intent intent= new Intent(VideoFrame.this, SimpleVideoPlayer.class);
			Intent intent= new Intent(VideoFrame.this, GLSurfaceVideoPlayer.class);
			
			startActivity(intent);				
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


	
