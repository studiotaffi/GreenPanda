/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.taffi.greenpanda;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.android.http.PlayerInteraction;
import org.apache.android.media.R;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView; //ECCO QUA COME TI FACCIO UN PLAYER!




public class GreenPanda extends Activity {
	private static final String TAG = "GreenPanda";

	private VideoView mVideoView;  //!!!!
	private EditText mPath;
	private ImageButton mPlay;
	private ImageButton mPause;
	private ImageButton mReset;
	private ImageButton mStop;
	private String current;
	private EditText mChat;
	private Button mChatPost;
	private LinearLayout mChatFlow;
	
	private static Handler mHandler;
	
	private String remoteUrl="http://cloud.taffi.it/vink/xmlrpc.php";
	private XmlRpcClientConfigImpl config;
	
	
	

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);   //!!!
		mVideoView = (VideoView) findViewById(R.id.surface_view); //!!!

		mPath = (EditText) findViewById(R.id.path);
		mPath.setText("http://cloud.taffi.it/video/video4.mp4");  
		
		mPlay = (ImageButton) findViewById(R.id.play);
		mPause = (ImageButton) findViewById(R.id.pause);
		mReset = (ImageButton) findViewById(R.id.reset);
		mStop = (ImageButton) findViewById(R.id.stop);
		
		this.mChat = (EditText) findViewById(R.id.mChat);
		mChatPost = (Button) findViewById(R.id.mChatPost);
		this.mChatFlow = (LinearLayout) findViewById(R.id.mChatFlow);
		
		mChatPost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendMessage(mChat.getText().toString());
			}
		});

		mPlay.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				playVideo();
			}
		});
		
		mPause.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (mVideoView != null) {
					mVideoView.pause();
				}
			}
		});
		
		mReset.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (mVideoView != null) {
					mVideoView.seekTo(0);
				}
			}
		});
		
		mStop.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (mVideoView != null) {
					current = null;
					mVideoView.stopPlayback();
				}
			}
		});
		
		runOnUiThread(new Runnable(){   //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			public void run() {
				playVideo();
			}
		});
	}  //onCreate
	
	
	private void playVideo() {
		try {
			final String path = mPath.getText().toString();
			Log.v(TAG, "path: " + path);
			if (path == null || path.length() == 0) {
				Toast.makeText(GreenPanda.this, "File URL/path is empty",
						Toast.LENGTH_LONG).show();

			} else {
				// If the path has not changed, just start the media player
				if (path.equals(current) && mVideoView != null) {
					mVideoView.start();
					mVideoView.requestFocus();
					return;
				}
				current = path;
				mVideoView.setVideoPath(getDataSource(path));
				mVideoView.start();
				mVideoView.requestFocus();
				
				mHandler = new Handler() {
					public void handleMessage(Message msg) {
						Log.v("Hector", msg.obj.toString());
						TextView t = new TextView(GreenPanda.this);
						t.setText(msg.obj.toString());
						mChatFlow.addView(t, 0);						
					}
				};
				
				PlayerInteraction p= PlayerInteraction.getInstance(mHandler);
				Thread t = new Thread(p);
				t.start();


			}
		} catch (Exception e) {
			Log.e(TAG, "error: " + e.getMessage(), e);
			//if (mVideoView != null) {
				//mVideoView.stopPlayback();
			//}
		}
	}

	private String getDataSource(String path) throws IOException {
		if (!URLUtil.isNetworkUrl(path)) {
			return path;
		} else {
			URL url = new URL(path);
			URLConnection cn = url.openConnection();
			cn.connect();
			InputStream stream = cn.getInputStream();
			if (stream == null)
				throw new RuntimeException("stream is null");
			File temp = File.createTempFile("mediaplayertmp", "dat");
			temp.deleteOnExit();
			String tempPath = temp.getAbsolutePath();
			FileOutputStream out = new FileOutputStream(temp);
			byte buf[] = new byte[128];
			do {
				int numread = stream.read(buf);
				if (numread <= 0)
					break;
				out.write(buf, 0, numread);
			} while (true);
			try {
				stream.close();
			} catch (IOException ex) {
				Log.e(TAG, "error: " + ex.getMessage(), ex);
			}
			return tempPath;
		}
	}

	
	private void sendMessage(String message) {
		Log.v("GreenPanda.sendMessage","Message da mandare: " + message);
		Object[] params = new Object[3];
		params[0] = Integer.valueOf(6);
		params[1] = "CHAT";
		params[2] = message;
		
		try {
			this.constructConfig();
			
			XmlRpcClient xmlreq=new XmlRpcClient();
			Log.v("GreenPanda.sendMessage","Configurazione server: " + this.config);
			xmlreq.setConfig(this.config);
			
			Object result = xmlreq.execute("player.tabletinteraction_send", params);
			Log.v("GreenPanda.sendMessage", "result: " + result);
			this.mChat.getText().clear();
		} catch (Throwable e) {
			Log.v("GreenPanda.sendMessage", e.getLocalizedMessage());
			e.printStackTrace();
		}
		
	}
	
	private void constructConfig() throws MalformedURLException {
		this.config = new XmlRpcClientConfigImpl();
		Log.v("GreenPanda.constructConfig","Configuro url: " + this.remoteUrl);
		this.config.setServerURL(new URL(this.remoteUrl));
		this.config.setConnectionTimeout(1000);
	}
	
	
	
}
