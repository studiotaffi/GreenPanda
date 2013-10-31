package it.taffi.videoframe;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class SurfaceVideoPlayer extends Activity implements
		SurfaceHolder.Callback {

	static final String TAG = "SurfaceVideoPlayer";

	private MediaPlayer mediaPlayer;

	private static MovieListAdapter movieListAdapter = new MovieListAdapter(
			VideoFrame.getVideoList());
	private Movie mymovie;

	private final static int videocount = movieListAdapter.getCount() - 1;

	private ArrayList<Integer> justSeen = new ArrayList<Integer>();

	// private final SurfaceView surfaceView =
	// (SurfaceView)findViewById(R.id.surfaceVideoPlayer);
	// private GLSurfaceView surfaceView;

	// private EffectContext mEffectContext;
	// private EffectFactory effectFactory;
	// private Effect mEffect;
	// private boolean mInitialized = false;
	// int mCurrentEffect;

	private void loadVideo() {

		int i = 0;
		Log.v(TAG, "videocount:" + videocount);

		if (videocount > 1) { // randomizzo video; escludo gli ultimi
								// videocount/2 recentemente visti.

			do {
				i = (int) (Math.random() * videocount);
				Log.v(TAG, "randomizzato: " + i);
			} while (justSeen.contains(i));

			// lastcall=i;

			justSeen.add(i);
			if (justSeen.size() > videocount / 2) {
				Log.v(TAG, "removed first element");
				justSeen.remove(0);
			}
		} 

		mymovie = (Movie) movieListAdapter.getItem(i);

		String mypath = mymovie.getMoviePath();
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
		Log.v(TAG, "playing video; duration:" + mediaPlayer.getDuration());
		// mVideoView.start();

		mediaPlayer.start();

	}

	public void surfaceCreated(SurfaceHolder holder) {

		// When the surface is created, assign it as the
		// display surface and assign and prepare a data
		// source.
		mediaPlayer.setDisplay(holder);
		// mediaPlayer.setDataSource("/sdcard/test2.3gp");

		// mEffect = Effect.createEffect(
		// EffectFactory.EFFECT_BRIGHTNESS;

		loadVideo();
		playVideo();

		mediaPlayer
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {

						loadVideo();
						playVideo();

					}
				});

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		mediaPlayer.release();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// effectFactory = mEffectContext.getFactory();
		// mEffect =
		// effectFactory.createEffect(EffectFactory.EFFECT_BLACKWHITE);

		Log.v(TAG, videocount + " videos found.");

		// Going full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN
						^ WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
						^ WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
				WindowManager.LayoutParams.FLAG_FULLSCREEN
						^ WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
						^ WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

		setContentView(R.layout.surfaceplayer);

		// Create a new Media Player.
		mediaPlayer = new MediaPlayer();

		// Get a reference to the Surface View.
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceVideoPlayer);

		// GLSurfaceView surfaceView =
		// (GLSurfaceView)findViewById(R.id.surfaceVideoPlayer);

		// Configure the Surface View.
		surfaceView.setKeepScreenOn(true);

		// provo a fare effetti
		// surfaceView.setEGLContextClientVersion(2);
		// surfaceView.setRenderer(this);
		// surfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		// mCurrentEffect = R.id.none;

		// Configure the Surface Holder and register the callback.
		SurfaceHolder holder = surfaceView.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		holder.setFixedSize(400, 300);

	}

}