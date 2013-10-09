package it.taffi.videoframe;

import java.io.IOException;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;
import android.widget.MediaController.MediaPlayerControl;

public class SurfaceVideoPlayer extends Activity implements SurfaceHolder.Callback {

  static final String TAG = "SurfaceViewVideoViewActivity";
  
  private MediaPlayer mediaPlayer;

    private static MovieListAdapter movieListAdapter = new MovieListAdapter( VideoFrame.getVideoList());
	private Movie mymovie;
	private int i=0;
	private int lastcall=0;
	private final static int videocount = movieListAdapter.getCount()-1;
//	private static final String TAG = "TaxiSimplePlayer";

//	private VideoView mVideoView;
//	private MediaController mC;
  
  private void loadVideo() {
				
		    do
			 i = (int)(Math.random() * videocount);
			while (i==lastcall);
		     lastcall=i;
		     
			mymovie = (Movie) movieListAdapter.getItem(i);	
			
	        String mypath =  mymovie.getMoviePath();
			Log.v(TAG, "loading " + i + " path: " + mypath);	
			mediaPlayer.reset();
	        try {
				mediaPlayer.setDataSource(mypath);
				mediaPlayer.prepare();
			
	        } catch (IllegalArgumentException e) {
	            Log.e(TAG, "Illegal Argument Exception", e);
	          } catch (IllegalStateException e) {
	            Log.e(TAG, "Illegal State Exception", e);
	          } catch (SecurityException e) {
	            Log.e(TAG, "Security Exception", e);
	          } catch (IOException e) {      
	            Log.e(TAG, "IO Exception", e);
	          }
	        
	        
		}	
		
		
		private void playVideo() {
		//	Log.v(TAG, "playing video; duration:" + mVideoView.getDuration());
		//	mVideoView.start();
			
	        mediaPlayer.start();		
			
		}
  
  public void surfaceCreated(SurfaceHolder holder) { 
    
      // When the surface is created, assign it as the
      // display surface and assign and prepare a data 
      // source.
      mediaPlayer.setDisplay(holder);
//      mediaPlayer.setDataSource("/sdcard/test2.3gp");
  

		loadVideo();
		playVideo();
        		
         mediaPlayer.setOnCompletionListener( new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
			    
				loadVideo();
				playVideo();
					
				
			}});
		    
	   
      
 
  }

  
  
  public void surfaceDestroyed(SurfaceHolder holder) {
    mediaPlayer.release();
  }  

  public void surfaceChanged(SurfaceHolder holder,
                             int format, int width, int height) { }

  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
	// Going full screen
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	getWindow().setFlags(
	WindowManager.LayoutParams.FLAG_FULLSCREEN ^ WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON ^ WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,  
	WindowManager.LayoutParams.FLAG_FULLSCREEN ^ WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON ^ WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);


    setContentView(R.layout.surfaceplayer);
    
    // Create a new Media Player.
    mediaPlayer = new MediaPlayer();

    // Get a reference to the Surface View.
    final SurfaceView surfaceView =
      (SurfaceView)findViewById(R.id.surfaceVideoPlayer);

    // Configure the Surface View.
    surfaceView.setKeepScreenOn(true);

    // Configure the Surface Holder and register the callback.
    SurfaceHolder holder = surfaceView.getHolder();
    holder.addCallback(this);
    holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    holder.setFixedSize(400, 300);
    
     
    
  
  }



}