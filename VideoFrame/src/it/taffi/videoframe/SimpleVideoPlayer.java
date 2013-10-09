package it.taffi.videoframe;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class SimpleVideoPlayer extends Activity {
	
	private static MovieListAdapter movieListAdapter = new MovieListAdapter( VideoFrame.getVideoList());
	private Movie mymovie;
	private int i=0;
	private int lastcall=0;
	private final static int videocount = movieListAdapter.getCount()-1;
	private static final String TAG = "TaxiSimplePlayer";

	private VideoView mVideoView;
	private MediaController mC;
	private void loadVideo() {
		
	//	i++;
	//	if (i>=videocount) i=0;
		
	    do
		 i = (int)(Math.random() * videocount);
		while (i==lastcall);
	     lastcall=i;
	     
		mymovie = (Movie) movieListAdapter.getItem(i);	
		
        String mypath =  mymovie.getMoviePath();
		Log.v(TAG, "loading " + i + " path: " + mypath);	
		mVideoView.setVideoPath(mypath);
		//mVideoView.requestFocus();
	    
	}	
	
	
	private void playVideo() {
		Log.v(TAG, "playing video; duration:" + mVideoView.getDuration());
		mVideoView.start();
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, videocount + " videos found.");	
		
				// Going full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(
		WindowManager.LayoutParams.FLAG_FULLSCREEN ^ WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON ^ WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,  
		WindowManager.LayoutParams.FLAG_FULLSCREEN ^ WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON ^ WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
	
		setContentView(R.layout.videolooper);
		
		mVideoView = (VideoView) findViewById(R.id.VideoLooper);			
//		mVideoView.setSystemUiVisibility(View.STATUS_BAR_HIDDEN); 
//		mVideoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); 
//		mVideoView.setKeepScreenOn(true);

//		mC = new MediaController(this);	     
//		mC.hide();
//		mVideoView.setMediaController(mC);
	    
//		mVideoView.requestFocus();
	        
	        
		loadVideo();
		playVideo(); 
		loadVideo();
		
		//mC.setOnCompletionListener();
		
		
		mVideoView.setOnCompletionListener( new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
			    
				
				playVideo();
				loadVideo();
				
				
			}});
		
		
		
		//mVideoView.setOnInfoListener( new MediaPlayer.OnInfoListener() {
			
 //  		Log.v(TAG,"position:" + mVideoView.getCurrentPosition());
	
	//});
 

	}
	
}
