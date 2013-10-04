package it.taffi.videoframe;



import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class VideoFrame extends Activity   {
	
	private Button mStart;
//	private VideoView mVideoView;  //Il player	
//	private EditText mPath; //il path del video in onda?


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome); 


//		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		 mStart = (Button) findViewById(R.id.start);

		

        mStart.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				

					Intent intent= new Intent(VideoFrame.this, MoviePlayerActivity.class);
					startActivity(intent);
			
				
				
			}
		});
//		final View controlsView = findViewById(R.id.fullscreen_content_controls);
//		final View contentView = findViewById(R.id.FullscreenActivity);
//		mVideoView = (VideoView) findViewById(R.id.surface_view); //!!!
//		mPath = (EditText) findViewById(R.id.path);
//		mPath.setText("http://cloud.taffi.it/video/video4.mp4");  
		

	
			//	Intent intent= new Intent(VideoFrame.this, MoviePlayerActivity.class);
			//	startActivity(intent);
		
		
/*	
	//Faccio partire il player in un thread!!
		runOnUiThread(new Runnable(){   //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			public void run() {
//				playVideo();
			}
		});
*/	
	
	}

		
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
/*	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
		
			return false;
		}
	};
*/

//ecco il player!	
	/*
	private void playVideo() {
		
		try {
			final String path = mPath.getText().toString();
	//		Log.v(TAG, "path: " + path);
//		if (path == null || path.length() == 0) {
	//			Toast.makeText(GreenPanda.this, "File URL/path is empty",
   //						Toast.LENGTH_LONG).show();

	//		} else {
				// If the path has not changed, just start the media player
//				if (path.equals(current) && mVideoView != null) {
	//				mVideoView.start();
	//				mVideoView.requestFocus();
	//				return;
//				}
	//			current = path;
				mVideoView.setVideoPath(path);
				mVideoView.start();
				mVideoView.requestFocus();
				
//				mHandler = new Handler() {
//					public void handleMessage(Message msg) {
//						Log.v("Hector", msg.obj.toString());
//						TextView t = new TextView(GreenPanda.this);
//						t.setText(msg.obj.toString());
//						mChatFlow.addView(t, 0);						
//					}
//				};
				

	//		}
		} catch (Exception e) {
//			Log.e(TAG, "error: " + e.getMessage(), e);
			if (mVideoView != null) {
				mVideoView.stopPlayback();
			}
		}
	}
*/

}


	
